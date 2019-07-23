package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallAgentManager implements CallAgentResultHandle {

    private ExecutorService callTaskPools = Executors.newFixedThreadPool(20);
    private Map<String, CallAgentTask> callAgentTaskPools = new ConcurrentHashMap<>();

    private List<ConferenceRoom> failedList = new ArrayList<>();

    public String createCallTask(ConferenceRoom conferenceRoom, CallAgentAction callAgentAction, CallAgentCallBack callAgentCallBack) {
        CallAgentTask callAgentTask = new CallAgentTask(conferenceRoom, callAgentAction, callAgentCallBack, this);
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
        }
    }

    public void agentRejectCall(String customerId) {
        if (customerId == null) return;
        CallAgentTask task = callAgentTaskPools.remove(customerId);
        if (task != null) {
            CallAgentCallBack callAgentCallBack = task.getCallAgentCallBack();
            callAgentCallBack.rejectCall(task.getConferenceRoom());
        }
    }

    @Override
    public void callSuccess(ConferenceRoom conferenceRoom) {
        conferenceRoom.setCallStatus(ConferenceRoom.CALLSUCCESS);
    }

    @Override
    public void callFailed(ConferenceRoom conferenceRoom) {
        conferenceRoom.setCallStatus(ConferenceRoom.CALLFAILED);
        failedList.add(conferenceRoom);
        callAgentTaskPools.remove(conferenceRoom.getCustomer().getId());
    }

    public List<ConferenceRoom> getFailedList() {
        return failedList;
    }
}
