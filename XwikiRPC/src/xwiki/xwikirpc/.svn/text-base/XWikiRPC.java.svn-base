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

package xwiki.xwikirpc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * 
 * Classe que implementa os principais metodos de acesso
 * remoto a um Wiki.
 * 
 * O principal objetivo desta classe eh fornecer metodos para recuperar
 * o conteudo das paginas e informacoes sobre os espacos, paginas e usuarios
 * que utilizam o Wiki.
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version Agosto, 2006
 * 
 * Com ArrayList e interacao com o pacote xwiki.model
 * @version Setembro, 2006
 * 
 * Alteracao para o pacote 3.0 do xml-rpc.
 * @version Abril, 2008
 * 
 */
public class XWikiRPC {

	/*
	 * Endereco do wiki que deve ser acessado
	 */
	//private String xmlRpcServerURL = "http://panteao.xwiki.com/xwiki/bin/xmlrpc/confluence";
	private String xmlRpcServerURL = "https://wiki.atech.br/xwiki/bin/xmlrpc/confluence";
	
	/*
	 * Usuario
	 * O usuario precisa ter direito a programa no wiki configurado
	 */
	private String user = "fbarth";
	
	/*
	 * Senha do usuario
	 */
	private String password = "ousadia01";
	
	/*
	 * Cliente da conexao
	 */
	private XmlRpcClient client;
	
	/**
	 * Realiza a conexao com os dados pre-estabelecidos
	 * @throws MalformedURLException
	 */
	public XWikiRPC() throws MalformedURLException{ 
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        URL url = new URL(this.xmlRpcServerURL);
        config.setServerURL(url);
        client = new XmlRpcClient();
        client.setConfig(config);
        //versão antiga
		//client = new XmlRpcClient(this.xmlRpcServerURL); 
	}
	
	/**
	 * Realiza a conexao com os dados fornecidos via parametros
	 * 
	 * @param url endereco do wiki
	 * @param user usuario que deve acessar o wiki 
	 * @param password senha do usuario
	 * @throws MalformedURLException
	 */
	public XWikiRPC(String url, String user, String password) throws MalformedURLException{
		this.xmlRpcServerURL = url;
		this.user = user;
		this.password = password;
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        URL endereco = new URL(this.xmlRpcServerURL);
        config.setServerURL(endereco);
        client = new XmlRpcClient();
        client.setConfig(config);
		//versão antiga
		//client = new XmlRpcClient(this.xmlRpcServerURL);
	}
	
	/**
	 * Realiza o login no wiki
	 * 
	 * @return token de identificacao do login realizado
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public String login() throws XmlRpcException, IOException { 
		String loginToken; 
		Vector<String> loginParams = new Vector<String>(2); 
		loginParams.add(this.user); 
		loginParams.add(this.password); 
		loginToken = (String) client.execute("confluence1.login", loginParams); 
		//loginToken = (String) client.execute("login", loginParams);
		//System.out.println("Login: " + loginToken); 
		return loginToken; 
	}

	/**
	 * Realiza o logout do wiki
	 * 
	 * @param loginToken token de identificacao do login realizado
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public void logout(String loginToken) throws XmlRpcException, IOException { 
		Vector<String> logoutParams = new Vector<String>(1); 
		logoutParams.add(loginToken); 
		//System.out.println("Logout: " + client.execute("confluence1.logout", logoutParams)); 
	}

	/**
	 * Recupera a pagina identificada
	 * 
	 * Itens recuperados:
	 * 	- space: identificacao do espaco que esta pagina pertence
	 *  - url: o endereco da pagina
	 *  - id: a chave da identificacao da pagina
	 *  - creator: username do criador da pagina
	 *  - modifier: username da ultima pessoa que modificou a pagina
	 *  - created: data (timestamp) da criacao da pagina
	 *  - parentID: o id da pagina que criou esta pagina
	 *  - version: o numero da versao da pagina
	 *  - content: o conteudo da pagina
	 * 
	 * @param pageName nome da pagina que deseja-se recuperar
	 * @param token token de identificacao do login realizado
	 * @return pagina (space,url,id,creator,modifier,created,parentID,version,content)
	 * @throws XmlRpcException
	 * @throws IOException
	 * 
	 * Alterou-se o retorno do metodo de Hastable para HashMap.
	 * A API do xml-rpc alterou a interface (versao 3.0)
	 * @version Abril, 2008.
	 * 
	 */
	public HashMap<String, String> getPage(String pageName, String token){ 
		try{
			Vector<String> params = new Vector<String>(); 
			params.add(token); 
			params.add(pageName); 
			System.out.println("Recuperando pagina "+pageName);
			HashMap<String, String> page = (HashMap<String, String>) client.execute("confluence1.getPage", params); 
			System.out.println("Recuperou a pagina: " + page); 
			return page;
		}catch(Exception e){
			System.out.println("A pagina "+pageName+" nao foi recuperada.");
			return null;
		}
	}
	
	/**
	 * Retorna todas as paginas do espaco informado como parametro do metodo
	 * 
	 * @param token token de identificacao do login realizado
	 * @param spaceKey key de identificacao do espaco
	 * @return todas as paginas do espaco definido em spaceKey
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public ArrayList<HashMap> getPages(String token, String spaceKey){
			Vector<String> params = new Vector<String>(); 
			params.add(token); 
			params.add(spaceKey);

			Object[] pages = new Object[1];
			
			//HashMap pages = (HashMap) client.execute("confluence1.getPages", params);
			try{
				pages = (Object[]) client.execute("confluence1.getPages", params);
			}catch(XmlRpcException Exception){
				System.out.println("Uma pagina nao foi encontrada "+params);
			}

			//System.out.println("Got pages: ");
			//for(int i=0; i<pages.size(); i++){
			//	Hashtable<String,String> page = (Hashtable<String,String>) pages.get(i);
			//	System.out.println(page);
			//}

			//return new ArrayList(pages);
			//return new ArrayList(pages);
			//return new ArrayList(new HashMap(pages).values());
			return this.toArrayList(pages);
	}
	
	//private Object rpcCall( String rpc, Vector<String> params) throws XmlRpcException, IOException
    //{
    //    return client.execute( "confluence1." + rpc, params);
    //}

	/**
	 * Adiciona conteuno na pagina passada como parametro
	 * 
	 * @param page a pagina que deve ser modificada
	 * @param token token de identificacao do login realizado
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public void putPage(Hashtable page, String token) throws XmlRpcException, IOException { 
		Vector<Object> params = new Vector<Object>(); 
		params.add(token); 
		params.add(page); 
		client.execute("confluence1.storePage", params); 
		//System.out.println("Attempted to put page: " + page); 
	}
	
	/**
	 * Retorna todos os espacos pertencentes ao wiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @return uma colecao com os espacos do wiki
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public ArrayList<HashMap> getSpaces(String token) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>(); 
		params.add(token);
		/*
		 * Devido a alteracao da versao da API do Xwiki 
		 * foi alterado de Vector para Object[]
		 */
		Object[] spaces = (Object[]) client.execute("confluence1.getSpaces", params);
		//System.out.println("Got spaces: ");
		//for(int i=0; i<spaces.size(); i++){
		//	Hashtable<String,String> space = (Hashtable<String,String>) spaces.get(i);
		//	System.out.println(space);
		//}
		return this.toArrayList(spaces);
	}
	
	/**
	 * Retorna o espaco especificado no parametro do metodo
	 * 
	 * Um espaco possui os seguintes atributos:
	 *  - key: the space key
     *  - name: the name of the space
     *  - url: the url to view this space online
     *  - homepage: the id of the space homepage
     *  - description: the HTML rendered space description 
	 * 
	 * @param token token de identificacao do login realizado 
	 * @param spaceKey id do espaco que deseja-se recuperar 
	 * @return o espaco especificado em spaceKey
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public HashMap<String, String> getSpace(String token, String spaceKey) throws XmlRpcException, IOException{
		Vector<String> params = new Vector<String>(); 
		params.add(token); 
		params.add(spaceKey); 
		HashMap<String, String> space = (HashMap<String, String>) client.execute("confluence1.getSpace", params); 
		//System.out.println("Got spage: " + space); 
		return space;
	}
	
	/**
	 * Retorna as versoes de uma determinada pagina (pageId)
	 * 
	 * Uma versao possui os seguintes atributos:
	 *  - id: identificador da pagina
	 *  - version: versao da pagina
	 *  - modifier: usuario que modificou a pagina
	 *  - modified: data da modificacao
	 * 
	 * @param token token de identificacao do login realizado
	 * @param pageId id da pagina
	 * @return todas as versoes da pagina pageId
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public ArrayList<HashMap> getPageHistory(String token, String pageId) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>(); 
		params.add(token);
		params.add(pageId);
		Object[] versions = (Object[]) client.execute("confluence1.getPageHistory", params);
		//System.out.println("Got versions: ");
		//for(int i=0; i<versions.size(); i++){
		//	Hashtable<String,String> version = (Hashtable<String,String>) versions.get(i);
		//	System.out.println(version);
		//}
		return this.toArrayList(versions);
	}	
	
	/**
	 * Retorna os filhos diretos da pagina pageId
	 * 
	 * @deprecated
	 * Nao estah implementado para o XWiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @param pageId id da pagina
	 * @return filhos diretos da pagina pageId
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public ArrayList<HashMap> getChildren(String token, String pageId) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>(); 
		params.add(token);
		params.add(pageId);
		Object[] children = (Object[]) client.execute("confluence1.getChildren", params);
		//System.out.println("Got children: ");
		//for(int i=0; i<children.size(); i++){
		//	Hashtable<String,String> child = (Hashtable<String,String>) children.get(i);
		//	System.out.println(child.get("id"));
		//}
		return this.toArrayList(children);
	}
	
	/**
	 * Retorna todos os comentarios de uma pagina
	 * 
	 * @deprecated
	 * Nao estah implementado para o XWiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @param pageId id da pagina
	 * @return os comentarios da pagina pageId
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public ArrayList<HashMap> getComments(String token, String pageId) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>(); 
		params.add(token);
		params.add(pageId);
		Object[] comments = (Object[]) client.execute("confluence1.getComments", params);
		//System.out.println("Got comments: ");
		//for(int i=0; i<comments.size(); i++){
		//	Hashtable<String,String> child = (Hashtable<String,String>) comments.get(i);
		//	System.out.println(child.get("id"));
		//}
		return this.toArrayList(comments);
	}
	
	/**
	 * Retorna um unico comentario
	 * 
	 * @deprecated
	 * Nao estah implementado para o XWiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @param commentId id de identificacao do comentario
	 * @return retorna o comentario commentId
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public HashMap<String, String> getComment(String token, String commentId) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>(); 
		params.add(token);
		params.add(commentId);
		HashMap<String, String> comment = (HashMap<String, String>) client.execute("confluence1.getComment", params); 
		//System.out.println("Got comment: " + comment); 
		return comment;
	}
	
	/**
	 * Retorna um determinado arquivo anexo
	 *
	 * @deprecated 
	 * Nao estah implementado para o XWiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @param pageId identificador da pagina
	 * @param fileName nome do arquivo
	 * @param versionNumber versao do arquivo
	 * @return uma determinada versao de um arquivo anexo identificado
	 * por pageId
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public HashMap<String, String> getAttachment(String token, String pageId, String fileName, String versionNumber) throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>();
		params.add(token);
		params.add(pageId);
		params.add(fileName);
		params.add(versionNumber);
		HashMap<String, String> attachment = (HashMap<String, String>) client.execute("confluence1.getAttachment", params); 
		//System.out.println("Got attachment: " + attachment); 
		return attachment;
	}
	
	public ArrayList<HashMap> getAttachments(String token, String pageId)throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>();
		params.add(token);
		params.add(pageId);
		Object[] attachments = (Object[]) client.execute("confluence1.getAttachments", params);
		//System.out.println("Got attachments: ");
		//for(int i=0; i<attachments.size(); i++){
		//	Hashtable<String,String> attachment = (Hashtable<String,String>)attachments.get(i);
		//	System.out.println(attachment);
		//}
		return this.toArrayList(attachments);
	}

	private ArrayList<HashMap> toArrayList(Vector<HashMap> v){
		ArrayList<HashMap> retorno = new ArrayList<HashMap>();
		for(int i=0; i<v.size(); i++){
			retorno.add(v.elementAt(i));
		}
		return retorno;
	}
	
	private ArrayList toArrayList(Object[] v){
		ArrayList retorno = new ArrayList();
		System.out.println(v.length);
		for(int i=0; i<v.length; i++){
			retorno.add(v[i]);
		}
		System.out.println("Retornando array...");
		return retorno;
	}
	
	/**
	 * Retorna todo o conteudo do espaco
	 * 
	 * @deprecated
	 * Este metodo nao estah implementado na API do XWiki
	 * 
	 * @param token token de identificacao do login realizado
	 * @param spaceKey identificador do espaco
	 * @param exportType tipo para o export (TYPE_XML, TYPE_PDF, or TYPE_HTML)
	 * @return retorna o conteudo de spaceKey de acordo com o tipo especificado por exportType
	 * @throws XmlRpcException
	 * @throws IOException
	 */
	public String exportSpace(String token, String spaceKey, String exportType)throws XmlRpcException, IOException{
		Vector<Object> params = new Vector<Object>();
		params.add(token);
		params.add(spaceKey);
		params.add(exportType);
		return (String) client.execute("confluence1.exportSpace",params);
	}
}
