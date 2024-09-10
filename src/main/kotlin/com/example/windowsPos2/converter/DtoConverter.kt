package com.example.windowsPos2.converter

import com.example.windowsPos2.orderManagement.dto.MenuDto
import com.example.windowsPos2.orderManagement.dto.MenuOptionDto
import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.example.windowsPos2.orderManagement.dto.OrderUpdateDto
import com.example.windowsPos2.orderManagement.entity.Menu
import com.example.windowsPos2.orderManagement.entity.MenuOption
import com.example.windowsPos2.orderManagement.entity.OrderManagement
import com.example.windowsPos2.orderManagement.entity.OrderUpdate
import com.example.windowsPos2.setting.dto.*
import com.example.windowsPos2.setting.entity.BreakTime
import com.example.windowsPos2.setting.entity.ClosedDays
import com.example.windowsPos2.setting.entity.EstimatedArrivalTime
import com.example.windowsPos2.setting.entity.EstimatedCookingTime
import com.example.windowsPos2.setting.entity.OperatePause
import com.example.windowsPos2.setting.entity.OperateTime
import com.example.windowsPos2.setting.entity.RegularHoliday
import com.example.windowsPos2.setting.entity.Setting
import com.example.windowsPos2.setting.entity.TemporaryHoliday
import java.time.Duration
import java.time.LocalDate

//   엔티티를 DTO로 변환해주는 구문
object DtoConverter {

//    OrderManagement 객체를 DTO로 변환하는 메서드
    fun convertToDto(orderManagement : OrderManagement) : OrderManagementDto {
        val orderManagementDto = OrderManagementDto()

        orderManagementDto.id = orderManagement.id
        orderManagementDto.orderTime = orderManagement.orderTime
        orderManagementDto.request = orderManagement.request
        orderManagementDto.address = orderManagement.address
        orderManagementDto.totalPrice = orderManagement.totalPrice
        orderManagementDto.menuTotalPrice = orderManagement.menuTotalPrice
        orderManagementDto.orderNumber = orderManagement.orderNumber
        orderManagementDto.deliveryFee = orderManagement.deliveryFee
        orderManagementDto.spoonFork = orderManagement.spoonFork

//      Enum -> String 타입 변환
        orderManagementDto.orderStatus = orderManagement.orderStatus?.name
        orderManagementDto.orderType = orderManagement.orderType?.name

        orderManagementDto.menuList = orderManagement.menuList.map { convertToDto(it) }

        orderManagementDto.orderUpdateDto = orderManagement.orderUpdate?.let { convertToDto(it) }

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
        menuOptionDto.id = menuOption.id
        menuOptionDto.optionName = menuOption.optionName
        menuOptionDto.optionPrice = menuOption.optionPrice

        return menuOptionDto
    }

//    Setting 객체를 DTO로 변환하는 메서드
    fun convertToDto(setting : Setting) : SettingDto {
        val settingDto = SettingDto()
        settingDto.id = setting.id
        settingDto.operatePauseDto = setting.operatePause?.let { convertToDto(it) }
        settingDto.closedDaysDto = setting.closedDays?.let { convertToDto(it) }
        settingDto.operateTimeDto = setting.operateTime?.let { convertToDto(it) }
        settingDto.estimatedCookingTimeDto = setting.estimatedCookingTime?.let { convertToDto(it) }
        settingDto.estimatedArrivalTimeDto = setting.estimatedArrivalTime?.let { convertToDto(it) }
        settingDto.breakTimeDto = setting.breakTime?.let { convertToDto(it) }
        settingDto.operateStatus = setting.operateStatus?.name
        settingDto.memberId = setting.member.id

        return settingDto
    }

//    BreakTime 객체를 DTO로 변환하는 메서드
    fun convertToDto(breakTime : BreakTime) : BreakTimeDto {
        val breakTimeDto = BreakTimeDto()
        breakTimeDto.id = breakTime.id
        breakTimeDto.temporaryEndTime = breakTime.temporaryEndTime
        breakTimeDto.weekdayBreakTime = breakTime.weekdayBreakTime
        breakTimeDto.weekdayBreakTimeStart = breakTime.weekdayBreakTimeStart
        breakTimeDto.weekdayBreakTimeEnd = breakTime.weekdayBreakTimeEnd
        breakTimeDto.saturdayBreakTime = breakTime.saturdayBreakTime
        breakTimeDto.saturdayBreakTimeStart = breakTime.saturdayBreakTimeStart
        breakTimeDto.saturdayBreakTimeEnd = breakTime.saturdayBreakTimeEnd
        breakTimeDto.sundayBreakTime = breakTime.sundayBreakTime
        breakTimeDto.sundayBreakTimeStart = breakTime.sundayBreakTimeStart
        breakTimeDto.sundayBreakTimeEnd = breakTime.sundayBreakTimeEnd

        return breakTimeDto
    }

//    EstimatedArrivalTime 객체를 DTO로 변환하는 메서드
    fun convertToDto(estimatedArrivalTime : EstimatedArrivalTime) : EstimatedArrivalTimeDto {
        val estimatedArrivalTimeDto = EstimatedArrivalTimeDto()
        estimatedArrivalTimeDto.id = estimatedArrivalTime.id
        estimatedArrivalTimeDto.estimatedArrivalTime = estimatedArrivalTime.estimatedArrivalTime
        estimatedArrivalTimeDto.estimatedArrivalTimeControl = estimatedArrivalTime.estimatedArrivalTimeControl

        return estimatedArrivalTimeDto
    }

//    EstimatedCookingTime 객체를 DTO로 변환하는 메서드
    fun convertToDto (estimatedCookingTime : EstimatedCookingTime) : EstimatedCookingTimeDto {
        val estimatedCookingTimeDto = EstimatedCookingTimeDto()
        estimatedCookingTimeDto.id = estimatedCookingTime.id
        estimatedCookingTimeDto.estimatedCookingTime = estimatedCookingTime.estimatedCookingTime
        estimatedCookingTimeDto.estimatedCookingTimeControl = estimatedCookingTime.estimatedCookingTimeControl

        return estimatedCookingTimeDto
    }

//    OperateTime 객체를 DTO로 변환하는 메서드
    fun convertToDto (operateTime : OperateTime) : OperateTimeDto {
        val operateTimeDto = OperateTimeDto()
        operateTimeDto.id = operateTime.id
        operateTimeDto.temporaryOperateStartTime = operateTime.temporaryOperateStartTime
        operateTimeDto.temporaryOperateEndTime = operateTime.temporaryOperateEndTime
        operateTimeDto.weekdayAllDay = operateTime.weekdayAllDay
        operateTimeDto.weekdayStartTime = operateTime.weekdayStartTime
        operateTimeDto.weekdayEndTime = operateTime.weekdayEndTime
        operateTimeDto.saturdayAllDay = operateTime.saturdayAllDay
        operateTimeDto.saturdayStartTime = operateTime.saturdayStartTime
        operateTimeDto.saturdayEndTime = operateTime.saturdayEndTime
        operateTimeDto.sundayAllDay = operateTime.sundayAllDay
        operateTimeDto.sundayStartTime = operateTime.sundayStartTime
        operateTimeDto.sundayEndTime = operateTime.sundayEndTime

        return operateTimeDto
    }

//    ClosedDays 객체를 DTO로 변환하는 메서드
    fun convertToDto(closedDays : ClosedDays) : ClosedDaysDto {
        val closedDaysDto = ClosedDaysDto()
        closedDaysDto.id = closedDays.id
        closedDaysDto.temporaryHolidayDates = closedDays.temporaryHolidayList.map { convertToDto(it) }
        closedDaysDto.regularHolidayList = closedDays.regularHolidayList.map { convertToDto(it) }
        return closedDaysDto
    }

//    TemporaryHoliday 객체를 DTO로 변환하는 메서드
    fun convertToDto(temporaryHoliday : TemporaryHoliday) : TemporaryHolidayDto {
        val temporaryHolidayDto = TemporaryHolidayDto()
        temporaryHolidayDto.id = temporaryHoliday.id
        temporaryHolidayDto.temporaryHolidayStartDate = temporaryHoliday.temporaryHolidayStartDate
        temporaryHolidayDto.temporaryHolidayEndDate = temporaryHoliday.temporaryHolidayEndDate
        return temporaryHolidayDto
    }

//    RegularHoliday 객체를 DTO로 변환하는 메서드
    fun convertToDto(regularHoliday : RegularHoliday) : RegularHolidayDto {
        val regularHolidayDto = RegularHolidayDto()
        regularHolidayDto.id = regularHoliday.id
        regularHolidayDto.dayOfWeek = regularHoliday.dayOfWeek?.name
        regularHolidayDto.regularClosedDays = regularHoliday.regularClosedDays?.name

        return regularHolidayDto
    }

//    OperatePause 객체를 DTO로 변환하는 메서드
    fun convertToDto(operatePause : OperatePause) : OperatePauseDto {
        val operatePauseDto = OperatePauseDto()
        operatePauseDto.id = operatePause.id
        operatePauseDto.operatePauseStartTime = operatePause.operatePauseStartTime
        operatePauseDto.operatePauseEndTime = operatePause.operatePauseEndTime

//    operatePauseDuration을 계산하여 설정
       if (operatePause.operatePauseStartTime != null && operatePause.operatePauseEndTime != null) {
           val duration = Duration.between(
               operatePause.operatePauseStartTime.atDate(LocalDate.now()),
               operatePause.operatePauseEndTime.atDate(LocalDate.now())
           ).toMinutes()
           operatePauseDto.operatePauseDuration = duration
       } else {
           operatePauseDto.operatePauseDuration = null
       }

        return operatePauseDto
    }
}