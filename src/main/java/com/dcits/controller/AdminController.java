package com.dcits.controller;

import com.alibaba.fastjson.JSONObject;
import com.dcits.entity.PaginatedResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author nanmiaoa
 * @data 2020/10/30 15:45
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @ResponseBody
    @RequestMapping(value = "/toLogin" , method =  {RequestMethod.POST},produces = "application/json")
    public PaginatedResult toLogin(@RequestBody String jsonString){
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String name = (String) jsonObject.get("name");
        String password = (String) jsonObject.get("password");

        return new PaginatedResult();
    }
}
