package com.kaushal.KafkaProject.Dto;

public class Transaction {
    private String txnId;
    private String userId;
    private double amt;
    private String time;

    public
    Transaction () {
    }

    public
    Transaction (String txnId, String userId, double amt, String time) {
        this.txnId = txnId;
        this.userId = userId;
        this.amt = amt;
        this.time = time;
    }

    public
    String getTxnId () {
        return txnId;
    }

    public
    void setTxnId (String txnId) {
        this.txnId = txnId;
    }

    public
    String getUserId () {
        return userId;
    }

    public
    void setUserId (String userId) {
        this.userId = userId;
    }

    public
    double getAmt () {
        return amt;
    }

    public
    void setAmt (double amt) {
        this.amt = amt;
    }

    public
    String getTime () {
        return time;
    }

    public
    void setTime (String time) {
        this.time = time;
    }

    @Override
    public
    String toString () {
        return "Transaction{" +
                "txnId='" + txnId + '\'' +
                ", userId='" + userId + '\'' +
                ", amt=" + amt +
                ", time='" + time + '\'' +
                '}';
    }
}
