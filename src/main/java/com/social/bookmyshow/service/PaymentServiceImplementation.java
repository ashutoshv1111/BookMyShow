package com.social.bookmyshow.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Async
public class PaymentServiceImplementation implements PaymentService{
        @Override
        public boolean processPayment(String userId, int amount) {
            CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(30000);
                    return true;
                } catch (InterruptedException e) {
                    return false;
                }
            });
            return future.join();
        }
    }

