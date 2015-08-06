package com.sumur.stock.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;;
/**
 * 引入Spring配置文件，以便注入,这里用到了spring test这个测试框架.
 * @author Li mao sen
 */
@ContextConfiguration(locations = { 
		"classpath:/xml/application-config.xml", 
		"classpath:/xml/application-mybatis.xml", 
		"classpath:/xml/application-mail.xml", 
		"classpath:/xml/application-thread-pool.xml"})
// 继承AbstractTransactionalJUnit4SpringContextTests，实现事务回滚
public abstract class BaseTestCaseNotTransactional extends AbstractJUnit4SpringContextTests {
	
}