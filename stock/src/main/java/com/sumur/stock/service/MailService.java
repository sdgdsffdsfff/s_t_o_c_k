package com.sumur.stock.service;

import java.util.Map;

import com.sumur.stock.exception.BizException;

public interface MailService {
	/**
	 * 通过模板发送包含:可变参数,文字,图片,附件 的邮件
	 * 
	 * @param subject 邮件主题
	 * @param tempName 模板名称
	 * @param params 模板中包含的参数列表 <参数名:对象>
	 * @param pics 模板中包含的图片 <图片名:图片路径>
	 * @param files 邮件的附件列表 <附件名:附件列表>
	 * @param accepters 接收者 列表
	 * @throws BizException
	 */
	public boolean send(String subject, String tempName, Map<String, Object> params, Map<String, String> pics, Map<String, String> files, String... accepters) throws BizException;

	/**
	 * 最简单的邮件发送接口
	 * 
	 * @param subject 邮件主题
	 * @param tempName 模板名称
	 * @param accepters 接收者 列表
	 * @throws BizException
	 */
	public boolean send(String subject, String tempName, String[] accepters) throws BizException;
}
