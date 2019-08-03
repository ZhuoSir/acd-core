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

    private List<CallFailedDetail> failedList = new ArrayList<>();

    private CallAgentResultHandle lastOneCallAgentResultHandle;

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
        if (lastOneCallAgentResultHandle != null) {
            lastOneCallAgentResultHandle.callSuccess(conferenceRoom);
        }
    }

    @Override
    public void callFailed(ConferenceRoom conferenceRoom) {
        conferenceRoom.setCallStatus(ConferenceRoom.CALLFAILED);
        failedList.add(new CallFailedDetail(conferenceRoom));
        callAgentTaskPools.remove(conferenceRoom.getCustomer().index());
        if (lastOneCallAgentResultHandle != null) {
            lastOneCallAgentResultHandle.callFailed(conferenceRoom);
        }
    }

    public List<CallFailedDetail> getFailedList() {
        return failedList;
    }

    public void setLastOneCallAgentResultHandle(CallAgentResultHandle lastOneHandle) {
        this.lastOneCallAgentResultHandle = lastOneHandle;
    }
}
