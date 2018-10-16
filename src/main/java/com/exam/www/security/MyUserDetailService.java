package com.exam.www.security;

import com.exam.www.entity.BackendUserDtail;
import com.exam.www.entity.Menu;
import com.exam.www.entity.User;
import com.exam.www.repository.MenuRepository;
import com.exam.www.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MenuRepository menuRepository;

	// 登录验证
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		//System.err.println("-----------MyUserDetailServiceImpl loadUserByUsername ----------- ");

		User user = userRepository
				.findByUserName(username);
		//System.out.println("显示："+username+">>>>>>>>>>>>>>>>>>>");
		if (user == null)
			throw new UsernameNotFoundException(username + " not exist!");
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);
		// 封装成spring security的user
		BackendUserDtail userdetail = new BackendUserDtail(user.getUserName(), user.getPassWord()==null?"":user.getPassWord(),
				true, true, true, true, grantedAuths
		);
		userdetail.setNickName(user.getName());
		BeanUtils.copyProperties(user, userdetail);

		return userdetail;
	}

	private Set<GrantedAuthority> obtionGrantedAuthorities(
			User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<Menu> menus = menuRepository.getAll();
		for (Menu menu : menus) {
			authSet.add(new SimpleGrantedAuthority("ROLE_" + menu.getMenuKey()));
		}
		return authSet;
	}
}