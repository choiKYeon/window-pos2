package com.example.windowsPos2.setting.dto

import jakarta.persistence.Entity
import lombok.*
import lombok.experimental.SuperBuilder
import java.time.LocalTime

data class OperatePauseDto (
    var id : Long? = null,

//    영업 일시 정지 시작 시간
    var operatePauseStartTime : LocalTime? = null,

//    영업 일시 정지 종료 시간
    var operatePauseEndTime : LocalTime? = null,

//    영업 일시 정지 지속 시간
    var operatePauseDuration : Long? = null
)