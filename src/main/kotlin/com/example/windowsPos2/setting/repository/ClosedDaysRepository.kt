package com.example.windowsPos2.setting.repository

import com.example.windowsPos2.setting.entity.ClosedDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClosedDaysRepository : JpaRepository<ClosedDays, Long> {
}