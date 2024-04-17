package com.travelassistant.controller;

import com.travelassistant.service.UserService;
import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;
import com.travelassistant.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/22 14:30 周六
 * description: 前台用户模块
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("list")
    @ResponseBody
    public Object list(PageParam pageParam){

        return userService.listPage(pageParam);
    }


    @PostMapping("remove")
    @ResponseBody
    public Object remove(Integer userId){

        if (userId == null){
            return R.fail("删除失败!");
        }
        return userService.remove(userId);
    }


    @PostMapping("update")
    @ResponseBody
    public Object update(User user){

        return userService.update(user);
    }


    @PostMapping("save")
    @ResponseBody
    public Object save(User user){

        return userService.save(user);
    }

}
