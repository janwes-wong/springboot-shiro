package com.janwes.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.pojo
 * @date 2021/5/22 18:43
 * @description 用户信息实体类
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -2476580025516756445L;

    /**
     * 用户id
     */
    private int userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 状态（0：禁用；1：锁定；2：启用）
     */
    private int state;

    /**
     * 博客地址
     */
    private String blogUrl;

    /**
     * 博客信息
     */
    private String blogRemark;

    /**
     * 角色实体对象集合
     */
    private List<RoleInfo> roleList;
}
