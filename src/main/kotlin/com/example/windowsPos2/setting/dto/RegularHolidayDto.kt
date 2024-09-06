package com.example.windowsPos2.setting.dto

import lombok.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class RegularHolidayDto (
    var id : Long? = null,

//    무슨 요일인지
    var dayOfWeek : String? = null,

//    매 주 언제 정기휴무인지 ex) 첫째 주 / 둘째 주 / 매 주
    var regularClosedDays : String? = null
)
