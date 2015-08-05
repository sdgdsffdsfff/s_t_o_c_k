package com.sumur.stock.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sumur.stock.exception.BizException;
import com.sumur.stock.exception.ResponseCode;
import com.sumur.stock.service.MailService;

import freemarker.template.Template;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage templateMessage;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Override
	public boolean send(String subject, String tempName, String[] accepters) throws BizException{
		send(subject, tempName, null, null, null, accepters);
		return false;
	}

	@Override
	public boolean send(String subject, String tempName, Map<String, Object> params, Map<String, String> pics, Map<String, String> files, String... accepters) throws BizException {
		if(StringUtils.isBlank(subject) || StringUtils.isBlank(tempName) || accepters.length<1){
			throw new BizException("必填项不能为空", ResponseCode._402);
		}
		try {
			Template tpl = this.freeMarkerConfigurer.getConfiguration().getTemplate(tempName);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, params);
			MimeMessage mailMsg = this.mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMsg, true, "UTF-8");
			messageHelper.setTo(accepters);// 接收邮箱
			messageHelper.setFrom(this.templateMessage.getFrom());// 发送邮箱
			// 设置邮件主题
			if (!StringUtils.isBlank(subject)) {
				messageHelper.setSubject(subject);
			}
			// 设置邮件内容 ,true 表示启动HTML格式的邮件
			messageHelper.setText(html, true);
			// 添加邮件图片
			addPic(messageHelper, pics);
			// 添加邮件附件
			addFile(messageHelper, files);
			this.mailSender.send(mailMsg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 在邮件中添加图片,图片需在模板中预留位置
	 * @param messageHelper
	 * @param pics
	 * @throws MessagingException 
	 */
	private void addPic(MimeMessageHelper messageHelper, Map<String, String> pics) throws MessagingException {
		if (pics == null) {
			return;
		}
		Set<Entry<String, String>> picSet = pics.entrySet();
		for (Entry<String, String> entry : picSet) {
			String fileName = entry.getKey();
			String filePath = entry.getValue();
			FileSystemResource rarfile = new FileSystemResource(new File(filePath));
			messageHelper.addInline(fileName, rarfile);
		}
	}
	
	/**
	 * 在邮件中加入附件,与模板无关
	 * @param messageHelper
	 * @param files
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	private void addFile(MimeMessageHelper messageHelper, Map<String, String> files) throws UnsupportedEncodingException, MessagingException {
		if (files == null) {
			return;
		}
		Set<Entry<String, String>> picSet = files.entrySet();
		for (Entry<String, String> entry : picSet) {
			String fileName = entry.getKey();
			String filePath = entry.getValue();
			FileSystemResource rarfile = new FileSystemResource(new File(filePath));
			messageHelper.addAttachment(MimeUtility.encodeWord(fileName), rarfile);
		}
	}
}
