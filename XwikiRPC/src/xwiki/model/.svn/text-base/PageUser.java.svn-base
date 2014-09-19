/*
 * Wiki2Group
 * 
 * Copyright (C) 2010  Fabrício J. Barth - http://fbarth.net.br

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xwiki.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Representa a ligacao entre os usuarios
 * e as paginas do espaco
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 17, setembro, 2006
 *
 */
public class PageUser implements Serializable{

	private static final long serialVersionUID = 42L;

	/**
	 * Singleton pattern
	 */
	
	private static PageUser INSTANCE;
	
	private PageUser(){
		itens = new HashSet<PageUserItem>();
	}
	
	public static PageUser getInstance(){
		if(INSTANCE == null)
			INSTANCE = new PageUser();
		return INSTANCE;
	}
	
	/**
	 * Retorna a instância de PageUser
	 * Usada apenas por @link xwiki.Storage#recuperarObjetos()
	 * @see xwiki.Storage#recuperarObjetos()
	 * @param p instância de PageUSer
	 */
	public static void setInstance(PageUser p){
		INSTANCE = p;
	}
	
	/**
	 * End of the singleton pattern
	 */
	
	
	/*
	 * Itens da relacao entre usuarios e paginas
	 */
	private HashSet<PageUserItem> itens;
	
	/**
	 * 
	 * @param p
	 * @param s
	 * @return
	 */
	public boolean addItem(Page p, User s){
		if(itens.add(new PageUserItem(p,s)))
			return true;
		else
			return false;
	}
	
	/**
	 * Adiciona todos os usuarios da pagina a 
	 * partir das versoes da pagina
	 * 
	 * @param p pagina
	 * @param versions ArrayList com as versoes da pagina
	 */
	public void addAllUsers(Page p, ArrayList<Version> versions){
		for(int i=0; i<versions.size(); i++){
			if(!this.containsUser(p,versions.get(i).getModifier()))
				this.addItem(p,new User(versions.get(i).getModifier()));
		}
	}
	
	/**
	 * Verifica se determinada pessoa eh usuario da pagina p
	 * 
	 * @param p pagina
	 * @param name nome da pessoa
	 * @return true se eh usuario da pagina, false caso contrario
	 */
	public boolean containsUser(Page p, String name){
		Iterator<PageUserItem> item = this.itens.iterator();
		while(item.hasNext()){
			PageUserItem i = item.next();
			if(i.getPage().getUrl().equals(p.getUrl()) &&
					i.getUser().getName().equals(name))
				return true;
		}
		return false;
	}
	
	/**
	 * Retorna todos os usuarios da pagina p
	 * 
	 * TODO precisa ser em ordem alfabetica
	 * 
	 * @param p pagina
	 * @return uma HashSet com todos os nomes dos usuarios
	 */
	public HashSet<User> getUsersByPage(Page p){
		HashSet<User> retorno = new HashSet<User>();
		Iterator<PageUserItem> puis = this.itens.iterator();
		while(puis.hasNext()){
			PageUserItem pui = puis.next();
			if(pui.getPage().getUrl().equals(p.getUrl()))
				retorno.add(pui.getUser());
		}
		return retorno;
	}
	
	/**
	 * Retorna todos os usuarios da pagina com ID igual a namePage
	 * 
	 * @param namePage id da página
	 * @return uma HashSet com todos os nomes dos usuarios
	 */
	public HashSet<User> getUsersByPage(String namePage){
		HashSet<User> retorno = new HashSet<User>();
		Iterator<PageUserItem> puis = this.itens.iterator();
		while(puis.hasNext()){
			PageUserItem pui = puis.next();
			if(pui.getPage().getId().equals(namePage))
				retorno.add(pui.getUser());
		}
		return retorno;
	}
	
	/**
	 * Retorna todas as paginas do usuario u
	 * 
	 * @param u usuario
	 * @return uma HashSet com todos os nomes das paginas
	 */
	public HashSet<Page> getPagesByUser(User u){
		HashSet<Page> retorno = new HashSet<Page>();
		Iterator<PageUserItem> puis = this.itens.iterator();
		while(puis.hasNext()){
			PageUserItem pui = puis.next();
			if(pui.getUser().getName().equals(u.getName()))
				retorno.add(pui.getPage());
		}
		return retorno;
	}
	
	/**
	 * Retorna todos os usuarios do espaco
	 * sem repeticao
	 * 
	 * @return usuarios
	 */
	public HashSet<User> getAllUsers(){
		HashSet<User> retorno = new HashSet<User>();
		Iterator<PageUserItem> pui = this.itens.iterator();
		while(pui.hasNext()){
			User u = pui.next().getUser();
			if(!usuarioExiste(u,retorno))
				retorno.add(u);
		}
		return retorno;
	}
	
	/**
	 * Verifica se existe usuario u em HashSet users
	 * 
	 * @param u usuario
	 * @param users usuarios
	 * @return true se usuario u existe em usuarios users
	 */
	private boolean usuarioExiste(User u, HashSet<User> users){
		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			if(it.next().getName().equals(u.getName()))
				return true;
		}
		return false;
	}
}

/**
 * Cada item da relacao entre usuario e pagina
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 17, setembro, 2006
 */
class PageUserItem implements Serializable{
	
	/*
	 * Neste momento eu não posso declarar esta variável.
	 * Pois alguns objetos já estão armazenados com serialVersionUID
	 * diferente.
	 * 
	 * @version 10,fevereiro,2007
	 * 
	 * TODO quando apagar os objetos em memória, deve-se remover este comentário.
	 */
	//private static final long serialVersionUID = 1l;
	
	private Page page;
	private User user;
	
	public PageUserItem(Page page, User user){
		this.page = page;
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public Page getPage(){
		return this.page;
	}
}	

