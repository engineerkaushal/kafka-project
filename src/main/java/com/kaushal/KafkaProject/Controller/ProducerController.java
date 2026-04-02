package com.kaushal.KafkaProject.Controller;

import com.kaushal.KafkaProject.Dto.CustomerDto;
import com.kaushal.KafkaProject.Producer.MessageProduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produce")
public class ProducerController {
    @Autowired
    private MessageProduce produce;

    @GetMapping("/sendMsg")
    public ResponseEntity<?> produceMsg(@RequestParam(required = false) String message) {
        try {
            for (int i = 0; i < 10000; i++)
                produce.sendMsgToTopic (message + " : " + i);
            return ResponseEntity.ok ( "Message Published Successfully.." );
        } catch (Exception e) {
            return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).build ();
        }
    }

    @PostMapping("/sendEvents")
    public ResponseEntity<?> produceMsgInObject(@RequestBody CustomerDto customer) {
        try {
            //for (int i = 0; i < 10000; i++)
                produce.sendMsgToTopicInObject (customer);
            return ResponseEntity.ok ( "Message Published Successfully.." );
        } catch (Exception e) {
            return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).build ();
        }
    }

    @GetMapping("/processEvent/{orderId}")
    public ResponseEntity<?> processEventWithoutKey(@PathVariable String orderId) {
        try {
            produce.processOrderWithoutKey (orderId);
            return ResponseEntity.ok ( "Order Published Successfully.." );
        } catch (Exception e) {
            return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).build ();
        }
    }

    @GetMapping("/processEventWithKey/{orderId}")
    public ResponseEntity<?> processEventWithKey(@PathVariable String orderId) {
        try {
            produce.processOrderWithPartitionKey (orderId);
            return ResponseEntity.ok ( "Order Published Successfully.." );
        } catch (Exception e) {
            return ResponseEntity.status (HttpStatus.INTERNAL_SERVER_ERROR).build ();
        }
    }
}
