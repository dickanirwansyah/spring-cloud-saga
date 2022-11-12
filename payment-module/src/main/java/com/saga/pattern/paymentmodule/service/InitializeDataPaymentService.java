package com.saga.pattern.paymentmodule.service;

import com.saga.pattern.paymentmodule.entity.UserBalance;
import com.saga.pattern.paymentmodule.repository.UserBalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class InitializeDataPaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @PostConstruct
    public void doInitializeDataPayment(){
        log.info("initialize data");
        if (this.userBalanceRepository.findAll().isEmpty()){
            log.info("insert data dummy");
            userBalanceRepository.saveAll(Stream.of(
                    new UserBalance(101, 5000),
                    new UserBalance(102, 6000),
                    new UserBalance(103, 7800),
                    new UserBalance(104, 8000)).collect(Collectors.toList()));
        }
    }
}
