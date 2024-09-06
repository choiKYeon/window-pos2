package com.example.windowsPos2.setting.dto

import lombok.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EstimatedArrivalTimeDto (
    var id : Long? = null,

//    예상 도착시간 자동 설정
    var estimatedArrivalTimeControl : Boolean? = false,

//    예상 도착시간
    var estimatedArrivalTime : Int? = null
)