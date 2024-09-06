package com.example.windowsPos2.global.jwtinterceptor

import com.example.windowsPos2.global.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor (
    private val jwtProvider: JwtProvider
) : HandlerInterceptor {
    override fun preHandle(request : HttpServletRequest, response: HttpServletResponse, handler : Any) : Boolean {
//        쿠키에서 토큰을 가져옵니다,
        val cookies = request.cookies
        var token: String? = null

        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == "accessToken") {
                    token = cookie.value
                    break
                }
            }
        }

        if (token != null) {
            return try {
                jwtProvider.verify(token)
                true
            } catch (e : Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                false
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
        return false
    }
}