package com.sumur.stock.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class LoggerUtil {

	private static final Map<Class, Logger> LOGGERMAP;

	public static Logger getLogger(Class clazz) {
		Logger logger = (Logger) LOGGERMAP.get(clazz);
		if (null == logger)
			logger = LoggerFactory.getLogger(clazz);

		return logger;
	}

	static {
		LOGGERMAP = new ConcurrentHashMap<Class, Logger>();
	}

}
