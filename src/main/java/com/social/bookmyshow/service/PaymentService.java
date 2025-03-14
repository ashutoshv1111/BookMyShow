package com.social.bookmyshow.service;

import org.springframework.stereotype.Service;


public interface PaymentService {
    boolean processPayment(String userId, int totalAmount);
}
