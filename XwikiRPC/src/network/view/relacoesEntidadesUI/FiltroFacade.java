package network.view.relacoesEntidadesUI;

import java.util.ArrayList;

import network.core.relacoesEntidades.Grafo;
import network.core.relacoesEntidades.Relacoes;
import xwiki.model.Page;

/**
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 24, maio, 2007
 */
public class FiltroFacade {

	private Relacoes r = new Relacoes();
	
	private static FiltroFacade INSTANCE = null;
	
	private FiltroFacade(){}
	
	public static FiltroFacade getInstance(){
		if(INSTANCE == null)
			INSTANCE = new FiltroFacade();
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param termosEscolhidos
	 * @return
	 */
	public Grafo gerandoGrafico(ArrayList<Page> docs){
		return r.geraGrafo(docs);
	}
	
	/**
	 * Com Named Entity Recognition
	 * @version 04, agosto, 2006
	 * 
	 * @param termosEscolhidos
	 * @param filtros
	 * 
	 * @return
	 */
	public Grafo gerandoGraficoInvertido(ArrayList<Page> docs){
		return r.geraGrafoInvertido(docs);
	}
}
