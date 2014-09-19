package entityDescription;

import java.util.ArrayList;

import frequency.Absoluta;

public class Control {

	private ArrayList <Entity> _entity;
	
	public Control(){
		
		/*
		 * Criando as entidades
		 */
		
		System.out.println("Creating the entities...");
		
		this._entity = new ArrayList<Entity>();
		Entity e1 = new Entity("Fabricio Jailson Barth site:.br");
		this._entity.add(e1);
		
		/*
		 * Procurando pelas descricoes das entidades
		 */
		
		System.out.println("Searching for descriptions...");
		
		java.util.Iterator it = this._entity.iterator();
		while(it.hasNext()){
			Entity temp = (Entity)it.next();
			Busca b = new Busca(temp.get_name());
			if(!b.dispara())
				System.out.println("Nenhum resultado foi retornado");
			else{
				b.download();
				temp.set_description(b.getSnippets());
				temp.set_snippets(b.getSnippets());
				temp.set_conteudos(b.getConteudo());
			}
		}
		
		/*
		 * Mostrando as descricoes
		 */
		/*
		System.out.println("Show the descriptions:");
		
		it = this._entity.iterator();
		while(it.hasNext()){
			Entity temp = (Entity)it.next();
			System.out.println("Name: "+temp.get_name());
			ArrayList <String> desc = temp.get_descriptionToString();
			Absoluta abs = new Absoluta(desc);
			System.out.println("Description and frequency");
			for(int i=0; i<desc.size(); i++){
				String palavraT = desc.get(i);
				double freqT = abs.tfIJ(desc.get(i));
				if(freqT>0.6d){
					System.out.println(palavraT+"  "+freqT);
				}
			}
			//System.out.println();
			*/
			//ArrayList snippets = temp.get_snippets();
			//System.out.println("Snippets");
			//for(int i=0; i<snippets.size(); i++){
			//	ArrayList wordSnip = (ArrayList) snippets.get(i);
			//	for(int j=0; j<wordSnip.size(); j++){
			//		System.out.print((String)wordSnip.get(j)+" ");
			//	}
			//	System.out.println("");
			//}
			//System.out.println();
			
			/*
			System.out.println("Imprimindo as frequencias das palavras");
			System.out.println("de acordo com o metodo TFIDF");
			*/
			/**
			 * Calculando a frequencia das palavras utilizando
			 * o metodo TFIDF.
			 * 
			 * O construtor da classe TFIDF recebe o conjunto de
			 * todos os documentos relacionados a entidade.
			 */
			/*
			Hashtable ht = new TFIDF(temp.get_snippets()).getListaPalavras();
			Enumeration hte = ht.elements();
			Enumeration keys = ht.keys();
			while(hte.hasMoreElements()){
				String palavra = keys.nextElement().toString();
				double peso = new Double(hte.nextElement().toString()).doubleValue();
				if(peso>1.3d){
					System.out.println(palavra+"  "+peso);
				}
			}
		*/
			/*
			 * Fazendo o calculo de frequencias sobre todo o conteudo
			 */
			
			
			
		System.out.println("======================================================");
		System.out.println("Fazendo o calculo de frequencias sobre todo o conteudo");
		System.out.println("======================================================");
		
		it = this._entity.iterator();
		while(it.hasNext()){
			Entity temp = (Entity)it.next();
			System.out.println("Name: "+temp.get_name());
			
			ArrayList <String> descTotal = temp.get_conteudosToStringToOneArray();
			Absoluta absTotal = new Absoluta(descTotal);
			System.out.println("Description and frequency");
			for(int i=0; i<descTotal.size(); i++){
				String palavraT = descTotal.get(i);
				double freqT = absTotal.tfIJ(descTotal.get(i));
				if(freqT>0.8d){
					System.out.println(palavraT+"  "+freqT);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Control();
	}

}
