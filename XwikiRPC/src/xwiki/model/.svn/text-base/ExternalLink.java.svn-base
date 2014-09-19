package xwiki.model;

public class ExternalLink extends xwiki.model.URI {

	private static final long serialVersionUID = 42L;

	private String contentType; //mime content type of the attachment (Required)

	public ExternalLink(String id, String url, String content, String contentType) {
		super(id, url, content);
		this.setContentType(contentType);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
