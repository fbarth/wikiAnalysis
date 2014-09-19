package document;
/**
 * 
 * @author Fabricio J. Barth
 * @since Dezember, 2005
 * @update January, 2006
 * 
 * Exemplo de utilizacao da classe StandardAnalyzer do Lucene
 * 
 */

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Token;

import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;


public class AnalisadorTexto {

	/**
	 * Stream de tokens analisados
	 * 
	 * @uml.property name="textoAnalisado"
	 * @uml.associationEnd multiplicity="(1 1)"
	 */
	private TokenStream textoAnalisado = null;

	
	/**
	 * Recebe o texto que deve ser analisado e 
	 * armazena no atributo org.apache.lucene.analysis.TokenStream textoAnalisado
	 * @param texto texto que deve ser analisado
	 */
	public AnalisadorTexto(String texto){
		try{
			//sem stopwords
			//StandardAnalyzer an = new StandardAnalyzer();
			
			/*
			 * Fazendo a analise do texto usando uma lista de stopwords
			 */
			StandardAnalyzer an = new StandardAnalyzer
				(PalavrasEliminadas.getInstance().getPalavrasEliminadas());
			textoAnalisado = an.tokenStream("texto",new StringReader(texto));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna o texto analisado no formato TokenStream
	 * @return texto analisado no formato TokenStream
	 */
	public TokenStream getTokenStream(){
		return this.textoAnalisado;
	}
	
	/**
	 * Verifica se o token informado como parametro deve
	 * continuar ou nao na lista de tokens
	 * @param token
	 * @return true/false
	 */
	private boolean tokenProibido(String token){
		if(token.matches("[-.,0-9]+")){
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna num ArrayList o texto analisado
	 * @return texto analisado
	 */
	public ArrayList<String> getTextoAnalisado(){
		Token t;
		ArrayList<String> retorno = new ArrayList<String>();
		try{
			while((t = textoAnalisado.next())!= null){
				if(!tokenProibido(t.termText())){
					retorno.add(t.termText());
				}
			}
		}catch(IOException e){}
		return retorno;
	}
	
	///**
	// * apenas para testes
	// * @param args[0] nome do arquivo pdf
	// */
	//public static void main(String[] args) {
	//	try{
	//		ExtractTextFromPDF extract = new ExtractTextFromPDF(args[0]);
	//		AnalisadorTexto analisador = new AnalisadorTexto(extract.getConteudoArquivoPDF());
	//	}catch(ArrayIndexOutOfBoundsException e1){
	//		System.out.println("java AnalisadorTexto arquivo.pdf");
	//	}catch(Exception e){
	//		e.printStackTrace();
	//	}
	//}
}
