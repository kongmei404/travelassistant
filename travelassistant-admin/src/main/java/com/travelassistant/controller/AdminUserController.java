package com.travelassistant.controller;

import com.travelassistant.param.AdminUserParam;
import com.travelassistant.pojo.AdminUser;
import com.travelassistant.service.AdminUserService;
import com.travelassistant.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 10:10 周六
 * description: 后台管理 user controller
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class AdminUserController {


    @Autowired
    private AdminUserService adminUserService;


    @GetMapping("logout")
    @ResponseBody
    public Object logout(HttpSession session){
        //清空session
        session.invalidate();

        return R.ok("退出登录成功!");
    }

    /**
     * 后台登录功能实现
     * @param adminUserParam
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public Object login(@Validated AdminUserParam adminUserParam, BindingResult bindingResult, HttpSession session){

        //参数校验
        if (bindingResult.hasErrors()) {
            log.info("AdminUserController.login业务,参数异常!");
            return R.fail("登录失败,核心参数为null");
        }
        //校验验证码
        String varCode = adminUserParam.getVerCode();
        String captcha = (String) session.getAttribute("captcha");
        if (!varCode.equalsIgnoreCase(captcha)){

            return R.fail("登录失败,验证码错误!");
        }
        //验证码通过验证数据
        R r = adminUserService.login(adminUserParam);

        //获取数据存储到session共享域,后期登录访问判断
        AdminUser adminUser = (AdminUser) r.getData();
        //存储到session共享域  key = userInfo 其他页面固定读取
        session.setAttribute("userInfo",adminUser);
        return r;
    }




}
