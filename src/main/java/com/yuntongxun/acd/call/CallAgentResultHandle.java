package com.yuntongxun.acd.call;

import com.yuntongxun.acd.call.Agent.ConferenceRoom;

public interface CallAgentResultHandle {

    void callSuccess(ConferenceRoom conferenceRoom);

    void callFailed(ConferenceRoom conferenceRoom);

}
