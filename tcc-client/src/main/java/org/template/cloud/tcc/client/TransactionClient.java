package org.template.cloud.tcc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class TransactionClient {
    static TransactionClient transactionClient = new TransactionClient();

    private TransactionClient() {

    }

    public static synchronized TransactionClient getInstance(String name, final String host, final Integer port) {
        if (TransactionClient.name != null)
            throw new RuntimeException("重复初始化");
        TransactionClient.name = name;
        TransactionClient.host = host;
        TransactionClient.port = port;
        transactionClient.init();
        return transactionClient;
    }

    static volatile Map<String, InformMessage> messageMap = new ConcurrentHashMap<>();
    static final Charset charset = Charset.forName("utf-8");
    static String name;
    final EventLoopGroup group = new NioEventLoopGroup((Runtime.getRuntime().availableProcessors() / 3));
    ChannelFuture channelFuture;
    Channel channel;
    static String host;
    static Integer port;
    Bootstrap client;

    void writeMessage(InformMessage message) {
        if (channel != null && !channel.isActive()) {
            messageMap.put(message.getId(), message);
            channel.writeAndFlush(message);
        } else {
            throw new RuntimeException("连接中断");
        }
    }

    void init() {
        final ChannelInitializer channelInitializer = new ChannelInitializer<SocketChannel>() {
            public void initChannel(SocketChannel channel)
                    throws Exception {
                channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(16 * 1024, 0, 4, 0, 4))
                        .addLast(new MessageDecoder())
                        .addLast("idleStateHandler", new IdleStateHandler(60 * 1000, 60 * 1000, 60 * 1000))
                        .addLast(new MessageEncoder())
                        .addLast(new MessageHandler());
            }
        };
        Executors.newSingleThreadExecutor().submit(
                new Callable<Object>() {
                    public Object call() throws Exception {
                        try {
                            client = new Bootstrap();
                            client.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                                    .option(ChannelOption.SO_KEEPALIVE, true)
                                    .option(ChannelOption.SO_SNDBUF, 128 * 1024)
                                    .option(ChannelOption.SO_SNDBUF, 128 * 1024)
                                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                                    .handler(new LoggingHandler(LogLevel.INFO))
                                    .handler(channelInitializer);
                            channelFuture = client.connect(host, port).sync();
                            channel = channelFuture.channel();
                            channelFuture.channel().closeFuture().sync();
                        } catch (Exception e) {
                            group.shutdownGracefully().sync();
                            throw new RuntimeException(e);
                        } finally {
                            reConnect();
                        }
                        return null;
                    }
                }
        );


    }

    private void reConnect() throws InterruptedException {
        try {
            channelFuture = client.connect(host, port).sync();
            channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            TimeUnit.MILLISECONDS.sleep(5000);
            reConnect();
            throw new RuntimeException(e);
        }
    }

    class MessageDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext cxt, ByteBuf buf, List<Object> list) throws Exception {
            try {
                InformMessage message = new InformMessage();
                message.setType(buf.readByte() & 0xFF);
                int len = buf.readByte() & 0xFF;
                byte[] bytes;
                if (len > 0) {
                    bytes = new byte[len];
                    buf.readBytes(bytes);
                    message.setId(new String(bytes, charset));
                }
                if (len > 0) {
                    bytes = new byte[len];
                    buf.readBytes(bytes);
                    message.setOperateId(new String(bytes, charset));
                }
                len = buf.readByte() & 0xFF;
                if (len > 0) {
                    bytes = new byte[len];
                    buf.readBytes(bytes);
                    message.setService(new String(bytes, charset));
                }
                len = buf.readByte() & 0xFF;
                if (len > 0) {
                    bytes = new byte[len];
                    buf.readBytes(bytes);
                    message.setMethod(new String(bytes, charset));
                }
                len = buf.readShort() & 0xFFFF;
                if (len > 0) {
                    bytes = new byte[len];
                    buf.readBytes(bytes);
                    message.setParams(new String(bytes, charset));
                }
                list.add(message);
            } catch (Exception e) {
                cxt.close();
                throw new RuntimeException("消息解析异常,通道关闭", e);
            }
        }
    }

    class MessageEncoder extends MessageToByteEncoder<Object> {
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf out) throws Exception {
            if (o instanceof InformMessage) {
                InformMessage message = (InformMessage) o;
                ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer();
                buf.writeByte(message.getType());
                byte[] bytes;
                if (message.getId() != null) {
                    bytes = message.getId().getBytes(charset);
                    buf.writeByte(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeByte(0);
                }
                if (message.getOperateId() != null) {
                    bytes = message.getOperateId().getBytes(charset);
                    buf.writeByte(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeByte(0);
                }
                if (message.getService() != null) {
                    bytes = message.getService().getBytes(charset);
                    buf.writeByte(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeByte(0);
                }
                if (message.getMethod() != null) {
                    bytes = message.getMethod().getBytes(charset);
                    buf.writeByte(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeByte(0);
                }
                if (message.getParams() != null) {
                    bytes = message.getParams().getBytes(charset);
                    buf.writeShort(bytes.length);
                    buf.writeBytes(bytes);
                } else {
                    buf.writeByte(0);
                }
                ByteBuf re = PooledByteBufAllocator.DEFAULT.buffer();
                re.writeInt(buf.readableBytes());
                re.writeBytes(buf);
                out.writeBytes(re);
            }
        }
    }

    class MessageHandler extends ChannelInboundHandlerAdapter {


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            /**
             * 连接丢失时所有事务全部失败
             */
            ConditionLock.closeAll();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            InformMessage message = (InformMessage) msg;
            if (message.getType() == InformMessage.ACK) {
                messageMap.remove(message.getParams());
            } else {
                writeMessage(new InformMessage(InformMessage.ACK, message.getOperateId(), null, null, message.getParams()));
                switch (message.getType()) {
                    case InformMessage.EXECUTE:
                        handle(message);
                        break;
                    case InformMessage.COMMIT:
                        ConditionLock.release(message.getOperateId(), true);
                        break;
                    case InformMessage.ROLLBACK:
                        ConditionLock.release(message.getOperateId(), false);
                        break;
                }
            }

        }


        void handle(InformMessage message) {
            try {
                Object service = SpringUtil.getBean(message.getService());
                if (service != null) {
                    Method execute = service.getClass().getDeclaredMethod(message.getMethod());
                    if (execute != null) {
                        Transactional t = service.getClass().getAnnotation(Transactional.class);
                        if (t == null)
                            throw new RuntimeException(message.getService() + ": 未配置事务管理器");
                        PlatformTransactionManager manager = null;
                        if ("".equals(t.value())) {
                            manager = (PlatformTransactionManager) new ArrayList<>(SpringUtil.getApplicationContext()
                                    .getBeansWithAnnotation(Transactional.class).values()).get(0);
                        } else
                            manager = (PlatformTransactionManager) SpringUtil.getBean(t.value());

                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                        TransactionStatus status = manager.getTransaction(def);
                        if (message.getParams() != null && !"".equals(message.getParams())) {
                            JSONArray paramArray = JSON.parseArray(message.getParams());
                            int m = paramArray.size();
                            Object[] params = new Object[paramArray.size()];
                            Class[] classes = execute.getParameterTypes();
                            for (int i = 0; i < m; i++) {
                                params[i] = paramArray.getObject(i, classes[i]);
                            }
                            execute.invoke(service, params);
                        } else {
                            execute.invoke(service);
                        }
                        if (ConditionLock.await(message.getOperateId())) {
                            writeMessage(new InformMessage(InformMessage.EXECUTE_SUCCESS, message.getOperateId(), null, null, null));
                            manager.commit(status);
                        } else {
                            writeMessage(new InformMessage(InformMessage.EXECUTE_FAIL, message.getOperateId(), null, null, null));
                            manager.rollback(status);
                        }
                    } else {
                        throw new RuntimeException(message.getService() + ": 无此方法:" + message.getMethod());
                    }
                } else {
                    throw new RuntimeException(message.getService() + ": 未找到bean");
                }
            } catch (Exception e) {
                writeMessage(new InformMessage(InformMessage.EXCEPTION, message.getOperateId(), null, null, e.getLocalizedMessage()));
            }
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.ALL_IDLE) {
                    /**
                     * 读写超时关闭连接:超时时间60秒
                     */
                    ctx.close();
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }
}