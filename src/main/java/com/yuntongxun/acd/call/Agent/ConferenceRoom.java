package com.yuntongxun.acd.call.Agent;

import com.yuntongxun.acd.queue.bean.Customer;

public class ConferenceRoom {

    private Agent agent;

    private Customer customer;

    public ConferenceRoom() {
    }

    public ConferenceRoom(Agent agent, Customer customer) {
        this.agent = agent;
        this.customer = customer;
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
