package entityDescription;
/**
 * Classe que implementa as buscas no google e ordena os resultados de acordo
 * com o profile do cliente.
 *
 * Modificacoes:
 *
 * Data: 27/07
 * Descricao: Incluida lista de palavras invalidas
 *
 * Data: 28/07
 * Descricao: mudanca de hasBadWord para hasWord
 *
 * Data: 30/07
 * Descricao: Removido o uso de threads, mudado o algoritmo de ordenacao e
 * removidos os metodos getConteudo() e getQuantidade()
 *
 * Data: 01/08
 * Descricao: Removida a chave de busca do google -> passou para Configuracao
 *
 * Data: 04/08
 * Descricao: Incluido o metodo classifica()
 *
 * Data: 08/08
 * Descricao: Incluido metodo useProxy()
 *            Modificado metodo extraiConteudo() para usar BufferedReader
 * 
 * Data: 11/08
 * Descricao: Incluida autenticacao de proxy no metodo extraiConteudo()
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.soap.search.GoogleSearch;
import com.google.soap.search.GoogleSearchResultElement;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.HashSet;
//import java.util.StringTokenizer;
//import sun.misc.BASE64Encoder;

public class Busca {
	
	private static final String DEFAULT_KEY = "UTkZD1tQFHK6B024AOClJaqS5H2I/mKh";
	//private static final String DEFAULT_KEY = "+iCun9lQFHLNphyZNtfBnHuXzI6l4A9N";
	
 	private GoogleSearch search;
	private GoogleSearchResultElement[] varios;
	private GoogleSearchResultElement[] temp;
	private String[] urls;
	private String[] snippets;
	private String[] conteudos;
//	private double[] relevancia;
	private int[] ordemCres;
	private int[] ordemDecr;
	private int maxResults;

	/**
	 * Construtor
	 */
	 
	public Busca(String query) {
		search = new GoogleSearch();
		search.setSafeSearch(true);
		search.setKey(DEFAULT_KEY);
		search.setQueryString(query);
		maxResults = 50;
	}
	
	/**
	 * Ajusta o numero maximo de resultados
	 */
	/* 
	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}*/
	
	/**
	 * Define a chave do google
	 */
	 /*
	public void setGoogleKey(String key) {
		search.setKey(key);
	}*/

	/**
	 * Ajusta o uso de Proxy
	 */
	 /*
	 public void useProxy(String host, String port, String user, String pass) {
	 	search.setProxyHost(host);
	 	search.setProxyPort(Integer.parseInt(port));
	 	search.setProxyUserName(user);
	 	search.setProxyPassword(pass);
	 }*/

	/**
	 * Faz a busca com a API do Google. Retorna true se encontrou alguma coisa.
	 * Os resultados nao sao ordenados de acordo com o perfil do cliente 
	 * neste metodo.
	 */
	 
	public boolean dispara() {
		int step = maxResults;
		if (maxResults > 10) {
			step = 10;
		}
		int start = 0;  
		varios = new GoogleSearchResultElement[maxResults];
		urls = new String[maxResults];
   		snippets = new String[maxResults];
   		conteudos = new String[maxResults];
   		ordemCres = new int[maxResults];
   		ordemDecr = new int[maxResults];
   		
   		for (int k=0; k<maxResults; k++) { // inicializa os arrays ordemCres
   			ordemCres[k] = k;			   // e ordemDecr
   			ordemDecr[k] = k;
   		}
   		
		try {
			while (start<maxResults) {
   		   		search.setStartResult(start);
   		   		temp = search.doSearch().getResultElements();
		   		for (int i=0; i<step; i++) {
		   			varios[i+start] = temp[i];
   			   		urls[i+start] = varios[i+start].getURL();
   			   		snippets[i+start] = varios[i+start].getSnippet();
   			   		//System.out.println("snippet: "+snippets[i+start]);
   		   		}
   		   		start += step;
   		   		if (start+step>maxResults) {
   		   			step = maxResults - start;
   		   		}
   			}
   			return true;
   		}
   		catch (Exception e) {
   			return false;
   		}
	}

	/**
	 *  Faz o download do conteudo das URLs 
	 */
	// 
	// public void download(Configuracao c) {
	public void download(){
		for (int i=0; i<maxResults; i++) {
			//conteudos[i] = extraiConteudo(i,c);
			conteudos[i] = extraiConteudo(i);
		}
	}
	
	 
	/**
	 * Captura o conteudo de um resultado
	 * 
	 * Autenticacao de proxy baseado no conteudo de
	 * http://www.rgagnon.com/javadetails/java-0084.html
	 *  
	 */
	//		 
	// private String extraiConteudo(int indice, Configuracao c) {
	private String extraiConteudo(int indice) {
		String saida="";
        try {
            URL url = new URL(urls[indice]);
            URLConnection uc = url.openConnection();
    //        if (c.usingProxy()) {
    //        	System.setProperty("http.proxyHost",c.getHost());
    //            System.setProperty("http.proxyPort",c.getPort()); 
    //        	BASE64Encoder encoder = new BASE64Encoder();
    //        	String encoded = encoder.encode(
    //        			(c.getName() + ":" + c.getPass()).getBytes());
    //           uc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
    //       }
            uc.connect();
            InputStream fonte = uc.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(fonte);
            BufferedReader br = new BufferedReader(isr); 
            String lin = br.readLine();
            while (lin!=null) {
                saida+=lin;
                lin = br.readLine();
            }
            //saida = Arquivo.stringhtml2txt(saida);
        }
        catch (Exception e) {
        	System.out.println("Erro Busca.extraiConteudo(int): "+e);
        	saida = null;
        }
        return saida; 
    }
	
	/**
	 * Calcula a relevancia de cada resultado da busca.
	 * Necessario invocar o metodo download antes deste metodo.
	 */
	// 
	//public void calcRelevancia(Profile p, WordList badList) {
	//	relevancia = new double[varios.length];
	//	for (int i = 0; i<varios.length; i++) {
	//		HashSet wI = new HashSet();
	//		double probTotal = 1.0;
	//		StringTokenizer st = new StringTokenizer(conteudos[i]);
	//		while (st.hasMoreTokens()) {
	//			String palavra = (String) st.nextToken();
	//			if ((!wI.contains(palavra)) && 
	//				(!badList.hasWord(palavra))) {
	//				probTotal *= p.getPwk(palavra);
	//				wI.add(palavra);
	//			}
	//		}
	//		relevancia[i] = probTotal;
	//	}
	//}
	//Nao precisa
	
	/**
	 * Coloca o resultado em ordem de relevancia e preenche os arrays
	 * ordemCres e ordemDecr com a sequencia correta de reultados
	 */
	// 
	//public void classifica() {
	//	ordenaResultado(0,relevancia.length-1);
	//	for (int i=0, k=ordemCres.length-1; i<ordemCres.length; i++, k--) {
	//		ordemDecr[i] = ordemCres[k];
	//	}
	//}
	// Nao precisa
	
	
	/* Funcionamento:
	 * 
	 * O array relevancia e' ordenado em ordem crescente pelo QuickSort. Dai,
	 * o array ordemCres armazena a sequencia dos resultados para a saida. 
	 * Quando um array for passado como parametro de saida de um metodo, 
	 * ele devera ser re-ordenando da forma como esta feito no metodo getUrl()
	 */
	
	/**
 	 * Ordena o array relevancia em ordem crescente 
 	 *
 	 * Algoritmo baseado na classe QSortAlgorithm.java disponivel em
 	 * http://java.sun.com/features/1998/05/SortDemo/QSortAlgorithm.java
 	 *
 	 * @author James Gosling
 	 * @author Kevin A. Smith
 	 * 
 	 * @param lo0     left boundary of array partition
     * @param hi0     right boundary of array partition 
 	 */
 	  /*	 	
 	private void ordenaResultado(int lo0, int hi0) {
      int lo = lo0;
      int hi = hi0;
      double mid;
      if (hi0 > lo0) {
         mid = relevancia[(lo0 + hi0) / 2];
         while(lo <= hi) {
	     	while((lo < hi0) && (relevancia[lo] < mid ))
		 		++lo;
	     	while((hi > lo0) && (relevancia[hi] > mid ))
		 		--hi;
            if(lo <= hi) {
               swap(lo,hi);
               ++lo;
               --hi;
            }
         }
         if(lo0 < hi)
            ordenaResultado(lo0, hi);
         if(lo < hi0)
            ordenaResultado(lo, hi0);
      }
   }*/
   
   /**
    * Inverte as posicoes dos arrays 
    *
    * @param indices das posicoes que serao invertidas
    */
 	/*
   private void swap(int i, int j) {
   	  double r;
      int t;
      r = relevancia[i];
      relevancia[i] = relevancia[j];
      relevancia[j] = r;
      t = ordemCres[i];
      ordemCres[i] = ordemCres[j];
      ordemCres[j] = t;
   }*/

	/**
	 * Retorna um array com as urls dos resultados.
	 *
	 * @return array de urls em ordem decrescente de relevancia
	 */
	/* 
	public String[] getURLs(){
		String[] saida = new String[urls.length];
		for (int i=0; i<ordemDecr.length; i++) {
			saida[i] = urls[ordemDecr[i]];
		}
		return saida;
	}*/
	
	/**
	 * Faz o download do conteudo das URLs retornando um 
	 * array de snippets
	 * @uml.property  name="snippets"
	 */
	 
	public String[] getSnippets(){
		String[] saida = new String[snippets.length];
		for (int i=0; i<ordemDecr.length; i++) {
			saida[i] = snippets[ordemDecr[i]];
		}
		return saida;
	}	
	
	/*
	 * Retorna um array de strings com o conteudo das URLS
	 * de uma determinada busca
	 */
	public String[] getConteudo(){
		String[] saida = new String[this.conteudos.length];
		for(int i=0; i<this.ordemDecr.length; i++){
			saida[i] = this.conteudos[this.ordemDecr[i]];
		}
		return saida;
	}
	
}