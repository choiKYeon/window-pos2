package com.example.windowsPos2.setting.repository

import com.example.windowsPos2.setting.entity.EstimatedCookingTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstimatedCookingTimeRepository : JpaRepository<EstimatedCookingTime, Long> {
}