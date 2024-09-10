package com.example.windowsPos2.setting.service

import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.setting.entity.BreakTime
import com.example.windowsPos2.setting.entity.ClosedDays
import com.example.windowsPos2.setting.entity.EstimatedArrivalTime
import com.example.windowsPos2.setting.entity.EstimatedCookingTime
import com.example.windowsPos2.setting.entity.OperatePause
import com.example.windowsPos2.setting.entity.OperateTime
import com.example.windowsPos2.setting.entity.Setting
import com.example.windowsPos2.setting.repository.SettingRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SettingService (
    private val settingRepository: SettingRepository,
    private val memberRepository: MemberRepository
) {
//    세팅 세부 클래스 기본 설정
    @Transactional
    fun newSettingLogin(setting : Setting) {
        val operatePause = OperatePause.Builder().setting(setting).build()
        val operateTime = OperateTime.Builder().setting(setting).build()
        val estimatedCookingTime = EstimatedCookingTime.Builder().setting(setting).build()
        val estimatedArrivalTime = EstimatedArrivalTime.Builder().setting(setting).build()
        val closedDays = ClosedDays.Builder().setting(setting).build()
        val breakTime = BreakTime.Builder().setting(setting).build()
        val operateStatus = setting.operateStatus
    }
}