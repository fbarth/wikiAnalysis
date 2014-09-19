package xwiki.model;

import java.io.Serializable;

public abstract class URI implements Serializable{

	private static final long serialVersionUID = 42L;

	protected String id; //the id of the URI
	protected String url; //the url to view this URI online
	protected String content; //the URI content
	
	protected URI(String id, String url, String content) {
		super();
		this.id = id;
		this.url = url;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
