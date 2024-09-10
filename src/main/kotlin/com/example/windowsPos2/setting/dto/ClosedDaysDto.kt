package com.example.windowsPos2.setting.dto

import lombok.*

data class ClosedDaysDto (
    var id : Long? = null,

//    임시 휴무일
    var temporaryHolidayDates : List<TemporaryHolidayDto>? = null,

//    정기 휴무일
    var regularHolidayList : List<RegularHolidayDto>? = null
)