package cn.org.bai.common;

import cn.org.bai.model.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bzh
 */
public class UserInfoUtil {
    public static User GetUserInfo(){
        // 获取请求/响应对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        // 登录认证
        User loginUser = (User) request.getSession().getAttribute( "LOGIN_USER" );
        return loginUser;
    }
}
