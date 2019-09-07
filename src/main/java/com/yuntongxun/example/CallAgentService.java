package com.yuntongxun.example;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.call.CallResult;
import com.yuntongxun.acd.queue.bean.Customer;
import com.yuntongxun.acd.queue.notification.QueueNotification;
import com.yuntongxun.acd.AbstractCallAgentService;

import java.util.*;

public class CallAgentService extends AbstractCallAgentService {

    public CallAgentService(List<Agent> agents) {
        super(agents);
    }

    @Override
    public CallResult callAgent(Customer customer, Agent agent) {
        System.out.println("customer" + customer + " calling " + agent);

//        int a = new Random().nextInt(10);
////        int a = 3;
//        try {
//            Thread.sleep(a * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        CallResult.Builder builder = new CallResult.Builder();

//        throw new RuntimeException(" unknown exception ");

//        if (a % 3 != 0) {
//            agree(customer.index());
////            builder.callDate(new Date()).success().build();
//        } else {
//            reject(customer.index());
////            builder.callDate(new Date()).failed(agent + " reject...").build();
//        }
//        agree(customer.index());
        callFinish(customer, agent);

        return builder.callDate(new Date()).failed().build();
//        return null;

    }

    @Override
    public void agreeCall(Customer customer, Agent agent) {
            System.out.println("agent : " + agent + " agree call from " + customer);
    }

    @Override
    public void rejectCall(Customer customer, Agent agent) {
            System.out.println("agent : " + agent + " reject call from " + customer);
    }

    @Override
    public void sendNotification(QueueNotification queueNotification) {
//        if (queueNotification.getLineStatus() == 0) {
//            System.out.println(" queue has changed Customer : " + ((Customer)queueNotification.getLineElement()).index() + " precount : " + queueNotification.getPreCount());
//        } else {
//            System.out.println(" customer : " + ((Customer)queueNotification.getLineElement()).index() + " has distributed agent: " + queueNotification.getDistributedAgent().getAgentId());
//        }
    }
}
