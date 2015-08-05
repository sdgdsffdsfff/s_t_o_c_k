package com.sumur.stock.test.insert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.sumur.stock.dao.mapper.StockCompanyMapper;
import com.sumur.stock.dao.mapper.StockRoleMapper;
import com.sumur.stock.dao.mapper.StockUserMapper;
import com.sumur.stock.dao.mapper.StockUserRoleMapper;
import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.entity.orm.StockRole;
import com.sumur.stock.entity.orm.StockRoleExample;
import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.entity.orm.StockUserExample;
import com.sumur.stock.entity.orm.StockUserRole;
import com.sumur.stock.service.StockCompanyService;
import com.sumur.stock.test.base.BaseTestCaseNotTransactional;

public class InitDataInsert extends BaseTestCaseNotTransactional {
	@Autowired
	private StockUserMapper stockUserMapper;
	@Autowired
	private StockRoleMapper stockRoleMapper;
	@Autowired
	private StockUserRoleMapper stockUserRoleMapper;
	@Autowired
	private StockCompanyService stockCompanyService;

	@Test
	public void initUserRole() {
		StockUser user = new StockUser();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setNickname("samforit");
		user.setEmail("samforit@163.com");
		Md5PasswordEncoder md5Filter = new Md5PasswordEncoder();
		String pass = md5Filter.encodePassword(user.getPassword(), user.getUsername());
		user.setPassword(pass);
		stockUserMapper.insertSelective(user);

		StockRole role = new StockRole();
		role.setName("ROLE_ADMIN");
		stockRoleMapper.insertSelective(role);

		StockUserExample userExample = new StockUserExample();
		userExample.createCriteria().andUsernameEqualTo("admin");
		StockUser u = stockUserMapper.selectByExample(userExample).get(0);

		StockRoleExample roleExample = new StockRoleExample();
		roleExample.createCriteria().andNameEqualTo("ROLE_ADMIN");
		StockRole r = stockRoleMapper.selectByExample(roleExample).get(0);

		StockUserRole userRole = new StockUserRole();
		userRole.setRoleId(r.getId());
		userRole.setUserId(u.getId());
		stockUserRoleMapper.insertSelective(userRole);
	}

	@Test
	public void intCompany() {
		StockCompany sc = new StockCompany();
		sc.setCode("600210.ss");
		sc.setName("紫江企业");
		stockCompanyService.addCompany(sc);

		sc.setCode("600368.ss");
		sc.setName("五洲交通");
		stockCompanyService.addCompany(sc);

		sc.setCode("000409.sz");
		sc.setName("山东地矿");
		stockCompanyService.addCompany(sc);
	}
}
