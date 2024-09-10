package com.example.windowsPos2.setting.dto

import lombok.*

data class SettingDto (
    var id : Long? = null,

//    영업 중단
    var operatePauseDto : OperatePauseDto? = null,

//    영업 시간
    var operateTimeDto : OperateTimeDto? = null,

//    조리 예상 시간
    var estimatedCookingTimeDto : EstimatedCookingTimeDto? = null,

//    도착 예상 시간
    var estimatedArrivalTimeDto : EstimatedArrivalTimeDto? = null,

//    휴무일
    var closedDaysDto : ClosedDaysDto? = null,

//    브레이크 타임
    var breakTimeDto : BreakTimeDto? = null,

//    영업 상태
    var operateStatus : String? = null,

    var memberId : Long? = null
)