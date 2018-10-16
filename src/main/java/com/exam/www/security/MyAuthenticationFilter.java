package com.exam.www.security;


import com.exam.www.entity.User;
import com.exam.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 这个类主要是用户登录验证
 * 
 * @author lanyuan 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
public class MyAuthenticationFilter extends
        UsernamePasswordAuthenticationFilter {
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	/**
	 * 登录成功后跳转的地址
	 */
	private String successUrl = "";
	/**
	 * 登录失败后跳转的地址
	 */
	private String errorUrl = "/login.html";
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 定义登录成功和失败的跳转地址
	 * @author LJN
	 * Email: mmm333zzz520@163.com
	 * @date 2013-12-5 下午7:02:32
	 */
	public void init() {
//		System.err.println(" ---------------  MyAuthenticationFilter init--------------- ");
		this.setUsernameParameter(USERNAME);
		this.setPasswordParameter(PASSWORD);
		System.out.println("username>>>>>>>"+USERNAME);
		System.out.println("PASSWORD>>>>>>>"+PASSWORD);
		// 验证成功，跳转的页面
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl(successUrl);
		System.out.println("successUrl>>>>>>>"+successUrl);
		this.setAuthenticationSuccessHandler(successHandler);

		// 验证失败，跳转的页面
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setDefaultFailureUrl(errorUrl);
		System.out.println("errorUrl>>>>>>>"+errorUrl);
		this.setAuthenticationFailureHandler(failureHandler);
	}

	//执行具体的认证。
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
//		System.err.println(" ---------------  MyAuthenticationFilter attemptAuthentication--------------- ");
		
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}
		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		System.out.println(username);
		System.out.println(password);
		System.out.println(request.getParameter("kaptcha"));

		// 验证用户账号与密码是否正�?
		User account = this.userRepository.findByUserName(username);
		if (account == null || (account != null && !account.getPassWord().equals(password))) {
			BadCredentialsException exception = new BadCredentialsException(
					"账号名或密码不匹配！");// 在界面输出自定义的信息！�?
			// request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
			// exception);
			throw exception;
		}
		request.getSession().setAttribute("accountSession", account);
		// 记录登录信息
//		UserLoginList userLoginList = new UserLoginList();
//		userLoginList.setUserId(users.getUserId());
//		System.out.println("userId----" + users.getUserId() + "---ip--"
//				+ Common.toIpAddr(request));
//		userLoginList.setLoginIp(Common.toIpAddr(request));
//		userLoginListService.add(userLoginList);
		// 实现 Authentication
		System.out.println(password);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}
}
