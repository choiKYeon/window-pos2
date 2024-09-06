package com.example.windowsPos2.setting.repository

import com.example.windowsPos2.setting.entity.EstimatedArrivalTime
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstimatedArrivalTimeRepository : JpaRepository<EstimatedArrivalTime, Long> {
}