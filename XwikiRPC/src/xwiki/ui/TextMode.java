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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import xwiki.XwikiConf;
import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.Space;
import xwiki.model.User;
import xwiki.model.Version;
import xwiki.retrieval.Retrieval;
import xwiki.retrieval.Spaces;
import xwiki.retrieval.Storage;

public class TextMode {

	private UIFacade facade;
	
	public static void main(String[] args) {
		new TextMode();		
	}
	
	public TextMode(){
		
		this.facade = new UIFacade();
		while(true){
			try{
				System.out.println("Wiki2Group  Copyright (C) 2010  Fabrício J. Barth");
				System.out.println("This program comes with ABSOLUTELY NO WARRANTY.");
				System.out.println("This is free software, and you are welcome to redistribute it");
				System.out.println("under certain conditions.");
				System.out.println("");
				System.out.println("Menu");
				System.out.println("1  - Recupera espaco http://p1.xwiki.com");
				System.out.println("6  - Cria e visualiza agrupamento das paginas jah recuperadas");
				System.out.println("7  - Lista a relacao de usuarios por paginas");
				System.out.println("8  - Lista a relacao de paginas por usuarios");
				System.out.println("9  - Imprime o conteudo manipulado das paginas jah recuperadas incluindo os links internos e externos");
				System.out.println("10  - Exclui dados já armazenados");
				System.out.println("11 - Armazena espaços recuperados em memória");
				System.out.println("12 - Recupera espaços armazenados em memória");
				System.out.println("13 - Lista somente o nome de todas as paginas");
				System.out.println("14 - Lista somente o nome de todos os usuarios");
				System.out.println("15 - Imprime o conteudo do perfil (long-term) do usuario");
				System.out.println("16 - Imprime o conteudo do perfil (short-term) do usuario");
				System.out.println("17 - Imprime o histórico de utilização de cada usuário");
				System.out.println("18 - Cria e visualiza agrupamento dos perfis de usuários");
				//System.out.println("19 - Gera XML para TimeLine a partir do histórico de utilização dos usuários");
				System.out.println("20 - Cria e visualiza agrupamento de documentos levando em consideração um documento");
				System.out.println("21 - Grava arquivo ARFF com a relação usuários x documentos");
				System.out.println("22 - Grava arquivo TXT com a relação usuários x documentos para o projeto KnnRecomendacao");
				System.out.println("23 - Imprime o histórico completo");
				System.out.println("24 - Imprime grafo de distâncias");
				System.out.println("25 - Lista a relacao de paginas por usuarios e grava os documentos para futura análise");
				System.out.println("26 - Gera grafo de relacionamentos de um usuário, nodo = pessoa");
				System.out.println("27 - Gera grafo de relacionamentos de um usuário, nodo = documento");
				System.out.println("28 - Gera rede social do Wiki");
				System.out.println("29 - Finaliza");
				System.out.println("");
				System.out.println("Digite uma opção: ");
		
				int opcao = new Integer(new BufferedReader(new InputStreamReader(System.in)).readLine()).intValue();
			
				switch (opcao){
				case 1:
					recuperaVariosEspacos("http://p1.xwiki.com/xwiki/xmlrpc","<USER>","<PASSWD>");
					break;
				case 6:
					System.out.println("Gerando os agrupamentos das páginas...");
					criaVisualizaAgrupamento();
					break;
				case 7:
					System.out.println("Usuários por páginas:");
					relacaoUsuariosPaginas();
					break;
				case 8:
					System.out.println("Páginas por usuários:");
					relacaoPaginasUsuarios();
					break;
				case 9:
					System.out.println("Imprimindo o conteudo manipulado das paginas em memoria");
					imprimeConteudoManipulado();
					break;
				case 10:
					System.out.println("Excluindo todos os dados armazenados...");
					excluirDadosArmazenados();
					break;
				case 11:
					System.out.println("Armazenando espaços recuperados em memória...");
					armazenarEspacosMemoria();
					break;
				case 12:
					System.out.println("Recuperando espaços recuperados em memória...");
					recuperarEspacosMemoria();
					break;
				case 13:
					System.out.println("Listando somente o nome das paginas...");
					listaNomePaginas();
					break;
				case 14:
					System.out.println("Listando somente o nome dos usuarios...");
					listaNomeUsuarios();
					break;
				case 15:
					System.out.println("Imprimindo o conteudo do perfil (long-term) do usuario...");
					listaConteudoPerfilLongTermUsuario();
					break;
				case 16:
					System.out.println("Imprimindo o conteudo do perfil (short-term) do usuario...");
					listaConteudoPerfilShortTermUsuario();
					break;
				case 17:
					System.out.println("Imprimindo o histórico de utilização de cada usuário");
					listaHistoricoUsuarioParaTodasAsPaginas();
					break;
				case 18:
					System.out.println("Gerando os agrupamentos dos usuários...");
					criaVisualizaAgrupamentoPerfil();
					break;
				//case 19:
				//	System.out.println("Gerando XML para TimeLine a partir do histórico de interações dos usuários...");
				//	geraXmlParaTimeLine();
				//	break;
				case 20:
					System.out.println("Digite o nome do documento que deverá ser analisado:");
					String doc = XwikiConf.getInstance().get("dirProject")+
						XwikiConf.getInstance().getDocsAgrupamentoPath()+
						new BufferedReader(new InputStreamReader(System.in)).readLine()+".txt";
					System.out.println("Gerando os agrupamentos dos documentos e analisando o documento "+doc);
					criaVisualizaAgrupamento(doc);
					break;
				case 21:
					System.out.println("Criando o arquivo ARFF...");
					facade.geraArquivoARFFcomTabelaUsuariosDocumentos();
					break;
				case 22:
					System.out.println("Criando o arquivo TXT...");
					facade.geraArquivoComTabelaUsuariosDocumentosParaKnnRecomendacao();
					break;
				case 23:
					System.out.println(facade.geraHistorico());
					break;
				case 24:
					System.out.println("Para todos os usuários? [Y|n]");
					String op = new BufferedReader(new InputStreamReader(System.in)).readLine();
					if(op.equals("Y"))
						System.out.println(facade.calculaGrafoDistancias());
					else{
						System.out.println("Digite o nome do usuário:");
						String us = new BufferedReader(new InputStreamReader(System.in)).readLine();
						System.out.println(facade.calculaGrafoDistancias(us));
					}
					break;
				case 25:
					relacaoPaginasUsuariosParaEM();
					break;
				case 26:
					System.out.println("Digite o nome do usuário:");
					String us1 = new BufferedReader(new InputStreamReader(System.in)).readLine();
					facade.geraGrafoArestaPessoa(new User(us1));
					break;
				case 27:
					System.out.println("Digite o nome do usuário:");
					String us2 = new BufferedReader(new InputStreamReader(System.in)).readLine();
					facade.geraGrafoArestaDocumento(new User(us2));
					break;
				case 28:
					facade.geraGrafoArestaPessoaTodasPessoasEdocs();
					break;
				case 29:
					System.out.println("Saindo ...");
					System.exit(0);
				}
			}catch(IOException ioe){
				System.out.println("Erro com a leitura do menu. Tente novamente.");
			}catch(Exception e){
				System.out.println("Erro de conexao. Tente novamente.");
			}
		}//fim while
	}
	
	/**
	 * Exclui o conteudo que estah armazenado localmente.
	 */
	private void excluirDadosArmazenados(){
		facade.excluiArquivosLocal();
	}
	
	/**
	 * Imprime o conteudo manipulado das paginas que estao em memoria
	 */
	private void imprimeConteudoManipulado(){
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Iterator<Page> pages = spaces.next().getPages().iterator();
			while(pages.hasNext()){
				Page page = pages.next();
				System.out.println("");
				System.out.println("Página   "+page.getId());
				System.out.println(page.getAtomos());
				HashMap vetor = page.getVetor();
				Iterator palavras = vetor.keySet().iterator();
				while(palavras.hasNext()){
					String pal = (String)palavras.next();
					System.out.println(pal+"  "+vetor.get(pal));
				}
			}
		}
	}
	
	/**
	 * Imprime a relação de usuários por páginas
	 */
	private void relacaoUsuariosPaginas(){
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Iterator<Page> pages = spaces.next().getPages().iterator();
			while(pages.hasNext()){
				Page page = pages.next();
				System.out.println("");
				System.out.println("Página   "+page.getId());
				System.out.println("Usuários ");
				//Iterator<String> users = page.getUsers().iterator();
				Iterator<User> users = PageUser.getInstance().getUsersByPage(page).iterator();
				while(users.hasNext())
					System.out.println(users.next().getName());
			}
		}
	}
	
	/**
	 * Imprime a relação de páginas por usuários.
	 * Ignora a informação do Space.
	 */
	private void relacaoPaginasUsuarios(){
		Iterator<User> users = PageUser.getInstance().getAllUsers().iterator();
		while(users.hasNext()){
			User user = users.next();
			System.out.println("Usuário   "+user.getName());
			System.out.println("Páginas ");
			Iterator<Page> pages = PageUser.getInstance().getPagesByUser(user).iterator();
			while(pages.hasNext())
				System.out.println(pages.next().getId());
		}
	}

	/**
	 * Cria e visualiza os agrupamentos criados a partir do
	 * conteudo das paginas 
	 */
	private void criaVisualizaAgrupamento(){
		facade.criaAgrupamento();
	}
	
	
	/**
	 * 
	 * @param documento
	 */
	private void criaVisualizaAgrupamento(String documento){
		facade.criaAgrupamento(documento);
	}
	
	/**
	 * Cria e visualiza os agrupamentos criados a partir dos
	 * perfis dos usuários
	 */
	private void criaVisualizaAgrupamentoPerfil(){
		facade.criaAgrupamentoPerfil();
	}
	
	/**
	 * 
	 * @param url
	 * @param usuario
	 * @param senha
	 * @param pagina
	 * @return
	 */
	private boolean recuperaEspaco(String url, String usuario, String senha, String pagina){
		try{
			Space space = facade.recuperaEspaco(url,usuario,senha,pagina);
			facade.gravaEspaco(space);
			facade.adicionaSpaceMemoria(space);
			return true;
		}catch(org.apache.xmlrpc.XmlRpcException exception){
			System.out.println("TextMode.recuperaEspaco: "+exception);
			return false;
		}
	}
	
	/**
	 * TODO
	 * @param url
	 * @param usuario
	 * @param senha
	 * @return
	 */
	private boolean recuperaVariosEspacos(String url, String usuario, String senha){
		try{
			String[] espacos = {"CP_Arquitetura","CP_Engenharia","CP_Testes","CP_Gestao","DIS"};
			for(int i=0; i<espacos.length; i++){
				Space space = facade.recuperaEspaco(url,usuario,senha,espacos[i]);
				facade.gravaEspaco(space);
				facade.adicionaSpaceMemoria(space);
			}
			return true;
		}catch(org.apache.xmlrpc.XmlRpcException exception){
			System.out.println("TextMode.recuperaEspaco: "+exception);
			return false;
		}
	}
	
	private void armazenarEspacosMemoria(){
		new Storage().armazenarObjetos(Spaces.getInstance());
	}
	
	private void recuperarEspacosMemoria(){
		Spaces.setInstance(new Storage().recuperarObjetos());
	}
	
	/**
	 * Lista a URL das paginas por espacos
	 */
	private void listaNomePaginas(){
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Space s = spaces.next();
			System.out.println("");
			System.out.println("Space: "+s.getUrl());
			Iterator<Page> pages = s.getPages().iterator();
			while(pages.hasNext()){
				System.out.println(pages.next().getUrl());
			}
		}
	}
	
	/**
	 *	Lista o nome dos usuarios por espacos
	 */
	private void listaNomeUsuarios(){
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Space s = spaces.next();
			System.out.println("");
			System.out.println("Space: "+s.getUrl());
			Iterator<User> users = s.getAllUsers().iterator();
			while(users.hasNext()){
				System.out.println(users.next().getName());
			}
		}
	}
	
	/**
	 * Para todos os usuários armazenados lista o conteúdo
	 * do perfil do usuário (long-term)
	 */
	private void listaConteudoPerfilLongTermUsuario(){
		Retrieval r = new Retrieval();
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Space s = spaces.next();
			System.out.println("");
			System.out.println("Space: "+s.getUrl());
			Iterator<User> users = s.getAllUsers().iterator();
			while(users.hasNext()){
				User u = users.next();
				System.out.println("");
				System.out.println("Usuário: "+u.getName());
				HashMap vetor = r.recuperaPerfilUsuarioLongTerm(u);
				Iterator palavras = vetor.keySet().iterator();
				while(palavras.hasNext()){
					String pal = (String)palavras.next();
					System.out.println(pal+"  "+vetor.get(pal));
				}
			}
		}		
	}
	
	/**
	 * TODO preciso definir o que caracteriza um perfil short-term
	 */
	private void listaConteudoPerfilShortTermUsuario(){
		
	}
	
	/**
	 * Lista o número de intervenções que um determinado
	 * usuário fez em determinadas páginas.
	 * 
	 */
	private void listaHistoricoUsuarioParaTodasAsPaginas(){
		Iterator<Space> spaces = facade.getSpacesEmMemoria().iterator();
		while(spaces.hasNext()){
			Space s = spaces.next();
			System.out.println("");
			System.out.println("Espaço: "+s.getUrl());
			Iterator<User> users = s.getAllUsers().iterator();
			while(users.hasNext()){
				User u = users.next();
				System.out.println("");
				System.out.println("Usuário: "+u.getName());
				System.out.println("Páginas ");
				Iterator<Page> pages = PageUser.getInstance().getPagesByUser(u).iterator();
				while(pages.hasNext()){
					Page p = pages.next();
					System.out.println(p.getUrl());
					Iterator<Version> versions = p.getVersions().iterator();
					while(versions.hasNext()){
						Version v = versions.next();
						if(v.getModifier().equalsIgnoreCase(u.getName()))
							System.out.println(v.print());
					}
				}
			}
		}
	}	
	
	/**
	 * Determina a relação de páginas por usuários e grava o conteúdo dos arquivos
	 * para futura análise.
	 * Ignora a informação do Space.
	 * 
	 * @version 24, maio, 2007
	 */
	private void relacaoPaginasUsuariosParaEM(){	
		System.out.println("Criando o conjunto de arquivos para futuro processamento...");
		facade.relacaoPaginasUsuariosParaEM();
	}
	
}
