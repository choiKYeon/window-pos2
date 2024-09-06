package com.example.windowsPos2.global.config

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SecurityUser : User {
    val id: Long

//    토큰을 복호화 시켜서 해당 유저 정보를 담는 구문
    constructor(id: Long, username: String, password: String, authorities: Collection<out GrantedAuthority>)
    : super(username, password, authorities) {
        this.id = id
    }

    constructor(username: String, password: String, enabled: Boolean, accountNonExpired: Boolean, credentialsNonExpired: Boolean, accountNonLocked: Boolean, authorities: Collection<out GrantedAuthority>)
            : super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities) {
        this.id = -1
    }

//    스프링 시큐리티 내에서 사용자명, 비밀번호, 권한 목록을 받아 객체를 생성하는 구문
    fun genAuthentication(): Authentication {
        return UsernamePasswordAuthenticationToken(
            this,
            this.password,
            this.authorities
        )
    }
}