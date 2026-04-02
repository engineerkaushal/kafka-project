package com.kaushal.KafkaProject.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaushal.KafkaProject.Dto.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    Logger log = LoggerFactory.getLogger (KafkaStreamConfig.class);

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfig() {

        Map<String, Object> props = new HashMap<> ();

        // 🔑 REQUIRED
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream-fraud-detection");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // 🔑 DEFAULT SERDES
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kaushal.KafkaProject.*");


        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<String, Transaction> fraudDetectStream(StreamsBuilder builder) {

        JsonSerde<Transaction> serde = new JsonSerde<>(Transaction.class);
        //Step 1: Read message from input topic
        KStream<String, Transaction> transactions = builder.stream ("transactions", Consumed.with (Serdes.String(), serde));

        //Step 2: Process the stream to detect fraudulent transactions
        transactions.filter ((key, txn) -> txn.getAmt () > 10000)
                .peek ((key, value) -> {
                    log.warn ("FRAUD ALERT - transactionId={}, value={}", key, value);
                })

        //Step 3: Write detected fraudulent transactions to an output topic

        .to ("alert", Produced.with(Serdes.String(), serde));

        return transactions;
    }


    @Bean
    public KStream<String, Transaction> useAllMethodUnderStream(StreamsBuilder builder) {

        JsonSerde<Transaction> serde = new JsonSerde<>(Transaction.class);
        KStream<String, Transaction> transactions = builder.stream ("transactions", Consumed.with (Serdes.String(), serde));

        // filter()
        transactions.filter ((key, txn) -> txn.getAmt () > 10000)
                .peek ((key, value) -> {
                    log.warn ("FRAUD ALERT - transactionId={}, value={}", key, value);
                });

        // filterNot()
        transactions.filter ((key, txn) -> txn.getAmt () < 10000)
                .peek ((key, value) -> {
                    log.warn ("NORMAL - transactionId={}, value={}", key, value);
                });

        //map() : We have option to modify key and value
        transactions.map ((key, value) -> KeyValue.pair (value.getUserId (), "User spent : " + value.getAmt ()))
                .peek ((key, value) -> {
                    log.warn ("User Transaction Summery - transactionId={}, value={}", key, value);
                });

        //mapValue() : We have option to modify only value
        transactions.mapValues ( value -> "Transaction of " + value.getAmt () + " by user")
                .peek ((key, value) -> {
                    log.warn ("User Transaction Summery - transactionId={}, value={}", key, value);
                });

        //flatMap()
        //FlatMapValue()

        //branch(): It will split stream into multiple stream based on condition
        KStream<String, Transaction>[] branch = transactions.branch (
                (key, value) -> value.getUserId ( ).equalsIgnoreCase ("A"),
                (key, value) -> value.getUserId ( ).equalsIgnoreCase ("B")
        );
        branch[0].peek ((key, tx)->
                log.info ("A : {}" , tx)
                );
        branch[1].peek ((key, tx)->
                log.info ("B : {}" , tx)
        );

        //groupBy() :
        transactions.groupBy ((key, tx)-> tx.getUserId ()).count ().toStream ();


        return transactions;
    }

}
