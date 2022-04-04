package com.myapp.usercenter.model.vo;

import lombok.Data;

@Data
public class RegisterVO {
    private String username;
    private String password;
    private String checkPassword;
}
