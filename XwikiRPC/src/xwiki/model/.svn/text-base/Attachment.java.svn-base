package xwiki.model;

import java.util.Date;
import java.util.HashMap;

public class Attachment extends xwiki.model.URI {
	
	private static final long serialVersionUID = 42L;

	private String pageId; //page ID of the attachment
	private String title; //title of the attachment
	private String fileName; //file name of the attachment (Required)
	private String fileSize; //numeric file size of the attachment in bytes
	private String contentType; //mime content type of the attachment (Required)
	private Date created; //creation date of the attachment
	private String creator; //creator of the attachment
	private String comment; //comment for the attachment (Required)
	
	public Attachment(HashMap attachment) {
		super((String)attachment.get("id"), (String)attachment.get("url"), "");
		this.setPageId((String)attachment.get("pageId"));
		this.setTitle((String)attachment.get("title"));
		this.setFileName((String)attachment.get("fileName"));
		this.setFileSize((String)attachment.get("fileSize"));
		this.setContentType((String)attachment.get("contentType"));
		this.setCreated((Date)attachment.get("created"));
		this.setCreator((String)attachment.get("creator"));
		this.setComment((String)attachment.get("comment"));
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
