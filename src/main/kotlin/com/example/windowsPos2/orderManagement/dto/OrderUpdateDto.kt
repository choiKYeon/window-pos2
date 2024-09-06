package com.example.windowsPos2.orderManagement.dto

import lombok.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class OrderUpdateDto (
    var id : Long? = null,

//    주문 상태
    var orderStatus : String? = null,

//    주문 거절 사유
    var rejectReason : String? = null,

//    예상 조리 시간
    var estimatedCookingTime : Int? = null,

//    도착 예상 시간
    var estimatedArrivalTime : Int? = null,

)