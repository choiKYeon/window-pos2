package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OrderUpdate extends BaseEntity {

    //    주문 거절 사유
    @Setter
    private String rejectionReason;

    //    예상 조리시간
    @Setter
    private Integer estimatedCookingTime;

    //    도착 예상 시간
    @Setter
    private Integer estimatedArrivalTime;

    @OneToOne
    @JoinColumn(name = "order_update_id")
    @JsonBackReference
    private OrderManagement orderManagement;
}
