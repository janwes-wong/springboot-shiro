package com.janwes.controller;

import com.janwes.pojo.UserInfo;
import com.janwes.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.controller
 * @date 2021/5/22 22:09
 * @description
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public UserInfo getUserByUserName(String username) {
        return userInfoService.getUserByUserName(username);
    }
}
