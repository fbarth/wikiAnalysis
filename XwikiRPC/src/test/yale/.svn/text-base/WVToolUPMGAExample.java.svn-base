package test.yale;

/**
 * WVTool
 * UPGMAClustering
 * 
 * @author Fabricio J. Barth (fbarth@atech.br)
 * @version 05, junho, 2006
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import edu.udo.cs.wvtool.main.WVTDocumentInfo;
import edu.udo.cs.wvtool.main.WVTInputList;
import edu.udo.cs.yale.Experiment;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.clustering.core.SimpleHierarchicalClusterModel;
import edu.udo.cs.yale.clustering.visualization.ClusterTreeVisualization;
import edu.udo.cs.yale.example.ExampleSet;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.operator.OperatorCreationException;
import edu.udo.cs.yale.operator.OperatorException;
import edu.udo.cs.yale.tools.OperatorService;

public class WVToolUPMGAExample {

	public static void main(String[] args) throws Exception{
		try{
			//Yale.init();
			//
			//System.out.println("Carregando operador");
			//Operator exampleSource = OperatorService.createOperator("WVTool");
			//System.out.println("Configurando parametros 1");
			//exampleSource.setParameter("default_content_language","portuguese");
			//System.out.println("Configurando parametros 2");
			//exampleSource.setParameter("output_word_list","training_words.list");
			//System.out.println("Configurando parametros 3");
			//exampleSource.setParameter("stemmer","edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper");
			//System.out.println("Configurando parametros 4");
			//
			//WVTInputList textos = new WVTInputList(1);
			//textos.addEntry(new WVTDocumentInfo("/home/fbarth/abordagensTextMining/miniBase","txt","","portuguese",0));
			//
			//Enumeration en = textos.getEntries(true);
			//List documents = new ArrayList();
			//List varios = new ArrayList();
			//while(en.hasMoreElements()){
			//	WVTDocumentInfo f = (WVTDocumentInfo)en.nextElement();
			//	System.out.println(f.getSourceName());
			//	varios.add(f.getSourceName());
			//}
			//documents.add(varios.toArray());
			/**
			 * documents deve ser um List onde cada elemento
			 * eh um Object[]
			 */
			//exampleSource.setListParameter("texts",documents);
			//
			//System.out.println("Criando o container de entrada");
			//Experiment e = new Experiment();
			//exampleSource.register(e,"");
			//IOContainer container = exampleSource.apply(new IOContainer());
			////IOContainer container = exampleSource.getInput();
			//System.out.println("Criando o conjunto de exemplos");
			//ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
			
			Yale.init();
			
			//learn
			Operator exampleSource = OperatorService.createOperator("ExampleSource");
			exampleSource.setParameter("attributes","/opt/yale-3.2/sample/data/polynomial.xml");
			IOContainer container = exampleSource.apply(new IOContainer());
			ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
			
			
			System.out.println("Tamanho do conjunto de treinamento: "+exampleSet.getSize());
			
			System.out.println("Iniciando o processo de aprendizado");
			//Learner learner = (Learner)OperatorService.createOperator("UPGMAClustering");
			Operator learner = OperatorService.createOperator("UPGMAClustering");
						
			SimpleHierarchicalClusterModel hc;
			hc = new SimpleHierarchicalClusterModel();
			
			//learner.register(e,"");
			System.out.println("Criando o modelo");
			//Model model = learner.learn(exampleSet);
			//learner.apply();
			container = learner.apply(container);
			System.out.println("Imprimindo o modelo");
			//System.out.println(model.toResultString());
			//e.run();
			//e.stop();
			
			System.out.println(container.toString());
			
			ClusterTreeVisualization visualization = new ClusterTreeVisualization(hc);
			
		}catch(IOException e) {
			System.err.println("Cannot initialize YALE:" + e.getMessage());
		}catch(OperatorCreationException e) {
			System.err.println("Cannot create operator:" + e.getMessage());
		}catch(OperatorException e){
			System.err.println ("Cannot create model: " + e.getMessage());
		}
	}
}
