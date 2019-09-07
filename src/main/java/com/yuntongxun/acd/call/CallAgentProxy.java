package com.yuntongxun.acd.call;

import com.yuntongxun.acd.distribution.Agent.ConferenceRoom;

public interface CallAgentProxy {

    void call(ConferenceRoom conferenceRoom, CallAgentCallBack callAgentCallBack);

    void callCancel(ConferenceRoom conferenceRoom);
}
