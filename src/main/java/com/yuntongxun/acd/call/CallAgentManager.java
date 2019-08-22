package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CallAgentManager implements CallAgentResultHandle, Thread.UncaughtExceptionHandler {

    private ExecutorService callTaskPools = Executors.newFixedThreadPool(20);
    private Map<String, CallAgentTask> callAgentTaskPools = new ConcurrentHashMap<>();

    private List<CallFailedDetail> failedList = new ArrayList<>();

    private CallAgentResultHandle lastOneCallAgentResultHandle;

    private AgentManager agentManager;

    public String createCallTask(ConferenceRoom conferenceRoom, CallAgentAction callAgentAction, CallAgentCallBack callAgentCallBack) {
        CallAgentTask callAgentTask = new CallAgentTask(conferenceRoom, callAgentAction, callAgentCallBack, this);
        callAgentTaskPools.put(callAgentTask.getCustomerId(), callAgentTask);

        callAgentTask.setUncaughtExceptionHandler(this);
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
        if (agentManager != null)
            agentManager.dismissConferenceRoom(conferenceRoom);
        if (lastOneCallAgentResultHandle != null) {
            lastOneCallAgentResultHandle.callFailed(conferenceRoom);
        }
    }

    @Override
    public void callError(ConferenceRoom conferenceRoom, Exception e) {
        conferenceRoom.setCallStatus(ConferenceRoom.CALLERROR);
        failedList.add(new CallFailedDetail(conferenceRoom, e));
        callAgentTaskPools.remove(conferenceRoom.getCustomer().index());
        if (agentManager != null)
            agentManager.dismissConferenceRoom(conferenceRoom);
        if (lastOneCallAgentResultHandle != null) {
            lastOneCallAgentResultHandle.callError(conferenceRoom, e);
        }
    }

    public List<CallFailedDetail> getFailedList() {
        return failedList;
    }

    public void setLastOneCallAgentResultHandle(CallAgentResultHandle lastOneHandle) {
        this.lastOneCallAgentResultHandle = lastOneHandle;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof CallAgentException) {
            e.printStackTrace();
        }
    }

    public void setAgentManager(AgentManager agentManager) {
        this.agentManager = agentManager;
    }
}
