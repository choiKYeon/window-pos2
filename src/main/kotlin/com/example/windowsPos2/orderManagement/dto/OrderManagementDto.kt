package com.example.windowsPos2.orderManagement.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalTime

data class OrderManagementDto (
    var id : Long? = null,

//    주문 시간
    @JsonFormat(pattern = "HH:mm")
    var orderTime : LocalTime? = null,

//    요청 사항
    var request : String? = null,

//    고객 주소
    var address : String? = null,

//    메뉴 총 금액
    var menuTotalPrice : Long? = 0L,

//    총 금액
    var totalPrice : Long? = 0L,

//    수저 포크
    var spoonFork : Boolean? = false,

//    배달비
    var deliveryFee : Long? = 0L,

//    주문 번호
    var orderNumber : Long? = null,

//    주문 상태 관리
    var orderStatus : String? = "DELIVERY",

//    포장인지 배달인지 주문 타입
    var orderType : String? = null,

//    주문 업데이트 상태
    var orderUpdateDto : OrderUpdateDto? = null,

//    메뉴 리스트
    var menuList : List<MenuDto> = ArrayList()
)