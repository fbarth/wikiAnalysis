package network.core.relacoesEntidades;


public class Aresta {

	/**
	 * @uml.property  name="valor"
	 */
	private String valor = "";

	/**
	 * Getter of the property <tt>valor</tt>
	 * @return  Returns the valor.
	 * @uml.property  name="valor"
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Setter of the property <tt>valor</tt>
	 * @param valor  The valor to set.
	 * @uml.property  name="valor"
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @uml.property  name="n1"
	 */
	private Nodo n1;

	/**
	 * Getter of the property <tt>n1</tt>
	 * @return  Returns the n1.
	 * @uml.property  name="n1"
	 */
	public Nodo getN1() {
		return n1;
	}

	/**
	 * Setter of the property <tt>n1</tt>
	 * @param n1  The n1 to set.
	 * @uml.property  name="n1"
	 */
	public void setN1(Nodo n1) {
		this.n1 = n1;
	}

	/**
	 * @uml.property  name="n2"
	 */
	private Nodo n2;

	/**
	 * Getter of the property <tt>n2</tt>
	 * @return  Returns the n2.
	 * @uml.property  name="n2"
	 */
	public Nodo getN2() {
		return n2;
	}

	/**
	 * Setter of the property <tt>n2</tt>
	 * @param n2  The n2 to set.
	 * @uml.property  name="n2"
	 */
	public void setN2(Nodo n2) {
		this.n2 = n2;
	}
	
	public Aresta(Nodo n1, Nodo n2, String valor){
		this.n1 = n1;
		this.n2 = n2;
		this.valor = valor;
	}

}
