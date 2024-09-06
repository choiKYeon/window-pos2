package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.orderManagement.orderEnum.OrderStatus;
import com.example.windowsPos2.orderManagement.orderEnum.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OrderManagement extends BaseEntity {
    //    주문 시간
    @JsonFormat(pattern = "HH:mm")
    private LocalTime orderTime;

    //    요청사항
    private String request;

    //    고객 주소
    private String address;

    //    메뉴 총 금액
    @Setter
    private Long menuTotalPrice;

    //    총 금액
    @Setter
    private Long totalPrice;

    //    수저포크
    private Boolean spoonFork;

    //    배달비
    private Long deliveryFee;

    //    주문 번호
    @Column(unique = true)
    private Long orderNumber;

    //    주문 상태관리
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //    포장인지 배달인지 주문 타입
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    //    주문 업데이트 상태
    @OneToOne(mappedBy = "orderManagement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Setter
    private OrderUpdate orderUpdate;

    //    메뉴 리스트
    //    @JsonManagedReference는 순환참조 에러를 해결하기 위한 방법
    @OneToMany(mappedBy = "orderManagement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Setter
    private List<Menu> menuList = new ArrayList<>();


    public void acceptOrder() {
        if (this.orderStatus == OrderStatus.WAITING) {
            this.orderStatus = OrderStatus.IN_PROGRESS;
        } else {
            throw new IllegalStateException("주문을 수락할 수 없는 상태입니다.");
        }
    }

    public void rejectOrder() {
        if (this.orderStatus == OrderStatus.WAITING) {
            this.orderStatus = OrderStatus.REJECTED;
        } else {
            throw new IllegalStateException("주문을 거절할 수 없는 상태입니다.");
        }
    }

    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.IN_PROGRESS) {
            this.orderStatus = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("주문을 취소할 수 없는 상태입니다.");
        }
    }

    public void completeOrder() {
        if (this.orderStatus == OrderStatus.IN_PROGRESS) {
            this.orderStatus = OrderStatus.COMPLETED;
        } else {
            throw new IllegalStateException("주문을 완료할 수 없는 상태입니다.");
        }
    }

}
