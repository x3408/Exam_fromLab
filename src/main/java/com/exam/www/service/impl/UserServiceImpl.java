package com.exam.www.service.impl;


import com.exam.www.entity.DataDictionary;
import com.exam.www.entity.User;
import com.exam.www.repository.DataDictionaryRepository;
import com.exam.www.repository.UserRepository;
import com.exam.www.service.IUserService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by admin on 2016/10/11.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;


    @Override
    public User findUserByUsername(String username) {
        User user = userRepository
                .findByUserName(username);
        return user;
    }

    @Override
    public User selectExistUser(HttpSession session) {
        User user = (User) session.getAttribute("adminUser");
        return user;
    }

    @Override
    public ModelAndView setModel(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainMenu");
        return modelAndView;
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setIsDelete("0");
        String md5Password = new Md5PasswordEncoder().encodePassword(
                "123456", "");
        user.setPassWord(md5Password);
        user.setCreateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        user.setCreateDate(new Date());
        userRepository.save(user);
    }

    @Override
    public String checkRole(String url, Map<String, Collection<ConfigAttribute>> resourceMap) {
        JSONObject j = new JSONObject();
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        Collection<ConfigAttribute> configAttributes = resourceMap
                .get(url);

        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        if (configAttributes == null) {
            j.put("msg", "您没有权限访问该页面！");
            j.put("success", false);
            return j.toString();
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            String needPermission = configAttribute.getAttribute();
            // System.out.println("needPermission is " + needPermission);
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (needPermission.equals(ga.getAuthority())) {
                    j.put("success", true);
                    return j.toString();
                }
            }
        }
        j.put("msg", "您没有权限访问该页面！");
        j.put("success", false);
        return j.toString();
    }

    @Override
    public DataTableReturnObject getUserPageMode(DataRequest dr, String searchUserInfo, String state, HttpSession session, String beginDate, String overDate) {
        try {
            DataTableReturnObject j = new DataTableReturnObject();
            String fieldName = dr.getSidx();
            Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
            Pageable pageable = new PageRequest(dr.getPage() - 1, dr.getRows(), sort);
            List<JSONObject> list = new ArrayList<JSONObject>();

            Page<User> userPage = userRepository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    // 逻辑删除标示
                    List<Predicate> list = new ArrayList<Predicate>();
                    Predicate p0 = cb.equal(root.get("isDelete").as(String.class), "0");
                    Predicate p = cb.and(p0);
                    list.add(p);
                    // 用户信息查询
                    if (!StringUtils.isEmpty(searchUserInfo)) {
                        Predicate p1 = cb.like(root.get("userName").as(String.class), "%" + searchUserInfo + "%");
                        Predicate p2 = cb.like(root.get("name").as(String.class), "%" + searchUserInfo + "%");
                        Predicate p5 = cb.and(cb.or(p1, p2));
                        list.add(p5);
                    }
                    // 状态查询
                    if (!StringUtils.isEmpty(state)) {
                        Predicate p6 = cb.equal(root.get("state").as(String.class), state);
                        list.add(p6);
                    }
                    //时间查询
                    if (!StringUtils.isEmpty(beginDate)) {
                        Predicate p7 = cb.greaterThanOrEqualTo(root.get("createDate").as(String.class), beginDate);
                        list.add(p7);
                    }
                    if (!StringUtils.isEmpty(overDate)) {
                        Predicate p8 = cb.lessThanOrEqualTo(root.get("createDate").as(String.class), overDate);
                        list.add(p8);
                    }
                   /*
                   //不能是超级管理员
                    Predicate p9=cb.notEqual(root.get("isAdmin").as(Integer.class),1);
                    list.add(p9);*/
                    //不能是他自己
                    User user = (User) session.getAttribute("adminUser");
                    Predicate p9 = cb.notEqual(root.get("userName").as(String.class), user.getUserName());
                    list.add(p9);
                    Predicate[] p10 = new Predicate[list.size()];
                    query.where(cb.and(list.toArray(p10)));
                    return null;
                }
            }, pageable);
            List<User> list1 = userPage.getContent();
            long counts = userPage.getTotalElements();
            List<DataDictionary> userState = dataDictionaryRepository.getDataDictionaryByType("USER_STATE");
            List<DataDictionary> userType = dataDictionaryRepository.getDataDictionaryByType("USER_TYPE");
            Map<String, String> map = new HashMap<String, String>();
            for (DataDictionary data : userState) {
                map.put(data.getCode(), data.getValue());
            }
            int sum=map.size();//得到此时map集合中的元素个数
            for(DataDictionary data:userType){
                map.put(data.getCode(),data.getValue());
            }
            for (User user : list1) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId());
                jsonObject.put("userName", user.getUserName());
                jsonObject.put("companyName", user.getCompanyName());
                jsonObject.put("createDate", user.getCreateDate());
                jsonObject.put("name", user.getName());
                if ("1".equals(user.getState())) {
                    jsonObject.put("state", "<span style=\"color: limegreen\">" + map.get(user.getState()) + "</span>");
                } else if ("2".equals(user.getState())) {
                    jsonObject.put("state", "<span style=\"color: red\">" + map.get(user.getState()) + "</span>");
                } else if ("3".equals(user.getState())) {
                    jsonObject.put("state", "<span style=\"color: red\">" + map.get(user.getState()) + "</span>");
                } else {
                    jsonObject.put("state", map.get(user.getState()));
                }
              if(0==user.getIsAdmin()){
                  jsonObject.put("userType",map.get(String.valueOf(user.getIsAdmin()+sum+1)));
              }else if(1==user.getIsAdmin()){
                  jsonObject.put("userType","<span style=\"color: red\">" + map.get(String.valueOf(user.getIsAdmin()+sum+1)) + "</span>");
              }else if(2==user.getIsAdmin()){
                  jsonObject.put("userType", "<span style=\"color: red\">"+map.get(String.valueOf(user.getIsAdmin()+sum+1))+"</span>");
              }else{
                  jsonObject.put("userType", map.get(String.valueOf(user.getIsAdmin())));
              }
                list.add(jsonObject);
            }
            j.setAaData(list);
            j.setiTotalDisplayRecords(counts);
            j.setiTotalRecords(counts);
            return j;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    public DataTableReturnObject getUserPageMode(DataRequest dr, final String searchUserInfo, final String state, HttpSession session) {
        try {
            DataTableReturnObject j = new DataTableReturnObject();
            String fieldName = dr.getSidx();
            Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
            Pageable pageable = new PageRequest(dr.getPage() - 1, dr.getRows(), sort);
            List<JSONObject> list = new ArrayList<JSONObject>();

            Page<User> userPage = userRepository.findAll(new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    // 逻辑删除标示
                    List<Predicate> list = new ArrayList<Predicate>();
                    Predicate p0 = cb.equal(root.get("isDelete").as(String.class), "0");
                    Predicate p = cb.and(p0);
                    list.add(p);
                    // 用户信息查询
                    if (!StringUtils.isEmpty(searchUserInfo)) {
                        Predicate p1 = cb.like(root.get("userName").as(String.class), "%" + searchUserInfo + "%");
                        Predicate p2 = cb.like(root.get("name").as(String.class), "%" + searchUserInfo + "%");
                        Predicate p5 = cb.and(cb.or(p1, p2));
                        list.add(p5);
                    }
                    // 状态查询
                    if (!StringUtils.isEmpty(state)) {
                        Predicate p6 = cb.equal(root.get("state").as(String.class), state);
                        Predicate p7 = cb.and(p6);
                        list.add(p7);
                    }
                   /*
                   //不能是超级管理员
                    Predicate p9=cb.notEqual(root.get("isAdmin").as(Integer.class),1);
                    list.add(p9);*/
                    //不能是他自己
                    User user = (User) session.getAttribute("adminUser");
                    Predicate p9 = cb.notEqual(root.get("userName").as(String.class), user.getUserName());
                    list.add(p9);
                    Predicate[] p10 = new Predicate[list.size()];
                    query.where(cb.and(list.toArray(p10)));
                    return null;
                }
            }, pageable);
            List<User> list1 = userPage.getContent();
            long counts = userPage.getTotalElements();
            List<DataDictionary> userState = dataDictionaryRepository.getDataDictionaryByType("USER_STATE");
            Map<String, String> map = new HashMap<String, String>();
            for (DataDictionary data : userState) {
                map.put(data.getCode(), data.getValue());
            }
            for (User user : list1) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", user.getId());
                jsonObject.put("userName", user.getUserName());
                jsonObject.put("companyName", user.getCompanyName());
                jsonObject.put("createDate", user.getCreateDate());
                jsonObject.put("name", user.getName());
                if ("1".equals(user.getState())) {
                    /*System.out.println("状态1");
                    System.out.println(map.get(user.getState()));*/
                    jsonObject.put("state", "<span style=\"color: limegreen\">" + map.get(user.getState()) + "</span>");
                } else if ("2".equals(user.getState())) {
                   /*System.out.println("状态2");*/
                    jsonObject.put("state", "<span style=\"color: red\">" + map.get(user.getState()) + "</span>");
                } else if ("3".equals(user.getState())) {
                   /*System.out.println("状态3");*/
                    jsonObject.put("state", "<span style=\"color: red\">" + map.get(user.getState()) + "</span>");
                } else {
                    jsonObject.put("state", map.get(user.getState()));
                }
                if (user.getIsAdmin() == 0) {
                    jsonObject.put("type", "普通用户");
                } else if (user.getIsAdmin() == 1) {
                    jsonObject.put("type", "<span style=\"color: red\">作业人员</span>");
                } else if (user.getIsAdmin() == 2) {
                    jsonObject.put("type", "<span style=\"color: red\">查阅人员</span>");
                }
                list.add(jsonObject);
            }
            j.setAaData(list);
            j.setiTotalDisplayRecords(counts);
            j.setiTotalRecords(counts);
            return j;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    @Transactional
    public void saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setIsDelete("0");
        user.setIsAdmin(0);
        String md5Password = new Md5PasswordEncoder().encodePassword(
                "123456", "");
        user.setPassWord(md5Password);
        user.setCreateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        user.setCreateDate(new Date());
        userRepository.save(user);
    }


    /**
     * 逻辑删除用户
     *
     * @param ids
     */
    @Transactional
    public void deleteUser(String[] ids) {
        StringBuilder sb = new StringBuilder("update user set isDelete = '1' where id in (");
        for (String id : ids) {
            sb.append("'");
            sb.append(id);
            sb.append("'");
            sb.append(",");
        }
        if (ids != null && ids.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        Query query = em.createNativeQuery(sb.toString(), User.class);
        query.executeUpdate();
    }

    /**
     * 更新用户状态
     *
     * @param ids
     * @param state
     */
    @Transactional
    public void updateUserState(String[] ids, String state) {
        StringBuilder sb = new StringBuilder("update user set state = :state where id in (");
        for (String id : ids) {
            sb.append("'");
            sb.append(id);
            sb.append("'");
            sb.append(",");
        }
        if (ids != null && ids.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        Query query = em.createNativeQuery(sb.toString(), User.class);
        query.setParameter("state", state);
        query.executeUpdate();
    }

    public User getUser(String id) {
        return userRepository.findOne(id);
    }

    /**
     * 更新保存用户
     *
     * @param user
     */
    public void updateUser(User user) {
        User updateUser = getUser(user.getId());
        if (updateUser != null) {
            updateUser.setUserName(user.getUserName());
            updateUser.setName(user.getName());
            updateUser.setState(user.getState());
            updateUser.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
            updateUser.setUpdateDate(new Date());
            userRepository.save(updateUser);
        }
    }

    public void update(User user) {
        user.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        user.setUpdateDate(new Date());
        userRepository.updatePasswordById(user.getPassWord(), user.getId());
    }

    /**
     * 后台登录用户重置密码
     *
     * @param id
     */
    public void resetUserPassword(String id) {
        User user = getUser(id);
        String md5Password = new Md5PasswordEncoder().encodePassword("123456", user.getUserName());
        user.setPassWord(md5Password);
        user.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
        user.setUpdateDate(new Date());
        userRepository.save(user);
    }

    /**
     * 判断该手机号是否已添加注册
     *
     * @param mobilePhone
     * @param id
     * @return
     */
    public User findUserByMobilePhone(String mobilePhone, String id) {
        if (!StringUtils.isEmpty(id)) {
            return userRepository.findUserByMobilePhone(mobilePhone, id);
        }
        return userRepository.findUserByMobilePhone(mobilePhone);
    }

    @Override
    public boolean checkUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public User checkProtalLogin(String userName, String passWord) {
      /*  String md5Password = new Md5PasswordEncoder().encodePassword(passWord,userName);*/
        String md5Password = new Md5PasswordEncoder().encodePassword(
                passWord, "");
        User user = userRepository.findByUserName(userName);
       /* System.out.println("你输入的密码:"+passWord);
        System.out.println("用户原来密码:"+user.getPassWord());
        System.out.println("你加密后的密码:"+md5Password);*/
        if (!user.getPassWord().equals(md5Password)) {
            return null;
        }
        return user;
    }

    @Override
    public User findAdminUserByUserName(String userName) {
        return userRepository.findAdminUserByUserName(userName);
    }

    @Override
    public List<User> findUserList(int page, int pageSize, String userProperty, String userDirection, Map<String, Object> map) throws Exception {
        StringBuilder sql = new StringBuilder("select * from user where 1=1");
        //添加删选条件
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("createOrder_startTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate>= '" + entry.getValue().toString() + "'");
                    }
                } else if (entry.getKey().equals("createOrder_endTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate<= '" + entry.getValue().toString() + "'");
                    }
                } else if (entry.getKey().equals("isDelete")) {
                    if (entry.getKey() != null) {
                        sql.append(" and isDelete!=" + entry.getValue().toString() + "'");
                    }
                }
            }
        }
        //拼接排序条件
        if (userProperty != null && !userProperty.equals("")) {
            if (userProperty.equals("createDate")) {
                sql.append(" order by createDate" + userDirection);
            }
        }
        //选择所有
        if (page != -100 && pageSize != -100) {
            Integer offset = (page - 1) * pageSize;
            sql.append(" limit " + offset + "," + pageSize);
        }
        Query query = em.createNativeQuery(sql.toString(), User.class);
        List<User> list = query.getResultList();
        return list;
    }

    @Override
    public List<User> findList() {
        Iterable<User> iterable = userRepository.findAll();
        List<User> list = new ArrayList<>();
        for (Iterator iter = iterable.iterator(); iter.hasNext(); ) {
            User user = (User) iter.next();
            list.add(user);
        }
        return list;
    }

    @Override
    public List<User> findList(Map<String, Object> map) {
        StringBuilder sql = new StringBuilder("select * from user where 1=1 and isDelete=0");
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getKey().equals("startTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate>= '" + entry.getValue().toString() + "'");
                    }
                } else if (entry.getKey().equals("endTime")) {
                    if (entry.getValue() != null) {
                        sql.append(" and createDate<= '" + entry.getValue().toString() + "'");
                    }
                }
            }
        }
        Query query = em.createNativeQuery(sql.toString(), User.class);
        List<User> list = query.getResultList();
        return list;
    }
}
