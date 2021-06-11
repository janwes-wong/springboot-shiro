package com.janwes.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.pojo
 * @date 2021/5/22 18:55
 * @description 角色信息实体类
 */
@Data
public class RoleInfo {

    /**
     * 角色id
     */
    private int roleId;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限实体对象集合
     */
    private List<PermissionInfo> permissionList;
}
