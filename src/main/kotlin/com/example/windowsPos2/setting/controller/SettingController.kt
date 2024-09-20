package com.example.windowsPos2.setting.controller

import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.global.rs.RsData
import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.setting.dto.SettingDto
import com.example.windowsPos2.setting.service.SettingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/setting")
@Tag(name = "SettingController", description = "세팅 컨트롤러 API")
class SettingController (
    private val settingService: SettingService,
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository
) {

    // 세팅 정보 불러오기
    @Operation(summary = "세팅 정보 확인")
    @GetMapping("/info")
    fun getSetting(
        @CookieValue("accessToken") accessToken: String,
        @CookieValue("refreshToken") refreshToken: String
    ): ResponseEntity<RsData<SettingDto>> {
        val username = jwtProvider.getUsername(accessToken)
        val member = username?.let { memberRepository.findByUsername(it) }
        return try {
            if (member == null) {
                ResponseEntity.status(404).body(RsData.of("E-1", "사용자를 찾을 수 없습니다.", null))
            } else {
                val settingDto = settingService.getSettingByMember(member)
                ResponseEntity.ok(RsData.of("S-1", "세팅 정보 불러오기 성공", settingDto))
            }
        } catch (e: Exception) {
            ResponseEntity.ok(RsData.of("E-1", "세팅 정보 불러오기 실패", null))
        }
    }

//    세팅 정보 업데이트 하는 구문
    @Operation(summary = "세팅 정보 수정")
    @PutMapping("/update")
    fun updateSetting(@RequestParam(value = "username") username: String, @RequestBody settingDto: SettingDto): ResponseEntity<RsData<SettingDto>> {
        settingService.updateSetting(username, settingDto)
        val member = memberRepository.findByUsername(username)
        val settingDto1 = settingService.getSettingByMember(member!!)
        return ResponseEntity.ok(RsData.of("S-1", "세팅 정보 수정 성공", settingDto1))
    }
}