package com.zliteams.hot.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zliteams.hot.web.security.PermissionSign;

/**
 * 表单控制器
 * 
 **/
@Controller
@RequestMapping("/form")
public class FormController {

	 /**
     * 基于权限标识的权限控制案例
     */
    @RequestMapping(value = "/json")
    @ResponseBody
    @RequiresPermissions(value = PermissionSign.USER_CREATE)
    public Map<String, Object> json(String value) {
    	Map<String, Object> result =  new HashMap<>();
    	result.put("1", "拥有user:create权限,能访问");
    	result.put("2", value);
    	result.put("3", "ccc");
        return result;
    }
}