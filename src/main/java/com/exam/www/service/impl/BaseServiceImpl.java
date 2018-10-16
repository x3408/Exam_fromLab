package com.exam.www.service.impl;


import com.exam.www.entity.BackendUserDtail;
import com.exam.www.entity.User;
import com.exam.www.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseServiceImpl {
	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	//spring security获得当前用户的id
	protected User getUser(UserRepository userRepository) {
//		spring security先post登录信息到/j_spring_security_check这条URL，
// 这条URL会调用UserDetailService去检查用户是否valid，如果通过，就把user信息塞进SecurityContextHolder。
		SecurityContext context = SecurityContextHolder.getContext();
		String userId = null;
		if (context.getAuthentication() != null && context.getAuthentication().getPrincipal() != null
				&& context.getAuthentication().getPrincipal() instanceof BackendUserDtail) {
			System.out.println("SecurityContextHolder.context" + SecurityContextHolder.getContext());
			System.out.println("SecurityContextHolder.context" + SecurityContextHolder.getContext().getAuthentication());
			System.out.println("SecurityContextHolder.context" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			BackendUserDtail userDetails = (BackendUserDtail) SecurityContextHolder.getContext()
					.getAuthentication()
					.getPrincipal();
			//根据id查询user对象
			User user=userRepository.findOne(userDetails.getId());
			return user;
		}
		return null;
	}

}
