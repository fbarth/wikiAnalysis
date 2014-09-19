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

import java.util.ArrayList;

public class Grafo {

	/**
	 * @uml.property  name="nodos"
	 */
	private ArrayList <Nodo> nodos;

	public boolean setNodo(Nodo n){
		if(existeNodoIgual(n.getNome())){
			return false;
		}else{
			nodos.add(n);
			return true;
		}
	}
	
	public boolean setNodo(String nomeNodo, String arquivo){
		if(existeNodoIgual(nomeNodo)){
			return false;
		}else{
			nodos.add(new Nodo(nomeNodo,arquivo));
			return true;
		}
	}
	
	private boolean existeNodoIgual(String nomeNodo){
		for(int i=0; i<this.nodos.size(); i++){
			Nodo temp = this.nodos.get(i);
			if(temp.getNome().equals(nomeNodo)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Getter of the property <tt>nodos</tt>
	 * @return  Returns the nodos.
	 * @uml.property  name="nodos"
	 */
	public ArrayList <Nodo> getNodos() {
		return nodos;
	}

	/**
	 * Setter of the property <tt>nodos</tt>
	 * @param nodos  The nodos to set.
	 * @uml.property  name="nodos"
	 */
	public void setNodos(ArrayList <Nodo> nodos) {
		this.nodos = nodos;
	}

	/**
	 * @uml.property  name="arestas"
	 */
	private ArrayList <Aresta> arestas;

	
	public boolean setAresta(String nomeNodo1, String arquivo1, String nomeNodo2, String arquivo2, String valor){
		if(existeArestaIgual(nomeNodo1,nomeNodo2,valor)){
			return false;
		}else{
			this.arestas.add(new Aresta(new Nodo(nomeNodo1,arquivo1), new Nodo(nomeNodo2,arquivo2), valor));
			return true;
		}
	}
	
	private boolean existeArestaIgual(String nomeNodo1, String nomeNodo2, String valor){
		for(int i=0; i<this.arestas.size(); i++){
			Aresta temp = this.arestas.get(i);
			if(temp.getN1().getNome().equals(nomeNodo1) &&
					temp.getN2().getNome().equals(nomeNodo2) &&
					temp.getValor().equals(valor)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Getter of the property <tt>arestas</tt>
	 * @return  Returns the arestas.
	 * @uml.property  name="arestas"
	 */
	public ArrayList <Aresta> getArestas() {
		return arestas;
	}

	/**
	 * Setter of the property <tt>arestas</tt>
	 * @param arestas  The arestas to set.
	 * @uml.property  name="arestas"
	 */
	public void setArestas(ArrayList <Aresta> arestas) {
		this.arestas = arestas;
	}
	
	public Grafo(){
		this.arestas = new ArrayList<Aresta>();
		this.nodos = new ArrayList<Nodo>();
		instance = this;
	}

	/*
	 * Adicionado em 14,Ago,2006
	 */
	
	private static Grafo instance;
	
	public static Grafo getInstance(){
		if(instance==null){
			instance = new Grafo();
		}
		return instance;	
	}
	
	/*
	 * Fim da adicao
	 */
	
	public String getArquivoDoNodo(String id){
		for(int i=0; i<this.nodos.size(); i++){
			if(this.nodos.get(i).getNome().equals(id)){
				//System.out.println("Retornando "+i+"  "+this.nodos.get(i).getArquivo());
				return this.nodos.get(i).getArquivo();
			}
		}
		return null;
	}
	
	public String getArquivoDaAresta(String id){
		for(int i=0; i<this.arestas.size(); i++){
			if(this.arestas.get(i).getValor().equals(id)){
				/*
				 * tanto faz se for getN1() ou getN2()
				 * o valor deve ser o mesmo.
				 */
				return this.arestas.get(i).getN1().getArquivo();
			}
		}
		return null;
	}
	
	public String printNodos(){
		String retorno = "";
		for(int i=0; i<this.nodos.size(); i++)
			retorno = retorno + this.nodos.get(i).getNome() + "\n";
		return retorno;
	}
	
	public String printArestas(){
		String retorno = "";
		for(int i=0; i<this.arestas.size(); i++)
			retorno = retorno + this.arestas.get(i).getValor() +
			" " + this.arestas.get(i).getN1().getNome() + " " +
			this.arestas.get(i).getN2().getNome() + "\n";
		return retorno;
	}
}
