package com.example.windowsPos2.orderManagement.repository

import com.example.windowsPos2.orderManagement.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : JpaRepository<Menu, Long> {
}