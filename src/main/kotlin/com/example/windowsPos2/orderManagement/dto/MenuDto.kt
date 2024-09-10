package com.example.windowsPos2.orderManagement.dto

import lombok.*

data class MenuDto (
    var id : Long? = null,

//    메뉴 이름
    var menuName : String? = null,

//    메뉴 가격
    var menuPrice : Long? = 0L,

//    메뉴 수량
    var menuCount : Int? = 0,

//    메뉴 옵션 리스트
    var menuOptionDtoList : List<MenuOptionDto> = ArrayList()

)