package com.example.windowsPos2.orderManagement.repository

import com.example.windowsPos2.orderManagement.entity.OrderManagement
import com.example.windowsPos2.orderManagement.orderEnum.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface OrderManagementRepository : JpaRepository<OrderManagement, Long> {

//    주문 번호의 최대값을 가져옴
    @Query("SELECT MAX(o.orderNumber) FROM OrderManagement o")
    fun findMaxOrderNumber(): Long?

//    생성일이 오늘인 주문을 가져옴
    @Query("SELECT o FROM OrderManagement o WHERE DATE(o.createDate) = :today")
    fun findOrdersByDate(@Param("today") today: LocalDate) : List<OrderManagement>

//    생성일이 어제인 주문을 가져옴
    @Query("SELECT o FROM OrderManagement o WHERE DATE(o.createDate) = :yesterday AND o.orderStatus IN (:statuses)")
    fun findPendingOrdersFromYesterday(
        @Param("yesterday") yesterday: LocalDate,
        @Param("statuses") statuses: List<OrderStatus>
    ): List<OrderManagement>
}