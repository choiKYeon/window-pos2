package com.example.windowsPos2.orderManagement.repository

import com.example.windowsPos2.orderManagement.entity.OrderManagement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderUpdateRepository : JpaRepository<OrderManagement, Long> {
}