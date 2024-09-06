package com.example.windowsPos2.setting.dto

import lombok.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EstimatedCookingTimeDto (
    var id : Long? = null,

//    예상 조리시간 자동 설정
    var estimatedCookingTimeControl : Boolean? = false,

//    예상 조리시간
    var estimatedCookingTime : Int? = null
)