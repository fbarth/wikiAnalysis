package test.yale;

import java.awt.Component;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.udo.cs.yale.Experiment;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.clustering.core.FlatClusterModel;
import edu.udo.cs.yale.clustering.core.HierarchicalClusterModel;
import edu.udo.cs.yale.clustering.visualization.ClusterGraphVisualization;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.tools.OperatorService;

/**
 * 
 * Dado um conjunto de documentos do tipo texto, esta classe
 * converte estes documentos em vetores e aplica ao conjunto
 * de vetores um algoritmo de agrupamento hierárquico (UPGMA)
 * e um algoritmo de agrupamento plano (Kmeans).
 * 
 * Os agrupamentos definidos sao visualizados em uma interface 
 * grafica.
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 12, Junho, 2006
 *
 */

public class WVToolUPGMAVisual extends JFrame{

	/**
	 * Aplica o algoritmo Kmeans e UPGMA ao conjunto de documentos
	 * presente no diretorio passado como parametro
	 * 
	 * @param diretorio caminho para o diretorio
	 * @return um objeto da interface que permite a visualizacao
	 * dos agrupamentos
	 */
	//private ResultDisplay runExperiment(String diretorio){
	private Component runExperiment(String diretorio){
	//private MyResultDisplay runExperiment(String diretorio){
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
	        textList.add(new Object[] {"textos",diretorio});
	        
	        //Operator clusterFlat = OperatorService.createOperator("KMeans");
	        //clusterFlat.setParameter("k","4");
	        //clusterFlat.setParameter("max_runs","10");
	        //clusterFlat.setParameter("max_optimization_steps","1000");
	        
	        wvtoolOperator.setListParameter("texts", textList);
	        wvtoolOperator.setParameter("default_content_language","portuguese");
	        wvtoolOperator.setParameter("loader","edu.udo.cs.wvtool.generic.loader.UniversalLoader");
	        wvtoolOperator.setParameter("inputfilter","edu.udo.cs.wvtool.generic.inputfilter.TextInputFilter");
	        wvtoolOperator.setParameter("charmapper","edu.udo.cs.wvtool.generic.charmapper.DummyCharConverter");
	        wvtoolOperator.setParameter("tokenizer","edu.udo.cs.wvtool.generic.tokenizer.SimpleTokenizer");
	        /*
	         * TODO StopWord para a lingua portuguesa ??
	         */
	        wvtoolOperator.setParameter("wordfilter","edu.udo.cs.wvtool.generic.wordfilter.StopWordsWrapper");
	        /*
	         * TODO BrazilianStemmer ??
	         */
	        wvtoolOperator.setParameter("stemmer","edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper");
	        wvtoolOperator.setParameter("vectorcreation","edu.udo.cs.wvtool.generic.vectorcreation.TFIDF");
	        wvtoolOperator.setParameter("prune_below","-1");
	        wvtoolOperator.setParameter("prune_above","-1");
	        wvtoolOperator.setParameter("id_attribute_type","long");
	     
	        experiment.registerOperator("Cluster",clusterer);
	        experiment.getRootOperator().addOperator(clusterer);
	        
	        //experiment.registerOperator("ClusterFlat",clusterFlat);
	        //experiment.getRootOperator().addOperator(clusterFlat);
	        
	        /**
	         * Aparentemente isto foi uma mudanca do 
	         * yale3.2 para o yale3.3
	         * O métdo prepareRun() não está mais visível.
	         */
	        experiment.prepareRun();
	        
	        IOContainer out = wvtoolOperator.apply(new IOContainer());
	        out = clusterer.apply(out);
	        
	        HierarchicalClusterModel hcm = (HierarchicalClusterModel) out.get(HierarchicalClusterModel.class);
	        
	        //out = clusterFlat.apply(out);
	        //FlatClusterModel fcm = (FlatClusterModel)out.get(FlatClusterModel.class);
	        
	        experiment.run();
	        	        
	        //ResultDisplay result = new ResultDisplay();
	        //result.setData(out,"Resultados");
	        //result.showSomething();
	        
	        //MyResultDisplay result = new MyResultDisplay();
	        //result.setData(out,"Resultados");
	        //result.showSomething();
	        //return result;
	        
	        //return hcm.getVisualisationComponent();
	        
	        MyClusterGraphVisualization result = new MyClusterGraphVisualization(hcm);
	        return result;
	        
		}catch(Exception e){
			System.out.println("Error WVToolUPGMAVisual.runExperiment "+e);
			return null;
		}
	}
	
	private JPanel panel = null;
	
	private JPanel getJPanel(String diretorio){
		if(panel == null){
			panel = new JPanel();
			panel.setSize(900,650);
			panel.add(runExperiment(diretorio));
		}
		return panel;
	}
	
	private void initialize(String diretorio){
		this.setSize(900, 650);
		this.setTitle("Agrupamento Hierárquico dos documentos");
		//this.setLayout(new BorderLayout());
		this.setContentPane(getJPanel(diretorio));
		//this.setContentPane(runExperiment(diretorio));
		this.setVisible(true);
	}
	
	public WVToolUPGMAVisual(String diretorio){
		try{
			initialize(diretorio);
		}catch(Exception e){
			System.out.println("Error WVToolUPGMAVisual "+e);
		}
	}
	
    public static void main(String[] args) throws Exception {
    	new WVToolUPGMAVisual(args[0]);
    }
}
