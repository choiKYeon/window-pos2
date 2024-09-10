package com.example.windowsPos2.member.service

import com.example.windowsPos2.member.entity.Member
import com.example.windowsPos2.member.repository.MemberRepository
import com.example.windowsPos2.setting.entity.Setting
import com.example.windowsPos2.setting.repository.SettingRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val settingRepository: SettingRepository
) {

//    처음 로그인 시 기본 세팅 설정하는 구문
    @Transactional
    fun newMemberLogin(member: Member) : Setting {
        var setting = settingRepository.findByMember(member).orElse(null)

        if (setting == null) {
            setting = Setting.Builder()
                .member(member)
                .build()
        }

        settingRepository.save(setting)
        return setting
    }

//    유저 아이디를 통해 회원을 찾는 구문
    fun findByUsername(username: String): Optional<Member> {
        val member = memberRepository.findByUsername(username)
        return Optional.ofNullable(member)
    }

//    Id를 통해 유저 조회하는 구문
    fun findById(id: Long): Optional<Member> {
        return memberRepository.findById(id)
    }

//    유저를 확인하는 구문
    fun memberCheck (username : String, password : String) : Boolean {
        val member = memberRepository.findByUsername(username)

        return if (member != null) {
            passwordEncoder.matches(password, member.password)
        } else {
            false
        }
    }

//    현재 로그인한 회원을 가져오는 구문
    fun getCurrentMember () : Member {
        val username = SecurityContextHolder.getContext().authentication.name
        return memberRepository.findByUsername(username) ?: throw UsernameNotFoundException("회원 못찾음: $username")
    }

}