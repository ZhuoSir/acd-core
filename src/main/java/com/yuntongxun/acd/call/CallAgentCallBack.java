package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

public interface CallAgentCallBack {

    void agreeCall(ConferenceRoom conferenceRoom);

    void rejectCall(ConferenceRoom conferenceRoom);
}
