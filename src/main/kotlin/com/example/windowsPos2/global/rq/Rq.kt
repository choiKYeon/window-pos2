package com.example.windowsPos2.global.rq

import com.example.windowsPos2.global.config.SecurityUser
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseCookie

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope


@Component
@RequestScope
class Rq(
    private val req: HttpServletRequest,
    private val resp: HttpServletResponse
) {

    //    refreshToken을 암호화 한 후에 저장하는 구문
    @Throws(Exception::class)
    fun setCrossDomainCookie(name: String, value: String, maxAge: Int) {
        val cookie = ResponseCookie.from(name, value)
            .path("/")
            .sameSite("None")
            .secure(true)
            .httpOnly(true)
            .maxAge(maxAge.toLong())
            .build()

        resp.addHeader("Set-Cookie", cookie.toString())
    }

    //    쿠키를 가져오는 구문
    fun getCookie(name: String): Cookie? {
        val cookies = req.cookies

        if (cookies == null) {
            return null
        }
        for (cookie in cookies) {
            if (cookie.name == name) {
                return cookie
            }
        }
        return null
    }

    //    쿠키의 이름을 기준으로 가져오는 구문
    fun getCookieValue(name: String, defaultValue: String): String {
        val cookie = getCookie(name) ?: return defaultValue

        return cookie.value
    }

    //    쿠키의 이름을 기준으로 값을 Long타입으로 반환해주는 구문
    private fun getCookieAsLong(name: String, defaultValue: Int): Long {
        val value = getCookieValue(name, null.toString())

        return value.toLong()
    }

    //    쿠키 제거하는 구문
    fun removeCookie(name: String) {
        val cookie = getCookie(name) ?: return

        cookie.path = "/"
        cookie.maxAge = 0
        resp.addCookie(cookie)

        val responseCookie = ResponseCookie.from(name, null.toString())
            .path("/")
            .maxAge(0)
            .sameSite("None")
            .secure(true)
            .httpOnly(true)
            .build()

        resp.addHeader("Set-Cookie", responseCookie.toString())
    }

    //    사용자 인증정보를 설정하는 구문
    fun setLogin(securityUser: SecurityUser) {
        SecurityContextHolder.getContext().authentication = securityUser.genAuthentication()
    }
}