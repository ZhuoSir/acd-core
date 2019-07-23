package com.yuntongxun.acd.call.Agent;

import com.yuntongxun.acd.queue.bean.Customer;

public class ConferenceRoom {

    private Agent agent;
    private Customer customer;

    public static final int CALLSUCCESS = 1;
    public static final int CALLFAILED = -1;

    // 1 call成功 0 call 失败
    private int callStatus;

    public ConferenceRoom() {
    }

    public ConferenceRoom(Agent agent, Customer customer) {
        this.agent = agent;
        this.customer = customer;
    }

    public void setCallStatus(int callStatus) {
        this.callStatus = callStatus;
    }

    public int getCallStatus() {
        return callStatus;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
