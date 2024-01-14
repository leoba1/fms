package cn.org.bai.controller;

import cn.org.bai.common.Result;
import cn.org.bai.model.dto.RegiserDto;
import cn.org.bai.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bzh
 */
@Slf4j
@CrossOrigin
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Result<?> loginPage(@RequestBody RegiserDto regiserDto) {
        String username = regiserDto.getUsername();

        RegiserDto user = registerService.getUserByName(username);
        if (user != null){
            return Result.error("0","用户名已注册");
        }

        boolean saved = registerService.save(regiserDto);
        return Result.success();
    }
}
