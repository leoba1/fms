package cn.org.bai.controller;

import cn.org.bai.model.entity.User;
import cn.org.bai.model.dto.RegiserDto;
import cn.org.bai.service.RegisterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.authentication.AuthenticationManager;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * @author bzh
 */
@Slf4j
@CrossOrigin
@Controller
public class LoginController {

    @Value("${fs.dir}")
    private String fileDir;

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RegisterService registerService;
    /**
     * 登录提交认证
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/auth")
    public String auth(User user, HttpSession session,HttpServletResponse response) {
        if (user!=null) {

            String pwd = user.getPwd();
            String uname = user.getUname();
            // 手动进行身份验证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(uname, pwd)
            );
            // 将身份验证结果存储到SecurityContextHolder中
            SecurityContextHolder.getContext().setAuthentication(authentication);


            QueryWrapper<RegiserDto> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("username",uname).eq("password",pwd);
            RegiserDto DBuser = registerService.getOne(queryWrapper);
            user.setUid(DBuser.getUid());
            if (DBuser != null){

                //创建用户文件
                String uid = DBuser.getUid();
                String userFile = fileDir +"/"+ uid +"/";
                File file = new File(userFile);
                if (!file.exists()){
                    file.mkdir();
                }

                // 使用Cookie存储用户信息
                Cookie cookie = new Cookie("LOGIN_USER", uid);
//                cookie.setMaxAge(-1); // Cookie的生命周期，-1表示浏览器关闭即失效
                cookie.setMaxAge(3600);
                cookie.setPath("/"); // 设置Cookie的作用路径
                response.addCookie(cookie);
                session.setAttribute( "LOGIN_USER", user );
                return "redirect:/index.html";
            }
        }
        return "redirect:/login.html";
    }
}
