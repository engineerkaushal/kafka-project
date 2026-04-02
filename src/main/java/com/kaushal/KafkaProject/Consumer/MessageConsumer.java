package com.kaushal.KafkaProject.Consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaushal.KafkaProject.Dto.CustomerDto;
import com.kaushal.KafkaProject.Dto.OrderEventDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageConsumer {
    Logger log = LoggerFactory.getLogger (MessageConsumer.class);

    private final  ExecutorService executor = Executors.newFixedThreadPool (4);

    //TODO: If consumer want to get get data from perticular partition so we can use topicPartitions and we can give topic name and partition number like below
    //@KafkaListener(topics = "topic1", groupId = "consumer-group", topicPartitions = {@TopicPartition (topic = "topic1", partitions = {"1"})})
    @KafkaListener(topics = "topic1", groupId = "consumer-group")
    public void consumer1(String message) {
        log.info ("Consumer1 consume the message {}" , message);
    }

    //TODO: This commited code if we have three partition in a topic and we have three consumer so one partition will be assign to one consumer
   /* @KafkaListener(topics = "topic2", groupId = "consumer-group")
    public void consumer1(String message) {
        log.info ("Consumer1 consume the message {}" , message);
    }
    @KafkaListener(topics = "topic2", groupId = "consumer-group")
    public void consumer2(String message) {
        log.info ("Consumer2 consume the message{}" , message);
    }

    @KafkaListener(topics = "topic2", groupId = "consumer-group")
    public void consumer3(String message) {
        log.info ("Consumer3 consume the message{}" , message);
    }*/

    @KafkaListener(topics = "topic2", groupId = "consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void consumerObject(CustomerDto dto) {
        log.info ("Consumer consume the message {}" , dto);
    }

    @RetryableTopic(attempts = "4") // Internally Kafka will create 3 topic (N-1 == 4-1)
    @KafkaListener(topics = "topic3", groupId = "consumer-group")
    public void consumerErrorHandling(CustomerDto dto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info ("Consumer received message {} from {} offset {}" , new ObjectMapper (  ).writeValueAsString (dto), topic, offset);
            List<String> emailList = List.of ( "ksrajp@gmail.com", "ksdhcu@gmail.com", "jogawan@gmail.com" );
            if ( emailList.contains (dto.getEmail ()) )
                throw new RuntimeException ( "Invalid email received !" );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    @DltHandler
    public void listenDLT(CustomerDto dto, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        log.info ("DLT received : message {} from topic {} and offset {}", dto.getName (), topic, offset);
    }



    @KafkaListener(topics = "topic4", groupId = "consumer-group")
    public void consumeOrder(ConsumerRecord<String, OrderEventDto> record) {
        OrderEventDto eventDto = record.value ( );
        System.out.printf ("CONSUMER: partition=%d offset=%d orderId=%s seq=%d event=%s%n",
                record.partition (), record.offset (), eventDto.getOrderId (), eventDto.getSeq (), eventDto.getEventType () );
    }


    //@KafkaListener(topics = "topic4", groupId = "consumer-group", properties = {"max.poll.records=1"})
    /*@KafkaListener(topics = "topic4", groupId = "consumer-group")
    public void consumeOrderBATCH(List<ConsumerRecord<String, OrderEventDto>> records) {
        Map<Integer, ExecutorService> partitionExecutor = new ConcurrentHashMap<> (  );
        for (ConsumerRecord<String, OrderEventDto> record : records) {
            partitionExecutor.computeIfAbsent (record.partition (), p-> Executors.newSingleThreadExecutor ())
            .submit (() -> {
                OrderEventDto eventDto = record.value ( );
                System.out.printf ("CONSUMER: partition=%d offset=%d orderId=%s seq=%d event=%s%n",
                        record.partition (), record.offset (), eventDto.getOrderId (), eventDto.getSeq (), eventDto.getEventType () );
            });
        }
    }*/
}
