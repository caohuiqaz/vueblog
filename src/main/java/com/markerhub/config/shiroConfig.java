package com.markerhub.config;

import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SessionsSecurityManager;
//import org.apache.shiro.realm.Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.markerhub.shiro.AccountRealm;
import com.markerhub.shiro.JwtFilter;

@Configuration
public class shiroConfig {
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Bean
	public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
	    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

	    // inject redisSessionDAO
	    sessionManager.setSessionDAO(redisSessionDAO);
	    
	    // other stuff...
	    
	    return sessionManager;
	}

	@Bean
	public SessionsSecurityManager securityManager(AccountRealm accountRealm, SessionManager sessionManager, RedisCacheManager redisCacheManager) {
	    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);

	    //inject sessionManager
	    securityManager.setSessionManager(sessionManager);

	    // inject redisCacheManager
	    securityManager.setCacheManager(redisCacheManager);
	    
	    // other stuff...
	    
	    return securityManager;
	}
	

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        
        Map<String, String> filterMap = new LinkedHashMap<>();
        
        filterMap.put("/**", "jwt"); // 主要通过注解方式校验权限
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }


    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);
        
        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
        
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }


}
