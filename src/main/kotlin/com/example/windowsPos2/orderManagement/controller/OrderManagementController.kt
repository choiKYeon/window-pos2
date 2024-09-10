package com.example.windowsPos2.orderManagement.controller

import com.example.windowsPos2.converter.DtoConverter
import com.example.windowsPos2.global.rs.RsData
import com.example.windowsPos2.global.websocketHandler.WebSocketMessageHandler
import com.example.windowsPos2.orderManagement.dto.OrderManagementDto
import com.example.windowsPos2.orderManagement.repository.OrderManagementRepository
import com.example.windowsPos2.orderManagement.service.OrderManagementService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderManagementController (
    private val orderManagementService: OrderManagementService,
    private val orderManagementRepository: OrderManagementRepository,
    private val webSocketMessageHandler: WebSocketMessageHandler
) {

//    주문 받는 구문
    @PostMapping("/create", consumes = ["application/json"], produces = ["application/json"])
    fun createOrder(@RequestBody orderManagementDto: OrderManagementDto) : ResponseEntity<RsData<OrderManagementDto>> {

        val orderManagement = orderManagementService.createOrder(orderManagementDto)
        val orderManagementDto1 = DtoConverter.convertToDto(orderManagement)
        //        웹소켓 브로드캐스트
        webSocketMessageHandler.broadcastOrderUpdate(orderManagementDto1)
        return ResponseEntity.ok(RsData.of("S-1", "주문 생성 성공", orderManagementDto1))
    }
}