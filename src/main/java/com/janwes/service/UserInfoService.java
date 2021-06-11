package com.janwes.service;

import com.janwes.mapper.UserInfoMapper;
import com.janwes.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.service
 * @date 2021/5/22 22:15
 * @description
 */
@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserByUserName(String username) {
        return userInfoMapper.getUserByUserName(username);
    }
}
