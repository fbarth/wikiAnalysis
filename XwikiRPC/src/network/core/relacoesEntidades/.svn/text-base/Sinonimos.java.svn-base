package network.core.relacoesEntidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Esta classe deverah armazenar todos os sinonimos
 * relevantes para o dominio da aplicacao
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 15, Setembro, 2005
 * 
 */
public class Sinonimos {

	/**
	 * Lista de termos encontrados nos documentos recuperados
	 */
	private HashSet<String> palavrasQueDevemSerTratadas;
	
	/**
	 * Lista de termos encontrados nos documentos recuperados
	 * com a identificacao de quem eh sinonimo de quem
	 */
	private HashSet<String> sinonimosTratados;
	
	/**
	 * Lista de sinonimos recuperadas a partir de um
	 * arquivo
	 */
	private ArrayList<HashSet> sinonimos;
	
	public Sinonimos(HashSet<String> palavrasQueDevemSerTratadas){
		try{
			this.sinonimos = ArquivoSinonimos.recuperaSinonimos();
			this.palavrasQueDevemSerTratadas = palavrasQueDevemSerTratadas;
			this.sinonimosTratados = geraSinonimosTratados();
		}catch(IOException e){
			System.out.println("Erro Sinonimos.Sinonimos: "+e);
		}
	}
	
	private HashSet<String> geraSinonimosTratados(){
		HashSet<String> retorno = new HashSet<String>();
		Iterator<String> palavras = this.palavrasQueDevemSerTratadas.iterator();
		while(palavras.hasNext()){
			/*
			 * Para cada palavra, deve-se verificar se
			 * existe um sinonimo
			 */
			String pal = palavras.next().toLowerCase();
			/*
			 * Elimina a parte da entidade que no diz respeito a 
			 * este processamento entidade#tipo = entidade
			 */
			//System.out.println("pal = "+pal);
			StringTokenizer st = new StringTokenizer(pal,"#");
			pal = st.nextToken();
			String tipo = st.nextToken();
			HashSet<String> temp = null;
			if((temp = existeSinonimo(pal)) != null){
				//System.out.println("A palavra "+pal+"  tem sinonimo.");
				//System.out.println("Os sinonimos sao "+temp);
				/*
				 * comparar cada elemento de temp com pal
				 * 
				 * imprime o tipo no final dos sinonimos.
				 */
				retorno.add(printSinonimos(temp,tipo));
			}else{
				retorno.add(new String(pal+"#"+tipo));
			}
		}
		return retorno;
	}
	
	/**
	 * Imprimi o conteudo do HashSet passado como parametro
	 * @param sin sinonimos
	 * @return uma string com o conteudo de sin
	 */
	private String printSinonimos(HashSet<String> sin,String tipo){
		Iterator<String> sins = sin.iterator();
		String retorno = sins.next();
		while(sins.hasNext()){
			retorno = retorno + " , " + sins.next();
		}
		retorno = retorno +"#"+tipo;
		return retorno;
	}
	
	public HashSet<String> getSinonimosTratados(){
		return this.sinonimosTratados;
	}
	
	/**
	 * Verifica se determinada palavra tem ou nao sinonimo
	 * @param pal palavra
	 * @return um HashSet se tem sinonimo, null caso contrario
	 */
	@SuppressWarnings("unchecked")
	private HashSet<String> existeSinonimo(String pal){
		Iterator<HashSet> sins = this.sinonimos.iterator();
		while(sins.hasNext()){
			HashSet<String> sin = sins.next();
			if(sin.contains(pal))
				return sin;
		}
		return null;
	}	
}
