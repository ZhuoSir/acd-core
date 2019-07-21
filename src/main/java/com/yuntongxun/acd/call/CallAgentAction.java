package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentAction {

    boolean callAgent(Customer customer, Agent agent);

    void callFinish(Agent agent);

    void callCancel(Customer customer, Agent agent);
}
