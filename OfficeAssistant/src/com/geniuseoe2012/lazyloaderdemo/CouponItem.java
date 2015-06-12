package com.geniuseoe2012.lazyloaderdemo;

import java.io.Serializable;

public class CouponItem implements Serializable{
	private static final long serialVersionUID = 3574221640526412736L;
	private String coid;
	private String fdate;
	private String sortno;
	private String sdate;
	private String edate;
	private String img;
	private String subject;

	
	
	public String getcoid() {
		return coid;
	}
	public void setcoid(String coid) {
		this.coid = coid;
	}
	public String getfdate() {
		return fdate;
	}
	public void setfdate(String fdate) {
		this.fdate = fdate;
	}
	public String getsortno() {
		return sortno;
	}
	public void sortno(String sortno) {
		this.sortno = sortno;
	}
	public String getsdate() {
		return sdate;
	}
	public void setsdate(String sdate) {
		this.sdate = sdate;
	}
	public String getedate (){
		return edate;
	}
	public void setedate(String edate) {
		this.edate = edate;
	}
	public String getimg() {
		return img;
	}
	public void img(String img) {
		this.img = img;
	}
	public String getsubject() {
		return subject;
	}
	public void subject(String subject) {
		this.subject = subject;
	}
	
	
	
	public CouponItem(String coid, String fdate, String sortno, String sdate, String edate, String img,String subject) {
		super();
	
		
		
		this.coid=coid;
		this.fdate=fdate;
		this.sortno=sortno;
		this.sdate=sdate;
		this.edate=edate;
		this.img=img;
		this.subject=subject;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	public CouponItem(){
		
	}

}
