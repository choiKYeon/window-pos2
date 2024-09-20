package com.example.windowsPos2.orderManagement.service

import com.example.windowsPos2.converter.DtoConverter
import com.example.windowsPos2.converter.EntityConverter
import com.example.windowsPos2.member.service.MemberService
import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.example.windowsPos2.orderManagement.dto.OrderUpdateDto
import com.example.windowsPos2.orderManagement.entity.Menu
import com.example.windowsPos2.orderManagement.entity.MenuOption
import com.example.windowsPos2.orderManagement.entity.OrderManagement
import com.example.windowsPos2.orderManagement.entity.OrderUpdate
import com.example.windowsPos2.orderManagement.orderEnum.OrderStatus
import com.example.windowsPos2.orderManagement.orderEnum.OrderType
import com.example.windowsPos2.orderManagement.repository.OrderManagementRepository
import com.example.windowsPos2.setting.repository.SettingRepository
import com.example.windowsPos2.setting.service.OperatePauseService
import com.example.windowsPos2.setting.settingEnum.OperateStatus
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
class OrderManagementService(
    private val orderManagementRepository: OrderManagementRepository,
    private val operatePauseService: OperatePauseService,
    private val settingRepository: SettingRepository,
    private val memberService: MemberService
) {
    //    최대 주문번호 찾아서 1씩 더하는 구문
    private fun getNextOrderNumber() : Long {
        val maxOrderNumber = orderManagementRepository.findMaxOrderNumber()
        return maxOrderNumber?.plus(1) ?: 1
    }

//    주문 생성하는 구문
    @Transactional
    fun createOrder(orderManagementDto: OrderManagementDto) : OrderManagement {
        val currentMember = memberService.getCurrentMember()
        val operateStatus = settingRepository.findByMember(currentMember).get().operateStatus

        if (operateStatus == OperateStatus.END || operateStatus == OperateStatus.PAUSE || operateStatus == OperateStatus.BREAKTIME) {
            throw IllegalStateException("영업 상태가 영업 종료 또는 영업 중단 상태입니다. 주문을 생성할 수 없습니다.")
        }

        val orderNumber = getNextOrderNumber()

        //    주문 총 가격
        var totalPrice : Long = 0

        val menus = orderManagementDto.menuList.map { EntityConverter.convertToEntity(it) }

        var orderManagement = OrderManagement.Builder()
            .orderTime(LocalTime.now())
            .request(orderManagementDto.request)
            .address(orderManagementDto.address)
            .menuTotalPrice(0L)
            .totalPrice(0L)
            .deliveryFee(orderManagementDto.deliveryFee)
            .spoonFork(orderManagementDto.spoonFork)
            .orderNumber(orderNumber)
            .orderStatus(OrderStatus.WAITING)
            //            ?.let 은 null일 때는 아무것도 실행하지 않고, null이 아닐때만 실행한다.
            .orderType(orderManagementDto.orderType?.let { OrderType.valueOf(it) })
            .menuList(menus)
            .build()

//        각 메뉴와 옵션을 주문에 연결
        menus.forEach { menu ->
//            메뉴 옵션 총 가격
            var menuOptionTotalPrice : Long = 0

//            메뉴와 주문 연결
            menu.orderManagement = orderManagement

//            옵션 가격 총합 계산
            menu.menuOptions?.let { menuOptions ->
                menuOptions.forEach { menuOption ->
//                    println("여기 실행안되나?")
                    menuOptionTotalPrice += menuOption.optionPrice
//                    옵션과 메뉴 연결
                    menuOption.menu = menu
                }
            }

//            메뉴 가격과 옵션 가격 총합을 메뉴 수량에 곱하여 총 가격 계산
            val menuTotalPrice = (menu.menuPrice + menuOptionTotalPrice) * menu.menuCount
            totalPrice += menuTotalPrice
        }

    //    주문 총 가격 설정
        orderManagement.menuTotalPrice = totalPrice
        orderManagement.totalPrice = totalPrice + orderManagement.deliveryFee

        orderManagementRepository.save(orderManagement)

        return orderManagement
    }

//    객체를 사용하여 주문을 업데이트 하는 구문
    @Transactional
    fun updateOrder(orderUpdateDto: OrderUpdateDto) {
        if (orderUpdateDto == null) return

//    주문 id로 주문을 조회
        val orderManagement: OrderManagement = orderUpdateDto.id?.let { orderManagementRepository.findById(it).orElseThrow { RuntimeException("주문 못 찾음") } }
            ?: return

        val newStatus = orderUpdateDto.orderStatus?.let { OrderStatus.valueOf(it) }
            ?: throw IllegalArgumentException("주문 상태가 없습니다.")

        when (newStatus) {
            OrderStatus.IN_PROGRESS -> orderManagement.acceptOrder()
            OrderStatus.COMPLETED -> orderManagement.completeOrder()
            OrderStatus.REJECTED -> orderManagement.rejectOrder()
            OrderStatus.CANCELLED -> orderManagement.cancelOrder()
            else -> throw IllegalArgumentException("주문 상태업로드 실패.")
        }

        // 주문 업데이트
        orderUpdateDto.rejectReason?.let {
            orderManagement.orderUpdate?.rejectionReason = it
        }
        orderUpdateDto.estimatedCookingTime?.let {
            orderManagement.orderUpdate?.estimatedCookingTime = it
        }
        orderUpdateDto.estimatedArrivalTime?.let {
            orderManagement.orderUpdate?.estimatedArrivalTime = it
        }

        orderManagementRepository.save(orderManagement)
    }
}