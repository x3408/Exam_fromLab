package com.exam.www.controller;


import com.exam.www.entity.Menu;
import com.exam.www.service.IMenuService;
import com.exam.www.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/score/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	/**
	 * 加载menu
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMenu",method=RequestMethod.GET)
	@ResponseBody
	public Json getMenu(HttpServletRequest request){
		Json j=new Json();
		List<Menu> httpMenuList = menuService.getMenu(request);
		j.setObj(httpMenuList);
		return j;
	}
}
