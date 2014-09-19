package frequency;

import java.util.ArrayList;

//import org.apache.lucene.analysis.Token;

public class Absoluta {
	
	private ArrayList Dj;
	
	/**
	 * Construtor da classe responsavel pelo calculo da
	 * frequencia de cada palavra em cada documento
	 * 
	 * @param Dj lista de palavras que pertencem ao 
	 * documento
	 */
	public Absoluta(ArrayList Dj){
		this.Dj = Dj;
	}
	
	/**
	 * @param Ki palavra Ki
	 * @return a frequencia normalizada da palavra (Ki) no 
	 * documento (Dj)
	 * 
	 */
	public double tfIJ(String Ki){
		return fIJ(Ki) / maxFzj();
	}
	
	/**
	 * @param Ki palavra Ki
	 * @return o numero de vezes que a palavra (Ki) 
	 * aparece no documento (Dj)
	 */
	private double fIJ(String Ki){
		try{
			double cont = 0;
			for(int i=0; i<Dj.size(); i++){
				if(Ki.equals((String)Dj.get(i))){
					cont++;
				}
			}
			return cont;
		}catch(Exception e){
			System.out.println("Erro no metodo frequency.Absoluta.fIJ(): "+e);
			e.printStackTrace();
			return 0.0;
		}
	}
	
	/**
	 * @return o maximo computado sobre as frequencias de todas as 
	 * palavras (Kz) que aparecem no documento (Dj)  
	 */
	private double maxFzj(){
		double max = 0.0;
		for(int i=0; i<Dj.size(); i++){
			double temp = fIJ((String)Dj.get(i));
			if(temp > max){
				max = temp;
			}
		}
		return max;
	}

	
}
