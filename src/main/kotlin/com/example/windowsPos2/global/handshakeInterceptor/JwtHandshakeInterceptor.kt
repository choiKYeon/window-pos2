package com.example.windowsPos2.global.handshakeInterceptor

import com.example.windowsPos2.global.filter.JwtAuthenticationFilter.Companion.extractAccessToken
import com.example.windowsPos2.global.jwt.JwtProvider
import com.example.windowsPos2.member.service.MemberService
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.lang.Nullable
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor
import java.lang.Exception

@Component
class JwtHandshakeInterceptor (
    private val jwtProvider: JwtProvider,
    private val memberService: MemberService
): HandshakeInterceptor {

    // handshake 전에 실행
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {

        println("잘 검증 하고 있씁니다.")

//        토큰 추출 및 검증
        // ServerHttpRequest를 HttpServletRequest로 변환
//        val req : HttpServletRequest = (request as ServletServerHttpRequest).servletRequest
//
//        val token = extractAccessToken(req)

        val queryParams = request.uri.query
        val token = queryParams?.split("accessToken=")?.get(1)


        if (token != null && jwtProvider.verify(token)) {

//            토큰에서 username 추출
            val claims = jwtProvider.getClaims(token)!!
            val username = claims["username"] as String
            val member = memberService.findByUsername(username).orElseThrow { UsernameNotFoundException(username) }

//            spring security의 User 객체 생성
            val user = User(member.username, member.password, member.authorities)

//            인증된 사용자 정보를 담은 Authentication 객체 생성 및 설정
            val authentication = UsernamePasswordAuthenticationToken.authenticated(
                user,
                null,
                member.authorities
            )

//            SecurityContext에 인증된 사용자 정보 저장
            SecurityContextHolder.getContext().authentication = authentication
            return true
        }
        return false
    }

//    Handshake 이후에 실행
    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        @Nullable exception: Exception?
    ) {
    }
}