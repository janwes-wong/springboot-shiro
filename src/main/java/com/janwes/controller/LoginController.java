package com.janwes.controller;

import com.janwes.exception.BusinessException;
import com.janwes.exception.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.controller
 * @date 2021/5/22 20:41
 * @description
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HttpServletRequest request;

    /**
     * 前往登录页面
     *
     * @return
     */
    @RequestMapping(value = "/toLoginView", method = RequestMethod.GET)
    public String toLoginView() {
        return "login.html";
    }

    /**
     * 登录操作
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // 前端参数验证
        if (username == null || username.length() == 0 || password == null || password.length() == 0) {
            // return "login.html";
            throw new BusinessException("用户名或密码为空");
        }
        // 账号密码令牌token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        LOGGER.info(token.toString());
        // 获得当前用户到登录对象，现在状态为未认证
        Subject subject = SecurityUtils.getSubject();

        try {
            // 将令牌传到shiro提供的login方法验证，需要自定义realm
            subject.login(token);
            // 没有异常表示验证成功,进入首页
            //return "redirect:/index";
            LOGGER.info(">>> 登录成功");
            return Result.ok(">>> 登录成功");
        } catch (IncorrectCredentialsException e) {
            request.setAttribute("message", "用户名或密码不正确！");

            LOGGER.error(">>>用户名或密码不正确", e);
            return Result.errorMessage("用户名或密码不正确");
        } catch (UnknownAccountException e) {
            request.setAttribute("message", "未知账户！");

            LOGGER.error(">>> 未知账户", e);
            return Result.errorMessage("未知账户");
        } catch (LockedAccountException e) {
            request.setAttribute("message", "账户被锁定！");
            LOGGER.error(">>> 账户被锁定", e);
            throw new BusinessException(">>> 账户被锁定");
        } catch (DisabledAccountException e) {
            request.setAttribute("message", "账户被禁用！");
            LOGGER.error(">>>账户被禁用", e);
        } catch (ExcessiveAttemptsException e) {
            request.setAttribute("message", "用户名或密码错误次数太多！");
            LOGGER.error(">>>用户名或密码错误次数太多");
        } catch (AuthenticationException e) {
            request.setAttribute("message", "验证未通过！");
            LOGGER.error(">>>验证未通过");
        } catch (Exception e) {
            request.setAttribute("message", "验证未通过！");
        }
        //return "login.html";
        return Result.ok();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout() {
        // 登出清除缓存
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        // return "redirect:/toLoginView";
        return Result.ok("退出成功");
    }
}
