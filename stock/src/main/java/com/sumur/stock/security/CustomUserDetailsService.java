package com.sumur.stock.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sumur.stock.dao.mapper.StockRoleMapper;
import com.sumur.stock.dao.mapper.StockUserMapper;
import com.sumur.stock.dao.mapper.StockUserRoleMapper;
import com.sumur.stock.entity.orm.StockRole;
import com.sumur.stock.entity.orm.StockRoleExample;
import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.entity.orm.StockUserExample;
import com.sumur.stock.entity.orm.StockUserRole;
import com.sumur.stock.entity.orm.StockUserRoleExample;

@SuppressWarnings("deprecation")
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private StockUserMapper stockUserMapper;

	@Autowired
	private StockRoleMapper stockRoleMapper;

	@Autowired
	private StockUserRoleMapper stockUserRoleMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		StockUserExample userExample = new StockUserExample();
		userExample.createCriteria().andUsernameEqualTo(username);
		StockUser stockUser = stockUserMapper.selectByExample(userExample).get(0);
		user = new User(stockUser.getUsername(), stockUser.getPassword(), true, true, true, true, getAuthorities(stockUser));
		return user;
	}

	/**
	 * 获取dbUser的所有权限
	 * 
	 * @param dbUser
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(StockUser stockUser) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		StockUserRoleExample ruExample = new StockUserRoleExample();
		ruExample.createCriteria().andUserIdEqualTo(stockUser.getId());
		List<StockUserRole> userRoles = stockUserRoleMapper.selectByExample(ruExample);

		List<Long> roleIds = new ArrayList<Long>(userRoles.size());
		for (StockUserRole ru : userRoles) {
			roleIds.add(ru.getRoleId());
		}

		StockRoleExample rExample = new StockRoleExample();
		rExample.createCriteria().andIdIn(roleIds);
		List<StockRole> roles = stockRoleMapper.selectByExample(rExample);

		for (StockRole r : roles) {
			authList.add(new GrantedAuthorityImpl(r.getName()));
		}
		return authList;
	}

}
