package com.saga.pattern.paymentmodule.service;

import com.saga.pattern.commonmodule.dto.OrderRequestDto;
import com.saga.pattern.commonmodule.dto.PaymentRequestDto;
import com.saga.pattern.commonmodule.event.OrderEvent;
import com.saga.pattern.commonmodule.event.PaymentEvent;
import com.saga.pattern.commonmodule.event.PaymentStatus;
import com.saga.pattern.paymentmodule.entity.UserTransaction;
import com.saga.pattern.paymentmodule.repository.UserBalanceRepository;
import com.saga.pattern.paymentmodule.repository.UserTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class NewPaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        log.info("new order even={}",orderEvent.toString());
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setOrderId(orderRequestDto.getOrderId());
        paymentRequestDto.setAmount(orderRequestDto.getAmount());
        paymentRequestDto.setUserId(orderRequestDto.getUserId());

       return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(userBalance -> userBalance.getPrice() > orderRequestDto.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice() - orderRequestDto.getAmount());

                    UserTransaction userTransaction = new UserTransaction();
                    userTransaction.setOrderId(orderRequestDto.getOrderId());
                    userTransaction.setUserId(orderRequestDto.getUserId());
                    userTransaction.setAmount(orderRequestDto.getAmount());
                    userTransactionRepository.save(userTransaction);

                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                })
               .orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

}
