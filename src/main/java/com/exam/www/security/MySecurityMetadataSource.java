package com.exam.www.security;


import com.exam.www.entity.Menu;
import com.exam.www.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * 加载资源与权限的对应关系
 * */
@Service
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	@Autowired
	private MenuRepository menuRepository;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	/**
	 */
	@PostConstruct
	private void loadResourceDefine() {
		// System.err.println("-----------MySecurityMetadataSource loadResourceDefine ----------- ");
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			//加载menu表中所有记录
			List<Menu> menus = menuRepository.getAll();
			for (Menu m : menus) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_"
						+ m.getMenuKey());
				configAttributes.add(configAttribute);
//				通过m.getMenuUrl---m.getMenuKey的方式来表明资源与权限之间的关系
				resourceMap.put(m.getMenuUrl(), configAttributes);
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// System.err.println("-----------MySecurityMetadataSource getAttributes ----------- ");
		//根据url找到相关的权限信息
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		// System.out.println("requestUrl is " + requestUrl);
		if (resourceMap == null) {
			loadResourceDefine();
		}
		if (requestUrl.indexOf("?") > -1) {
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
		Collection<ConfigAttribute> configAttributes = resourceMap
				.get(requestUrl);
		return configAttributes;
	}

	public Map<String, Collection<ConfigAttribute>> getResourceMap(){
		return resourceMap;
	}

}