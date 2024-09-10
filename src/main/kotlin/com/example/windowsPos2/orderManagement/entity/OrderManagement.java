package com.example.windowsPos2.orderManagement.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.orderManagement.orderEnum.OrderStatus;
import com.example.windowsPos2.orderManagement.orderEnum.OrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
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
    private OrderUpdate orderUpdate;

    //    메뉴 리스트
    //    @JsonManagedReference는 순환참조 에러를 해결하기 위한 방법
    @OneToMany(mappedBy = "orderManagement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Menu> menuList = new ArrayList<>();

//    @Builder
    public OrderManagement(LocalTime orderTime,
                           String request,
                           String address,
                           Long menuTotalPrice,
                           Long totalPrice,
                           Boolean spoonFork,
                           Long deliveryFee,
                           Long orderNumber,
                           OrderStatus orderStatus,
                           OrderType orderType,
                           OrderUpdate orderUpdate,
                           List<Menu> menuList) {
        this.orderTime = orderTime != null ? orderTime : this.orderTime;
        this.request = request != null ? request : this.request;
        this.address = address != null ? address : this.address;
        this.menuTotalPrice = menuTotalPrice != null ? menuTotalPrice : this.menuTotalPrice;
        this.totalPrice = totalPrice != null ? totalPrice : this.totalPrice;
        this.spoonFork = spoonFork != null ? spoonFork : this.spoonFork;
        this.deliveryFee = deliveryFee != null ? deliveryFee : this.deliveryFee;
        this.orderNumber = orderNumber != null ? orderNumber : this.orderNumber;
        this.orderStatus = orderStatus != null ? orderStatus : this.orderStatus;
        this.orderType = orderType != null ? orderType : this.orderType;
        this.orderUpdate = orderUpdate != null ? orderUpdate : this.orderUpdate;
        this.menuList = menuList != null ? menuList : this.menuList;
    }

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

    public static class Builder {
        private LocalTime orderTime;
        private String request;
        private String address;
        private Long menuTotalPrice;
        private Long totalPrice;
        private Boolean spoonFork;
        private Long deliveryFee;
        private Long orderNumber;
        private OrderStatus orderStatus;
        private OrderType orderType;
        private OrderUpdate orderUpdate;
        private List<Menu> menuList;

        public Builder orderTime(LocalTime orderTime) {
            this.orderTime = orderTime;
            return this;
        }

        public Builder request(String request) {
            this.request = request;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder menuTotalPrice(Long menuTotalPrice) {
            this.menuTotalPrice = menuTotalPrice;
            return this;
        }

        public Builder totalPrice(Long totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder spoonFork(Boolean spoonFork) {
            this.spoonFork = spoonFork;
            return this;
        }

        public Builder deliveryFee(Long deliveryFee) {
            this.deliveryFee = deliveryFee;
            return this;
        }

        public Builder orderNumber(Long orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder orderType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }

        public Builder orderUpdate(OrderUpdate orderUpdate) {
            this.orderUpdate = orderUpdate;
            return this;
        }

        public Builder menuList(List<Menu> menuList) {
            this.menuList = menuList;
            return this;
        }

        public OrderManagement build() {
            return new OrderManagement(orderTime, request, address, menuTotalPrice, totalPrice, spoonFork, deliveryFee, orderNumber, orderStatus, orderType, orderUpdate, menuList);
        }
    }
}