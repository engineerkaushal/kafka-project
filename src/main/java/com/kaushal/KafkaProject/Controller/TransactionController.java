package com.kaushal.KafkaProject.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaushal.KafkaProject.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trans")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/send")
    public String sendTransaction() throws JsonProcessingException {

        return service.sendTransaction ();
    }
}
