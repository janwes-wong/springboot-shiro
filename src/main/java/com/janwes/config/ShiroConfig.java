package com.janwes.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.config
 * @date 2021/5/22 20:52
 * @description shiro配置类
 */
@Configuration
public class ShiroConfig {

    /*@Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager) {
        // ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/image/**", "anon");
        filterChainDefinitionMap.put("/toLoginView", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        // 配置退出过滤器,退出代码Shiro已经替我们实现
        // filterChainDefinitionMap.put("/logout", "logout");
        // 过滤链定义，从上向下顺序执行，/**放在最下边;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }*/


    // 配置url过滤器
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 代表哪些请求可以匿名访问
        chainDefinition.addPathDefinition("/captcha", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/druid/**", "anon");
        chainDefinition.addPathDefinition("/api/**", "anon");
        chainDefinition.addPathDefinition("/user/login", "anon");
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/user/abc","anon");
        chainDefinition.addPathDefinition("/401","anon");
        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    /**
     * 将自定义验证方式交给spring容器管理
     *
     * @return
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher()); //配置使用哈希密码匹配
        return customRealm;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     *
     * @return
     */
    /*@Bean
    public SessionsSecurityManager securityManager() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm());
        return defaultSecurityManager;
    }*/

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    /**
     * 凭证匹配器
     * 密码校验交给Shiro的SimpleAuthenticationInfo进行处理
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5"); // 散列算法
        // 数据库存储的密码字段使用HEX还是BASE64方式加密 true为HEX编号方式
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        hashedCredentialsMatcher.setHashIterations(5); // 散列迭代次数
        return hashedCredentialsMatcher;
    }

    /**
     * 异常解析器
     *
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError"); // 数据库异常处理
        mappings.setProperty("UnauthorizedException", "403");
        resolver.setExceptionMappings(mappings);
        resolver.setDefaultErrorView("error");
        resolver.setExceptionAttribute("ex");  // 缺省值"exception"
        return resolver;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
