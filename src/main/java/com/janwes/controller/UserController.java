package com.janwes.controller;

import com.janwes.exception.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.controller
 * @date 2021/6/7 17:45
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @RequiresRoles(value = "2001") // 角色验证注解
    @RequiresPermissions(value = "user:list") // 权限验证注解
    @GetMapping("/show")
    public Result getUserList() {
        try {
            return Result.ok("恭喜您有查看用户权限");
        } catch (UnauthorizedException e) {
            log.error("您的权限不足", e);
            return Result.errorMessage(401, "操作失败", "Unauthorized");
        }
    }

    @RequiresRoles(value = {"2001", "2003"}, logical = Logical.OR) // 角色验证注解 Logical.OR两者的其中一个都可以 Logical.AND全部满足
    @RequiresPermissions(value = "user:delete")
    @DeleteMapping("/delete/{userId}")
    public Result delete(@PathVariable("userId") Integer userId) {
        try {
            return Result.ok("恭喜您有删除用户权限");
        } catch (UnauthorizedException e) {
            log.error("您的权限不足", e);
            return Result.errorMessage(401, "操作失败", "Unauthorized");
        }
    }
}
