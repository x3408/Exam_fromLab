package com.exam.www.interceptor;

import com.exam.www.entity.BackendUserDtail;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/12/30.
 */
public class LoginInterceptor implements HandlerInterceptor {
    private  static  final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext context=SecurityContextHolder.getContext();
        Authentication authentication=context.getAuthentication();
        logger.info("context path: "+request.getContextPath());
        if(authentication != null && authentication.getPrincipal() instanceof BackendUserDtail){
            return true;
        }else{
            response.sendRedirect(request.getContextPath()+"/login?notLogin");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
