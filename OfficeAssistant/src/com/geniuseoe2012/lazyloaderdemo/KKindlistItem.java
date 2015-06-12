package com.geniuseoe2012.lazyloaderdemo;

import java.io.Serializable;

public class KKindlistItem implements Serializable{
	private static final long serialVersionUID = 3574221640526412736L;
	private String fkid;
	private String subject;
	private String img;
	

	public String getfkid() {
		return fkid;
	}
	public void setfkid(String fkid) {
		this.fkid = fkid;
	}
	
	public String getsubject() {
		return subject;
	}
	public void subject(String subject) {
		this.subject = subject;
	}
	public String getimg() {
		return img;
	}
	public void img(String img) {
		this.img = img;
	}

	
	
	
	public KKindlistItem( String fkid,String subject, String img) {
		super();
		this.fkid=fkid;
		this.img=img;
		this.subject=subject;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	

}
