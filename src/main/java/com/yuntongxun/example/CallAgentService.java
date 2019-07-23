package com.yuntongxun.example;

import com.yuntongxun.acd.call.AbstractCallAgentProxy;
import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.call.CallResult;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CallAgentService extends AbstractCallAgentProxy implements CallAgentCallBackProxy {

    public CallAgentService(List<Agent> agents) {
        super(agents);
    }

    @Override
    public CallResult callAgent(Customer customer, Agent agent) {
        System.out.println("customer" + customer + " calling " + agent);

        int a = new Random().nextInt(10);
//        try {
//            Thread.sleep(a * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        CallResult.Builder builder = new CallResult.Builder();

        if (a % 3 != 0) {
            agree(customer.getId());
//            builder.callDate(new Date()).success().build();
        } else {
            reject(customer.getId());
//            builder.callDate(new Date()).failed(agent + " reject...").build();
        }
//        agree(customer.getId());
        callFinish(agent);

        return builder.callDate(new Date()).success().build();
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
