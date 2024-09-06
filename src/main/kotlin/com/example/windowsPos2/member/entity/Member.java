package com.example.windowsPos2.member.entity;

import com.example.windowsPos2.global.baseentity.BaseEntity;
import com.example.windowsPos2.setting.entity.Setting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
            authorities.add(new SimpleGrantedAuthority("gysoft"));
        }

        return authorities;
    }
    public boolean isAdmin() {
        return "gysoft".equals(username);
    }

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Setting setting = new Setting();

}
