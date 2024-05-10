package com.travelassistant.controller;

import com.travelassistant.param.PageParam;
import com.travelassistant.service.CategoryService;
import com.travelassistant.param.ProductParamsString;
import com.travelassistant.pojo.Category;
import com.travelassistant.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 21:46 周一
 * description: 类别controller
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询类别集合
     * @return
     */
    @GetMapping
    public List<Category> list(){

        return  categoryService.list();
    }

    /**
     * 查询类别详情
     * @param categoryName
     * @return
     */
    @GetMapping("/{categoryName}")
    public Category detail(@PathVariable(value = "categoryName")String categoryName){

        return categoryService.detail(categoryName);
    }


    /**
     * 供搜索服务使用，根据传入的类别名称，查询类别id集合
     * @param productParamsString
     * @return
     */
    @PostMapping("/names")
    public List<Integer> names(@RequestBody ProductParamsString productParamsString){

        return categoryService.names(productParamsString);
    }


    /**
     * 后台管理调用服务
     * @param pageParam
     * @return
     */
    @PostMapping("admin/list")
    public R pageList(@RequestBody PageParam pageParam){

        return categoryService.page(pageParam);
    }


    @PostMapping("admin/update")
    public R update(@RequestBody Category category){

        return categoryService.update(category);
    }


    @PostMapping("admin/remove")
    public R remove(@RequestBody Integer categoryId){

        return categoryService.remove(categoryId);
    }


    @PostMapping("admin/save")
    public R remove(@RequestBody Category category){

        return categoryService.save(category);
    }

}
