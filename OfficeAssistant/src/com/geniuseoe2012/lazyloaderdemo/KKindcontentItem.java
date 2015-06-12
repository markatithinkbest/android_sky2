package com.geniuseoe2012.lazyloaderdemo;

import java.io.Serializable;


public class KKindcontentItem implements Serializable{
	private static final long serialVersionUID = 3574221640526412736L;
	private String word;
	private String subject;
	private String img;
	

	public String getword() {
		return word;
	}
	public void setword(String word) {
		this.word = word;
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

	
	
	
	public KKindcontentItem( String word,String subject, String img) {
		super();
		this.word=word;
		this.img=img;
		this.subject=subject;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	

}
