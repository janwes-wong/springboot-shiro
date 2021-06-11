package com.janwes.config;

import com.janwes.mapper.UserInfoMapper;
import com.janwes.pojo.PermissionInfo;
import com.janwes.pojo.RoleInfo;
import com.janwes.pojo.UserInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.config
 * @date 2021/5/22 20:19
 * @description 自定义Realm域
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoMapper userInfoMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRealm.class);

    /**
     * 权限配置（授权）
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("* * * * * 开始授权 * * * * *");
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null");
        }
        // 1. 获取用户信息
        String username = (String) principalCollection.getPrimaryPrincipal();
        //UserInfo userInfo = userInfoMapper.getUserByUserName(username);
        UserInfo userInfo = userInfoMapper.getUsers(username);
        // 2. 获取角色实体对象
        List<RoleInfo> roleList = userInfo.getRoleList();
        // 3. 创建授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 4. 遍历角色对象，并添加角色信息和权限信息
        if (roleList != null && roleList.size() > 0) {
            for (RoleInfo role : roleList) {
                // 添加角色信息
                simpleAuthorizationInfo.addRole(role.getRoleCode());
                // 添加权限信息
                List<PermissionInfo> permissionList = role.getPermissionList();
                if (permissionList != null && permissionList.size() > 0) {
                    for (PermissionInfo permissionInfo : permissionList) {
                        simpleAuthorizationInfo.addStringPermission(permissionInfo.getPermissionCode());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("* * * * * 开始认证 * * * * *");
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        // 获取用户输入的账户
        String username = authenticationToken.getPrincipal().toString();
        LOGGER.info("身份：" + username);
        LOGGER.info("凭证：" + authenticationToken.getCredentials());

        // 获取用户信息
        UserInfo userInfo = userInfoMapper.getUserByUserName(username);
        if (userInfo == null) {
            throw new UnknownAccountException("未知账号"); // 未知账号
        }

        // 判断账号是否被锁定，状态（0：禁用；1：锁定；2：启用）
        if (userInfo.getState() == 0) {
            throw new DisabledAccountException("账号禁用"); // 账号禁用
        }

        if (userInfo.getState() == 1) {
            throw new LockedAccountException("账号禁用");
        }

        // 验证 数据库密码需要经过加密
        return new SimpleAuthenticationInfo(
                username, // 用户名
                userInfo.getPassword(), // 密码
                ByteSource.Util.bytes(userInfo.getSalt()), // 盐
                this.getName());// realm name
    }
}