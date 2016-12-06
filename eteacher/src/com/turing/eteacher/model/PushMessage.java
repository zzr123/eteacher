package com.turing.eteacher.model;

import java.util.Map;

public class PushMessage {
	
	private String title;
	private String content;
	private String show;
	private String action;
	private Map extra;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Map getExtra() {
		return extra;
	}
	public void setExtra(Map extra) {
		this.extra = extra;
	}
}
