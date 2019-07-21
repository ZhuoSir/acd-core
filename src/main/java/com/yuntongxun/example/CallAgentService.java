package com.yuntongxun.example;

import com.yuntongxun.acd.call.AbstractCallAgentProxy;
import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.CallAgentCallBackProxy;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.List;

public class CallAgentService extends AbstractCallAgentProxy implements CallAgentCallBackProxy {


    public CallAgentService(List<Agent> agents) {
        super(agents);
    }

    @Override
    public boolean callAgent(Customer customer, Agent agent) {
        System.out.println("customer" + customer + " calling " + agent);
        return true;
    }

    public void agree(String agenitId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        callAgentManager.agentAgreeCall(agenitId);
    }

    public void reject(String agentId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        callAgentManager.agentRejectCall(agentId);
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
