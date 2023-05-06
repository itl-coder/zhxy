package com.example.zhxy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginVO {
    private Integer id;
    private String sex;
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String username;
    private String password;
    private String userType;
    private Integer noEnabled;
    private Integer noAccountNonLocked;
    private Integer noAccountNonExpired;
    private Integer noCredentialsNonExpired;
}
