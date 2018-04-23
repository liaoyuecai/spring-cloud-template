package org.template.cloud.tcc.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class ConditionLock {
    private static Lock lock = new ReentrantLock();
    private static Map<String, Task> conditionMap = new ConcurrentHashMap();
    private static Map<String, AsynchronousData> sysMap = new ConcurrentHashMap();


    static boolean await(String key) throws Exception {
        if (sysMap.containsKey(key)) {
            AsynchronousData data = sysMap.get(key);
            sysMap.remove(key);
            return data.result;
        }
        try {
            if (conditionMap.containsKey(key)) {
                throw new TransactionException("This task is exist");
            }
            Condition con = lock.newCondition();
            lock.lock();
            Task task = new Task(con);
            conditionMap.put(key, task);
            con.await();
            return task.result;
        } catch (Exception e) {
            throw e;
        } finally {
            conditionMap.remove(key);
            lock.unlock();
        }
    }

    static void release(String key, boolean result) {
        if (conditionMap.containsKey(key)) {
            try {
                Condition con = conditionMap.get(key).con;
                conditionMap.get(key).result = result;
                lock.lock();
                con.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            sysMap.put(key, new AsynchronousData(result));
        }
    }

    static void closeAll() {
        for (String key : conditionMap.keySet()) {
            release(key, false);
        }
        for (String key : sysMap.keySet()) {
            release(key, false);
        }
        conditionMap.clear();
        sysMap.clear();
    }

    static class Task {
        private Condition con;
        private boolean result;

        public Task(Condition con) {
            this.con = con;
        }
    }

    static class AsynchronousData {
        private boolean result;

        public AsynchronousData(boolean result) {
            this.result = result;
        }
    }
}