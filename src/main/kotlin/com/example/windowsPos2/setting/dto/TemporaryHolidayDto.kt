package com.example.windowsPos2.setting.dto

import lombok.*
import java.time.LocalDate

data class TemporaryHolidayDto (
    var id : Long? = null,

//    임시 휴무 시작 날짜
    var temporaryHolidayStartDate : LocalDate? = null,

//    임시 휴무 종료 날짜
    var temporaryHolidayEndDate : LocalDate? = null
)