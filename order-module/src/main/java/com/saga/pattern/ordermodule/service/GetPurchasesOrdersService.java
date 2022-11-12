package com.saga.pattern.ordermodule.service;

import com.saga.pattern.ordermodule.entity.PurchaseOrder;
import com.saga.pattern.ordermodule.repository.PurchaseOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetPurchasesOrdersService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public List<PurchaseOrder> getList(){
        log.info("get list order");
        return purchaseOrderRepository.findAll();
    }
}
