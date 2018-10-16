package com.exam.www.controller;

import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * Created by admin on 2017/2/7.
 */
public abstract class BaseController {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

  /*  @Autowired
    private ISpecialistService specialistService;

    *//**
     * 添加Model消息
     */
    protected void addMessage(HttpServletRequest request, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        request.setAttribute("message", sb.toString());
    }

   /* public String getSnsApiUserInfoUrl(HttpServletRequest request, String authorizedOriginalUrl) {
        StringBuffer urlPath = request.getRequestURL();
        String tempContextUrl = urlPath.delete(urlPath.length() - request.getRequestURI().length(), urlPath.length()).append(request.getSession().getServletContext().getContextPath()).toString();
        logger.info("tempContextUrl: "+tempContextUrl);
        logger.info("authorizedOriginalUrl: "+authorizedOriginalUrl);
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        url = url.replace("APPID", WeChatUtil.getProperties().getProperty("appid"));
        //url = url.replace("REDIRECT_URI", urlEnodeUTF8(tempContextUrl + "/wechat/specialist/authorizedRedirect"));
        logger.info("REDIRECT_URI: "+(tempContextUrl + authorizedOriginalUrl));
        url = url.replace("REDIRECT_URI", urlEnodeUTF8(tempContextUrl + authorizedOriginalUrl));
        logger.info("redirect: "+url);
        return url;
    }
*/


    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
