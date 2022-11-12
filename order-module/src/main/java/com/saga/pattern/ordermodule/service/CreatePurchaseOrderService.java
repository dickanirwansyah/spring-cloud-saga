package com.saga.pattern.ordermodule.service;

import com.saga.pattern.commonmodule.dto.OrderRequestDto;
import com.saga.pattern.commonmodule.event.OrderStatus;
import com.saga.pattern.ordermodule.entity.PurchaseOrder;
import com.saga.pattern.ordermodule.repository.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CreatePurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PublisherPurchaseOrderService publisherPurchaseOrderService;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
        log.info("create order={}", orderRequestDto.toString());
        PurchaseOrder purchaseOrder = purchaseOrderRepository.save(convertDtoToEntity(orderRequestDto));
        log.info("--produce kafka with status order ORDER CRATED--");
        orderRequestDto.setOrderId(purchaseOrder.getId());
        publisherPurchaseOrderService.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }

    private PurchaseOrder convertDtoToEntity(OrderRequestDto orderRequestDto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(orderRequestDto.getProductId());
        purchaseOrder.setUserId(orderRequestDto.getUserId());
        purchaseOrder.setPrice(orderRequestDto.getAmount());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }
    
}
