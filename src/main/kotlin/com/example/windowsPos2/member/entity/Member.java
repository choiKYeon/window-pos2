package com.example.windowsPos2.member.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.setting.entity.Setting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 아무런 값도 갖지 않는 의미 없는 객체의 생성을 막음
//@SuperBuilder(toBuilder = true) // 기존 객체 필드를 유지하면서 일부 값만 업데이트 가능
@Entity
public class Member extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("MEMBER"));

        if (isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return authorities;
    }
    public boolean isAdmin() {
        return "gysoft".equals(username);
    }

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Setting setting;

//    @Builder
    public Member (String name, String username, String password,  Setting setting) {
        this.name = name != null ? name : this.name;
        this.username = username != null ? username : this.username;
        this.password = password != null ? password : this.password;
        this.setting = setting != null ? setting : this.setting;
    }

    public static class Builder {
        private String name;
        private String username;
        private String password;
        private Setting setting;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder setting(Setting setting) {
            this.setting = setting;
            return this;
        }

        public Member build() {
            return new Member(name, username, password, setting);
        }
    }
}
