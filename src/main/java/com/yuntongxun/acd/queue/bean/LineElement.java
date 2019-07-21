package com.yuntongxun.acd.queue.bean;

public abstract class LineElement {

    public abstract String getId();

    @Override
    public String toString() {
        return "LineElement{id:"+ getId() +"}";
    }
}
