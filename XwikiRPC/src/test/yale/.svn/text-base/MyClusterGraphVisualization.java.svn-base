package test.yale;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
 * @version 23, setembro, 2006
 *
 */
public class MyClusterGraphVisualization extends MyJUNGVisualization {

    private static final long serialVersionUID = -8141164104359659162L;
    
	JList itemList = null;

    public MyClusterGraphVisualization(FlatClusterModel cm) {

        this(new SimpleHierarchicalClusterModel(cm));
    }

    public MyClusterGraphVisualization(HierarchicalClusterModel hcm) {

        init(createGraph(hcm));
        setUpGUIElements();
    }

    private SparseTree createGraph(HierarchicalClusterModel hcm) {

        if (hcm.getRootNode() == null)
            return new SparseTree(new SparseVertex());

        Vertex root = new ClusterVertex(hcm.getRootNode());

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
        Vertex v = new ClusterVertex(node);
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

    public void vertexClicked(Vertex selectedVertex_) {

        // Visualization
        DefaultListModel items = new DefaultListModel();
        ClusterNode node = ((ClusterVertex) selectedVertex_).getNode();
        
        /**
         * Tive que mudar porque estou usando
         * a versao yale3.2
         */
        //Iterator it = node.getObjectsInSubtree();
        Iterator it = node.getObjectsInSubtree().iterator();
        items.addElement("Documentos:");
        while (it.hasNext())
            items.addElement(it.next());
        
        //it = node.getObjectsInSubtree();
        it = node.getObjectsInSubtree().iterator();
        items.addElement("Usuários:");
        while(it.hasNext())
        	encontraUsuarios((String)it.next(),items);
        
        itemList.clearSelection();
        itemList.setModel(items);
        itemList.repaint();
    }
    
    /**
     * Inclui vários itens ao DefaultListModel
     * 
     * @param arquivo nome da página
     * @param i usuários relacionas com a página
     */
    private void encontraUsuarios(String arquivo, DefaultListModel i){
    	Iterator<User> users = PageUser.getInstance().getUsersByPage(pegaID(arquivo)).iterator();
    	while(users.hasNext()){
    		String name = users.next().getName();
    		System.out.println(name);
    		i.addElement(name);
    	}
    }
    
    private String pegaID(String arq){
    	File f = new File(arq);
    	String temp = f.getName().replace(".txt","");
    	System.out.println(temp);
    	return temp;
    }

    private void setUpGUIElements() {

        itemList = new JList();
        itemList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent arg0) {

                int index = itemList.getSelectedIndex();
                ListModel model = itemList.getModel();
                if (!itemList.isSelectionEmpty() && (model.getSize() > index)) {
                    String id = (String) itemList.getModel().getElementAt(index);
                    /**
                     * TODO alterar aqui !!!
                     */
                    ObjectVisualizer viz = Yale.getExperiment().getVisualizerForObject(id);
                    viz.startVisualization(id);
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

class ClusterVertex extends DirectedSparseVertex {

    private ClusterNode node;

    public ClusterVertex(ClusterNode node) {
        super();
        this.node = node;
    }

    public ClusterNode getNode() {
        return node;
    }

    public String toString() {

        return node.getId() + ":" + node.getDescription();
    }
}
