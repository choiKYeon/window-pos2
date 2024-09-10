package com.example.windowsPos2.orderManagement.dto

import lombok.*

data class MenuOptionDto (
    var id : Long? = null,

//    옵션 이름
    var optionName : String? = null,

//    옵션 가격
    var optionPrice : Long? = 0L,

)