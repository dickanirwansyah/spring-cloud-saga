package com.saga.pattern.ordermodule.config;

import com.saga.pattern.commonmodule.dto.OrderRequestDto;
import com.saga.pattern.commonmodule.event.OrderStatus;
import com.saga.pattern.commonmodule.event.PaymentStatus;
import com.saga.pattern.ordermodule.entity.PurchaseOrder;
import com.saga.pattern.ordermodule.repository.PurchaseOrderRepository;
import com.saga.pattern.ordermodule.service.PublisherPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PublisherPurchaseOrderService publisherPurchaseOrderService;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> orderConsumer){
        purchaseOrderRepository.findById(id)
                .ifPresent(orderConsumer.andThen(this::doUpdateOrder));
    }

    private void doUpdateOrder(PurchaseOrder purchaseOrder){
        boolean isPaymentCompleted = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCEL;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentCompleted){
            publisherPurchaseOrderService.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }
    }

    public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder){
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
