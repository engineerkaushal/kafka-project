package com.kaushal.KafkaProject.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${topic.name1}")
    private String topicName;

    @Value("${topic.name2}")
    private String topicNam2;

    @Value("${topic.name3}")
    private String topicNam3;
    @Value("${topic.name4}")
    private String topicNam4;

    @Value("${topic.name5}")
    private String topicNam5;

    @Value("${topic.name6}")
    private String topicNam6;



    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name (topicName)
                .partitions (3)
                .replicas (1)
                .build ();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name (topicNam2)
                .partitions (3)
                .replicas (1)
                .build ();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name (topicNam3)
                .partitions (3)
                .replicas (1)
                .build ();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name (topicNam4)
                .partitions (3)
                .replicas (1)
                .build ();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name (topicNam5)
                .partitions (3)
                .replicas (1)
                .build ();
    }

    @Bean
    public NewTopic topic6() {
        return TopicBuilder.name (topicNam6)
                .partitions (3)
                .replicas (1)
                .build ();
    }
}
