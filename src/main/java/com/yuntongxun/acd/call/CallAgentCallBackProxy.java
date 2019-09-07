package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.Agent;
import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentCallBackProxy {

    void agreeCall(Customer customer, Agent agent);

    void rejectCall(Customer customer, Agent agent);
}
