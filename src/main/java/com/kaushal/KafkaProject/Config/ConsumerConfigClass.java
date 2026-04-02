package com.kaushal.KafkaProject.Config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerConfigClass {


    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> consume = new HashMap<> (  );

        consume.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 🔑 KEY
        consume.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        consume.put (ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);

        // 🔑 VALUE (JSON + STRING)
        consume.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        consume.put (ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        // 🔓 TRUST ALL PACKAGES
        consume.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kaushal.*");

        // ❗ VERY IMPORTANT
        consume.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);

        return consume;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig ());
    }

    @Bean
    public DefaultErrorHandler batchErrorHandler() {

        // retry 3 times
        FixedBackOff backOff = new FixedBackOff(1000L, 3);

        DefaultErrorHandler handler = new DefaultErrorHandler(backOff);

        // ❗ log and skip bad record
        handler.addNotRetryableExceptions(
                NullPointerException.class,
                IllegalArgumentException.class
        );

        return handler;
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainer() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<> ();

        factory.setConsumerFactory (consumerFactory ());
        factory.setBatchListener (true);

        // 👇 REQUIRED for SerializationException
        factory.setCommonErrorHandler(batchErrorHandler());

        return factory;
    }
}
