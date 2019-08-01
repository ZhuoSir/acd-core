package com.yuntongxun.acd.queue.bean;

public abstract class LineElement {

    public abstract String index();

    private int waitingCount;

    public void setWaitingCount(int waitingCount) {
        this.waitingCount = waitingCount;
    }

    public int getWaitingCount() {
        return waitingCount;
    }

    @Override
    public String toString() {
        return "LineElement{" +
                "id=" + index() +
                "waitingCount=" + waitingCount +
                '}';
    }

    public LineElementInfo getInfo() {
        LineElementInfo info = new LineElementInfo();
        info.setIndex(index());
        info.setWaitingCount(waitingCount);
        return info;
    }
}
