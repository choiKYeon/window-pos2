package com.example.windowsPos2.orderManagement.repository

import com.example.windowsPos2.orderManagement.entity.MenuOption
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuOptionRepository : JpaRepository<MenuOption, Long>{
}