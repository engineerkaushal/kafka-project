package com.kaushal.KafkaProject.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaushal.KafkaProject.Dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TransactionService {

    @Autowired
    private KafkaTemplate<String, Object> template;

    private final ObjectMapper mapper = new ObjectMapper ();

    public String sendTransaction() throws JsonProcessingException {
        for (int i=0; i< 50; i++) {
            String transactionId = "txn-" + System.currentTimeMillis () + "-" + i;
            double amount = 8000 + new Random (  ).nextDouble () * (11000 - 8000);

            Transaction txn = new Transaction (transactionId, "USER_" + i, amount, LocalDateTime.now ().toString ());

            String txnJson = mapper.writeValueAsString (txn);

            template.send ("transactions", transactionId, txnJson);
        }

        return "Transaction sent to kafka !";
    }
}
