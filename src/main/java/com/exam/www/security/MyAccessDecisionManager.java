package com.exam.www.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * 自定义决策管理器
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
	/**
	 * 通过传递的参数来决定用户是否有访问对应受保护对象的权限
	 * @param authentication  正在请求受保护对象的authentication
	 * @param object   受保护对象
	 * @param configAttributes   与正在请求的受保护对象相关联的配置属性
	 * @throws AccessDeniedException
	 * @throws InsufficientAuthenticationException
	 */
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
//		System.out.println(authentication.getPrincipal()+"______________");
		
//		User user = (User)authentication.getPrincipal();
		
//		System.out.println(user.getUsername()+">>>>>>>>>>>>>>>>");
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			String needPermission = configAttribute.getAttribute();
			// System.out.println("needPermission is " + needPermission);
			//获得访问受保护对象所需权限信息与正在请求受保护对象的authentication的权限相比对
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		// 没有权限
		throw new AccessDeniedException(" 没有权限访问 ");
	}

	/**
	 * 表示当前AccessDecisionManager是否支持对应的ConfigAttribute
	 * @param attribute
	 * @return
	 */
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * \表示当前AccessDecisionManager是否支持对应的受保护对象类型
	 * @param clazz
	 * @return
	 */
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}