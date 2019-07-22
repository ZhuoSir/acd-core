package com.yuntongxun.example;

import com.yuntongxun.acd.call.AbstractCallAgentProxy;
import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.*;

public class CallAgentService extends AbstractCallAgentProxy implements CallAgentCallBackProxy {

    public CallAgentService(List<Agent> agents) {
        super(agents);
    }

    @Override
    public boolean callAgent(Customer customer, Agent agent) {
        System.out.println("customer" + customer + " calling " + agent);

        int a = new Random().nextInt(10);
        if (a % 3 != 0) {
            agree(customer.getId());
        } else {
            reject(customer.getId());
        }
//        agree(customer.getId());
        callFinish(agent);
        return true;
    }

    public void agree(String customerId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        callAgentManager.agentAgreeCall(customerId);
    }

    public void reject(String customerId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        callAgentManager.agentRejectCall(customerId);
    }

    @Override
    public void agreeCall(Customer customer, Agent agent) {
            System.out.println("agent : " + agent + " agree call from " + customer);
    }

    @Override
    public void rejectCall(Customer customer, Agent agent) {
            System.out.println("agent : " + agent + " reject call from " + customer);
    }
}
