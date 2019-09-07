package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;

public interface CallAgentCallBack {

    void agreeCall(ConferenceRoom conferenceRoom);

    void rejectCall(ConferenceRoom conferenceRoom);
}
