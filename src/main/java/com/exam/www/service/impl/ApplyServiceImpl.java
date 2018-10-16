package com.exam.www.service.impl;


import com.exam.www.entity.Apply;
import com.exam.www.entity.DataDictionary;
import com.exam.www.entity.User;
import com.exam.www.repository.ApplyRepository;
import com.exam.www.repository.DataDictionaryRepository;
import com.exam.www.repository.UserRepository;
import com.exam.www.service.IApplyService;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class ApplyServiceImpl extends BaseServiceImpl implements IApplyService{
	@Autowired
	private ApplyRepository applyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DataDictionaryRepository dataDictionaryRepository;
	@PersistenceContext
	private EntityManager em;


	@Override
	public List<Apply> selectTodayApplyByUId(String id, Integer state) {
		return applyRepository.selectTodayApplyByUId(id,state);
	}

	@Override
	public void produceApply(User user) {
		Apply apply=new Apply();
		apply.setCreateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
		apply.setCreateDate(new Date());
		apply.setState(0);//0---待处理
		apply.setVersion(0);
		apply.setId(UUID.randomUUID().toString());
		apply.setUser(user);
		apply.setApplyContent("请求再次参加食品安全考试");
		applyRepository.save(apply);
	}

	@Override
	public DataTableReturnObject getUserApplyPageMode(DataRequest dr, String searchUserInfo, String state, HttpSession session) {
		try {
			DataTableReturnObject j = new DataTableReturnObject();
			String fieldName = dr.getSidx();
			Sort sort = new Sort("asc".equalsIgnoreCase(dr.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC, fieldName);
			Pageable pageable = new PageRequest(dr.getPage() - 1, dr.getRows(), sort);
			List<JSONObject> list = new ArrayList<JSONObject>();
			Page<Apply> userApplyPage = applyRepository.findAll(new Specification<Apply>(){
				@Override
				public Predicate toPredicate(Root<Apply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					// 用户信息查询
					if (!StringUtils.isEmpty(searchUserInfo)) {
						Predicate p1 = cb.like(root.get("user").get("userName"), "%" + searchUserInfo + "%");
						Predicate p2 = cb.like(root.get("user").get("name"), "%" + searchUserInfo + "%");
						Predicate p3 = cb.or(p1,p2);
						list.add(p3);
					}
					// 状态查询
					if (!StringUtils.isEmpty(state)) {
						Predicate p4 = cb.equal(root.get("state").as(String.class), state);
						list.add(p4);
					}
					Predicate[] p5 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p5)));
					return null;
				}
			}, pageable);
			List<Apply> list1 = userApplyPage.getContent();
			long counts = userApplyPage.getTotalElements();
			List<DataDictionary> userApplyState = dataDictionaryRepository.getDataDictionaryByType("APPLY_STATE");
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (DataDictionary data : userApplyState) {
				map.put(Integer.valueOf(data.getCode()), data.getValue());
			}
			for (Apply apply : list1) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", apply.getId());
				jsonObject.put("userName", apply.getUser().getUserName());
				jsonObject.put("name", apply.getUser().getName());
				jsonObject.put("content",apply.getApplyContent());
				jsonObject.put("createDate",apply.getCreateDate());
				if (0==apply.getState()) {
					jsonObject.put("state", "<span style=\"color: red\">" + map.get(apply.getState()) + "</span>");
				}else if(1==apply.getState()){
					jsonObject.put("state", "<span style=\"color: limegreen\">" + map.get(apply.getState()) + "</span>");
				}else if(2==apply.getState()){
					jsonObject.put("state", "<span style=\"color: red\">" + map.get(apply.getState()) + "</span>");
				}else {
					jsonObject.put("state", map.get(apply.getState()+1));
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

	@Override
	public void deleteUserApply(String[] ids) {
		for(String id:ids){
			System.out.println("id是:"+id);
			applyRepository.delete(id);
		}
	}

	@Override
	public void updateApply(Apply apply) {
		Apply applyUpdate=applyRepository.findOne(apply.getId());
		if (applyUpdate != null) {
			applyUpdate.setState(apply.getState());
			applyUpdate.setUpdateBy(getUser(userRepository) == null ? "" : getUser(userRepository).getId());
			applyUpdate.setUpdateDate(new Date());
			applyRepository.save(applyUpdate);
		}
	}

	@Override
	public Apply getApply(String id) {
		return applyRepository.findOne(id);
	}

	/*@Override
	public void deleteUserApplyByUId(String id) {
		applyRepository.deleteUserApplyById(id);
	}*/

	@Override
	public void deleteUserApplyById(String id) {
		applyRepository.delete(id);
	}
}
