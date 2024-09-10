package com.example.windowsPos2.global.filter

import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.global.jwt.JwtUtill
import com.example.windowsPos2.global.rq.Rq
import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.service.MemberService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.logging.Filter

// HTTP 요청이 들어올때마다 사용자의 토큰을 검사하는 구문
@Component
class JwtAuthenticationFilter (
    private val rq: Rq,
    private val jwtUtill : JwtUtill,
    private val jwtProvider: JwtProvider,
    private val memberService: MemberService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val path = request.requestURI
        val token = extractAccessToken(request)

//        "/api/v1/member/login" 경로를 제외
        if ("/api/v1/member/login" == path) {
            filterChain.doFilter(request, response)
            return
        }

        if (token != null) {
            if (jwtProvider.verify(token)) {
                val claims = jwtProvider.getClaims(token)!!
                val username = claims["username"] as String

                val member = memberService.findByUsername(username).orElseThrow { UsernameNotFoundException(username) }
                forceAuthentication(member)
            }
        }

        val accessToken = rq.getCookieValue("accessToken", null.toString())
        val rememberMe = rq.getCookieValue("rememberMe", null.toString())

//        accessToken이 비어있지 않고, 검증되지 않았을 경우
        if (!jwtProvider.verify(accessToken)) {

//            accessToken이 만료된 경우, refreshToken 검증 및 새 accessToken 발급
            val refreshToken = rq.getCookieValue("refreshToken", null.toString())

//            장시간 행동이 없어서 refreshToken과 accessToken이 만료됐을 때
            if ((!jwtProvider.verify(accessToken) && !jwtProvider.verify(refreshToken)) || !jwtProvider.verify(refreshToken)) {
                rq.removeCookie("accessToken")
                rq.removeCookie("refreshToken")

//                refreshtoken이 존재하고 검증이 되었을 때
            } else if (jwtProvider.verify(refreshToken)) {

//                새로운 accessToken 생성
                val accessUsername = jwtProvider.getUsername(refreshToken)!!
                val newAccessToken = jwtUtill.genAccessToken(accessUsername)

//                자동 로그인 여부에 따라 쿠키 설정
                if (rememberMe == "true") {
                    rq.setCrossDomainCookie("accessToken", newAccessToken!!, 60 * 30)
                } else {
                    rq.setCrossDomainCookie("accessToken", newAccessToken!!, -1)
                }

//                새로 발급한 토큰으로 사용자 정보 조회 및 인증 설정
                val securityUser = jwtUtill.getUserFromAccessToken(newAccessToken)
                rq.setLogin(securityUser)

//                refreshToken 토큰이 유효할 경우에
                val username = jwtProvider.getUsername(refreshToken)!!

//                새로운 refreshToken 생성
                val newRefreshToken = jwtUtill.genRefreshToken(username)

//                새 토큰 쿠키에 저장 (자동 로그인일 때)
                if (rememberMe == "true") {
                    rq.setCrossDomainCookie("refreshToken", newRefreshToken!!, 60 * 60 * 24 * 365 * 10)
                } else {
                    rq.setCrossDomainCookie("refreshToken", newRefreshToken!!, -1)
                }
            }

            if (!jwtProvider.verify(accessToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token is missing or invalid")
                return
            }

        } else if (jwtProvider.verify(accessToken)) {

            val securityUser = jwtUtill.getUserFromAccessToken(accessToken)
            rq.setLogin(securityUser)
        }
        filterChain.doFilter(request, response)
    }

//    강제로 로그인 처리하는 메서드 (세션 기반일 경우에)
    private fun forceAuthentication(member: Member) {
        val user = User(member.username, member.password, member.authorities)

        val authentication = UsernamePasswordAuthenticationToken.authenticated(
            user,
            null,
            member.authorities
        )

        // 스프링 시큐리티 내에 우리가 만든 authentication 객체를 저장할 context 생성
        val context = SecurityContextHolder.createEmptyContext()
        // context에 authentication 객체를 저장
        context.authentication = authentication
        // 스프링 시큐리티에 context를 등록
        SecurityContextHolder.setContext(context)
    }

//    싱글턴 객체로 작동하는 구문. java의 static이랑 똑같다. 쿠키에 저장되어 있는 값을 확인함
    companion object {
        fun extractAccessToken(request: HttpServletRequest): String? {
            val cookies = request.cookies
            if (cookies != null) {
                for (cookie in cookies) {
                    if (cookie.name == "accessToken") {
                        return cookie.value
                    }
                }
            }
            return null
        }

        fun extractRefreshToken(request: HttpServletRequest): String? {
            val cookies = request.cookies
            if (cookies != null) {
                for (cookie in cookies) {
                    if (cookie.name == "refreshToken") {
                        return cookie.value
                    }
                }
            }
            return null
        }

        fun extractCookieValue(request: HttpServletRequest): String? {
            val cookies = request.cookies
            if (cookies != null) {
                for (cookie in cookies) {
                    if (cookie.name == "rememberMe") {
                        return cookie.value
                    }
                }
            }
            return null
        }
    }
}