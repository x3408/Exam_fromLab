package com.exam.www.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;

@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements
        Filter {
    // 与applicationContext-security.xml里的myFilter的属性securityMetadataSource对应�?
    // 其他的两个组件，已经在AbstractSecurityInterceptor定义
    @Autowired
//  资源源数据定义，即定义某一资源可以被哪些角色访问
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
//   访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源
    private MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
//    认证管理器，实现用户认证的入口
    private AuthenticationManager myAuthenticationManager;

    @PostConstruct
    public void init() {
        // System.err.println(" ---------------  MySecurityFilter init--------------- ");
        super.setAuthenticationManager(myAuthenticationManager);
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.mySecurityMetadataSource;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    private void invoke(FilterInvocation fi) throws IOException,
            ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }

    public void destroy() {

    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }
}