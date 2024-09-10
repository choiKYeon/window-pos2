package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class OrderUpdate extends BaseEntity {

    //    주문 거절 사유
    private String rejectionReason;

    //    예상 조리시간
    private Integer estimatedCookingTime;

    //    도착 예상 시간
    private Integer estimatedArrivalTime;

    @OneToOne
    @JoinColumn(name = "order_update_id")
    @JsonBackReference
    private OrderManagement orderManagement;

//    @Builder
    public OrderUpdate(String rejectionReason,
                       Integer estimatedCookingTime,
                       Integer estimatedArrivalTime,
                       OrderManagement orderManagement) {
        this.rejectionReason = rejectionReason != null ? rejectionReason : this.rejectionReason;
        this.estimatedCookingTime = estimatedCookingTime != null ? estimatedCookingTime : this.estimatedCookingTime;
        this.estimatedArrivalTime = estimatedArrivalTime != null ? estimatedArrivalTime : this.estimatedArrivalTime;
        this.orderManagement = orderManagement != null ? orderManagement : this.orderManagement;
    }

    public static class Builder {
        private String rejectionReason;
        private Integer estimatedCookingTime;
        private Integer estimatedArrivalTime;
        private OrderManagement orderManagement;

        public Builder rejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
            return this;
        }

        public Builder estimatedCookingTime(Integer estimatedCookingTime) {
            this.estimatedCookingTime = estimatedCookingTime;
            return this;
        }

        public Builder estimatedArrivalTime(Integer estimatedArrivalTime) {
            this.estimatedArrivalTime = estimatedArrivalTime;
            return this;
        }

        public Builder orderManagement(OrderManagement orderManagement) {
            this.orderManagement = orderManagement;
            return this;
        }

        public OrderUpdate build() {
            return new OrderUpdate(rejectionReason, estimatedCookingTime, estimatedArrivalTime, orderManagement);
        }
    }
}
