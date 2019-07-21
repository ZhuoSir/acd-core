package com.yuntongxun.example;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.AcdQueue;
import com.yuntongxun.acd.queue.CustomerQueueManager;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        AcdQueue acdQueue = new AcdQueue();
        CustomerQueueManager customerQueueManager = new CustomerQueueManager();
        customerQueueManager.setAcdQueue(acdQueue);
        customerQueueManager.notifySwitchOff();

        List<Agent> agents = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Agent agent = new Agent(i);
            agents.add(agent);
        }
        CallAgentService callAgentService = new CallAgentService(agents);

        customerQueueManager.setCallAgentProxy(callAgentService);
        customerQueueManager.setCallAgentCallBackProxy(callAgentService);

        for (int i = 0; i < 1000; i++) {
            customerQueueManager.line(new Customer(i));
        }

        customerQueueManager.start();

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        while (true) {
            for (Agent agent : agents) {
                if (agent.getStatus() == 1) {
//                    Thread.sleep(2000);
                    int a = new Random().nextInt(10);
                    if (a%3!=0) {
                        callAgentService.agree(agent.getAgentId());
                    } else {
                        callAgentService.reject(agent.getAgentId());
                    }
//                    callAgentService.reject(agent.getAgentId());
                    agent.setStatus(0);
                    callAgentService.callFinish(agent);
                                    System.out.println(acdQueue.detail());

                }
            }
        }

//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
