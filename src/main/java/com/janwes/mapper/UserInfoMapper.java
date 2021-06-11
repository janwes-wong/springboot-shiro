package com.janwes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.janwes.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mapper
 * @date 2021/5/22 20:06
 * @description
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Select("select * from user_info where username=#{username}")
    UserInfo getUserByUserName(@Param(value = "username") String username);

    UserInfo getUsers(@Param(value = "username") String username);

    UserInfo getUserList(@Param(value = "username") String username);
}
