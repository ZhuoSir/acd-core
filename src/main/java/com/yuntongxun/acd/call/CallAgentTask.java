package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

import java.util.Date;

public class CallAgentTask implements Runnable {

    private ConferenceRoom conferenceRoom;
    private CallAgentAction callAgentAction;
    private CallAgentCallBack callAgentCallBack;

    private Date taskStartTime;
    private Date taskEndTime;

    // 0 失败 1 成功 -1 未执行
    private int status = NONEXECUTED;
    public static int SUCCESS = 1;
    public static int FAILED = 0;
    public static int NONEXECUTED = -1;

    public CallAgentTask(ConferenceRoom conferenceRoom, CallAgentAction callAgentAction, CallAgentCallBack callAgentCallBack) {
        this.conferenceRoom = conferenceRoom;
        this.callAgentAction = callAgentAction;
        this.callAgentCallBack = callAgentCallBack;
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
        taskStartTime = new Date();
        boolean r = callAgentAction.callAgent(conferenceRoom.getCustomer(), conferenceRoom.getAgent());
//        if (r) {
//            callAgentCallBack.callSuccess(conferenceRoom);
//            status = SUCCESS;
//        } else {
//            callAgentCallBack.callFail(conferenceRoom);
//            status = FAILED;
//        }
        taskEndTime = new Date();
    }
}