package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.Agent;
import com.yuntongxun.acd.call.Agent.ConferenceRoom;
import com.yuntongxun.acd.queue.bean.Customer;

import java.util.Date;

public class CallFailedDetail {

    private Customer customer;
    private Agent agent;
    private String failedReason;
    private Exception exception;
    private Date callDate;

    public CallFailedDetail() {
    }

    public CallFailedDetail(ConferenceRoom room) {
        setAgent(room.getAgent());
        setCallDate(room.getCallResult().getCallDate());
        setCustomer(room.getCustomer());
        setException(room.getCallResult().getException());
        setFailedReason(room.getCallResult().getFailedReason());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }
}
