package document;
/**
 * @author Fabrício J. Barth
 * @since Dezember, 2005
 * 
 * Recupera a partir de um arquivo a lista de 
 * palavras que devem ser eliminadas do documento
 * recuperado.
 * 
 */

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class PalavrasEliminadas {

	/**
	 * Representa as palavras que devem ser eliminadas
	 * 
	 * @uml.property name="_palavrasEliminadas"
	 * @uml.associationEnd elementType="java.lang.String" multiplicity="(0 -1)"
	 */
	private ArrayList<String> _palavrasEliminadas;

	/**
	 * arquivo com stopwords para o ingles.
	 * (pode-se colocar termos do portugues e 
	 * ingles no mesmo arquivo)
	 */
	//private String arquivo = "data/english.stop";
	private String arquivo = "data/geral.txt";
	
	/**
	 * objeto que representa a instancia desta
	 * classe (implementacao do Pattern Singleton)
	 */
	private static PalavrasEliminadas instance = null;
	
	/**
	 * Retorna a instancia desta classe
	 * (implementacao do Pattern Singleton)
	 * @return instancia da classe
	 */
	public static PalavrasEliminadas getInstance(){
		if(instance==null){
			instance = new PalavrasEliminadas();
		}
		return instance;
	}
	/**
	 * Abre o arquivo e preenche o ArrayList _palavrasEliminadas
	 * com as palavras que devem ser eliminadas do documento 
	 */
	private PalavrasEliminadas(){
		try{
			this._palavrasEliminadas = new ArrayList<String>();
			File f = new File(this.arquivo);
			RandomAccessFile rf = new RandomAccessFile(f,"rw");
			
			String palavra;
			while((palavra = rf.readLine()) != null){
				this._palavrasEliminadas.add(palavra);
			}
			rf.close();
		}catch(Exception e){}
	}
	
	/**
	 * Retorna as palavras que devem ser eliminadas
	 * do conteudo de um documento
	 * @return palavras que devem ser eliminadas
	 */
	public String [] getPalavrasEliminadas(){
		String [] retorno = new String[this._palavrasEliminadas.size()];
		Iterator it = this._palavrasEliminadas.iterator();
		int i=0;
		while(it.hasNext()){
			retorno[i] = it.next().toString();
			i++;
		}
		return retorno;
	}
	
}
