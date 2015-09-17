package com.sumur.stock.pool.jedis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化reids连接池
 *
 */
public class RedisService {

	private static final Logger log = LoggerFactory.getLogger(RedisService.class);
	private GenericObjectPoolConfig config;
	private String masters;
	private String sentinels;
	private Integer timeout;

	public static ShardedJedisSentinelPool pool = null;

	public void init() {
		pool = new ShardedJedisSentinelPool(masters, sentinels, config, timeout);
	}

	public GenericObjectPoolConfig getConfig() {
		return config;
	}

	public void setConfig(GenericObjectPoolConfig config) {
		this.config = config;
	}

	public String getMasters() {
		return masters;
	}

	public void setMasters(String masters) {
		this.masters = masters;
	}

	public String getSentinels() {
		return sentinels;
	}

	public void setSentinels(String sentinels) {
		this.sentinels = sentinels;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

}
