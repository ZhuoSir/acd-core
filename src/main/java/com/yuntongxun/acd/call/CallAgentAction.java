package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentAction {

    CallResult callAgent(Customer customer, Agent agent);

    void callFinish(Customer customer, Agent agent);

    void callCancel(Agent agent);
}
