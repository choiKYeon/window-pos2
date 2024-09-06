package com.example.windowsPos2.setting.repository

import com.example.windowsPos2.setting.entity.OperatePause
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OperatePauseRepository : JpaRepository<OperatePause, Long> {
}