package com.travelassistant.controller;

import com.travelassistant.param.PageParam;
import com.travelassistant.pojo.Category;
import com.travelassistant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/24 10:41 周一
 * description: 后台管理类别controller
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Object list(PageParam pageParam){

        return  categoryService.listPage(pageParam);
    }

    @PostMapping("/update")
    public Object update(Category category){

        return categoryService.update(category);
    }


    @PostMapping("/remove")
    public Object remove(Integer categoryId){

        return categoryService.remove(categoryId);
    }


    @PostMapping("/save")
    public Object save(Category category){

        return categoryService.save(category);
    }
}
