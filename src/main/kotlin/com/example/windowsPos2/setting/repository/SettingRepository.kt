package com.example.windowsPos2.setting.repository

import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.setting.entity.Setting
import com.example.windowsPos2.setting.settingEnum.OperateStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SettingRepository : JpaRepository<Setting, Long>{
    fun findByMember(member : Member) : Optional<Setting>

    fun findByOperateStatus(operateStatus: OperateStatus, pageable: Pageable)
}