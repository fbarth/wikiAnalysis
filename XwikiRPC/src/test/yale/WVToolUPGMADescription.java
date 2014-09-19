package test.yale;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import edu.udo.cs.yale.Experiment;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.clustering.core.Cluster;
import edu.udo.cs.yale.clustering.core.FlatClusterModel;
import edu.udo.cs.yale.clustering.core.HierarchicalClusterModel;
import edu.udo.cs.yale.gui.ResultDisplay;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.tools.OperatorService;

/**
 * 
 * Dado um conjunto de documentos do tipo texto, esta classe
 * converte estes documentos em vetores e aplica ao conjunto
 * de vetores um algoritmo de agrupamento hierarquico (UPGMA)
 * e um algoritmo de agrupamento plano (Kmeans).
 * 
 * TODO Falta implementar um metodo para realizar a descricao
 * dos agrupamentos utilizando as palavras mais significativas
 * de cada agrupamento.
 * 
 * Os agrupamentos definidos sao visualizados em uma interface 
 * grafica.
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 12, Junho, 2006
 *
 */

public class WVToolUPGMADescription extends JFrame{

	private ResultDisplay runExperiment(String diretorio){
		try{
			Yale.init();
	        Experiment experiment = new Experiment();
	        Yale.setExperiment(experiment);
	        
	        Operator wvtoolOperator = OperatorService.createOperator("WVTool");
	        Operator clusterer = OperatorService.createOperator("UPGMAClustering");
	        clusterer.setParameter("keep_example_set","true");
	        clusterer.setParameter("distance_measure","euclidian");
	        clusterer.setParameter("cluster_distance_measure","average");
	        
	        experiment.registerOperator("WVTool",wvtoolOperator);
	        experiment.getRootOperator().addOperator(wvtoolOperator);
	        
	                
	        List textList = new LinkedList();	        
	        textList.add(new Object[] {"infopol",diretorio});
	        
	        Operator clusterFlat = OperatorService.createOperator("KMeans");
	        clusterFlat.setParameter("k","4");
	        clusterFlat.setParameter("max_runs","10");
	        clusterFlat.setParameter("max_optimization_steps","1000");
	        
	        wvtoolOperator.setListParameter("texts", textList);
	        wvtoolOperator.setParameter("default_content_language","portuguese");
	        wvtoolOperator.setParameter("loader","edu.udo.cs.wvtool.generic.loader.UniversalLoader");
	        wvtoolOperator.setParameter("inputfilter","edu.udo.cs.wvtool.generic.inputfilter.TextInputFilter");
	        wvtoolOperator.setParameter("charmapper","edu.udo.cs.wvtool.generic.charmapper.DummyCharConverter");
	        wvtoolOperator.setParameter("tokenizer","edu.udo.cs.wvtool.generic.tokenizer.SimpleTokenizer");
	        wvtoolOperator.setParameter("wordfilter","edu.udo.cs.wvtool.generic.wordfilter.StopWordsWrapper");
	        wvtoolOperator.setParameter("stemmer","edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper");
	        wvtoolOperator.setParameter("vectorcreation","edu.udo.cs.wvtool.generic.vectorcreation.TFIDF");
	        wvtoolOperator.setParameter("prune_below","-1");
	        wvtoolOperator.setParameter("prune_above","-1");
	     
	        experiment.registerOperator("Cluster",clusterer);
	        experiment.getRootOperator().addOperator(clusterer);
	        
	        experiment.registerOperator("ClusterFlat",clusterFlat);
	        experiment.getRootOperator().addOperator(clusterFlat);
	        
	        experiment.prepareRun();
	        
	        IOContainer out = wvtoolOperator.apply(new IOContainer());
	        out = clusterer.apply(out);
	        
	        HierarchicalClusterModel hcm = (HierarchicalClusterModel) out.get(HierarchicalClusterModel.class);
	        
	        out = clusterFlat.apply(out);
	        FlatClusterModel fcm = (FlatClusterModel)out.get(FlatClusterModel.class);
	        
	        for(int i=0; i<fcm.getNumberOfClusters(); i++){
	        	Cluster temp = fcm.getClusterAt(i);
	        	Iterator elementos = temp.getObjects();
	        	while(elementos.hasNext()){
	        		String elemento = elementos.next().toString();
	        		System.out.println(elemento);
	        		
	        	}
	        	System.out.println(temp.getDescription());
	        }
	        
	        experiment.run();
	        	        
	        ResultDisplay result = new ResultDisplay();
	        result.setData(out,"Resultados");
	        result.showSomething();
	        return result;
		}catch(Exception e){
			System.out.println("Error WVToolUPGMA.runExperiment "+e);
			return null;
		}
	}
	
	private void initialize(String diretorio){
		this.setSize(900, 650);
		this.setTitle("Agrupamento Hierárquico dos documentos");
		this.setContentPane(runExperiment(diretorio));
		this.setVisible(true);
	}
	
	public WVToolUPGMADescription(String diretorio){
		try{
			initialize(diretorio);
		}catch(Exception e){
			System.out.println("Error WVToolUPGMA "+e);
		}
	}
	
    public static void main(String[] args) throws Exception {
    	new WVToolUPGMADescription(args[0]);
    }
}
