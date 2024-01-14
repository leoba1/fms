package cn.org.bai.controller;

import cn.org.bai.model.entity.User;
import cn.org.bai.model.dto.RegiserDto;
import cn.org.bai.service.RegisterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
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
    private RegisterService registerService;
    /**
     * 登录提交认证
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/auth")
    public String auth(User user, HttpSession session) {
        if (user!=null) {

            String pwd = user.getPwd();
            String uname = user.getUname();

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

                session.setAttribute( "LOGIN_USER", user );
                return "redirect:/index.html";
            }
        }
        return "redirect:/login.html";
    }
}
