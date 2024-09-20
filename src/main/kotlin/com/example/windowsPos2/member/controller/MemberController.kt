package com.example.windowsPos2.member.controller

import com.example.windowsPos2.global.filter.JwtAuthenticationFilter.Companion.extractAccessToken
import com.example.windowsPos2.global.filter.JwtAuthenticationFilter.Companion.extractCookieValue
import com.example.windowsPos2.global.filter.JwtAuthenticationFilter.Companion.extractRefreshToken
import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.global.jwt.JwtUtill
import com.example.windowsPos2.global.rq.Rq
import com.example.windowsPos2.global.rs.RsData
import com.example.windowsPos2.member.dto.MemberDto
import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.member.service.MemberService
import com.example.windowsPos2.setting.service.SettingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/member")
@Tag(name = "MemberController", description = "회원 컨트롤러 API")
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
    @Operation(summary = "로그인, 로그인 성공 시 accessToken, refreshToken 쿠키 설정")
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

//    자동 로그인하는 구문
    @Operation(summary = "자동 로그인, 자동 로그인 시 accessToken, refreshToken 쿠키 설정")
    @PostMapping("/auto-login")
    fun autoLogin(request: HttpServletRequest): ResponseEntity<RsData<LoginResponse>> {
        val checkRememberMe = extractCookieValue(request)

        if ("false".equals(checkRememberMe)) {
            return ResponseEntity.ok(RsData.of("E-1", "자동 로그인이 설정되어 있지 않습니다.", null))
        }

        val accessToken = extractAccessToken(request)
        val refreshToken = extractRefreshToken(request)

        if (accessToken != null && jwtProvider.verify(accessToken)) {
//            accessToken으로 자동 로그인 검증
            return handleSuccessfullLogin(accessToken)
        } else if (refreshToken != null && jwtProvider.verify(refreshToken)) {
//            refreshToken으로 자동 로그인 검증
            return handleSuccessfullLogin(refreshToken)
        } else {
            return ResponseEntity.status(401).body(RsData.of("E-1", "자동 로그인 실패", null))
        }
    }

//    자동 로그인 토큰으로 검증해주는 구문
    private fun handleSuccessfullLogin(token: String): ResponseEntity<RsData<LoginResponse>> {
        val username = jwtProvider.getUsername(token)!!
        val member = memberService.findByUsername(username).orElseThrow { RuntimeException("회원을 찾을 수 없습니다.") }

        if (member == null) {
            return ResponseEntity.status(401).body(RsData.of("E-1", "회원을 찾을 수 없습니다.", null))
        }

        val newAccessToken = jwtUtill.genAccessToken(username)
        val newRefreshToken = jwtUtill.genRefreshToken(username)

        val setting = memberService.newMemberLogin(member)
        settingService.newSettingLogin(setting)

        rq.setCrossDomainCookie("accessToken", newAccessToken.toString(), 60 * 60)
        rq.setCrossDomainCookie("refreshToken", newRefreshToken.toString(), 60 * 60 * 24 * 365 * 10)

        return ResponseEntity.ok(RsData.of(
            "S-1",
            "자동 로그인 성공",
            LoginResponse(username, newAccessToken.toString(), newRefreshToken.toString())
        ))
    }

//    로그아웃 구문
    @Operation(summary = "로그아웃, 로그아웃 시 accessToken, refreshToken 쿠키 제거")
    @PostMapping("/logout")
    fun logout(req : HttpServletRequest): ResponseEntity<RsData<LoginResponse>> {
        val accessToken = extractAccessToken(req)
        val refreshToken = extractRefreshToken(req)

        rq.removeCookie("accessToken")
        rq.removeCookie("refreshToken")
        rq.removeCookie("rememberMe")

        return ResponseEntity.ok(RsData.of("S-1", "로그아웃 성공", null))
    }

//    현재 로그인 유저 확인하는 구문
    @Operation(summary = "현재 로그인 유저 확인")
    @GetMapping("/login-user")
    fun loginUser(request: HttpServletRequest): ResponseEntity<RsData<LoginUser>> {
        val token = extractAccessToken(request)  // 헤더에 담긴 쿠키에서 토큰 요청

        val userId = (jwtProvider.getClaims(token!!)?.get("id") as Int).toLong()  // 헤더에 담긴 쿠키에서 토큰 요청

        val loginUser = memberService.findById(userId).orElse(null)  // Id값으로 현재 로그인 유저를 찾음

        return ResponseEntity.ok(RsData.of("S-1", "로그인 유저 확인 성공", LoginUser(loginUser)))
    }

//    토큰 갱신해주는 구문
    @Operation(summary = "토큰 갱신, 토큰 갱신 시 accessToken, refreshToken 쿠키 설정")
    @PostMapping("/refresh")
    fun refresh(request: HttpServletRequest): ResponseEntity<RsData<RefreshResponse>> {
        val refreshToken = extractRefreshToken(request)  // 헤더에 담긴 쿠키에서 토큰 요청

        val userId = (jwtProvider.getClaims(refreshToken!!)?.get("id") as Int).toLong()  // 헤더에 담긴 쿠키에서 토큰 요청

        val currentLoginUser = memberService.findById(userId).orElse(null)   // Id값으로 현재 로그인 유저를 찾음

        return if (currentLoginUser != null) {
            val newAccessToken = jwtUtill.genAccessToken(currentLoginUser.username)
            val newRefreshToken = jwtUtill.genRefreshToken(currentLoginUser.username)

            val rememberMe = extractCookieValue(request)

            if (rememberMe == "true") {
                rq.setCrossDomainCookie("accessToken", newAccessToken.toString(), 60 * 60)
                rq.setCrossDomainCookie("refreshToken", newRefreshToken.toString(), 60 * 60 * 24 * 365 * 10)
            } else {
                rq.setCrossDomainCookie("accessToken", newAccessToken.toString(), -1)
                rq.setCrossDomainCookie("refreshToken", newRefreshToken.toString(), -1)
            }

            ResponseEntity.ok(RsData.of("S-1", "토큰 갱신 성공", RefreshResponse(newAccessToken.toString(), newRefreshToken.toString())))
        } else {
            ResponseEntity.ok(RsData.of("E-1", "토큰 갱신 실패", null))
        }
    }
}