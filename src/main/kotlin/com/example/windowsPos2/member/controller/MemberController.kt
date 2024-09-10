package com.example.windowsPos2.member.controller

import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.global.jwt.JwtUtill
import com.example.windowsPos2.global.rq.Rq
import com.example.windowsPos2.global.rs.RsData
import com.example.windowsPos2.member.dto.MemberDto
import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.member.service.MemberService
import com.example.windowsPos2.setting.service.SettingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/member")
class MemberController (
    private val memberService : MemberService,
    private val settingService : SettingService,
    private val memberRepository: MemberRepository,
    private val jwtProvider: JwtProvider,
    private val jwtUtill: JwtUtill,
    private val rq : Rq
) {

    data class LoginResponse(
        val name: String,
        val accessToken: String,
        val refreshToken: String
    )

    data class LoginUser (
        val member: Member?
    )

    data class RefreshResponse(
        val newAccessToken: String,
        val newRefreshToken: String
    )

//    로그인 구문
    @PostMapping("/login", produces = ["application/json"], consumes = ["application/json"])
    fun login(@RequestBody memberDto: MemberDto, @RequestParam(name = "rememberMe", required = false) rememberMe : Boolean): ResponseEntity<RsData<LoginResponse>> {
        val loginMember = memberService.memberCheck(memberDto.username!!, memberDto.password!!)
        val member = memberRepository.findByUsername(memberDto.username!!)

        return if (loginMember) {
            val accessToken = jwtUtill.genAccessToken(memberDto.username!!)
            val refreshToken = jwtUtill.genRefreshToken(memberDto.username!!)

            if (rememberMe) {
                rq.setCrossDomainCookie("accessToken", accessToken.toString(), 60 * 60)
                rq.setCrossDomainCookie("refreshToken", refreshToken.toString(), 60 * 60 * 24 * 365 * 10)
                rq.setCrossDomainCookie("rememberMe", "true", 60 * 60 * 24 * 365 * 10)
            } else {
                rq.setCrossDomainCookie("accessToken", accessToken.toString(), -1)
                rq.setCrossDomainCookie("refreshToken", refreshToken.toString(), -1)
                rq.setCrossDomainCookie("rememberMe", "false", -1)
            }

            val setting = memberService.newMemberLogin(member!!)
            settingService.newSettingLogin(setting)
            val loginResponse = LoginResponse(memberDto.username!!, accessToken.toString(), refreshToken.toString())

//            println("잘 되고 있습니다.")

            ResponseEntity.ok(RsData.of("S-1", "로그인 성공", loginResponse))
        } else {
            ResponseEntity.ok(RsData.of("E-1", "계정이 일치하지 않습니다", null))
        }
    }

}