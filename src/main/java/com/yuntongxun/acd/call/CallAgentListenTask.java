package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;

public class CallAgentListenTask implements Runnable {

    // 秒级等待时间
    private int timeout;
    private ConferenceRoom conferenceRoom;
    private boolean isResponse;
    private boolean isInValidEnd = false;
    private ResponseCallBack responseCallBack;


    public CallAgentListenTask(int timeout, ConferenceRoom conferenceRoom, ResponseCallBack responseCallBack) {
        this.timeout = timeout;
        this.conferenceRoom = conferenceRoom;
        this.responseCallBack = responseCallBack;
    }

    public void setResponse(boolean response) {
        isResponse = response;
    }

    public void setInValidEnd(boolean inValidEnd) {
        isInValidEnd = inValidEnd;
    }

    public ConferenceRoom getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(ConferenceRoom conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    @Override
    public void run() {
        if (isInValidEnd) {
            return;
        }
        String threadName = "CallAgentListenTask[" + conferenceRoom.getCustomer().getIndex() + "-" + conferenceRoom.getAgent().getAgentId() + "]";

        Thread.currentThread().setName(threadName);
        try {
            if (isInValidEnd) {
                return;
            } else {
                if (responseCallBack != null) {
                    if (!isResponse) {
                        responseCallBack.notresponsed(conferenceRoom);
                    } else {
                        responseCallBack.responsed(conferenceRoom);
                    }
                }
            }

            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!isInValidEnd) {
            if (responseCallBack != null) {
                if (!isResponse) {
                    responseCallBack.notresponsed(conferenceRoom);
                } else {
                    responseCallBack.responsed(conferenceRoom);
                }
            }
        }
    }

    public interface ResponseCallBack {
        void responsed(ConferenceRoom conferenceRoom);
        void notresponsed(ConferenceRoom conferenceRoom);
    }
}
