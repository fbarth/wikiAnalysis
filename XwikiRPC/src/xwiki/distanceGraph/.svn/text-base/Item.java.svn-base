package xwiki.distanceGraph;

import java.util.Date;

import xwiki.model.Page;

/**
 * 
 * @author Fabrício J. Barth 
 * @version 16, abril, 2007
 *
 */
public class Item implements Comparable{

	private String user;
	
	private Date access;
	
	private Page page;
	
	private Double distance = 0d;
		
	public Item(String user, Date access, Page page) {
		super();
		this.user = user;
		this.access = access;
		this.page = page;
	}
	
	public void setDistance(Double d){
		this.distance = d;
	}
	
	public Double getDistance(){
		return this.distance;
	}

	public Date getAccess() {
		return access;
	}

	public void setAccess(Date access) {
		this.access = access;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int compareTo(Object o) {
		return this.getAccess().compareTo(((Item)o).getAccess());
	}

}
