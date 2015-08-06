package com.sumur.stock.test.insert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.sumur.stock.dao.mapper.StockCompanyMapper;
import com.sumur.stock.dao.mapper.StockRoleMapper;
import com.sumur.stock.dao.mapper.StockUserMapper;
import com.sumur.stock.dao.mapper.StockUserRoleMapper;
import com.sumur.stock.entity.orm.StockCompany;
import com.sumur.stock.entity.orm.StockCompanyExample;
import com.sumur.stock.entity.orm.StockRole;
import com.sumur.stock.entity.orm.StockRoleExample;
import com.sumur.stock.entity.orm.StockUser;
import com.sumur.stock.entity.orm.StockUserExample;
import com.sumur.stock.entity.orm.StockUserRole;
import com.sumur.stock.service.StockCompanyService;
import com.sumur.stock.test.auto.mybatis.GenMain;
import com.sumur.stock.test.base.BaseTestCaseNotTransactional;

public class InitDataInsert extends BaseTestCaseNotTransactional {
	@Autowired
	private StockUserMapper stockUserMapper;
	@Autowired
	private StockRoleMapper stockRoleMapper;
	@Autowired
	private StockUserRoleMapper stockUserRoleMapper;
	@Autowired
	private StockCompanyMapper stockCompanyMapper;

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
	public void intCompany() throws IOException {
		StockCompanyExample scExample = new StockCompanyExample();
		scExample.createCriteria();
		stockCompanyMapper.deleteByExample(scExample);
		
		String szPath = "/sz.csv";
		String ssPath = "/ss.csv";
		File szFile = new File(GenMain.class.getResource(szPath).getFile());
		File ssFile = new File(GenMain.class.getResource(ssPath).getFile());
		BufferedReader szbuffer = new BufferedReader(new InputStreamReader(new FileInputStream(szFile)));
		BufferedReader ssbuffer = new BufferedReader(new InputStreamReader(new FileInputStream(ssFile)));
		String szStr = null;
		String ssStr = null;
		StockCompany sc = new StockCompany();
		while ((szStr = szbuffer.readLine()) != null) {
			String[] szs = szStr.split(",");
			if(szs.length<2){
				continue;
			}else{
				if(szs[0].length()<6){
					szs[0] = "0000000"+szs[0];
					szs[0] = szs[0].substring(szs[0].length()-6);
				}
				sc.setCode(szs[0].replaceAll("\\s", ""));
				sc.setExt("sz");
				sc.setName(szs[1].replaceAll("\\s", ""));
				stockCompanyMapper.insertSelective(sc);
			}
		}
		while ((ssStr = ssbuffer.readLine()) != null) {
			String[] sss = ssStr.split(",");
			if(sss.length<2){
				continue;
			}else{
				sc.setCode(sss[0].replaceAll("\\s", ""));
				sc.setExt("ss");
				sc.setName(sss[1].replaceAll("\\s", ""));
				stockCompanyMapper.insertSelective(sc);
			}
		}
	}
}
