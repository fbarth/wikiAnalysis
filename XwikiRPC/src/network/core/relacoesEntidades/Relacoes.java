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

package network.core.relacoesEntidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.User;

/**
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 24, maio, 2007
 * 
 */
public class Relacoes {
	

	/**
	 * Gera um grafo onde os nodos sao os documentos e as arestas
	 * sao as pessoas.
	 * 
	 * @param docs
	 * @return
	 */
	public Grafo geraGrafo(ArrayList<Page> docs){
		Grafo g = new Grafo();		
		for(int i=0; i<docs.size(); i++){
			Iterator<User> usuariosDocumento =  PageUser.getInstance().getUsersByPage(docs.get(i)).iterator();
			while(usuariosDocumento.hasNext()){
				User usuario = usuariosDocumento.next();
				for(int j=i+1; j<docs.size(); j++){
					if(PageUser.getInstance().getUsersByPage(docs.get(i)).contains(usuario)){
						g.setNodo(docs.get(i).getId(),docs.get(i).getTitle());
						g.setNodo(docs.get(j).getId(),docs.get(j).getTitle());
						if(g.setAresta(
								docs.get(i).getId(), 
								docs.get(i).getTitle(), 
								docs.get(j).getId(), 
								docs.get(j).getTitle(),
								usuario.getName()))
							System.out.println("Aresta adicionada "+docs.get(i).getId()+" "+docs.get(j).getId()+" "+usuario.getName());
						else
							System.out.println("Aresta nao adicionada "+docs.get(i).getId()+" "+docs.get(j).getId()+" "+usuario.getName());
					}
				}
			}
		}	
		return g;
	}

	/**
	 * Gera grafo onde os nodos sao as pessoas e as arestas sao
	 * os documentos.
	 * 
	 * @param docs
	 * @return
	 */
	public Grafo geraGrafoInvertido(ArrayList<Page> docs){
		Grafo g = new Grafo();
		for(int i=0; i<docs.size(); i++){
			/*
			 * Para cada documento sao extraidos as pessoas relacionadas
			 */
			Object pessoasDocumento[] = PageUser.getInstance().getUsersByPage(docs.get(i)).toArray();
			
			for(int j=0; j<pessoasDocumento.length; j++){
				try{
					g = ligaTermosAoTermoEncontrado(docs.get(i),pessoasDocumento,((User)pessoasDocumento[j]).getName(),j+1,g);
				}catch(Exception e){
					System.out.println("Nao foi possivel adicionar estes nodos e arestas: "+e);
				}
			}
		}
		return g;
	}
	
	
	private Grafo ligaTermosAoTermoEncontrado(Page doc, Object[] usuariosDocumento, String pessoa, int posicao, Grafo g) throws Exception{
		for(int i=posicao; i<usuariosDocumento.length; i++){
			g.setNodo(pessoa,doc.getTitle());
			g.setNodo(((User)usuariosDocumento[i]).getName(),doc.getTitle());
			/*
			 * Xunxo
			 */
			String pessoas [] = new String[2];
			pessoas[0] = pessoa;
			pessoas[1] = ((User)usuariosDocumento[i]).getName();
			Arrays.sort(pessoas);
			g.setAresta(pessoas[0],doc.getTitle(),pessoas[1],doc.getTitle(),doc.getId());
			/*
			 * Fim do xunxo
			 */
			//g.setAresta(pessoa,doc.getTitle(),((User)usuariosDocumento[i]).getName(),doc.getTitle(),doc.getId());
		}
		return g;
	}
}