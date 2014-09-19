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

package xwiki.ui;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import network.view.relacoesEntidadesUI.FiltroFacade;
import network.view.relacoesEntidadesUI.GraphViewEntity;

import org.apache.xmlrpc.XmlRpcException;
import xwiki.XwikiConf;
import xwiki.distanceGraph.Distance;
import xwiki.distanceGraph.Item;
import xwiki.model.Attachment;
import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.Space;
import xwiki.model.User;
import xwiki.model.Version;
import xwiki.retrieval.Retrieval;
import xwiki.retrieval.Spaces;
import xwiki.retrieval.Storage;
import yale.WVToolUPGMA;
import yale.WVToolUPGMAUsers;


/**
 * @author Fabrício J. Barth (fabricio.barth@gmail.com)
 * @version September, 2006
 * 
 * criaAgrupamento(String)
 * @version fevereiro, 2007
 */
public class UIFacade {

	private String diretorio;
	
	/**
	 * Invoca a classe WVToolUPGMA para a criacao do
	 * agrupamento hierarquico para um conjunto
	 * de paginas de um espaco ou varios espacos
	 * 
	 */
	public void criaAgrupamento(){
		diretorio = XwikiConf.getInstance().getDocsAgrupamentoPath();
		new WVToolUPGMA(diretorio);
	}
	
	/**
	 * 
	 * @param documento
	 */
	public void criaAgrupamento(String documento){
		diretorio = XwikiConf.getInstance().getDocsAgrupamentoPath();
		new WVToolUPGMA(diretorio,documento);
	}
	
	/**
	 * Invoca a classe WVToolUPGMAUsers para a criacao do
	 * agrupamento hierarquico para um conjunto
	 * de usuários de um espaço ou varios espaços
	 *
	 */
	public void criaAgrupamentoPerfil(){
		try{
			diretorio = XwikiConf.getInstance().getPerfilPath();
			criaDocumentos(diretorio);
			new WVToolUPGMAUsers(diretorio);
		}catch(Exception e){
			System.out.println("Erro UIFacade.criaAgrupamentoPerfil: "+e);
		}
	}
	
	/**
	 * 
	 * @param diretorio
	 * @throws IOException
	 */
	private void criaDocumentos(String diretorio)throws IOException{
		File f = new File(diretorio);
		if(f.isDirectory()){
			File fs[] = f.listFiles();
			for(int i=0; i<fs.length; i++){
				fs[i].delete();
			}
			Iterator<User> users = PageUser.getInstance().getAllUsers().iterator();
			while(users.hasNext()){
				User u = users.next();
				File uf = new File(diretorio+u.getName().replace(".","_").replace(":","_"));
				RandomAccessFile ruf = new RandomAccessFile(uf,"rw");
				Iterator<Page> pages = PageUser.getInstance().getPagesByUser(u).iterator();
				while(pages.hasNext()){
					ruf.writeBytes(pages.next().getContent());
					ruf.writeBytes("\n");
				}
				ruf.close();
				System.out.println("Gravou arquivo "+diretorio+u.getName());
			}
		}else{
			throw new IOException("Diretório não existe");
		}
	}
	
	/**
	 * Grava o conteudo das paginas recuperadas em
	 * um diretorio pre-determinado
	 * 
	 * @param s espaco escolhido
	 */
	public void gravaEspaco(Space s){
		diretorio = XwikiConf.getInstance().getDocsAgrupamentoPath();
		new Storage().armazenaArquivosLocal(s,diretorio);
	}
	
	public void excluiArquivosLocal(){
		diretorio = XwikiConf.getInstance().getDocsAgrupamentoPath();
		new Storage().excluiArquivosLocal(diretorio);
	}
	
	/**
	 * 
	 * @param url
	 * @param usuario
	 * @param senha
	 * @param pagina
	 * @return
	 */
	public Space recuperaEspaco(String url, String usuario, String senha, String pagina) throws XmlRpcException{
		Retrieval r = new Retrieval();
		Space space = r.retrievalSpace(url,usuario,senha,pagina);
		System.out.println("Space "+space.getKey()+"  "+space.getName());
		ArrayList<Page> pages = space.getPages();
		for(int j=0; j<pages.size(); j++){
			System.out.println("   Page "+pages.get(j).getId());
			// Aqui nao eh lugar para este tipo de calculo
			//Calculo de frequencia das palavras nos documentos
			//ArrayList<String> atomos = pages.get(j).getAtomos();
			//Absoluta abs = new Absoluta(atomos);
			//for(int elemento=0; elemento<atomos.size(); elemento++){
			//	System.out.print("("+atomos.get(elemento)+","+abs.tfIJ(atomos.get(elemento))+") ");
			//}
			ArrayList<Version> versions = pages.get(j).getVersions();
			for(int k=0; k<versions.size(); k++){
				System.out.println("           "+versions.get(k).getModifier()+"  "+versions.get(k).getModified()+"  "+versions.get(k).getVersion());
			}
			ArrayList<Attachment> attachments = pages.get(j).getAttachments();
			for(int k=0; k<attachments.size(); k++){
				System.out.println("           "+attachments.get(k).getFileName());
			}
		}
		return space;
	}
	
	public void adicionaSpaceMemoria(Space space){
		Spaces spaces = Spaces.getInstance();
		spaces.addSpace(space);
	}
	
	public void adicionaSpacesMemoria(ArrayList<Space> spaces){
		Spaces s = Spaces.getInstance();
		s.addSpaces(spaces);
	}
	
	/**
	 * Retorna todos os spaces em memoria
	 * @return ArrayList com os Spaces
	 */
	public ArrayList<Space> getSpacesEmMemoria(){
		return Spaces.getInstance().getSpaces();
	}
	
	/**
	 * Gera o histórico de utilização dos usuários.
	 * 
	 * @return cada linha retornada representa um acesso.
	 */
	public String geraHistorico(){
		String historico = "";
		Iterator<Space> spaces = getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Space s = spaces.next();
			Iterator<Page> pages = s.getPages().iterator();
			while(pages.hasNext()){
				Page p = pages.next();
				Iterator<Version> versions = p.getVersions().iterator();
				while(versions.hasNext()){
					Version v = versions.next();
					v.getModified();
					v.getModifier();
					p.getUrl();
					historico = historico + "\n" + v.getModifier() + ","+v.getModified() + ","+p.getUrl();
				}
			}
		}
		return historico;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String calculaGrafoDistancias(){
		Iterator<Space> spaces = getSpacesEmMemoria().iterator();
		ArrayList<Item> itens = new ArrayList<Item>();
		
		System.out.println("Aquisição dos itens...");
		itens = getItensFromSpaces(spaces);	
		System.out.println("Reordenando os itens de acordo com o tempo...");
		Collections.sort(itens);
		System.out.println("Calculando a distância entre os itens...");
		System.out.println("Número de itens igual a "+itens.size());
		itens = configurandoDistancias(itens);
		System.out.println("Retornando os valores em uma string...");
		return transformaEmString(itens);
	}
	
	@SuppressWarnings("unchecked")
	public String calculaGrafoDistancias(String user){
		Iterator<Page> pages = PageUser.getInstance().getPagesByUser(new User(user)).iterator();
		ArrayList<Item> itens = new ArrayList<Item>();
		
		System.out.println("Aquisição dos itens...");
		itens = getItensFromPages(pages,user);	
		System.out.println("Reordenando os itens de acordo com o tempo...");
		Collections.sort(itens);
		System.out.println("Calculando a distância entre os itens...");
		System.out.println("Número de itens igual a "+itens.size());
		itens = configurandoDistancias(itens);
		System.out.println("Retornando os valores em uma string...");
		return transformaEmString(itens);
	}
	
	
	/**
	 *
	 * Adquirindo os valores para calculaGrafoDistancias()
	 * 
	 * @param spaces
	 * @return
	 */
	private ArrayList<Item> getItensFromSpaces(Iterator<Space> spaces){
		ArrayList<Item> itens = new ArrayList<Item>();
		while(spaces.hasNext()){
			Space s = spaces.next();
			Iterator<Page> pages = s.getPages().iterator();
			while(pages.hasNext()){
				Page p = pages.next();
				Iterator<Version> versions = p.getVersions().iterator();
				while(versions.hasNext()){
					Version v = versions.next();
					itens.add(new Item(v.getModifier(),v.getModified(),p));
				}
			}
		}
		return itens;
	}
	
	/**
	 *
	 * Adquirindo os valores para calculaGrafoDistancias(String user)
	 * 
	 * @param spaces
	 * @return
	 */
	private ArrayList<Item> getItensFromPages(Iterator<Page> pages, String user){
		ArrayList<Item> itens = new ArrayList<Item>();
		while(pages.hasNext()){
			Page p = pages.next();
			Iterator<Version> versions = p.getVersions().iterator();
			while(versions.hasNext()){
				Version v = versions.next();
				if(v.getModifier().equals(user))
					itens.add(new Item(v.getModifier(),v.getModified(),p));
			}
		}
		return itens;
	}
	/**
	 * Calculando as distâncias entre os documentos pares
	 * 
	 * @param itens
	 * @return
	 */
	private ArrayList<Item> configurandoDistancias(ArrayList<Item> itens){
		itens.get(0).setDistance(0d);
		for(int i=1; i<itens.size(); i++){
			Distance d = new Distance(itens.get(i-1).getPage().getVetor(), 
					itens.get(i).getPage().getVetor());
			itens.get(i).setDistance(d.getEuclidianDistance());
		}
		return itens;
	}
	
	/**
	 * Retorna os valores em uma string (temporario)
	 * 
	 * @param itens
	 * @return
	 */
	private String transformaEmString(ArrayList<Item> itens){
		String retorno = "";
		for(int i=0; i<itens.size(); i++){
			Date d = itens.get(i).getAccess();
			//retorno = retorno + "\n" + DateFormat.getTimeInstance(DateFormat.LONG).format(d)+","
			retorno = retorno + "\n" + d.toString()+","
			+itens.get(i).getUser()+","
			+itens.get(i).getPage().getUrl()+","
			+itens.get(i).getDistance();
		}
		return retorno;
	}
	
	/**
	 * Imprime a relação usuários x documentos em um arquivo
	 * com formato ARFF
	 * 
	 * f: U x D -> R
	 * 
	 * @version 21,fevereiro,2007
	 *
	 */
	public void geraArquivoARFFcomTabelaUsuariosDocumentos(){
		String conteudo = "@relation usuariosDocumentos\n";
		Iterator<Space> spaces = getSpacesEmMemoria().iterator();
		Iterator<Page> pages;
		ArrayList<Page> pagesTable = new ArrayList<Page>();
		Iterator<User> users;
		while(spaces.hasNext()){
			Space s = spaces.next();
			pages = s.getPages().iterator();
			while(pages.hasNext()){
				Page p = pages.next();
				conteudo = conteudo + "@attribute "+p.getTitle()+" numeric\n";
				pagesTable.add(p);
			}
		}
		
		String usuarios = "@attribute Usuario {";
		users = PageUser.getInstance().getAllUsers().iterator();
		usuarios = usuarios + users.next().getName();
		while(users.hasNext()){
			usuarios = usuarios + "," + users.next().getName();
		}
		usuarios = usuarios + "}";
		conteudo = conteudo+usuarios+"\n"+"\n@data\n\n";
		
		users = PageUser.getInstance().getAllUsers().iterator();
		while(users.hasNext()){
			User user = users.next();
			HashSet<Page> pagesUser = PageUser.getInstance().getPagesByUser(user);
			String linha = "";
			for(int i=0; i<pagesTable.size(); i++){
				if(pagesUser.contains(pagesTable.get(i))){
					linha = linha + "1,";
				}else{
					linha = linha + "0,";
				}
			}
			linha = linha + user.getName();
			conteudo = conteudo + linha + "\n";
		}
		
		gravarConteudoEmArquivo(conteudo,"data/usuariosDocumentos.arff");
	}
	
	/**
	 * 
	 * @param conteudo
	 * @param arquivo
	 */
	private void gravarConteudoEmArquivo(String conteudo, String arquivo){
		try{
			File f = new File(arquivo);
			if(f.exists())
				f.delete();
			RandomAccessFile rf = new RandomAccessFile(f,"rw");
			rf.writeBytes(conteudo);
			rf.close();
		}catch(Exception e){
			System.out.println("Erro UIFacade.geraArquivoARFFcomTabelaUsuariosDocumentos() "+e);
		}
	}
	
	/**
	 * Imprime a relação usuários x documentos em um arquivo
	 * com formato TXT para o algoritmo KnnRecomendacao
	 * (Ver projeto KnnRecomendacao)
	 * 
	 * @version 22,fevereiro,2007
	 *
	 */
	public void geraArquivoComTabelaUsuariosDocumentosParaKnnRecomendacao(){
		String conteudo = "";
		Iterator<Space> spaces = getSpacesEmMemoria().iterator();
		Iterator<Page> pages;
		ArrayList<Page> pagesTable = new ArrayList<Page>();
		Iterator<User> users;
		while(spaces.hasNext()){
			pages = spaces.next().getPages().iterator();
			while(pages.hasNext()){
				pagesTable.add(pages.next());
			}
		}
		
		users = PageUser.getInstance().getAllUsers().iterator();
		while(users.hasNext()){
			User user = users.next();
			HashSet<Page> pagesUser = PageUser.getInstance().getPagesByUser(user);
			String linha = "";
			for(int i=0; i<pagesTable.size(); i++){
				if(pagesUser.contains(pagesTable.get(i))){
					linha = linha + "1";
				}else{
					linha = linha + "0";
				}
				if(i!=(pagesTable.size()-1))
					linha = linha + ",";
			}
			linha = linha +"#"+ user.getName()+ "\n";
			conteudo = conteudo + linha;
		}
		
		try{
			File f = new File("data/usuariosDocumentos.txt");
			if(f.exists())
				f.delete();
			RandomAccessFile rf = new RandomAccessFile(f,"rw");
			rf.writeBytes(conteudo);
			rf.close();
		}catch(Exception e){
			System.out.println("Erro UIFacade.geraArquivoComTabelaUsuariosDocumentosParaKnnRecomendacao() "+e);
		}
	}
	
	/**
	 * Determina a relação de páginas por usuários e grava o conteúdo dos arquivos
	 * para futura análise.
	 * Ignora a informação do Space.
	 * 
	 * @version 24, maio, 2007
	 */
	public void relacaoPaginasUsuariosParaEM(){
		try{
			String dir = XwikiConf.getInstance().get("dirEM");
			Iterator<User> users = PageUser.getInstance().getAllUsers().iterator();
			while(users.hasNext()){
				User user = users.next();
				System.out.println("Usuário   "+user.getName());
				File f = new File(dir+"/"+user.getName().replace(".", "_").replace(":", "_"));
				f.mkdir();
				System.out.println("Páginas ");
				Iterator<Page> pages = PageUser.getInstance().getPagesByUser(user).iterator();
				while(pages.hasNext()){
					Page p = pages.next();
					System.out.println(p.getId());
					File fTemp = new File(f.getAbsolutePath()+"/"+p.getId().replace(".", "_")+".txt");
					RandomAccessFile rf = new RandomAccessFile(fTemp,"rw");
					rf.writeBytes(p.getContent());
					rf.close();
				}
			}
		}catch(Exception e){
			System.out.println("Erro UIFacade.relacaoPaginasUsuariosParaEM: "+e);
		}
	}
	
	
	/**
	 * Geracao dos grafos levando em consideracao
	 * um usuario. Cada nodo do grafo eh um documento.
	 * 
	 * @param user = nome de um usuario.
	 */
	public void geraGrafoArestaDocumento(User user){
		ArrayList<Page> temp = new ArrayList<Page>();
		Iterator<Page> pages = PageUser.getInstance().getPagesByUser(user).iterator();
		while(pages.hasNext()){
			temp.add(pages.next());
		}
		new GraphViewEntity(FiltroFacade.getInstance().gerandoGrafico(temp));
	}
	
	/**
	 * Geracao dos grafos levando em consideracao
	 * um usuario. Cada nodo do grafo eh um documento.
	 * 
	 * @param user = nome de um usuario.
	 */
	public void geraGrafoArestaPessoa(User user){
		ArrayList<Page> temp = new ArrayList<Page>();
		Iterator<Page> pages = PageUser.getInstance().getPagesByUser(user).iterator();
		while(pages.hasNext()){
			temp.add(pages.next());
		}
		new GraphViewEntity(FiltroFacade.getInstance().gerandoGraficoInvertido(temp));
	}
	
	/**
	 * Geracao do grafo para todas as paginas em memoria.
	 *  
	 */
	public void geraGrafoArestaPessoaTodasPessoasEdocs(){
		ArrayList<Page> temp = new ArrayList<Page>();
		Iterator<Space> spaces = Spaces.getInstance().getSpaces().iterator();
		while(spaces.hasNext()){
			Iterator<Page> pages = spaces.next().getPages().iterator(); 
			while(pages.hasNext()){
				temp.add(pages.next());
			}
		}
		new GraphViewEntity(FiltroFacade.getInstance().gerandoGraficoInvertido(temp));
	}
}
