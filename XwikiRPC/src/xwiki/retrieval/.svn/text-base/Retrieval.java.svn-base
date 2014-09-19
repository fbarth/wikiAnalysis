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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.xmlrpc.XmlRpcException;

import xwiki.model.Attachment;
import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.Space;
import xwiki.model.User;
import xwiki.model.Version;
import xwiki.xwikirpc.XWikiRPC;

public class Retrieval {
	
	/**
	 * Recupera todos os spaces de um URL.
	 * Ateh o momento recupera todas as spaces de uma URL.
	 * Todas as pages de uma space.
	 * Todas as versions de um page.
	 * Nao recupera ainda:
	 *  - ExternalLinks de uma page
	 *  - InternalLinks de uma page
	 * 
	 * @version 03, setembro, 2006
	 * 
	 * @param url endereco do wiki
	 * @param user usuario da wiki
	 * @param password senha do usuario
	 * @return todas as spaces da wiki
	 */
	
	private XWikiRPC xwiki;
	private String token;

	public Space retrievalSpace(String url, String user, String password, String spaceId) throws XmlRpcException{
		try{
			xwiki = new XWikiRPC(url,user,password);
			token = xwiki.login();
			System.out.println("Conectou...");
			Space space = new Space(xwiki.getSpace(token,spaceId));
			
			ArrayList<Page> pages = this.hashToPage(xwiki.getPages(token,space.getKey()));
			System.out.println("Retornou as páginas...");
			/*
			 * Recupera todas as versions, attachments e users de todas as pages
			 */
			for(int j=0; j<pages.size(); j++){
				try{
					ArrayList<Version> versions = this.hashToVersion(xwiki.getPageHistory(token,pages.get(j).getId()));
					System.out.println("Retornou a versão da primeira página...");
					pages.get(j).setVersion(versions);
					ArrayList<Attachment> attachments = this.hashToAttachment(xwiki.getAttachments(token,pages.get(j).getId()));
					System.out.println("Retornou os attachments...");
					pages.get(j).setAttachments(attachments);
					//pages.get(j).addAllUsers(versions);
					space.addAllUsers(pages.get(j),versions);
					System.out.println("Adicionou os usuários...");
				}catch(Exception e){
					System.out.println("Nao foi possivel recuperar a pagina: "+pages.get(j).getTitle());
				}
			}
			space.setPages(pages);
			xwiki.logout(token);
			return space;
		}catch(Exception e){
			System.out.println("Erro Retrieval.retrievalSpace (X): "+e);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private ArrayList<Space> hashToSpace(ArrayList<HashMap> h){
		ArrayList<Space> spaces = new ArrayList<Space>();
		for(int i=0; i<h.size(); i++){
			spaces.add(new Space(h.get(i)));
		}
		return spaces;
	}
	
	private ArrayList<Page> hashToPage(ArrayList<HashMap> h)throws IOException, XmlRpcException{
		ArrayList<Page> pages = new ArrayList<Page>();
		for(int i=0; i<h.size(); i++){
				String nomePagina = (String)h.get(i).get("id");
				Page p = new Page(xwiki.getPage(nomePagina,token));
				if(p!=null)
					pages.add(p);
		}
		return pages;
	}
	
	private ArrayList<Version> hashToVersion(ArrayList<HashMap> h){
		ArrayList<Version> versions = new ArrayList<Version>();
		for(int i=0; i<h.size(); i++){
			versions.add(new Version(h.get(i)));
		}
		return versions;
	}

	private ArrayList<Attachment> hashToAttachment(ArrayList<HashMap> h){
		ArrayList<Attachment> attachments = new ArrayList<Attachment>();
		for(int i=0; i<h.size(); i++){
			attachments.add(new Attachment(h.get(i)));
		}
		return attachments;
	}
	
	/**
	 * Para cada usuário retorna um vetor de palavras correpondente
	 * ao conteúdo dos documentos manipulados pelo usuário.
	 * 
	 * @param userName nome do usuário
	 * @return um HashMap com a seguinte estrutura (palavra, frequencia)
	 */
	@SuppressWarnings("unchecked")
	public HashMap recuperaPerfilUsuarioLongTerm(User user){
		HashMap retorno = new HashMap();
		PageUser p = PageUser.getInstance();
		Iterator<Page> pages = p.getPagesByUser(user).iterator();
		while(pages.hasNext()){
			retorno.putAll(pages.next().getVetor());
		}
		return retorno;
	}
}
