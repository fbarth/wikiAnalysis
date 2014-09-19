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

package network.core.relacoesEntidades;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Documento {

	private String id;
	
	/**
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public String getId(){
		return this.id;
	}
	
	/**
	 * @uml.property  name="palavras"
	 */
	private HashSet palavras;

	/**
	 * Getter of the property <tt>palavras</tt>
	 * @return  Returns the palavras.
	 * @uml.property  name="palavras"
	 */
	public HashSet getPalavras() {
		return palavras;
	}

	/**
	 * Setter of the property <tt>palavras</tt>
	 * @param palavras  The palavras to set.
	 * @uml.property  name="palavras"
	 */
	public void setPalavras(HashSet palavras) {
		this.palavras = palavras;
	}
	
	
	//public void addPalavra(String palavra){
	//	this.palavras.add(palavra);
	//}

	/**
	 * @uml.property  name="nomeArquivo"
	 */
	private String nomeArquivo;

	/** 
	 * Getter of the property <tt>nome</tt>
	 * @return  Returns the nome.
	 * @uml.property  name="nomeArquivo"
	 */
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	/** 
	 * Setter of the property <tt>nome</tt>
	 * @param nome  The nome to set.
	 * @uml.property  name="nomeArquivo"
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	
	/**
	 * Descreve se o documento eh ou nao Boletim de Ocorrncia
	 * Esta informao eh utilizada na identificao de entidades
	 * nomeadas.
	 * 
	 * A identificao das entidades nomeadas para os BOs acontece
	 * atravs de um select direto no banco de dados.
	 * 
	 */
	private boolean ehBoletimOcorrencia = false;
	
	
	public boolean ehBoletimOcorrencia(){
		return this.ehBoletimOcorrencia;
	}
	
	public void setEhBoletimOcorrencia(boolean b){
		this.ehBoletimOcorrencia = b;
	}
	
	/**
	 * Dado um arquivo formato texto, este metodo retorna
	 * um conjunto de palavras (Set), os atomos deste arquivo
	 * @param nomeArquivo
	 * @return
	 */
	private HashSet<String> trataArquivo(String nomeArquivo){
		try{
			HashSet<String> retorno = new HashSet<String>();
			File f = new File(nomeArquivo);
			RandomAccessFile rf = new RandomAccessFile(f,"rw");
			String linha = rf.readLine();
			while(linha != null){
				StringTokenizer st = new StringTokenizer(linha);
				while(st.hasMoreTokens())
					retorno.add(st.nextToken().toString().toLowerCase());
				linha = rf.readLine();
			}
			rf.close();
			return retorno;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private HashSet<String> trataConteudo(String cont){
		HashSet<String> retorno = new HashSet<String>();
		StringTokenizer st = new StringTokenizer(cont);
		while(st.hasMoreTokens())
			retorno.add(st.nextToken().toString().toLowerCase());
		return retorno;
	}
	
	/**
	 * 
	 * @param id
	 * @param nomeArquivo
	 * @param ehBoletim
	 */
	public Documento(String id, String nomeArquivo, boolean ehBoletim){
		this.id = id;
		this.nomeArquivo = nomeArquivo;
		this.palavras = trataArquivo(this.nomeArquivo);
		this.termosEncontrados = new HashSet<String>();
		this.ehBoletimOcorrencia = ehBoletim;
	}
	
	private String texto;
	
	public String getTexto(){
		return this.texto;
	}
	
	public void setTexto(String t){
		this.texto = t;
	}
	
	/**
	 * 
	 * @param id
	 * @param url
	 * @param conteudo
	 * @param ehBoletim
	 */
	public Documento(String id, String url, String conteudo, boolean ehBoletim){
		this.id = id;
		this.nomeArquivo = url;
		this.palavras = trataConteudo(conteudo);
		this.termosEncontrados = new HashSet<String>();
		this.texto = conteudo;
		this.ehBoletimOcorrencia = ehBoletim;
	}

	/**
	 * @uml.property  name="termosEncontrados"
	 */
	private HashSet<String> termosEncontrados;

	/**
	 * Getter of the property <tt>termosEncontrados</tt>
	 * @return  Returns the termosEncontrados.
	 * @uml.property  name="termosEncontrados"
	 */
	public HashSet<String> getTermosEncontrados() {
		//Object[] temp = this.termosEncontrados.toArray();
		//Arrays.sort(temp);
		//HashSet newHash = new HashSet();
		//for(int i=0; i<temp.length; i++)
		//	newHash.add(temp[i]);
		//return newHash;
		return termosEncontrados;
	}
	
	public String printTermosEncontrados(){
		String retorno = "";
		Iterator it = this.termosEncontrados.iterator();
		while(it.hasNext()){
			retorno += it.next().toString()+"\n";
		}
		return retorno;
	}

	/**
	 * Setter of the property <tt>termosEncontrados</tt>
	 * @param termosEncontrados  The termosEncontrados to set.
	 * @uml.property  name="termosEncontrados"
	 */
	public void setTermosEncontrados(HashSet<String> termosEncontrados) {
		this.termosEncontrados = termosEncontrados;
	}
	
	public void addTermoEncontrado(String palavra){
		this.termosEncontrados.add(palavra);
	}
	
}
