package xwiki.distanceGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Distance {
	
	private HashMap contentPage1 = new HashMap();
	
	private HashMap contentPage2 = new HashMap();
	
	private HashSet<String> geral = new HashSet<String>();
	
	@SuppressWarnings("unchecked")
	public Distance(HashMap p1, HashMap p2){
		this.contentPage1 = p1;
		this.contentPage2 = p2;
		this.geral.addAll(this.contentPage1.keySet());
		this.geral.addAll(this.contentPage2.keySet());
	}
	
	public Double getEuclidianDistance(){
		double soma = 0f;
		Iterator<String> elementos = this.geral.iterator();
		while(elementos.hasNext()){
			String elemento = elementos.next();
			Double freqAbs1 = (Double)this.contentPage1.get(elemento);
			if(freqAbs1==null)
				freqAbs1=0d;
			Double freqAbs2 = (Double)this.contentPage2.get(elemento);
			if(freqAbs2==null)
				freqAbs2 = 0d;
			soma = soma + ((freqAbs1 - freqAbs2)*(freqAbs1 - freqAbs2));
		}
		return new Double(Math.sqrt(soma));
	}
	
}
