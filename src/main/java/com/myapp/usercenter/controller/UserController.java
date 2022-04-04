package com.myapp.usercenter.controller;

import com.myapp.usercenter.model.domain.User;
import com.myapp.usercenter.model.vo.LoginVO;
import com.myapp.usercenter.model.vo.RegisterVO;
import com.myapp.usercenter.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public long register(@RequestBody RegisterVO registerVO) {
        return userService.userRegister(registerVO.getUsername(),registerVO.getPassword(),registerVO.getCheckPassword());
    }
    @PostMapping("/doLogin")
    public User doLogin(@RequestBody LoginVO loginVO, HttpServletRequest req) {
        return userService.doLogin(loginVO.getUsername(),loginVO.getPassword(),req);
    }
    @GetMapping("/user")
    public String GetUsers() {
        return userService.getById(1).toString();
    }

}
