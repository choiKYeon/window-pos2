package com.example.windowsPos2.setting.dto

import lombok.*
import java.time.LocalTime

data class OperateTimeDto (
    var id : Long? = null,

//    임시 시작 시간
    var temporaryOperateStartTime : Boolean? = false,

//    임시 종료 시간
    var temporaryOperateEndTime : Boolean? = false,

//    평일 24시간 영업
    var weekdayAllDay : Boolean? = false,

//    평일 시작 시간
    var weekdayStartTime : LocalTime? = null,

//    평일 종료 시간
    var weekdayEndTime : LocalTime? = null,

//    토요일 24시간 영업
    var saturdayAllDay : Boolean? = false,

//    토요일 시작 시간
    var saturdayStartTime : LocalTime? = null,

//    토요일 종료 시간
    var saturdayEndTime : LocalTime? = null,

//    일요일 24시간 영업
    var sundayAllDay : Boolean? = false,

//    일요일 시작 시간
    var sundayStartTime : LocalTime? = null,

//    일요일 종료 시간
    var sundayEndTime : LocalTime? = null
)