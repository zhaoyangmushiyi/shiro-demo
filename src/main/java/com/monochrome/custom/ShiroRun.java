package com.monochrome.custom;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author monochrome
 * @date 2022/10/7
 */
public class ShiroRun {
    public static void main(String[] args) {
        // 1.初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:custom_shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 2.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3.创建token对象，web应用用户名密码从页面传递
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "z322");
        // 4.完成登录
        try {
            subject.login(token);
            System.out.println("login successful");
            // 判断角色
            boolean isAdmin = subject.hasRole("admin");
            System.out.println("has admin role? " + isAdmin);
            // 判断权限
            boolean isPermitted = subject.isPermitted("user:insert");
            System.out.println("has 'user:insert' permission? " + isPermitted);
            // 也可以用 checkPermission 方法，但没有返回值，没权限抛 AuthenticationException subject.checkPermission("user:select");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("user dose not exist");
        } catch (IncorrectCredentialsException e) {
            System.out.println("password is wrong");
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("login failed");
        }
    }
}
