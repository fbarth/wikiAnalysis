package test.yale;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import edu.udo.cs.wvtool.main.WVTDocumentInfo;
import edu.udo.cs.wvtool.main.WVTInputList;
import edu.udo.cs.wvtool.main.WVTWordVector;
import edu.udo.cs.yale.Experiment;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.tools.OperatorService;

public class ExperimentCluster {

	
	/**
	 * @return
	 */
	private static Experiment createExperiment(){
		try{
			Experiment experiment = new Experiment();
			
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
			//experiment.getRootOperator().addOperator(exampleSource);
			
			Operator learner = OperatorService.createOperator("UPGMAClustering");
			//Operator learner = OperatorService.createOperator("KMeans");
			experiment.getRootOperator().addOperator(learner);
			return experiment;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//private static Experiment createExperiment2(){
	//	try{
	//		Experiment experiment = new Experiment();
	//		Operator learner = OperatorService.createOperator("J48");
	//		experiment.getRootOperator().addOperator(learner);
	//		return experiment;
	//	}catch(Exception e){
	//		e.printStackTrace();
	//		return null;
	//	}
	//}

	//private static Experiment createExperiment3(){
	//	try{
	//		Experiment experiment = new Experiment();
	//		
	//		System.out.println("Carregando operador");
	//		Operator exampleSource = OperatorService.createOperator("WVTool");
	//		System.out.println("Configurando parametros 1");
	//		exampleSource.setParameter("default_content_language","portuguese");
	//		System.out.println("Configurando parametros 2");
	//		exampleSource.setParameter("output_word_list","training_words.list");
	//		System.out.println("Configurando parametros 3");
	//		exampleSource.setParameter("stemmer","edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper");
	//		System.out.println("Configurando parametros 4");
	//		
	//		WVTInputList textos = new WVTInputList(0);
	//		textos.addEntry(new WVTDocumentInfo("/home/fbarth/abordagensTextMining/miniBase","txt","","portuguese"));
	//		
	//		Enumeration en = textos.getEntries(true);
	//		List documents = new ArrayList();
	//		
	//		while(en.hasMoreElements()){
	//			List varios = new ArrayList();
	//			WVTDocumentInfo f = (WVTDocumentInfo)en.nextElement();
	//			System.out.println(f.getSourceName());
	//			WVTWordVector wordVector = new WVTWordVector();
	//			wordVector.setDocumentInfo(f);
	//			//varios.add(f.getSourceName());
	//			varios.add(wordVector.getValues());
	//			documents.add(varios.toArray());
	//		}
	//		
	//		
	//		/**
	//		 * documents deve ser um List onde cada elemento
	//		 * eh um Object[]
	//		 */
	//		exampleSource.setListParameter("texts",documents);
	//		experiment.getRootOperator().addOperator(exampleSource);
	//		Operator learner = OperatorService.createOperator("UPGMAClustering");
	//		//Operator learner = OperatorService.createOperator("KMeans");
	//		experiment.getRootOperator().addOperator(learner);
	//		experiment.setExperimentFile(new File("out.txt"));
	//		return experiment;
	//	}catch(Exception e){
	//		e.printStackTrace();
	//		return null;
	//	}
	//}

	
	private static IOContainer createInput(){
		try{
			System.out.println("Carregando operador");
			Operator exampleSource = OperatorService.createOperator("WVTool");
			System.out.println("Configurando parametros 1");
			exampleSource.setParameter("default_content_language","portuguese");
			System.out.println("Configurando parametros 2");
			exampleSource.setParameter("output_word_list","training_words.list");
			System.out.println("Configurando parametros 3");
			exampleSource.setParameter("stemmer","edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper");
			System.out.println("Configurando parametros 4");
			
			WVTInputList textos = new WVTInputList(1);
			textos.addEntry(new WVTDocumentInfo("/home/fbarth/abordagensTextMining/miniBase","txt","","portuguese",0));
			
			Enumeration en = textos.getEntries(true);
			List documents = new ArrayList();
			List varios = new ArrayList();
			while(en.hasMoreElements()){
				WVTDocumentInfo f = (WVTDocumentInfo)en.nextElement();
				System.out.println(f.getSourceName());
				varios.add(f.getSourceName());
			}
			documents.add(varios.toArray());
			/**
			 * documents deve ser um List onde cada elemento
			 * eh um Object[]
			 */
			exampleSource.setListParameter("texts",documents);
			IOContainer container = exampleSource.apply(new IOContainer());
			return container;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private static IOContainer createInput2(){
		try{
			//Operator exampleSource = OperatorService.createOperator("ExampleSource");
			Operator exampleSource = OperatorService.createOperator("ArffExampleSource");
			//exampleSource.setParameter("attributes","/opt/yale-3.2/sample/data/golf.xml");
			exampleSource.setParameter("data_file","/home/fbarth/eclipse/workspace/AprendizadoSupervisionadoMaisNaoSupervisionado/situacao.arff");
			IOContainer container = exampleSource.apply(new IOContainer());
			return container;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Yale.init();
		Experiment experiment = createExperiment();
		//Experiment experiment = createExperiment2();
		//Experiment experiment = createExperiment3();
		//IOContainer input = createInput();
		IOContainer input = createInput2();
		experiment.prepareRun();
		experiment.run(input);
		//experiment.run();
	}

}
