package com.example.windowsPos2.orderManagement.controller

import com.example.windowsPos2.converter.DtoConverter
import com.example.windowsPos2.global.rs.RsData
import com.example.windowsPos2.global.websocketHandler.WebSocketMessageHandler
import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.example.windowsPos2.orderManagement.dto.OrderUpdateDto
import com.example.windowsPos2.orderManagement.entity.OrderManagement
import com.example.windowsPos2.orderManagement.orderEnum.OrderStatus
import com.example.windowsPos2.orderManagement.repository.OrderManagementRepository
import com.example.windowsPos2.orderManagement.service.OrderManagementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "OrderManagementController", description = "주문 관리 컨트롤러 API")
class OrderManagementController (
    private val orderManagementService: OrderManagementService,
    private val orderManagementRepository: OrderManagementRepository,
    private val webSoketMessageHandler: WebSocketMessageHandler
) {

//    주문 받는 구문
    @Operation(summary = "주문 생성")
    @PostMapping("/create", consumes = ["application/json"], produces = ["application/json"])
    fun createOrder(@RequestBody orderManagementDto: OrderManagementDto) : ResponseEntity<RsData<OrderManagementDto>> {

        val orderManagement = orderManagementService.createOrder(orderManagementDto)
        val orderManagementDto1 = DtoConverter.convertToDto(orderManagement)

//        웹소켓 브로드캐스트
        webSoketMessageHandler.broadcastOrderUpdate(orderManagementDto1)

        return ResponseEntity.ok(RsData.of("S-1", "주문 생성 성공", orderManagementDto1))
    }

//    주문 상태 업데이트 하는 구문
    @Operation(summary = "주문 상태 수정")
    @PutMapping("/{id}", consumes = ["application/json"], produces = ["application/json"])
    fun updateOrderState(@PathVariable("id") id: Long, @RequestBody orderUpdateDto: OrderUpdateDto): ResponseEntity<RsData<OrderManagementDto>> {
        orderUpdateDto.id = id
        orderManagementService.updateOrder(orderUpdateDto)
        val orderManagement = orderManagementRepository.findById(id).get()
        val orderManagementDto = DtoConverter.convertToDto(orderManagement)

        webSoketMessageHandler.broadcastOrderUpdate(orderManagementDto)
        return ResponseEntity.ok(RsData.of("S-1", "주문 상태 수정 성공", orderManagementDto))
    }

//    주문 전체 불러오는 구문
    @Operation(summary = "주문 리스트 확인")
    @GetMapping("/list")
    fun listOrders(): ResponseEntity<RsData<*>> {
//        오늘 날짜
        val today = LocalDate.now()
//        어제 날짜
        val yesterday = today.minusDays(1)

        val statusList = listOf(OrderStatus.WAITING, OrderStatus.IN_PROGRESS)

        val orderTodayList = orderManagementRepository.findOrdersByDate(today)

    //    하루마다 주문리스트 초기화시키지만, 주문 대기 혹은 주문 진행중인 주문 리스트는 하루가 지났더라도 받아와야함
        val pendingOrdersYesterday = orderManagementRepository.findPendingOrdersFromYesterday(yesterday, statusList)

        val allOrderList = mutableListOf<OrderManagement>()
        allOrderList.addAll(orderTodayList)
        allOrderList.addAll(pendingOrdersYesterday)

        return ResponseEntity.ok(RsData.of("S-1", "주문 리스트 불러오기 성공", allOrderList))
    }
}