package com.example.practice.service;

import com.example.practice.client.CustomerClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Service related to {@link CustomerService}
 */
@Service
public class CustomerService {

    private static final Logger logger = getLogger(CustomerService.class);



    private final CustomerClient customerClient;


    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }
}
