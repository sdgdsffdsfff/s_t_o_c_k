package com.sumur.stock.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sumur.stock.util.LoggerUtil;
import com.sumur.stock.zookeeper.ZkClientRegister;

public class ContextInitListener implements ServletContextListener {
	Logger logger = LoggerUtil.getLogger(ContextInitListener.class);
	
	private WebApplicationContext context;
	
	public ContextInitListener() {
    	
    }


    public void contextInitialized(ServletContextEvent sce)  { 
    	context = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
    	
    	writeLog("zookeeper初始化");
    	ZkClientRegister.init();
    }


    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }
    
    private void writeLog(String log){
    	logger.info("/**************************************************************************************************/");
    	logger.info("/*    "+log+"    */");
    	logger.info("/**************************************************************************************************/");
    }
	
}
