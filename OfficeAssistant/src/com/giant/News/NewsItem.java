package com.giant.News;

public class NewsItem  {
	private String publishDate;
	private String subject;
//	private String subject_TW;
	private int rowId;
	private String app_anpic;
	private String medias;
	private String fields;
	
	
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getApp_anpic() {
		return app_anpic;
	}
	public void setApp_anpic(String app_anpic) {
		this.app_anpic = app_anpic;
	}
//	public String getSubject_TW() {
//		return subject_TW;
//	}
//	public void setSubject_TW(String subject_TW) {
//		this.subject_TW = subject_TW;
//	}	
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getMedias() {
		return medias;
	}
	public void setMedias(String medias) {
		this.medias = medias;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public NewsItem(String publishDate, String subject, String app_anpic, int rowId, String medias, String fields) {
		super();
		this.publishDate = publishDate;
		this.subject = subject;
//		this.subject_TW = subject_TW;
		this.app_anpic = app_anpic;
		this.rowId = rowId;
		this.medias = medias; 
		this.fields = fields;
	}
}
