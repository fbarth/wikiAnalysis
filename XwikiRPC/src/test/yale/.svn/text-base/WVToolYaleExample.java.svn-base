package test.yale;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import edu.udo.cs.yale.Experiment;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.clustering.core.HierarchicalClusterModel;
import edu.udo.cs.yale.gui.ResultDisplay;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.tools.OperatorService;

/**
 * Illustrates how to use the word vector tool plugin and Yale 
 * from a Java application.
 * 
 * @author Michael Wurst
 * @version $Id$
 *
 * @author Fabrício J. Barth (altered by)
 * Illustrates how to use the word vector tool plugin and Yale 
 * from a Java application with graphic results.
 */

public class WVToolYaleExample extends JFrame{

	private ResultDisplay runExperiment(){
		try{
			Yale.init();
	        Experiment experiment = new Experiment();
	        Yale.setExperiment(experiment);
	        
	        Operator wvtoolOperator = OperatorService.createOperator("WVTool");
	        Operator clusterer = OperatorService.createOperator("UPGMAClustering");
	        clusterer.setParameter("distance_measure","euclidian");
	        clusterer.setParameter("cluster_distance_measure","average");
	        
	        experiment.registerOperator("WVTool",wvtoolOperator);
	        experiment.getRootOperator().addOperator(wvtoolOperator);
	                
	        List textList = new LinkedList();	        
	        textList.add(new Object[] {"teste","/home/fbarth/abordagensTextMining/miniBase2"});
	        
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
	        experiment.prepareRun();
	        
	        IOContainer out = wvtoolOperator.apply(new IOContainer());
	        out = clusterer.apply(out);
	        
	        HierarchicalClusterModel hcm = (HierarchicalClusterModel) out.get(HierarchicalClusterModel.class);
	            	        
	        experiment.run();
	        	        
	        ResultDisplay result = new ResultDisplay();
	        result.setData(out,"Resultados");
	        result.showSomething();
	        return result;
		}catch(Exception e){
			System.out.println("Error WVToolYaleExample.runExperiment "+e);
			return null;
		}
	}
	
	private void initialize(){
		this.setSize(900, 650);
		this.setTitle("Example using WVTool, UPGMAClustering and ResultDisplay in Yale package");
		this.setContentPane(runExperiment());
		this.setVisible(true);
	}
	
	public WVToolYaleExample(){
		try{
			initialize();
		}catch(Exception e){
			System.out.println("Error WVToolYaleExample "+e);
		}
	}
	
    public static void main(String[] args) throws Exception {
    	new WVToolYaleExample();
    }
}
