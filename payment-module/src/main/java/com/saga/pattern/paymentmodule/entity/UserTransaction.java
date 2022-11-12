package com.saga.pattern.paymentmodule.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_transaction")
public class UserTransaction {

    @Id
    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "amount")
    private int amount;
}
