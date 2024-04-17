package com.travelassistant.user.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.User;
import com.travelassistant.user.constants.UserConstants;
import com.travelassistant.user.mapper.UserMapper;
import com.travelassistant.user.service.UserService;
import com.travelassistant.utils.MD5Util;
import com.travelassistant.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * projectName: b2c_cloud_store
 *
 * @author: 邱绍峰
 * time: 2024/03/16 21:31 周日
 * description:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        List<User> users = userMapper.selectList(null);
        log.info("UserServiceImpl.list业务结束，结果:{}",users);
        return users;
    }

    /**
     * 检查账号是否可用
     *
     * @param userName
     * @return
     */
    @Override
    public R check(String userName) {

        //1.账号非空校验
        if (StringUtils.isEmpty(userName)){
            log.info("UserServiceImpl.check业务开始，参数:{}",userName);
            return R.fail("账号为null,不可用!");
        }
        //2.数据库查询
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);
        Long count = userMapper.selectCount(queryWrapper);

        //3.结果处理
        log.info("UserServiceImpl.check业务结束，结果:{}",count);

        if (count > 0 ){

            return R.fail("账号已经存在,不可用!");
        }

        return R.ok("账号不存在,可以使用!");
    }

    /**
     * 进行账号注册
     *
     * @param user 参数没有校验
     * @return
     */
    @Override
    public R register(User user) {

        //1.参数校验
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword()))
        {
            log.info("UserServiceImpl.register业务结束，结果:{}",user);
            return R.fail("账号或者密码为null,注册失败!");
        }
        //2.数据库查询
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",user.getUserName());
        Long count = userMapper.selectCount(queryWrapper);

        if (count>0) {
            log.info("UserServiceImpl.register业务结束，结果:{}",count);
            return R.fail("账号已经存在,不可用!");
        }
        //3.数据库插入

        //代码加密处理,注意加盐,生成常量
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);

        user.setPassword(newPwd);

        int rows = userMapper.insert(user);
        //4.结果处理
        if (rows > 0){
            log.info("UserServiceImpl.register业务结束，注册成功,结果:{}",rows);
            return R.ok("注册成功!");
        }

        return R.fail("账号已经存在,不可用!");
    }

    /**
     * 进行账号登录
     *
     * @param user
     * @return
     */
    @Override
    public R login(User user) {

        //1.参数校验
        //1.参数校验
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword()))
        {
            log.info("UserServiceImpl.login业务结束，结果:{}",user);
            return R.fail("账号或者密码为null,登录失败!");
        }

        //2.数据库查询
        //代码加密处理,注意加盐,生成常量
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        queryWrapper.eq("password",user.getPassword());

        User loginUser = userMapper.selectOne(queryWrapper);

        //3.结果封装

        if (loginUser == null) {
            log.info("UserServiceImpl.login业务结束，登录失败,结果:{}",loginUser);
            return R.fail("账号或者密码错误,登录失败!");
        }

        //设置为null,配合NoN_NULL注解,不返回给前端
        loginUser.setPassword(null);
        //注意修改 user的别名
        log.info("UserServiceImpl.login业务结束，登录成功,结果:{}",loginUser);
        return R.ok("登录成功!",loginUser);
    }

    /**
     * 分页数据查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public Object listPage(PageParam pageParam) {

        int currentPage = pageParam.getCurrentPage();
        int pageSize = pageParam.getPageSize();

        //设置分页属性
        IPage<User> page = new Page<>(currentPage,pageSize);
        page = userMapper.selectPage(page, null);

        //结果封装
        long total = page.getTotal();
        List<User> records = page.getRecords();

        R ok = R.ok("查询成功!", records, total);

        log.info("UserServiceImpl.listPage业务结束，结果:{}",ok);

        return ok;
    }

    /**
     * 删除用户数据
     *
     * @param userId
     * @return
     */
    @Override
    public Object remove(Integer userId) {

        int rows = userMapper.deleteById(userId);

        log.info("UserServiceImpl.remove业务结束，结果:{}",rows);

        if (rows > 0){
            return R.ok("删除用户数据成功!");
        }
        return R.fail("删除用户数据失败!");
    }

    /**
     * 修改用户密码
     *
     * @param user
     * @return
     */
    @Override
    public Object update(User user) {

        //检查密码,如果和数据库一致 不需要加密! 证明密码没有修改!
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        queryWrapper.eq("password",user.getPassword());
        Long total = userMapper.selectCount(queryWrapper);

        if (total == 0){
           //密码不同,已经修改! 新密码需要加密
           user.setPassword(MD5Util.encode(user.getPassword()+ com.travelassistant.constants.UserConstants.USER_SLAT));
        }

        int rows = userMapper.updateById(user);

        if (rows == 0){
            return R.fail("用户修改失败!");
        }
        return R.fail("用户修改成功");
    }
}
