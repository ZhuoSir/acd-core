package com.yuntongxun.acd.distribution;

import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

public interface AgentDistributeProxy {

    ConferenceRoom distributeConference(Customer customer);

}
