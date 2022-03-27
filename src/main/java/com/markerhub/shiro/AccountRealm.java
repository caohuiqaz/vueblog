package com.markerhub.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.markerhub.entity.User;
import com.markerhub.service.UserService;
import com.markerhub.util.JwtUtils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Console;

@Component
public class AccountRealm extends AuthorizingRealm {
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserService userService;
	
	public boolean supports(AuthenticationToken token) { 
		return token instanceof JwtToken;
	}
	
	// 授权 - 访问控制。比如某个用户是否具有某个操作的使用权限。
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;		
	}
	
	// 认证 - 用户身份识别，通常被称为用户“登录”
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		JwtToken jwtToken = (JwtToken) token;
		
		String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
		
	 	User user =	userService.getById(Long.valueOf(userId));
	 	if (user == null) {
	 		throw new UnknownAccountException("Account not exist.");
	 	}
	 	
	 	if (user.getStatus() == -1) {
	 		throw new LockedAccountException("Account is locked.");
	 	}
	 	
	 	AccountProfile profile = new AccountProfile();
	 	BeanUtil.copyProperties(user, profile);
		
		Console.log("=================");
		
		return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
	}
}
