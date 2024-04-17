package com.travelassistant.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.travelassistant.clients.ProductClient;
import com.travelassistant.mapper.CategoryMapper;
import com.travelassistant.param.PageParam;
import com.travelassistant.service.CategoryService;
import com.travelassistant.param.ProductParamsString;
import com.travelassistant.pojo.Category;
import com.travelassistant.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 21:48 周一
 * description: 类别业务集合
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductClient productClient;
    /**
     * 查询类别数据
     * @return 类别集合
     */
    @Override
    public List<Category> list() {

        List<Category> categories = categoryMapper.selectList(null);

        //最多返回12条数据
        List<Category> list = categories.stream().limit(categories.size() > 12
                ? 12 : categories.size()).collect(Collectors.toList());

        log.info("CategoryServiceImpl.list业务结束，结果:{}",list);
        return list;
    }

    /**
     * 类别详情查询
     *
     * @param categoryName
     * @return
     */
    @Override
    public Category detail(String categoryName) {

        //参数判断
        if (StringUtils.isEmpty(categoryName)){
            //如果没有默认类型,给一个手机类型
            categoryName = "手机";
        }
        //数据库查询
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",categoryName);
        Category category = categoryMapper.selectOne(queryWrapper);
        //返回对象
        log.info("CategoryServiceImpl.detail业务结束，结果:{}",category);
        return category;
    }

    /**
     * 类别名称查询,类别id集合
     *
     * @param productParamsString
     * @return 类别id集合
     */
    @Override
    public List<Integer> names(ProductParamsString productParamsString) {

        List<Integer> ids = new ArrayList<>();
        //获取类别名称
        List<String> categoryName = productParamsString.getCategoryName();
        //判断返回
        if (categoryName == null || categoryName.size() == 0){
            log.info("CategoryServiceImpl.names业务结束，没有类别名称!结果:{}",ids);
            return ids;
        }


        for (String s : categoryName) {
            System.err.println(s);
        }

        //查询数据库
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_name",categoryName);
        queryWrapper.select("category_id");
        List<Object> list = categoryMapper.selectObjs(queryWrapper);

        //结果封装
        Integer[] idsArray = list.toArray(new Integer[]{});
        ids = Arrays.asList(idsArray);

        log.info("CategoryServiceImpl.names业务结束，结果:{}",ids);
        return ids;
    }

    /**
     * 分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public R page(PageParam pageParam) {

        //分页参数
        IPage<Category> page = new Page<>(pageParam.getCurrentPage()
                                ,pageParam.getPageSize());
        //查询参数获取
        page = categoryMapper.selectPage(page, null);

        List<Category> records = page.getRecords();
        long total = page.getTotal();

        R r = R.ok("查询类别数据成功!", records, total);

        log.info("CategoryServiceImpl.page业务结束，结果:{}",r);

        return r;
    }

    /**
     * 修改类别名
     *
     * @param category
     * @return
     */
    @Override
    public R update(Category category) {

        int rows = categoryMapper.updateById(category);

        if (rows > 0){
            return R.ok("类别修改成功!");
        }

        return R.fail("类别修改失败!");
    }

    /**
     * 删除对应的类别! 需要判断是否被引用
     *
     * @param categoryId
     * @return
     */
    @Override
    public R remove(Integer categoryId) {

        //调用商品服务,查询类别对应的商品数量
        long count = productClient.count(categoryId);
        //判断数量,如果有引用,不能删除,反之可以删除
        if (count > 0){

            return R.fail("无法删除类别,有:"+count+"件商品引用!");
        }

        int rows = categoryMapper.deleteById(categoryId);

        if (rows == 0){

            return R.fail("删除类别失败!");
        }
        return R.ok("类别删除成功!");
    }

    /**
     * 保存类别数据
     *
     * @param category
     * @return
     */
    @Override
    public R save(Category category) {

        int rows = categoryMapper.insert(category);

        if (rows > 0){
            return R.ok("类别保存成功!");
        }

        return R.fail("类别保存失败!");
    }
}
