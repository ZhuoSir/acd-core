package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallAgentManager {

    private ExecutorService callTaskPools = Executors.newFixedThreadPool(20);
    private Map<String, CallAgentTask> callAgentTaskPools = new ConcurrentHashMap<>();

    public String createCallTask(ConferenceRoom conferenceRoom, CallAgentAction callAgentAction, CallAgentCallBack callAgentCallBack) {
        CallAgentTask callAgentTask = new CallAgentTask(conferenceRoom, callAgentAction, callAgentCallBack);
        callAgentTaskPools.put(callAgentTask.getCustomerId(), callAgentTask);

        callTaskPools.submit(callAgentTask);
        return callAgentTask.getAgentId();
    }

    public void agentAgreeCall(String customerId) {
        if (customerId == null) return;
        CallAgentTask task = callAgentTaskPools.remove(customerId);
        if (task != null) {
            CallAgentCallBack callAgentCallBack = task.getCallAgentCallBack();
            callAgentCallBack.agreeCall(task.getConferenceRoom());
        } else {
            System.out.println(" 没有CallAgentTask... ");
        }
    }

    public void agentRejectCall(String customerId) {
        if (customerId == null) return;
        CallAgentTask task = callAgentTaskPools.remove(customerId);
        if (task != null) {
            CallAgentCallBack callAgentCallBack = task.getCallAgentCallBack();
            callAgentCallBack.rejectCall(task.getConferenceRoom());
        } else {
            System.out.println(" 没有CallAgentTask... ");
        }
    }
}
