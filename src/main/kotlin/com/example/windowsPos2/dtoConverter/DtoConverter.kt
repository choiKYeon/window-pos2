package com.example.windowsPos2.dtoConverter

import com.example.windowsPos2.orderManagement.dto.MenuDto
import com.example.windowsPos2.orderManagement.dto.MenuOptionDto
import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.example.windowsPos2.orderManagement.dto.OrderUpdateDto
import com.example.windowsPos2.orderManagement.entity.Menu
import com.example.windowsPos2.orderManagement.entity.MenuOption
import com.example.windowsPos2.orderManagement.entity.OrderManagement
import com.example.windowsPos2.orderManagement.entity.OrderUpdate

object DtoConverter {

//    OrderManagement 객체를 DTO로 변환하는 메서드
    fun convertToDto(order : OrderManagement) : OrderManagementDto {
        val orderManagementDto = OrderManagementDto()
        orderManagementDto.id = order.id
        orderManagementDto.orderTime = order.orderTime
        orderManagementDto.request = order.request
        orderManagementDto.address = order.address
        orderManagementDto.totalPrice = order.totalPrice
        orderManagementDto.menuTotalPrice = order.menuTotalPrice
        orderManagementDto.orderNumber = order.orderNumber
        orderManagementDto.deliveryFee = order.deliveryFee
        orderManagementDto.spoonFork = order.spoonFork

//      Enum -> String 타입 변환
        orderManagementDto.orderStatus = order.orderStatus?.name
        orderManagementDto.orderType = order.orderType?.name

        orderManagementDto.menuList = order.menuList.map { convertToDto(it) }

        orderManagementDto.orderUpdateDto = order.orderUpdate?.let { convertToDto(it) }

        return orderManagementDto
    }

//    OrderUpdate 객체를 DTO로 변환하는 메서드
    fun convertToDto(orderUpdate : OrderUpdate) : OrderUpdateDto {
        val orderUpdateDto = OrderUpdateDto()
        orderUpdateDto.id = orderUpdate.id
        orderUpdateDto.rejectReason = orderUpdate.rejectionReason
        orderUpdateDto.estimatedArrivalTime = orderUpdate.estimatedArrivalTime
        orderUpdateDto.estimatedCookingTime = orderUpdate.estimatedCookingTime

        return orderUpdateDto
    }

//    menuList를 DTO로 변환하는 메서드
    fun convertToDto(menu: Menu) : MenuDto {
        val menuDto = MenuDto()
        menuDto.id = menu.id
        menuDto.menuName = menu.menuName
        menuDto.menuPrice = menu.menuPrice
        menuDto.menuCount = menu.menuCount
        menuDto.menuOptionDtoList = menu.menuOptions.map { convertToDto(it) }

        return menuDto
    }

//    MenuOption 객체를 DTO로 변환하는 메서드
    fun convertToDto(menuOption : MenuOption) : MenuOptionDto {
        val menuOptionDto = MenuOptionDto()
        menuOptionDto.id = menuOptionDto.id
        menuOptionDto.optionName = menuOptionDto.optionName
        menuOptionDto.optionPrice = menuOptionDto.optionPrice

        return menuOptionDto
    }

//    Setting 객체를 DTO로 변환하는 메서드
}