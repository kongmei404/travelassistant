package com.travelassistant.user.controller;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;
import com.travelassistant.user.service.UserService;
import com.travelassistant.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/16 21:26 周日
 * description: 用户模块controller
 */

@RestController
@RequestMapping("user")
public class FrontUserController {

    @Autowired
    private UserService userService;


    @GetMapping("list")
    public List<User> list(){

        return userService.list();
    }


    /**
     * 后台管理调用
     * @param pageParam
     * @return
     */
    @PostMapping("/list")
    public Object listPage(@RequestBody PageParam pageParam){

        return userService.listPage(pageParam);
    }

    /**
     * 后台管理调用,删除用户数据
     * @param userId
     * @return
     */
    @PostMapping("/remove")
    public Object remove(@RequestBody Integer userId){

        return userService.remove(userId);
    }


    /**
     * 后台管理调用,修改用户数据
     * @param user
     * @return
     */
    @PostMapping("/update")
    public  Object update(@RequestBody User user){

        return userService.update(user);
    }



    @PostMapping("check")
    public R check(@RequestBody User user){

        return userService.check(user.getUserName());
    }


    @PostMapping("register")
    public R register(@RequestBody User user){

        return userService.register(user);
    }


    @PostMapping("login")
    public R login(@RequestBody User user){

        return userService.login(user);
    }
}
