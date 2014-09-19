package xwiki.model;

import java.io.Serializable;

/**
 * Representa um usuario do ambiente colaborativo
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 17, setembro, 2006
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 42L;
	
	/*
	 * Nome do usuario
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * Provavelmente irao existir outros
	 * atributos.
	 */
	
	/**
	 * Construtor
	 */
	public User(String name){
		this.name = name;
	}

}
