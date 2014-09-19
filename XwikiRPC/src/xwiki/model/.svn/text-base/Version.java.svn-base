package xwiki.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Version implements Serializable{

	private static final long serialVersionUID = 42L;

	private String id; //the id of the historical page
	private Integer version; //the version of this historical page
	private String modifier; //the user who made this change
	private Date modified; //timestamp change was made
	
	public Version(HashMap v){
		this.setId((String)v.get("id"));
		this.setVersion((Integer)v.get("version"));
		this.setModifier((String)v.get("modifier"));
		this.setModified((Date)v.get("modified"));
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public String getModifier() {
		return modifier;
	}
	
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String print(){
		return this.modified+"  "+this.version;
	}
}
