package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentAction {

    CallResult callAgent(Customer customer, Agent agent);

    void callFinish(Customer customer, Agent agent);

    void callCancel(Agent agent);
}
