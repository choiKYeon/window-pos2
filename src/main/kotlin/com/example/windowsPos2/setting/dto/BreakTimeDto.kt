package com.example.windowsPos2.setting.dto

import lombok.*
import java.time.LocalTime

data class BreakTimeDto (
    var id : Long? = null,

//    브레이크 타임 임시 종료 시간
    var temporaryEndTime : LocalTime? = null,

//    평일 브레이크 타임 설정
//    평일 브레이크 타임 설정 여부
    var weekdayBreakTime : Boolean? = false,

//    평일 브레이크 타임 시작 시간
    var weekdayBreakTimeStart : LocalTime? = null,

//    평일 브레이크 타임 종료 시간
    var weekdayBreakTimeEnd : LocalTime? = null,

//    토요일 브레이크 타임 설정
//    토요일 브레이크 타임 설정 여부
    var saturdayBreakTime : Boolean? = false,

//    토요일 브레이크 타임 시작 시간
    var saturdayBreakTimeStart : LocalTime? = null,

//    토요일 브레이크 타임 종료 시간
    var saturdayBreakTimeEnd : LocalTime? = null,

//    일요일 브레이크 타임 설정
//    일요일 브레이크 타임 설정 여부
    var sundayBreakTime : Boolean? = false,

//    일요일 브레이크 타임 시작 시간
    var sundayBreakTimeStart : LocalTime? = null,

//    일요일 브레이크 타임 종료 시간
    var sundayBreakTimeEnd : LocalTime? = null

)