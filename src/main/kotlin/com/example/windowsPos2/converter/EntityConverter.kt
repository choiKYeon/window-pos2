package com.example.windowsPos2.converter

import com.example.windowsPos2.orderManagement.dto.MenuDto
import com.example.windowsPos2.orderManagement.dto.MenuOptionDto
import com.example.windowsPos2.orderManagement.entity.Menu
import com.example.windowsPos2.orderManagement.entity.MenuOption

//  DTO를 엔티티로 변환해주는 구문
object EntityConverter {

//    메뉴주문을 DTO로 변환하는 메서드
    fun convertToEntity(menuDto : MenuDto) : Menu {
        // 메뉴 옵션 리스트가 null일 경우 빈 리스트로 처리
        val menuOptions = menuDto.menuOptionDtoList?.map { convertToEntity(it) } ?: emptyList()
//        생성자를 사용하여 Menu 객체 생성

        val menu = Menu.Builder()
            .menuName(menuDto.menuName ?: "")
            .menuCount(menuDto.menuCount ?: 1)
            .menuPrice(menuDto.menuPrice ?: 0L)
            .menuOptions(menuOptions)
            .build()

        return menu
    }

    fun convertToEntity(menuOptionDto: MenuOptionDto): MenuOption {
        val menuOption = MenuOption.Builder()
            .optionName(menuOptionDto.optionName ?: "")
            .optionPrice(menuOptionDto.optionPrice ?: 0L)
            .build()
        
        return menuOption
    }
}