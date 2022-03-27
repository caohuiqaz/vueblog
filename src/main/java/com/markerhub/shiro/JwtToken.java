package com.markerhub.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken{

	private String token;
	
	public JwtToken(String jwt) {
		this.token = jwt;
	}
	
	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return token;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return token;
	}
	
}
