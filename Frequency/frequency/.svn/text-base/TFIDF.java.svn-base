package frequency;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * 
 * TFIDF(Term Frequency / Inverse Document Frequency)
 * aik = fik * log(N/ni)
 * 
 * O peso eh proporcional ao numero de ocorrencias do 
 * termo no documento e inversamente proporcional ao numero
 * de documento onde o termo aparece.
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 *
 */

public class TFIDF{
	
	private Absoluta abs;
	
	/**
	 * numero de documentos
	 */
	private int N;
	
	/**
	 * lista das palavras com 
	 * as respectivas frequencias
	 */
	private Hashtable listaPalavras;
	
	public Hashtable getListaPalavras(){
		return this.listaPalavras;
	}
	
	
	/**
	 * Construtor da classe TFIDF
	 * 
	 * @param Dj conjunto de todos os documentos
	 * que devem ser analisados
	 */
	@SuppressWarnings("unchecked")
	public TFIDF(ArrayList Dj){
		listaPalavras = new Hashtable();
		this.N = Dj.size();
		for(int i=0; i<this.N; i++){
			ArrayList d = (ArrayList)Dj.get(i);
			for(int j=0; j<d.size(); j++){
				listaPalavras.put((String)d.get(j),
						aIK((String)d.get(j),d,ocorre((String)d.get(j),Dj)));
			}
		}
	}
	
	/**
	 * Calcula o peso da palavra ki no documento dj
	 * @param ki palavra 
	 * @param dj lista de palavras do documento dj
	 * @param ni quantidade de vezes que a palavra ki aparece em
	 * todo o conjunto de documentos
	 * @return peso da palavra ki no documento dj
	 */
	private double aIK (String ki, ArrayList dj, int ni){
		abs = new Absoluta(dj);		
		return abs.tfIJ(ki) * log10(this.N,ni);
	}
	
	/**
	 * calcula a quantidade de documentos em que a palavra
	 * ki aparece
	 * @param ki palavra
	 * @param Dj conjunto de todos os documentos
	 * @return o numero de documentos em que a palavra ki eh 
	 * encontrada
	 */
	private int ocorre(String ki, ArrayList Dj){
		int encontrou = 0;
		for(int i=0; i<Dj.size(); i++){
			if(((ArrayList)Dj.get(i)).contains(ki)){
				encontrou++;
			}
		}
		return encontrou;
	}
	
	/**
	 * 
	 * Tive que implementar este metodo pois o java soh implementa
	 * log na base e.
	 * 
	 * @param N numero de documentos
	 * @param ni numero de documentos onde a palavra (Ki) aparece
	 * @return o log na base 10 de (N/ni)
	 */
	private double log10(int N, int ni){
		return ((Math.log(N/ni))/(Math.log(10)));
	}

}