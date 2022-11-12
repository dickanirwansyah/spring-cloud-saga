package com.saga.pattern.ordermodule.entity;
import com.saga.pattern.commonmodule.event.OrderStatus;
import com.saga.pattern.commonmodule.event.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @Column(name = "id", nullable = false, unique = true, length = 100)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "product_id", nullable = false)
    private Integer productId;
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
