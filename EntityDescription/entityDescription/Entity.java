package entityDescription;
import java.util.ArrayList;
//import java.util.Vector;

import org.apache.lucene.analysis.Token;

import document.AnalisadorTexto;
//import frequency.TFIDF;
import brazilianStemmer.BrazilianStemFilter;

public class Entity {

	/**
	 * @uml.property  name="_name"
	 */
	private String _name = "";

	/**
	 * Getter of the property <tt>_name</tt>
	 * @return  Returns the _name.
	 * @uml.property  name="_name"
	 */
	public String get_name() {
		return _name;
	}

	/**
	 * Setter of the property <tt>_name</tt>
	 * @param _name  The _name to set.
	 * @uml.property  name="_name"
	 */
	public void set_name(String _name) {
		this._name = _name;
	}

	/**
	 * @uml.property  name="_description"
	 */
	private ArrayList <Token> _description;

	/**
	 * Getter of the property <tt>_description</tt>
	 * @return  Returns the _description.
	 * @uml.property  name="_description"
	 */
	public ArrayList <Token> get_description() {
		return _description;
	}

	/**
	 * Setter of the property <tt>_description</tt>
	 * @param _description  The _description to set.
	 * @uml.property  name="_description"
	 */
	public void set_description(String[] snippets) {
		/*
		 * Usando um Analyser do Lucene (Brasileiro)
		 */
		try{
			for(int i=0; i<snippets.length; i++){
				AnalisadorTexto at = new AnalisadorTexto(snippets[i]);
				//ArrayList al = at.getTextoAnalisado();
				BrazilianStemFilter bs = new BrazilianStemFilter(at.getTokenStream());
				Token t;
				while((t = bs.next()) != null)
					this._description.add(t);
			}
		}catch(java.io.IOException e){
			System.out.println("Aconteceu um erro com a leitura do Token");
		}
		
		/*
		 * usando um metodo bem simples de identificacao de
		 * tokens
		 */
		//for(int i=0; i<snippets.length; i++){
		//	StringTokenizer st = new StringTokenizer(snippets[i]);
		//	while(st.hasMoreTokens()){
		//		this._description.add(st.nextToken());
		//	}
		//}
	}
	
	/**
	 * Retorna as cinco palavras com maior frequencia
	 * em java.util.ArrayList _description
	 * 
	 * O metodo para calculo de frequencia utilizado foi
	 * TFIDF
	 * metodo inutilizado
	 */
	//public ArrayList <Token> getMainDescription(){
	//	//TFIDF t = new TFIDF(this._snippets);
	//	/*
	//	 * falta calcular
	//	 */
	//	
	//	return this._description;
	//}
	
	/*
	 * Armazena cada _snippets da entidade
	 * Tive que fazer isto para poder calcular
	 * a frequencia utilizando o TFIDF
	 * 
	 * 07/04/2006
	 */
	private ArrayList <ArrayList> _snippets;
	
	//public void set_snippets(Vector snippets){
	//	//Vector temp = new Vector();
	//	//for(int i=0; i<snippets.size(); i++){
	//	//	temp.addElement(snippets.elementAt(i));
	//	//}
	//	//this._snippets.add(temp);
	//	this._snippets.add(snippets);
	//}

	/**
	 * Setter of the property <tt>_snippets</tt>
	 * @param _snippets  The _snippets to set.
	 * @uml.property  name="_snippets"
	 */
	public void set_snippets(String[] snippets) {
		/*
		 * Usando um Analyser do Lucene (Brasileiro)
		 */
		try{
			for(int i=0; i<snippets.length; i++){
				AnalisadorTexto at = new AnalisadorTexto(snippets[i]);
				//ArrayList al = at.getTextoAnalisado();
				BrazilianStemFilter bs = new BrazilianStemFilter(at.getTokenStream());
				Token t;
				ArrayList<String> a = new ArrayList<String>();
				while((t = bs.next()) != null){
					a.add(t.termText());
				}
				this._snippets.add(a);
			}
		}catch(java.io.IOException e){
			System.out.println("Aconteceu um erro com a leitura do Token");
		}
	}
	
	
	public ArrayList get_snippets(){
		return this._snippets;
	}
	
	public ArrayList <String> get_descriptionToString(){
		ArrayList<String> retorno = new ArrayList <String>();
		for(int i=0; i<this._description.size(); i++){
			retorno.add(((Token)this._description.get(i)).termText());
		}
		return retorno;
	}

	//public ArrayList <String> get_snippetsToString(int i){
	//	ArrayList retorno = this._snippets.get(i);
	//	for(int j=0; i<this._description.size(); j++){
	//		retorno.add(((Token)this._description.get(j)).termText());
	//	}
	//	return retorno;
	//}

	
	private ArrayList <ArrayList> _conteudos;
	
	/**
	 * Setter of the property <tt>_snippets</tt>
	 * @param _snippets  The _snippets to set.
	 * @uml.property  name="_snippets"
	 */
	public void set_conteudos(String[] conteudos) {
		/*
		 * Usando um Analyser do Lucene (Brasileiro)
		 */
		try{
			for(int i=0; i<conteudos.length; i++){
				AnalisadorTexto at = new AnalisadorTexto(conteudos[i]);
				//ArrayList al = at.getTextoAnalisado();
				BrazilianStemFilter bs = new BrazilianStemFilter(at.getTokenStream());
				Token t;
				ArrayList<String> a = new ArrayList<String>();
				while((t = bs.next()) != null){
					a.add(t.termText());
				}
				this._conteudos.add(a);
			}
		}catch(java.io.IOException e){
			System.out.println("Aconteceu um erro com a leitura do Token");
		}
	}

	
	public ArrayList get_conteudos(){
		return this._conteudos;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList <String> get_conteudosToStringToOneArray(){
		ArrayList<String> retorno;
		try{
			retorno = new ArrayList <String> ();
			for(int i=0; i<this._conteudos.size(); i++){
				ArrayList <Token> t = this._conteudos.get(i);
				for(int j=0; j<t.size(); j++){
					retorno.add(((Token)t.get(j)).termText());
				}
			}
		}catch(Exception e){
			System.out.println("Erro Entity.get_conteudosToStringToOneArray(): "+e);
			retorno = null;
		}
		return retorno;
	}

	
	public Entity(String name){
		this._name = name;
		this._description = new ArrayList <Token> ();
		this._snippets = new ArrayList <ArrayList> ();
		this._conteudos = new ArrayList <ArrayList>();
	}

}
