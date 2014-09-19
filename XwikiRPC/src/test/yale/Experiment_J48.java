package test.yale;

/**
 * Exemplo simples de utilizacao do algoritmo J48 do pacote YALE
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 */

import java.io.IOException;

import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.example.ExampleSet;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.Model;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.operator.OperatorCreationException;
import edu.udo.cs.yale.operator.OperatorException;
import edu.udo.cs.yale.operator.learner.Learner;
import edu.udo.cs.yale.tools.OperatorService;

public class Experiment_J48 {

	public static void main(String[] args) {
		try{
			Yale.init();
			//learn
			Operator exampleSource = OperatorService.createOperator("ExampleSource");
			exampleSource.setParameter("attributes","/opt/yale-3.2/sample/data/golf.xml");
			IOContainer container = exampleSource.apply(new IOContainer());
			ExampleSet exampleSet = (ExampleSet)container.get(ExampleSet.class);
			
			Learner learner = (Learner)OperatorService.createOperator("J48");
			//Cluster learner = (Cluster)OperatorService.createOperator("KMeans");
			Model model = learner.learn(exampleSet);
			
			
			
			//loading the test set (plus adding the model to result container )
			//Operator testSource = OperatorService.createOperator("ExampleSource");
			//testSource.setParameter("attributes","/opt/yale-3.2/sample/data/golf.test.xml");
			//container = testSource.apply(new IOContainer());
			//container = container.append(model);
			
			//applying the model
			//Operator modelApp = OperatorService.createOperator("ModelApplier");
			//container = modelApp.apply(container);
			
			//print model
			System.out.println(model.toResultString());
			
			//print results
			//ExampleSet resultSet = (ExampleSet)container.get(ExampleSet.class);
			//Attribute predictedLabel = resultSet.getPredictedLabel();
			//ExampleReader reader = resultSet.getExampleReader();
			//while(reader.hasNext()) {
			//	System.out.println(reader.next().getAttributesAsString()+"   "+reader.next().getValueAsString(predictedLabel));
			//}
		
		}catch(IOException e) {
			System.err.println("Cannot initialize YALE:" + e.getMessage());
		}catch(OperatorCreationException e) {
			System.err.println("Cannot create operator:" + e.getMessage());
		}catch(OperatorException e){
			System.err.println ("Cannot create model: " + e.getMessage());
		}
	}

}
