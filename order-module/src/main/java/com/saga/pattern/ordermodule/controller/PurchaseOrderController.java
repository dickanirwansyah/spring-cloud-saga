package com.saga.pattern.ordermodule.controller;

import com.saga.pattern.commonmodule.dto.OrderRequestDto;
import com.saga.pattern.ordermodule.entity.PurchaseOrder;
import com.saga.pattern.ordermodule.service.CreatePurchaseOrderService;
import com.saga.pattern.ordermodule.service.GetPurchasesOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private CreatePurchaseOrderService createPurchaseOrderService;
    @Autowired
    private GetPurchasesOrdersService getPurchasesOrdersService;

    @PostMapping(value = "/create")
    public PurchaseOrder create(@RequestBody OrderRequestDto orderRequestDto){
        return this.createPurchaseOrderService.createOrder(orderRequestDto);
    }

    @GetMapping(value = "/list")
    public List<PurchaseOrder> list(){
        return this.getPurchasesOrdersService.getList();
    }
}
