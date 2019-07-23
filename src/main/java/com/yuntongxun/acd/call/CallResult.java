package com.yuntongxun.acd.call;

import java.util.Date;

public class CallResult {

    private boolean isSuccess;
    private String failedReason;
    private Exception exception;
    private Date callDate;

    public CallResult() {
    }

    public CallResult(boolean isSuccess, String failedReason, Exception exception, Date callDate) {
        this.isSuccess = isSuccess;
        this.failedReason = failedReason;
        this.exception = exception;
        this.callDate = callDate;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public static class Builder {
        private boolean isSuccess;
        private String failedReason;
        private Exception exception;
        private Date callDate;

        public Builder success() {
            success(new Date());
            return this;
        }

        public Builder success(Date date) {
            this.callDate = date;
            this.isSuccess = true;
            return this;
        }

        public Builder failed() {
            failed(null);
            return this;
        }

        public Builder failed(String failedReason) {
            this.isSuccess = false;
            this.failedReason = failedReason;
            return this;
        }

        public Builder exception(Exception e) {
            failed(null);
            this.exception = e;
            return this;
        }

        public Builder callDate(Date date) {
            this.callDate = date;
            return this;
        }

        public CallResult build() {
            CallResult callResult = new CallResult(isSuccess, failedReason, exception, callDate);
            return callResult;
        }
    }
}
