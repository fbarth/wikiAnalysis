package test.yale;

/**
 * Um exemplo simples de utilizacao do plugin WVTool do YALE.
 * 
 * @author Fabricio J. Barth (fbarth@atech.br)
 * 
 */

import java.io.FileWriter;

import edu.udo.cs.wvtool.config.WVTConfiguration;
import edu.udo.cs.wvtool.config.WVTConfigurationFact;
import edu.udo.cs.wvtool.generic.output.WordVectorWriter;
import edu.udo.cs.wvtool.generic.stemmer.SnowballStemmerWrapper;
import edu.udo.cs.wvtool.generic.vectorcreation.TFIDF;
import edu.udo.cs.wvtool.main.WVTDocumentInfo;
import edu.udo.cs.wvtool.main.WVTInputList;
import edu.udo.cs.wvtool.main.WVTool;
import edu.udo.cs.wvtool.wordlist.WVTWordList;

public class WVToolExample {

	public static void main(String[] args) throws Exception{
		WVTool wvt = new WVTool(true);
		WVTConfiguration config = new WVTConfiguration();
		config.setConfigurationRule(WVTConfiguration.STEP_STEMMER,
				new WVTConfigurationFact(new SnowballStemmerWrapper()));
		WVTInputList list = new WVTInputList(1);
		list.addEntry(new WVTDocumentInfo("/home/fbarth/abordagensTextMining/miniBase",
				"txt","","portuguese",0));
		WVTWordList wordList = wvt.createWordList(list,config);
		wordList.storePlain(new FileWriter("wordlist.txt"));
		FileWriter outfile = new FileWriter("wv.txt");
		WordVectorWriter wvw = new WordVectorWriter(outfile,true);
				
		config.setConfigurationRule(WVTConfiguration.STEP_OUTPUT,
				new WVTConfigurationFact(wvw));
		config.setConfigurationRule(WVTConfiguration.STEP_VECTOR_CREATION,
				new WVTConfigurationFact(new TFIDF()));
		wvt.createVectors(list,config,wordList);
		wvw.close();
		outfile.close(); 
		
		
		////Yale.init();
		//////learn
		////Operator exampleSource = OperatorService.createOperator("ExampleSource");
		////exampleSource.setParameter("attributes","/opt/yale-3.2/sample/data/golf.xml");
		////IOContainer container = exampleSource.apply(new IOContainer());
		////ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
		////
		////Learner learner = (Learner)OperatorService.createOperator("J48");
		////Model model = learner.learn(exampleSet);
		////
		////System.out.println(model.toResultString());
		//
		//Yale.init();
		//
		////OperatorDescription od = new OperatorDescription(,"WVTool","WVToolOperator","Trata documentos do tipo texto","Text","wvt");
		////Operator exampleSource = OperatorService.createOperator(WVToolOperator.class);
		//
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
		/**
		 * tem alguma coisa errada aqui
		 */
		//ArrayList lt = new ArrayList();
		//ArrayList al = new ArrayList();
		//al.add("/home/fbarth/abordagensTextMining/miniBase/");
		//ParameterTypeString st = new ParameterTypeString("1","/home/fbarth/abordagensTextMining/miniBase/");
		//ParameterTypeList ps = new ParameterTypeList("1","Texto 1",st,al);
		//lt.add(ps);
		//lt.add("/home/fbarth/abordagensTextMining/miniBase/");
		//exampleSource.setListParameter("texts",lt);
		//exampleSource.setParameter("texts","/home/fbarth/abordagensTextMining/miniBase/");
		//exampleSource.setListParameter("texts",ps);
		
		/*
		 * Nova versao
		 */
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
		//
		//System.out.println("Criando o container de entrada");
		//IOContainer container = exampleSource.apply(new IOContainer());
		////IOContainer container = exampleSource.apply(WVTool.class);
		//System.out.println("Criando o conjunto de exemplos");
		//ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
	
		//estah errado !!!
		//Operator exampleSource = OperatorService.createOperator("ExampleSource");
		//exampleSource.setParameter("attributes","wv.txt");
		//IOContainer container = exampleSource.apply(new IOContainer());
		//ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
		
		//OperatorDescription op = OperatorService.getOperatorDescription("UPGMA");		
		
		//System.out.println("Iniciando o processo de aprendizado");
		//Learner learner = (Learner)OperatorService.createOperator("UPGMA");
		//Model model = learner.learn(exampleSet);
		
		//System.out.println(model.toResultString());
	}
}
