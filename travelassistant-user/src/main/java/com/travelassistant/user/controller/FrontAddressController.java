package com.travelassistant.user.controller;

import com.travelassistant.param.AddressParam;
import com.travelassistant.user.service.AddressService;
import com.travelassistant.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/17 11:10 周一
 * description: 前台地址维护controller
 */
@RestController
@RequestMapping("/user/address")
public class FrontAddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("list")
    public R list(@RequestBody Map<String,Integer> params){
        Integer userId = params.get("user_id");
        return addressService.list(userId);
    }


    @PostMapping("save")
    public R save(@RequestBody AddressParam address){

        return addressService.save(address);
    }


    @PostMapping("remove")
    public R remove(@RequestBody Map<String,Integer> params){
        Integer id = params.get("id");
        return addressService.remove(id);
    }


}
