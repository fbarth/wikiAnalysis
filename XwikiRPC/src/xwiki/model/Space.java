package xwiki.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Space implements Serializable{

	private static final long serialVersionUID = 42L;
	
	private String key; //the space key
	private String name; //the name of the space
	private String url; //the url to view this space online
	private String homepage; //the id of the space homepage
	private String description; //the HTML rendered space description
	
	public Space(HashMap<String, String> s)throws NullPointerException{
		this.setKey(s.get("key"));
		this.setName(s.get("name"));
		this.setUrl(s.get("url"));
		this.setHomepage(s.get("homepage"));
		this.setDescription(s.get("description"));
		
		this.pageUser = PageUser.getInstance();
	}
	
	private ArrayList<Page> pages;
	
	private PageUser pageUser;
	
	public void addAllUsers(Page p, ArrayList<Version> v){
		pageUser.addAllUsers(p,v);
	}
	
	/**
	 * Retorna todos os usuarios do espaco
	 * 
	 * @return um HashSet com todos os usuarios do espaco
	 */
	public HashSet<User> getAllUsers(){
		return PageUser.getInstance().getAllUsers();
	}
	
	/**
	 * Retorna todos os usuarios da pagina p
	 * 
	 * @param p pagina
	 * @return usuarios
	 */
	public HashSet<User> getAllUsers(Page p){
		HashSet<User> retorno = new HashSet<User>();
		return retorno;
	}
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
