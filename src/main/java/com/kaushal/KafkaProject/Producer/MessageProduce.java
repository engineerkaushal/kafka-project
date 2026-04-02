package com.kaushal.KafkaProject.Producer;

import com.kaushal.KafkaProject.Dto.CustomerDto;
import com.kaushal.KafkaProject.Dto.OrderEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MessageProduce {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Value ("${topic.name1}")
    private String topicName;

    @Value("${topic.name2}")
    private String topicNam2;

    @Value("${topic.name3}")
    private String topicNam3;

    @Value("${topic.name4}")
    private String topicNam4;

    public void sendMsgToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send (topicName, message);

        future.whenComplete ((result, ex) -> {
            if ( ex == null ) {
                System.out.println ("Sent Message=[" + message + "] " +
                        "with offset=[" + result.getRecordMetadata ().offset () + "] " +
                        "and partition=[" + result.getRecordMetadata ().partition () + "]"+
                        "and topic=[" + result.getRecordMetadata ().topic () + "]" );
            } else {
                System.out.println ("Unable to send message=[" + message + "] due to : " +ex.getMessage () );
            }
        });
    }

    public void sendMsgToTopicInObject(CustomerDto customer) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send (topicNam3, customer);
            future.whenComplete ((result, ex) -> {
                if ( ex == null ) {
                    System.out.println ("Sent Message=[" + customer.toString () + "] " +
                            "with offset=[" + result.getRecordMetadata ().offset () + "] " +
                            "and partition=[" + result.getRecordMetadata ().partition () + "]"+
                            "and topic=[" + result.getRecordMetadata ().topic () + "]" );
                } else {
                    System.out.println ("Unable to send message=[" + customer.toString () + "] due to : " +ex.getMessage () );
                }
            });
        } catch (Exception e) {
            System.out.println ("Error : " + e.getMessage () );
        }
    }

    public void processOrderWithoutKey(String orderId) {
        System.out.println ("Processing Order without Key: " + orderId );
        kafkaTemplate.send (topicNam4, new OrderEventDto (orderId, 1, "OrderPlaced"));
        kafkaTemplate.send (topicNam4, new OrderEventDto (orderId, 2, "PaymentProcessed"));
        kafkaTemplate.send (topicNam4, new OrderEventDto (orderId, 3, "OrderShipped"));
        kafkaTemplate.send (topicNam4, new OrderEventDto (orderId, 4, "OrderDelivered"));
        System.out.println ("Completed Processing Order without Key: " + orderId );
    }

    public void processOrderWithPartitionKey(String orderId) {
        System.out.println ("Processing Order with Partition Key: " + orderId );
        kafkaTemplate.send (topicNam4, orderId, new OrderEventDto (orderId, 1, "OrderPlaced"));
        kafkaTemplate.send (topicNam4, orderId, new OrderEventDto (orderId, 2, "PaymentProcessed"));
        kafkaTemplate.send (topicNam4, orderId, new OrderEventDto (orderId, 3, "OrderShipped"));
        kafkaTemplate.send (topicNam4, orderId, new OrderEventDto (orderId, 4, "OrderDelivered"));
        System.out.println ("Completed Processing Order with Partition Key: " + orderId );
    }
}
