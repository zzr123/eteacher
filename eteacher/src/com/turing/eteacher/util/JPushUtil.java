package com.turing.eteacher.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turing.eteacher.model.PushMessage;
/**
 * 极光推送
 * @author lifei
 *
 */
public class JPushUtil {
	// 在极光注册上传应用的 appKey 和 masterSecret
	private static final String appKey = "5b73c7485fe5534a9262e4c4";// //必填，例如466f7032ac604e02fb7bda89
	private static final String masterSecret = "d0c4ca3f6625e81e16b09850";// 必填，每个应用都对应一个masterSecret
	private static JPushClient jpush = null;
	// 消息内容
	public static final String ALERT = "JPush Test - alert";
	
	public static String SHOW_ON = "1"; 
	public static String SHOW_OFF = "0"; 
	public static String ACTION_NOTICE_DETAIL = "1"; 
	public static String ACTION_HOMEWORK_DETAIL = "2"; 
	public static String ACTION_SIGNIN_DETAIL = "3"; 
	public static String ACTION_ALERT = "4"; 

	public static JPushClient getPushClient() {
		if (null == jpush) {
			jpush = new JPushClient(masterSecret, appKey);
		}
		return jpush;
	}
	
	public static void main(String[] args) {
		PushMessage message = new PushMessage();
		message.setAction(ACTION_HOMEWORK_DETAIL);
		message.setContent("消息内容！");
		message.setTitle("消息标题！");
		message.setShow(SHOW_ON);
		pushMessage(message);
	}
	
	/**
	 * 发送消息
	 * @param message
	 */
	private static void pushMessage(PushMessage message) {
		PushPayload payload = buildPushMessage(message);
		if (null != payload) {
			try {
				PushResult result = getPushClient().sendPush(payload);
				System.out.println("Got result - " + result);
			} catch (APIConnectionException e) {
				System.out.println("Connection error, should retry later" + e);
			} catch (APIRequestException e) {
				System.out
						.println("Should review the error, and fix the request"
								+ e);
				System.out.println("HTTP Status: " + e.getStatus());
				System.out.println("Error Code: " + e.getErrorCode());
				System.out.println("Error Message: " + e.getErrorMessage());
			}
		}
	}

	/**
	 * 向所有平台推送消息
	 * @param message
	 * @return
	 */
	public static PushPayload buildPushMessage(PushMessage message) {
			try {
				System.out.println("message:"+new ObjectMapper().writeValueAsString(message));
				return PushPayload.messageAll(new ObjectMapper().writeValueAsString(message));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	/**
	 * 快捷地构建推送对象：所有平台，所有设备的通知。
	 * 
	 * @param content
	 *            内容
	 * @return
	 */
	public static PushPayload buildPushObject_all_alert(String content) {
		return PushPayload.alertAll(ALERT);
	}

	/**
	 * 构建推送对象：平台是 Android
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param tag
	 *            设备标签
	 * @return
	 */
	public static PushPayload buildMessage_android_tag_alert(String title,
			String content, String tag) {
		return PushPayload.newBuilder().setPlatform(Platform.android())
				.setAudience(Audience.tag(tag))
				.setNotification(Notification.android(content, title, null))
				.build();
	}

	/**
	 * 构建推送对象：平台是 iOS
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param tag
	 *            设备标签
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tag_alert(String title,
			String content, String tag) {
		return PushPayload.newBuilder().setPlatform(Platform.ios())
				.setAudience(Audience.tag(tag))
				.setNotification(Notification.android(content, title, null))
				.build();
	}
}