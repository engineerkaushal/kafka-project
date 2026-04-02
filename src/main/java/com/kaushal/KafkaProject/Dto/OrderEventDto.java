package com.kaushal.KafkaProject.Dto;

public
class OrderEventDto {
    private String orderId;
    private int seq;
    private String eventType;

    public
    OrderEventDto () {
    }

    public
    OrderEventDto (String orderId, int seq, String eventType) {
        this.orderId = orderId;
        this.seq = seq;
        this.eventType = eventType;
    }

    public
    String getOrderId () {
        return orderId;
    }

    public
    void setOrderId (String orderId) {
        this.orderId = orderId;
    }

    public
    int getSeq () {
        return seq;
    }

    public
    void setSeq (int seq) {
        this.seq = seq;
    }

    public
    String getEventType () {
        return eventType;
    }

    public
    void setEventType (String eventType) {
        this.eventType = eventType;
    }

    @Override
    public
    String toString () {
        return "OrderEventDto{" +
                "orderId='" + orderId + '\'' +
                ", seq=" + seq +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
