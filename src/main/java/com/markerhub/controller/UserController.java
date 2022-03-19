package com.markerhub.controller;

import cn.hutool.core.lang.*;
import cn.hutool.core.date.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/index")
	public Object index() {
		
		Console.log("===========>>>>>>>>", DateUtil.date());		
		
		User user = userService.getById(1);
		Console.log(user);
		return Result.succ( user);
		
	}
	
}
