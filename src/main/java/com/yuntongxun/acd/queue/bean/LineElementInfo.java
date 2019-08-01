package com.yuntongxun.acd.queue.bean;

public class LineElementInfo {

    private int waitingCount;

    private String index;

    public int getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "LineElementInfo{" +
                "waitingCount=" + waitingCount +
                ", index='" + index + '\'' +
                '}';
    }
}
