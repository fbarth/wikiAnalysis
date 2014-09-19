package yale;

import java.awt.BorderLayout;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.User;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.graph.impl.SparseTree;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.udo.cs.yale.ObjectVisualizer;
import edu.udo.cs.yale.Yale;
import edu.udo.cs.yale.clustering.core.ClusterNode;
import edu.udo.cs.yale.clustering.core.FlatClusterModel;
import edu.udo.cs.yale.clustering.core.HierarchicalClusterModel;
import edu.udo.cs.yale.clustering.core.SimpleHierarchicalClusterModel;

/**
 * Visualizes a cluster as a graph using the JUNG package.
 * 
 * @author Michael Wurst
 * @version $Id: ClusterGraphVisualization.java,v 1.3 2006/08/03 14:39:51 ingomierswa Exp $
 * 
 * Alterado por
 * @author Fabricio J. Barth
 * @version 03, outubro, 2006
 *
 */
public class MyClusterGraphVisualizationUsers extends MyJUNGVisualization {

    private static final long serialVersionUID = -8141164104359659162L;
    
	JList itemList = null;

    public MyClusterGraphVisualizationUsers(FlatClusterModel cm) {

        this(new SimpleHierarchicalClusterModel(cm));
    }

    public MyClusterGraphVisualizationUsers(HierarchicalClusterModel hcm) {

        init(createGraph(hcm));
        setUpGUIElements();
    }

    private SparseTree createGraph(HierarchicalClusterModel hcm) {

        if (hcm.getRootNode() == null)
            return new SparseTree(new SparseVertex());

        Vertex root = new ClusterVertexUsers(hcm.getRootNode());

        SparseTree graph = new SparseTree(root);

        /**
         * Tive que mudar porque estou usando
         * a versao yale3.2
         */
        //Iterator it = hcm.getRootNode().getSubNodes();
        Iterator it = hcm.getRootNode().getChildren();
        while (it.hasNext())
            createGraph(graph, root, (ClusterNode) it.next());

        return graph;
    }

    private void createGraph(Graph graph, Vertex parent, ClusterNode node) {

        // Create vertex
        Vertex v = new ClusterVertexUsers(node);
        graph.addVertex(v);
        graph.addEdge(new DirectedSparseEdge(parent, v));

        /**
         * Tive que mudar porque estou usando
         * a versao yale3.2
         */
        //Iterator it = node.getSubNodes();
        Iterator it = node.getChildren();
        while (it.hasNext())
            createGraph(graph, v, (ClusterNode) it.next());

    }

    ClusterNode nodeClicked;
    
    public void vertexClicked(Vertex selectedVertex_) {

        // Visualization
        DefaultListModel items = new DefaultListModel();
        nodeClicked = ((ClusterVertexUsers) selectedVertex_).getNode();
        
        /**
         * Tive que mudar porque estou usando
         * a versao yale3.2
         */
        //Iterator it = nodeClicked.getObjectsInSubtree();
        Iterator it = nodeClicked.getObjectsInSubtree().iterator();
        items.addElement("Usuários:");
        while (it.hasNext())
            items.addElement(it.next());
        
        //it = nodeClicked.getObjectsInSubtree();
        it = nodeClicked.getObjectsInSubtree().iterator();
        items.addElement("Documentos:");
        HashSet<String> docs = new HashSet<String>();
        while(it.hasNext()){
        	docs.addAll(encontraDocs((String)it.next()));
        }
        
        Iterator<String> it2 = docs.iterator();
        while(it2.hasNext())
        	items.addElement(it2.next());
        
        itemList.clearSelection();
        itemList.setModel(items);
        itemList.repaint();
    }
    
    private HashSet<String> encontraDocs(String usuario){
    	HashSet<String> retorno = new HashSet<String>();
    	Iterator<Page> docs = PageUser.getInstance().getPagesByUser(new User(pegaNome(usuario))).iterator();
    	while(docs.hasNext()){
    		retorno.add(docs.next().getUrl());
    	}
    	return retorno;
    }
    	
    
    private String pegaNome(String arq){
    	File f = new File(arq);
    	return f.getName().replace(".txt","");
    }

    private void setUpGUIElements() {

        itemList = new JList();
        itemList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {
            	//System.out.println(arg0);
            	if(arg0.getValueIsAdjusting()){
            		int index = itemList.getSelectedIndex();
            		ListModel model = itemList.getModel();
            		if (!itemList.isSelectionEmpty() && (model.getSize() > index)) {
            			String id = (String) itemList.getModel().getElementAt(index);
            			
            			/**
            			 * TODO alterar !!!
            			 */
            			ObjectVisualizer viz = Yale.getExperiment().getVisualizerForObject(id);
            			viz.startVisualization(id);	
            		}
            	}
            }

        });

        JSplitPane spane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        spane.setTopComponent(getMainPanel());
        spane.setBottomComponent(new JScrollPane(itemList));
        spane.setDividerLocation(600);

        setLayout(new BorderLayout());
        add(new JScrollPane(spane), BorderLayout.CENTER);
        add(getControlPanel(), BorderLayout.NORTH);

    }
}


class ClusterVertexUsers extends DirectedSparseVertex {

    private ClusterNode node;

    public ClusterVertexUsers(ClusterNode node) {
        super();
        this.node = node;
    }
    
    public ClusterNode getNode() {
        return node;
    }

    /**
     * TODO Aqui fica a descricao do 
     * nodo. Eu devo alterar.
     * Por exemplo, pelas palavras mais frequentes 
     * do agrupamento.
     */
    public String toString() {

        //return node.getId() + ":" + node.getDescription();
    	//return new Double(node.getWeight()).toString();
    	return node.getDescription();
    }
}
