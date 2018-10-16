package com.exam.www.controller;


import com.exam.www.entity.DataDictionary;
import com.exam.www.service.ISystemService;
import com.exam.www.utils.FileUploadUtil;
import com.exam.www.utils.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^1\\d{10}$";
    /**
     * 编码格式
     */
    private static String ENCODING = "UTF-8";
    /**
     * 服务http地址
     */
    private static String BASE_URI = "http://yunpian.com";
    /**
     * 服务版本号
     */
//    private static String VERSION = "v1";
    /**
     * 通用发送接口的http地址
     */
//    private static String URI_SEND_SMS = BASE_URI + "/" + VERSION + "/sms/send.json";

//    private static String apikey = "df4e8127ef95c8946cac8df0a6e85881";

    @Autowired
    private ISystemService systemService;



    /**
     * 数据字典查询
     * @param dataDictionary
     * @return
     */
    @RequestMapping(value = "/selectMenuDataByType", produces="text/html;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public String selectMenuDataByType(DataDictionary dataDictionary) {
        List<DataDictionary> dataList = systemService.getDataDictionaryByType(dataDictionary.getType());
        JSONArray json = new JSONArray();
        if (dataList != null && !dataList.isEmpty()) {
            for (DataDictionary data: dataList) {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("code", data.getCode());
                jsonObject.put("value", data.getValue());
                json.put(jsonObject);
            }
        }
        return json.toString();
    }

    /**
     * 上传文件
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Json uploadImage(@RequestParam(value = "file", required = false) MultipartFile file,
                            HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
        Json json = new Json();
        try {
            if (file.getInputStream() != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                logger.info("[SystemController]uploadImage->suffix:"+suffix);
                String urlPath = FileUploadUtil.uploadFile(file.getInputStream(), suffix);
                logger.info("[SystemController]uploadImage->urlPath:"+urlPath);
                String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
                logger.info("[SystemController]uploadImage->contextPath:"+contextPath);
                String imgUrl = contextPath + urlPath;
                logger.info("[SystemController]uploadImage->imgUrl:"+imgUrl);
                json.setSuccess(true);
                json.setMsg("");
                json.setObj(imgUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();

            json.setSuccess(false);
            json.setMsg("上传失败，服务器异常，请重试！");
            json.setObj(null);
        }

        return json;
    }




    /**
     * 找回密码页面
     * @return
     */
  /*  @RequestMapping("/findPassword")
    public ModelAndView findPassword() {
        ModelAndView model = new ModelAndView();
        model.setViewName("forgetPassword");
        return model;
    }*/

    /**
     * 判断后台用户电话是否已存在
     * @param mobilePhone
     * @param id
     * @return
     */
  /*  @RequestMapping(value = "/checkMobilePhone", method = RequestMethod.GET)
    @ResponseBody
    public Json checkMobilePhone(@RequestParam(value = "mobilePhone") String mobilePhone,
                                 @RequestParam(value = "id", required = false) String id) {
        Json j=new Json();
        boolean isMobile = Pattern.matches(REGEX_MOBILE, mobilePhone);
        if (!isMobile) {
            j.setSuccess(false);
            j.setMsg("请输入正确格式手机号码！");
            return j;
        }
        User user = userService.findUserByMobilePhone(mobilePhone, id);
        if (user != null) {
            j.setObj(user.getId());
            j.setSuccess(true);
            j.setMsg("提交成功！");
        } else {
            j.setSuccess(false);
            j.setMsg("改手机号码未被注册，请联系客服注册改手机号码！");
        }
        return j;
    }*/


    /**
     * 用户跳转重置密码页面
     * @param idUpdate
     * @param request
     * @param user
     * @return
     */
   /* @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(@RequestParam(value = "id") String idUpdate,
                                      HttpServletRequest request,
                                      @ModelAttribute("user") User user) {
        ModelAndView model = new ModelAndView();
        if (!StringUtils.isEmpty(idUpdate)) {
            User userExist = userService.getUser(idUpdate);
            if (userExist != null) {
                request.setAttribute("id", idUpdate);
                model.setViewName("resetPassword");
                user.setId(idUpdate);
                return model;
            }
        }
        model.setViewName("error");
        return model;
    }*/


    /**
     * 保存重置新密码
     * @param user
     * @param request
     * @return
     */
  /*  @RequestMapping(value = "/resetNewPassword", method = RequestMethod.POST)
    public String resetNewPassword(@ModelAttribute("user") User user, HttpServletRequest request) {
        if (!StringUtils.isEmpty(user.getId())
            && !StringUtils.isEmpty(user.getPassWord())) {
            User updateUser = userService.getUser(user.getId());
            String md5Password = new Md5PasswordEncoder().encodePassword(
                    user.getPassWord(), "");
            updateUser.setPassWord(md5Password);
            userService.update(updateUser);
            return "redirect:/login?message="+"密码修改成功";
        }
        return "redirect:/error";
    }*/


    /**
     * 判断用户验证码是否正确
     * @param mobilePhone
     * @param checkCode
     * @param id
     * @param password
     * @return
     */
   /* @RequestMapping(value = "/isCheckCodeCorrect", method = RequestMethod.POST)
    @ResponseBody
    public Json mobilePhoneSubmit(@RequestParam(value = "mobilePhone") String mobilePhone,
                                  @RequestParam(value = "checkCode") String checkCode,
                                  @RequestParam(value = "role") String role,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "password", required = false) String password) {
        Json j=new Json();
        //取出已存入数据库中的短信信息
        CheckCode codeEntity = systemService.getCheckCode(mobilePhone);
        if (codeEntity == null || !codeEntity.getCode().equals(checkCode)) {
            j.setSuccess(false);
            j.setMsg("验证码不正确！");
            return j;
        }
        long sendTime = codeEntity.getSendTime().getTime();
        long now = (new Date()).getTime();
        //时间超过7min则失效
        if (now - sendTime > 7 * 60 * 1000) {
            j.setSuccess(false);
            j.setMsg("验证码已超时！");
            return j;
        }
       if(role.equals("specialist")){
            Specialist specialist = specialistService.getSpecialistListByMobilePhone(mobilePhone).get(0);
            if (specialist == null) {
                j.setSuccess(false);
                j.setMsg("该手机号未被注册！");
                return j;
            }
            j.setSuccess(true);
            j.setObj(specialist.getId());
            return j;
        }else{
            User user = userService.findUserByMobilePhone(mobilePhone, id);
            if (user == null) {
                j.setSuccess(false);
                j.setMsg("该手机号未被注册！");
                return j;
            }
            j.setSuccess(true);
            j.setObj(user.getId());
            return j;
        }
    }
*/
    /**
     * 向手机号发送验证码
     * @param mobilePhone
     * @return
     */
   /* @RequestMapping(value = "/sendCheckCode", method = RequestMethod.GET)
    @ResponseBody
    public Json sendCheckCode(@RequestParam(value = "mobilePhone") String mobilePhone) {
        Json json=new Json();
        boolean isMobile = Pattern.matches(REGEX_MOBILE, mobilePhone);
        if (!isMobile) {
            json.setSuccess(false);
            json.setMsg("请输入正确格式手机号码！");
            return json;
        }
        Random random = new Random();
        String code = String.valueOf(random.nextInt(899999)+100000);
        // 设置对应的短信模板变量值
        String tpl_value = PropertiesUtils.getProperty("sms_text");
        tpl_value=tpl_value.replace("#code#",code);
        logger.info("[systemController]sendCheckCode->tpl_value:"+tpl_value);
//        String tpl_value = "【路演打分系统】您的验证码是" + code + "，有效时间为一分钟。如非本人操作，请忽略本短信。";

        try {
            //获得发短信使用的apikey
            String sms_apikey = PropertiesUtils.getProperty("sms_apikey");
            //调用短信接口
            String message = sendMessage(sms_apikey, tpl_value, mobilePhone);
            CheckCode checkCode = new CheckCode();
            checkCode.setMoiblePhone(mobilePhone);
            checkCode.setCode(code);
            checkCode.setSendTime(new Date());
            //保存验证码,设置发送时间等信息
            systemService.saveCheckCode(checkCode);
            json.setObj(message);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        }
        return json;
    }
*/
    /**
     * 调用发送短信接口
     * @param apikey
     * @param text
     * @param mobile
     * @return
     * @throws IOException
     */
 /*   private static String sendMessage(String apikey, String text, String mobile) throws IOException {
        HttpClient client = new HttpClient();
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("apikey", apikey);
        nameValuePairs[1] = new NameValuePair("text", text);
        nameValuePairs[2] = new NameValuePair("mobile", mobile);
        String url = PropertiesUtils.getProperty("sms_http");
        PostMethod method = new PostMethod(url);
        method.setRequestBody(nameValuePairs);
        HttpMethodParams param = method.getParams();
        param.setContentCharset(ENCODING);
        client.executeMethod(method);
        return method.getResponseBodyAsString();
    }*/
}