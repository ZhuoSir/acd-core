package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

public interface CallAgentProxy {

    ConferenceRoom call(Customer customer, CallAgentCallBack callAgentCallBack);

    void callCancel(ConferenceRoom conferenceRoom);
}
