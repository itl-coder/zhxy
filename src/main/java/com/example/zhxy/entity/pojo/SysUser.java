package com.example.zhxy.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements UserDetails, Serializable {
    private User user;

    // 权限集合
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        String myPassword = user.getPassword();
        // 擦除我们的密码，防止传到前端
        user.setPassword(null);
        return myPassword;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getNoAccountNonExpired().equals(1);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getNoAccountNonLocked().equals(1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getNoCredentialsNonExpired().equals(1);
    }

    @Override
    public boolean isEnabled() {
        return user.getNoEnabled().equals(1);
    }

}
