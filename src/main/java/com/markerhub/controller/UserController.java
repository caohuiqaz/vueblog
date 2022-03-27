package com.markerhub.controller;

import cn.hutool.core.lang.*;
import cn.hutool.core.date.*;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@RequiresAuthentication
	@GetMapping("/index")
	public Result index() {
		
		Console.log("111===========>>>>>>>>{}", DateUtil.date());
		
		User user = userService.getById(1);
		Console.log(user);
		return Result.succ( user);
		
	}

	@PostMapping("/save")
	public Result save (@Validated @RequestBody User user) {

		return Result.succ(user);
	}
}
