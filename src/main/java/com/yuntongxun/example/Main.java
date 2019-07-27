package com.yuntongxun.example;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.AcdQueue;
import com.yuntongxun.acd.queue.CustomerQueueManager;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.bean.QueueInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AcdQueue acdQueue = new AcdQueue();
        CustomerQueueManager customerQueueManager = new CustomerQueueManager();
        customerQueueManager.setAcdQueue(acdQueue);
        customerQueueManager.notifySwitchOn();

        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Agent agent = new Agent(i);
            agents.add(agent);
        }
        CallAgentService callAgentService = new CallAgentService(agents);

        customerQueueManager.setCallAgentProxy(callAgentService);
        customerQueueManager.setCallAgentCallBackProxy(callAgentService);
        customerQueueManager.setQueueNotifyProxy(callAgentService);

        customerQueueManager.start();

        for (int i = 0; i < 10; i++) {
//            Thread.sleep(1000);
//            if (i % 5 == 0) {
//                customerQueueManager.linePriority(new Customer(i));
//            } else {
//                customerQueueManager.line(new Customer(i));
//            }
            QueueInfo queueInfo = customerQueueManager.line(new Customer(i));
        }

//        customerQueueManager.start();

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        int i = 0;
//        while (true) {
//            for (Agent agent : agents) {
//                if (agent.getStatus() == 1) {
////                    Thread.sleep(1000);
//                    int a = new Random().nextInt(10);
////                    if (a%3!=0) {
////                        callAgentService.agree(agent.getAgentId());
////                    } else {
////                        callAgentService.reject(agent.getAgentId());
////                    }
//                    i++;
//                    callAgentService.agree(agent.getAgentId());
//                    agent.setStatus(0);
//                    callAgentService.callFinish(agent);
//                                    System.out.println(acdQueue.detail());
//                    System.out.println(" main i :" + i);
//                }
//            }
//        }

//        int i = 0;
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
////            Iterator<String> iterator = callAgentService.agentHashMap.keySet().iterator();
////            while (iterator.hasNext()) {
////                String customerId = iterator.next();
////                Agent agent = callAgentService.agentHashMap.get(customerId);
////                int a = new Random().nextInt(10);
//////                    if (a%3!=0) {
//////                        callAgentService.agree(agent.getAgentId());
//////                    } else {
//////                        callAgentService.reject(agent.getAgentId());
//////                    }
////                callAgentService.agree(customerId);
////                callAgentService.callFinish(agent);
////                System.out.println(acdQueue.detail());
////                System.out.println(" main i :" + i);
////            }
//                    List<String> customerIds = callAgentService.customerIds;
//                    for (int j = 0; j < customerIds.size(); j++) {
//                        String customerId = customerIds.get(j);
//                        Agent agent = callAgentService.agentHashMap.get(customerId);
//                        callAgentService.agree(customerId);
//                        callAgentService.callFinish(agent);
//                        customerIds.remove(0);
//                        System.out.println(" main i :" + i);
//                    }
//                }
//            }
//        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
