package com.exam.www.service;

import com.exam.www.entity.Menu;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by clyde on 2016/11/2.
 */
public interface IMenuService {
    public List<Menu> getMenu(HttpServletRequest request);
}
