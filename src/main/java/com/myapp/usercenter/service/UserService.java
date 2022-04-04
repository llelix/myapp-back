package com.myapp.usercenter.service;

import com.myapp.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;


/**
 *
 */
public interface UserService extends IService<User> {
    /**
     * @param userAccount
     * @param Password
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String Password, String checkPassword);

    User doLogin(String username, String password, HttpServletRequest req);

}
