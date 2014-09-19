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

package xwiki.retrieval;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import xwiki.model.Space;

/**
 * Armazena informacao sobre todos os 
 * spaces recuperados durante uma secao de
 * trabalho
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 14, Setembro, 2006
 *
 */
public class Spaces implements Serializable{

	private static final long serialVersionUID = 42L;
	
	private ArrayList<Space> spaces;
	
	/**
	 * singleton pattern
	 */
	private static Spaces INSTANCE;
	
	private Spaces(){
		spaces = new ArrayList<Space>();
	}
	
	public static Spaces getInstance(){
		if(INSTANCE==null){
			INSTANCE = new Spaces();
			return INSTANCE;
		}else{
			return INSTANCE;
		}
	}
	
	/*
	 * Foi necessário uma pequena alteração
	 * do padrão para implementar a serialização
	 * de objetos
	 */
	public static void setInstance(Spaces s){
		INSTANCE = s;
	}
	
	/**
	 * end of singleton pattern
	 */
	
	/**
	 * Adiciona um conjunto de spaces se eles ainda
	 * nao existirem em memoria
	 * 
	 * @param conjunto de spaces
	 */
	public void addSpaces(ArrayList<Space> spaces){
		Iterator<Space> it = spaces.iterator();
		while(it.hasNext())
			addSpace(it.next());
	}
	
	/**
	 * Adiciona um unico space ao conjunto de
	 * Spaces se o space informado ainda nao existir
	 * no conjunto de Spaces
	 * 
	 * @param space espaco que deve ser inserido no
	 * conjunto de Spaces
	 */
	public void addSpace(Space space){
		System.out.println("#### "+space.getUrl()+" ####");
		if(!containSpace(space.getUrl())){
			this.spaces.add(space);
		}
			
	}
	
	/**
	 * Verifica se um determinado space jah estah armazenado no
	 * conjunto de Spaces
	 * 
	 * @param url descricao da url do espaco
	 * @return true se o espaco jah estiver armazenado no conjunto
	 * de Spaces e false senao
	 */
	private boolean containSpace (String url){
		Iterator<Space> it = this.spaces.iterator();
		while(it.hasNext())
			if(it.next().getUrl().equalsIgnoreCase(url))
				return true;
		return false;
	}
	
	/**
	 * Retorna todos os espacos armazenados em memoria
	 * @return ArrayList com os Spaces
	 */
	public ArrayList<Space> getSpaces(){
		return this.spaces;
	}
}
