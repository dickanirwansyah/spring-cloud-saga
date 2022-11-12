package com.saga.pattern.paymentmodule.config;

import com.saga.pattern.commonmodule.event.OrderEvent;
import com.saga.pattern.commonmodule.event.OrderStatus;
import com.saga.pattern.commonmodule.event.PaymentEvent;
import com.saga.pattern.paymentmodule.service.CancelPaymentService;
import com.saga.pattern.paymentmodule.service.NewPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    @Autowired
    private NewPaymentService newPaymentService;
    @Autowired
    private CancelPaymentService cancelPaymentService;

    @Bean
    public Function<Flux<OrderEvent>,Flux<PaymentEvent>> paymentProcessor(){
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent){
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            return Mono.fromSupplier(() -> this.newPaymentService.newOrderEvent(orderEvent));
        }else{
            return Mono.fromRunnable(() -> this.cancelPaymentService.cancelOrderEvent(orderEvent));
        }
    }
}
