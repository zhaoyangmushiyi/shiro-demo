package com.monochrome;

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
 * @date 2022/9/27
 */
public class ShiroRun {
    public static void main(String[] args) {
        // 1.初始化获取SecurityManager
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 2.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3.创建token对象，web应用用户名密码从页面传递
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "z3");
        // 4.完成登录
        try {
            subject.login(token);
            System.out.println("login successful");
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
