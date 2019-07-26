package com.yuntongxun.acd.queue.bean;

public class QueueInfo {

    private int waitingCount;

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    @Override
    public String toString() {
        return "QueueInfo{" +
                "waitingCount=" + waitingCount +
                '}';
    }
}
