package com.myapp.usercenter.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {
    @Resource
    private  UserService userService;


    @Test
    void testUser(){
//        User user = new User();
//        user.setUsername("lee");
//        boolean result = userService.save(user);
//        Assertions.assertTrue(result);
        System.out.println(userService.getById("1"));
    }
}