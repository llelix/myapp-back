package com.myapp.usercenter.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myapp.usercenter.model.domain.User;
import com.myapp.usercenter.mapper.UserMapper;
import com.myapp.usercenter.service.UserService;
import com.myapp.usercenter.utils.UserConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public long userRegister(String username, String password, String checkPassword) {
        //校验
        if (StrUtil.hasBlank(username, password, checkPassword)) {
            return -1;
        }
        if (username.length() < 6) {
            return -1;
        }
        if (password.length() < 6 || checkPassword.length() < 6) {
            return -1;
        }
        if (!password.equals(checkPassword)) {
            return -1;
        }
        //匹配特殊字符串
//        String reg = ".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*";
//        Matcher matcher = Pattern.compile(reg).matcher(username);
//        if (!matcher.find()) {
//            return -1;
//        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        int count = this.count(userQueryWrapper);
        if (count > 0) {
            return -1;
        }
        //2.密码加密
        String secret = new Digester(DigestAlgorithm.MD5).digest(UserConst.salt + password).toString();
        User user2Db = new User();
        user2Db.setUsername(username);
        user2Db.setPassword(password);
        boolean save = save(user2Db);
        return 1;
    }

    @Override
    public User doLogin(String username, String password, HttpServletRequest req) {
        //校验
        //校验
        if (StrUtil.hasBlank(username, password)) {
            return null;
        }
        if (username.length() < 6) {
            return null;
        }
        if (password.length() < 6) {
            return null;
        }
        //查询用户
        //// TODO: 2022/4/4  限流
        String secret = new Digester(DigestAlgorithm.MD5).digest(UserConst.salt + password).toString();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username).eq(User::getPassword, secret);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user not found ");
            return null;
        }
        user.setPassword(null);
        //记录登陆态
        req.getSession().setAttribute("LOGIN_USER",user);
        return user;
    }

    ;
}




