package org.template.cloud.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.cloud.bean.module.Transcript;
import org.template.cloud.bean.module.User;
import org.template.cloud.service.ServiceApplication;
import org.template.cloud.service.service.mongo.TranscriptService;
import org.template.cloud.service.service.mysql.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class MongoTest {


    @Value("test")
    int test;

    @Autowired
    TranscriptService transcriptService;
    @Autowired
    UserService userService;

    /**
     * 批量插入数据
     */
    public void insertTest() {
        List<User> users = userService.findAll();
        List<Transcript> transcripts = new ArrayList<Transcript>();
        Transcript transcript;
        Random random = new Random();
        Calendar cl = Calendar.getInstance();
        for (int i = 0; i < 100; i++) {
            for (User user : users) {
                transcript = new Transcript();
                transcript.setDate(cl.getTime());
                transcript.setUserId(user.getId());
                transcript.setScore(random.nextInt(100));
                transcripts.add(transcript);
            }
            cl.add(Calendar.MONTH, -1);
        }
        transcriptService.insert(transcripts);
    }

    @Test
    public void select_1() {
        System.out.println(test);
        User user = userService.findByName("No1");
        Calendar cl = Calendar.getInstance();
        Date end = cl.getTime();
        cl.add(Calendar.MONTH, -4);
        Date start = cl.getTime();
        List<Transcript> transcripts = transcriptService.findByUserIdAndDateBetween(user.getId(), start, end);
        for (Transcript t : transcripts)
            System.out.println(user.getUserName() + " date:" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                            format(t.getDate()) + " score:" + t.getScore());
    }


}
