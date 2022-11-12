package com.saga.pattern.ordermodule.service;

import com.saga.pattern.commonmodule.dto.OrderRequestDto;
import com.saga.pattern.commonmodule.event.OrderEvent;
import com.saga.pattern.commonmodule.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Slf4j
@Service
public class PublisherPurchaseOrderService {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        log.info("do call publishOrderEvent payload={} and status={} ",orderRequestDto.toString(), orderStatus.toString());
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
    }
}
