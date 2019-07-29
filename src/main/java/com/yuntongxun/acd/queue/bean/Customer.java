package com.yuntongxun.acd.queue.bean;

public class Customer extends LineElement {

    private int index;

    private String account;

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

    public int getIndex() {
        return index;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "index=" + getId() +
                ", account='" + account + '\'' +
                '}';
    }
}
