package com.exam.www.service;

import com.exam.www.entity.Apply;
import com.exam.www.entity.User;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IApplyService {
    /*
    根据id,state查询当天Apply
     */
    public List<Apply> selectTodayApplyByUId(String id,Integer state);
    /*
    生成申请
     */
    public void produceApply(User user);
/*
得到apply列表
 */
    public DataTableReturnObject getUserApplyPageMode(DataRequest dr, final String searchUserInfo, final String state, HttpSession session);

     /*
     根据ids删除申请
      */
     public void deleteUserApply(String []ids);
     /*
     更新apply
      */
     public void updateApply(Apply apply);
     /*
     根据id的到apply
      */
     public Apply getApply(String id);

     /*
     根据uid删除申请
      */
  /*   public void deleteUserApplyByUId(String uid);*/

  /*
  根据id删除申请
   */
       public void deleteUserApplyById(String id);
}

