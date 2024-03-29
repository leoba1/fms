package cn.org.bai.annotation;

import cn.org.bai.common.UserInfoUtil;
import cn.org.bai.model.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 登录切面
 */
@Aspect
@Component
@Order(1)
public class LoginAspect {

//    @Value("${domain}")
//    private String domain;

    @Pointcut("execution(public * cn.org.bai.controller.*.*(..)) && @annotation(cn.org.bai.annotation.Login)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        // 获取请求/响应对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String uid = UserInfoUtil.GetUserInfoByCookie();
        if (uid == null){
            response.sendRedirect( "/login.html" );
            return null;
        }
        // 登录认证
//        User loginUser = (User) request.getSession().getAttribute( "LOGIN_USER" );
//        if (loginUser == null) {
//            try {
//                response.sendRedirect( "/login.html" );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
    }
}
