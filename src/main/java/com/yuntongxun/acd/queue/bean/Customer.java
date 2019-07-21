package com.yuntongxun.acd.queue.bean;

public class Customer extends LineElement {

    public int index;

    public Customer(int index) {
        this.index = index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getId() {
        return "C" + index;
    }
}
