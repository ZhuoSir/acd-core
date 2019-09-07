package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;

import java.util.Date;

public class CallAgentTask implements Runnable {

    private ConferenceRoom conferenceRoom;
    private CallAgentAction callAgentAction;
    private CallAgentCallBack callAgentCallBack;
    private CallAgentResultHandle callAgentResultHandle;

    private Date taskStartTime;
    private Date taskEndTime;

    // 0 失败 1 成功 -1 未执行
    private int status = NONEXECUTED;
    public static int SUCCESS = 1;
    public static int FAILED = 0;
    public static int NONEXECUTED = -1;
    public static int EXECUEDERROR = -2;

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public CallAgentTask(ConferenceRoom conferenceRoom, CallAgentAction callAgentAction, CallAgentCallBack callAgentCallBack, CallAgentResultHandle callAgentResultHandle) {
        this.conferenceRoom = conferenceRoom;
        this.callAgentAction = callAgentAction;
        this.callAgentCallBack = callAgentCallBack;
        this.callAgentResultHandle = callAgentResultHandle;
    }

    public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler) {
        uncaughtExceptionHandler = exceptionHandler;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    public CallAgentAction getCallAgentAction() {
        return callAgentAction;
    }

    public void setCallAgentAction(CallAgentAction callAgentAction) {
        this.callAgentAction = callAgentAction;
    }

    public CallAgentCallBack getCallAgentCallBack() {
        return callAgentCallBack;
    }

    public void setCallAgentCallBack(CallAgentCallBack callAgentCallBack) {
        this.callAgentCallBack = callAgentCallBack;
    }

    public String getAgentId() {
        return this.conferenceRoom.getAgent().getAgentId();
    }

    public String getCustomerId() {
        return this.conferenceRoom.getCustomer().index();
    }

    public int getStatus() {
        return status;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    @Override
    public void run() {
        Thread.currentThread().setUncaughtExceptionHandler(uncaughtExceptionHandler);
        Thread.currentThread().setName("CallAgentTask[" + conferenceRoom.getCustomer().index() + "," + conferenceRoom.getAgent().getAgentId() + "]");

        taskStartTime = new Date();
        conferenceRoom.setCallDate(taskStartTime);
        CallResult callResult = null;
        try {
            callResult = callAgentAction.callAgent(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
        } catch (Exception e) {
            status = EXECUEDERROR;
            callAgentResultHandle.callError(conferenceRoom, e);
        }

        conferenceRoom.setCallResult(callResult);
        if (callResult == null) {
            throw new CallAgentException(" CallAgent method return reference can not be NULL,  call failed otherwise");
        }

        if (callResult.isSuccess()) {
            callAgentResultHandle.callSuccess(conferenceRoom);
            status = SUCCESS;
        } else {
            callAgentResultHandle.callFailed(conferenceRoom);
            status = FAILED;
        }
        taskEndTime = new Date();

    }
}
