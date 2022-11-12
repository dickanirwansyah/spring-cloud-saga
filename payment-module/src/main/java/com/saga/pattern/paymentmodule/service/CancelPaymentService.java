package com.saga.pattern.paymentmodule.service;

import com.saga.pattern.commonmodule.event.OrderEvent;
import com.saga.pattern.paymentmodule.repository.UserBalanceRepository;
import com.saga.pattern.paymentmodule.repository.UserTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CancelPaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent){
        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(userTransaction -> {
                    userTransactionRepository.delete(userTransaction);
                    userBalanceRepository.findById(userTransaction.getUserId())
                            .ifPresent(userBalance -> userBalance.setPrice(userBalance.getPrice() + userTransaction.getAmount()));
                });
    }
}
