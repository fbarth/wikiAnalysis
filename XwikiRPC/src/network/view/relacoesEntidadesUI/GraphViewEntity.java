/*
 * Wiki2Group
 * 
 * Copyright (C) 2010  Fabrício J. Barth - http://fbarth.net.br

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package network.view.relacoesEntidadesUI;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import network.core.relacoesEntidades.Aresta;
import network.core.relacoesEntidades.Grafo;
import network.core.relacoesEntidades.Nodo;
import network.view.com.swtdesigner.SwingResourceManager;

import org.apache.commons.collections.Predicate;

import edu.uci.ics.jung.algorithms.cluster.BicomponentClusterer;
import edu.uci.ics.jung.algorithms.cluster.ClusterSet;
import edu.uci.ics.jung.exceptions.ConstraintViolationException;
import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgeStringer;
import edu.uci.ics.jung.graph.decorators.EdgeStrokeFunction;
import edu.uci.ics.jung.graph.decorators.PickableEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.ToolTipFunctionAdapter;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.UndirectedSparseVertex;
import edu.uci.ics.jung.utils.Pair;
import edu.uci.ics.jung.utils.TestGraphs;
import edu.uci.ics.jung.utils.UserData;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphMouseListener;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.MultiPickedState;
import edu.uci.ics.jung.visualization.PickedInfo;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.StaticLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.subLayout.CircularSubLayout;
import edu.uci.ics.jung.visualization.subLayout.SubLayout;
import edu.uci.ics.jung.visualization.subLayout.SubLayoutDecorator;

/**
 * @author  Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 24, maio, 2007
 * 
 */
public class GraphViewEntity {

    private Graph graph;
    private VisualizationViewer vv;
    private JDialog dialog;
    private JFrame frame;    
    private Vertex selecionado;

    private JPopupMenu popup;
    private JMenuItem menuItem;
    private JLabel labelFiltroArestas;
    private JTextField textFieldFiltroArestas;
    private JButton buttonFiltroArestas;
    private JButton buttonEliminarFiltroArestas;
    
    private ArrayList<Object> espessurasSelecionadas = new ArrayList<Object>(); 
    
    private ArrayList<Vertex> clicks = new ArrayList<Vertex>();
    private ArrayList<Vertex> clicksFiltro = new ArrayList<Vertex>();
    
    public void setVertexSelecionado(Vertex s){
    	this.selecionado = s;
    }
    
    public Vertex getVertexSelecionado(){
    	return this.selecionado;
    }
    
	private Vertex[] v;
	private Graph g;
 
	/**
	 * Generates a graph: in this case, reads it from the file
	 * "resultado.net"
	 * @return A sample undirected graph
	 */
	private Graph getGraph(Grafo grafo) throws IOException, UniqueLabelException{ 
		System.out.println("Nodos ...");
		System.out.println(grafo.printNodos());
		System.out.println("Arestas ...");
		System.out.println(grafo.printArestas());
		//g = new SparseGraph();
		g = new UndirectedSparseGraph();
		v = createVertices(grafo.getNodos().size(),g);
		createEdges(v,grafo.getArestas(),grafo.getNodos(),g);
		return g;
	}	
	
	
	/**
     * create some vertices
     * @param count how many to create
     * @return the Vertices in an array
     */
    private Vertex[] createVertices(int count, Graph g) {
        Vertex[] v = new Vertex[count];
        for (int i = 0; i < count; i++) {
            v[i] = g.addVertex(new UndirectedSparseVertex());
        	//v[i] = g.addVertex(new SparseVertex());
        }
        return v;
    }

    /**
     * create edges for this graph
     * @param v an array of Vertices to connect
     */    
    private ArrayList <Edge> edgesValues;
    
    private void createEdges(Vertex[] v, ArrayList <Aresta> relacoes, ArrayList <Nodo> nodos, Graph g) {
    	edgesValues = new ArrayList<Edge>();
    	int indice1 = 0, indice2 = 0;
    	for(int i=0; i<relacoes.size(); i++){
    		Aresta temp = relacoes.get(i);
    		System.out.println(temp.getN1().getNome()+"  "+temp.getN2().getNome());
    		for(int k=0; k<nodos.size(); k++){
    			if(nodos.get(k).getNome().equals(temp.getN1().getNome())){
    				indice1 = k;
    				break;
    			}
    		}
    		for(int k=0; k<nodos.size(); k++){
    			if(nodos.get(k).getNome().equals(temp.getN2().getNome())){
    				indice2 = k;
    				break;
    			}
    		}
    		System.out.println(indice1+"  "+indice2);
    		try{
    			edgesValues.add(g.addEdge(new UndirectedSparseEdge(v[indice1], v[indice2])));
    		}catch(ConstraintViolationException e){
    			//nada
    			System.out.println(e.getViolatedConstraint().toString());
    		}
    	}
    }	
        
    private MyVertexStringerEntity myStringerV;
    
    public MyVertexStringerEntity getMyVertexStringer(){
    	return this.myStringerV;
    }
    
    private MyEdgeStringerEntity myStringer;
    
    public MyEdgeStringerEntity getMyEdgeStringer(){
    	return this.myStringer;
    }
    
    private MyEdgeStringerEntityOnlyGraph myStringerOnlyGraph;
    
    public MyEdgeStringerEntityOnlyGraph getMyEdgeStringerEntityOnlyGraph(){
    	return this.myStringerOnlyGraph;
    }
    
    private MyToolTipFunction myToolTipFunction;
    
    public MyToolTipFunction getMyToolTipFunction(){
    	return this.myToolTipFunction;
    }
    
    private PluggableRenderer pr;
    //private Grafo grafo;
    
    @SuppressWarnings("deprecation")
	public GraphViewEntity(Grafo g) {
        
    	try{
    		//this.grafo = g;
    		graph = getGraph(g);
    	}catch(Exception e){
    		graph = TestGraphs.getOneComponentGraph();
    	}
    	
    	vv = paintGraph(graph,g);
    	

        frame = new JFrame("Relação entre Entidades");
        Container content = frame.getContentPane();
        panel = new JPanel(new BorderLayout());        
        panel.add(vv);
       
        content.add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setIconImage(SwingResourceManager.getImage
				(GraphViewEntity.class, "/br/atech/smartsearch/view/images/logo-small.JPG"));
        dialog = new JDialog(frame);
        
        content = dialog.getContentPane();
        
        // create the BirdsEyeView for zoom/pan
        final edu.uci.ics.jung.visualization.BirdsEyeVisualizationViewer bird =
            new edu.uci.ics.jung.visualization.BirdsEyeVisualizationViewer(vv, 0.25f, 0.25f);
        
        JButton reset = new JButton("Sem Zoom");
        // 'reset' unzooms the graph via the Lens
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bird.resetLens();
            }
        });
        final ScalingControl scaler = new ViewScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 0.9f, vv.getCenter());
            }
        });
        JButton help = new JButton("Ajuda");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String zoomHelp = "<html><center>Arraste o retngulo azul para deslocar a imagem<p>"+
                "Arraste um lado do retngulo para ajustar o zoom</center></html>";
                JOptionPane.showMessageDialog(dialog, zoomHelp);
            }
        });
        JPanel controls = new JPanel(new GridLayout(2,2));
        controls.add(plus);
        controls.add(minus);
        controls.add(reset);
        controls.add(help);
        content.add(bird);
        content.add(controls, BorderLayout.SOUTH);
        
        JButton zoomer = new JButton("Mostrar tela de zoom");
        zoomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.pack();
                int w = dialog.getWidth() + 5;
                int h = dialog.getHeight() + 5; // 35;
                dialog.setLocation((int) (frame.getLocationOnScreen().getX() + frame.getWidth() - w), 
                        (int) frame.getLocationOnScreen().getY() + frame.getHeight() - h);
                //dialog.show();
                dialog.setVisible(true);
                //bird.initLens();
            }
        });
 
        // [mcrb] Popup menu (Agrupar/Remover/Remover Selecao)
        popup = new JPopupMenu();

        menuItem = new JMenuItem("Agrupar Nodos");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//TODO
        	}
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Remover Nodo");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	myVertexDisplayPredicate.filter(true);
            	clicksFiltro.add(selecionado);
				pr.setVertexPaintFunction(new MyVertexPaintFunction());
				vv.repaint();
            }
        });
        popup.add(menuItem);

        menuItem = new JMenuItem("Remover Seleção");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        		clicks = new ArrayList<Vertex>();
        		pr.setVertexPaintFunction(new MyVertexPaintFunction());
        		// para evitar que o 'ultimo selecionado permaneca em destaque:
        		selecionado = null;
    			vv.repaint();
            }
        });
        popup.add(menuItem);
        
        labelFiltroArestas = new JLabel("Apresentar arestas com tamanho maior que ");
        textFieldFiltroArestas = new JTextField(2);

        buttonFiltroArestas = new JButton("Filtrar");
        buttonEliminarFiltroArestas = new JButton("Remover Filtros");
        buttonEliminarFiltroArestas.setEnabled(false);
        
        buttonFiltroArestas.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		Object objValue = textFieldFiltroArestas.getText();
               	try{
               		new Integer((String) objValue).intValue();
               	} catch(NumberFormatException ex) {
               		objValue = "0";
               		textFieldFiltroArestas.setText("");
               	}
        		espessurasSelecionadas.add(objValue);
        		myEdgeDisplayPredicate.filter(true, espessurasSelecionadas.toArray());
        		buttonEliminarFiltroArestas.setEnabled(true);
        		vv.repaint();
        	}
        });
        
        buttonEliminarFiltroArestas.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		textFieldFiltroArestas.setText("");
        		espessurasSelecionadas = new ArrayList<Object>();
        		myEdgeDisplayPredicate.filter(false, espessurasSelecionadas.toArray());
        		vv.repaint();
        	}
        });
        
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));

        
        // [inicio] acrescimo dos botoes de zoom
        JButton mais = new JButton();
        mais.setToolTipText("Ampliar");
        mais.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        mais.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton menos = new JButton();
        menos.setToolTipText("Reduzir");
        menos.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));       
        menos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        // [fim] acrescimo dos botoes de zoom

        final Color[] cores = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, 
        		Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE,
        		Color.RED};
        
        JButton agrupamento = new JButton("Agrupar");
        agrupamento.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		clusterAndRecolor(new SubLayoutDecorator(new FRLayout(graph)),1,cores,true);
        		vv.validate();
        		vv.repaint();
            }
        });
        
        
        p.add(mais);
        p.add(menos);
        p.add(zoomer);
        p.add(labelFiltroArestas);
        p.add(textFieldFiltroArestas);
        p.add(buttonFiltroArestas);
        p.add(buttonEliminarFiltroArestas);
        p.add(agrupamento);
        
        frame.getContentPane().add(p, BorderLayout.NORTH);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }
    
    private Layout layout;
    private JPanel panel;
    
    private VisualizationViewer paintGraph(Graph graph, Grafo g){
    	
    	//layout = new FRLayout(graph);
    	layout = new StaticLayout(graph);
        pr = new PluggableRenderer(); 
        vv = new VisualizationViewer(layout, pr);
        vv.addGraphMouseListener(new MyGraphMouseListenerEntity());
        
        vv.setBackground(Color.white);

        /*
         * EdgeStringer usado na classe EntityViewEntity para
         * a visualizao das relaes.
         * Este EdgeStringer nao deve ser visualizado no grafo,
         * apenas na tela de relacoes (EntityViewEntity)
         * No usar pr.setEdgeStringer(myStringer);
         */
        myStringer = new MyEdgeStringerEntity(edgesValues,g);
        
        myStringerOnlyGraph = new MyEdgeStringerEntityOnlyGraph(edgesValues,g);
        pr.setEdgeStringer(myStringerOnlyGraph);

        MyPickableEdgePaintFunction myPickableEdgePaintFunction = new MyPickableEdgePaintFunction(pr, Color.black, Color.cyan); 
        pr.setEdgePaintFunction(myPickableEdgePaintFunction);
        
        myStringerV = new MyVertexStringerEntity(v,g);
        pr.setVertexStringer(myStringerV);
        
        MyEdgeStrokeFunction myStroke = new MyEdgeStrokeFunction();
        pr.setEdgeStrokeFunction(myStroke);
        
        pr.setVertexPaintFunction(new MyVertexPaintFunction());
       
        myVertexDisplayPredicate = new VertexDisplayPredicate(true);
        pr.setVertexIncludePredicate(myVertexDisplayPredicate);
        
        myEdgeDisplayPredicate = new EdgeDisplayPredicate(false);
        pr.setEdgeIncludePredicate(myEdgeDisplayPredicate);
                
        myToolTipFunction = new MyToolTipFunction(v,g);
        vv.setToolTipFunction(myToolTipFunction);
        
        return vv;
    }

    
    private VertexDisplayPredicate myVertexDisplayPredicate;
    
    public VertexDisplayPredicate getMyVertexDisplayPredicate(){
    	return this.myVertexDisplayPredicate;
    }
    
    private EdgeDisplayPredicate myEdgeDisplayPredicate;
    
    public EdgeDisplayPredicate getMyEdgeDisplayPredicate(){
    	return this.myEdgeDisplayPredicate;
    }
    
    /**
     * 
     * Refaz o grafo.
     * 
     * Faz uso do Grafo grafo e do ArrayList<Vertex> clicked
     * e do ArrayList<Vertex> clicksFiltro
     * 
     * Observao importante: Cuidado ao utilizar esta funo em
     * grafos onde o nodo  o documento!!!
     * 
     * @param alterarSinonimos informa se deve ou no incluir os
     * nodos selecionados na lista de sinnimos
     */
    //private Grafo reagrupar(boolean alterarSinonimos){
    //	try{
    //		FiltroFacade f = FiltroFacade.getInstance();
    //		ArrayList<String> nomesNodos = new ArrayList<String>();
    //		System.out.println("Nodos selecionados:");
    //		for(int i=0; i<clicks.size(); i++){
    //			System.out.println(myToolTipFunction.getToolTipText(clicks.get(i)));
    //			nomesNodos.add(myToolTipFunction.getToolTipText(clicks.get(i)));
    //		}
    //		ArrayList<String> clicksFiltroName = new ArrayList<String>();
    //		for(int i=0; i<clicksFiltro.size(); i++)
    //			clicksFiltroName.add(myToolTipFunction.getToolTipText(clicksFiltro.get(i)));
    //		Grafo temp = f.geraNovoGrafoInvertido(grafo, nomesNodos, clicksFiltroName);
    //		
    //		
    //		if(alterarSinonimos){
    //			ArrayList<String> linha = new ArrayList<String>();
    //	    	for(int i=0; i<clicks.size(); i++){
    //	    		linha.add(myToolTipFunction.getToolTipText(clicks.get(i)));
    //	    	}
    //			ArquivoSinonimos.gravarSinonimos(linha);
    //		}
    //		return temp;
    //	}catch(Exception e){
    //		System.out.println("Erro GraphViewEntity.reagrupar: "+e);
    //		return null;
    //	}
    //}
   
    class MyGraphMouseListenerEntity implements GraphMouseListener{
    	
    	public void	graphClicked(Vertex v, MouseEvent me){
    		if(me.isControlDown()){
    			/*
    			 * Mostrar visualmente que o nodo estah selecionado
    			 */
    			clicks.add(v);
    			pr.setVertexPaintFunction(new ClickedPaintFunction(clicks));
    			//vv.repaint();
    			System.out.println("O usurio clicou em "+myStringerV.getLabel(v));
    		} else if(SwingUtilities.isLeftMouseButton(me)) {
    			EntityViewEntity entity = new EntityViewEntity(v,GraphViewEntity.this);
    			//entity.show();
    			entity.setVisible(true);
    		}
    		else if (SwingUtilities.isRightMouseButton(me)) { // ok!
    			System.out.println(" Vertice selecionado: "
    					+ myStringerV.getLabel(v));
    			popup.show(me.getComponent(), me.getX(), me.getY());
    		}
    		setVertexSelecionado(v);
    	}

    	public void	graphPressed(Vertex v, MouseEvent me){
    		//System.out.println("Ops...");
    	}

    	public void	graphReleased(Vertex v, MouseEvent me){
    		//System.out.println("Ops...");
    	}

    }

    /**
     * Atribui uma identificacao aos nodos
     */
    class MyVertexStringerEntity implements VertexStringer{

    	private HashMap map = new HashMap();

    	//public MyVertexStringer(Vertex[] vertices) {
    	//    for(int i=0; i<vertices.length; i++) {
    	//        map.put(vertices[i], Grafo.getInstance().getNodos().get(i).getNome());
    	//    }
    	//}

    	@SuppressWarnings("unchecked")
		public MyVertexStringerEntity(Vertex[] vertices, Grafo g) {
    		for(int i=0; i<vertices.length; i++) {
    			map.put(vertices[i], g.getNodos().get(i).getNome());
    		}
    	}

    	/* (non-Javadoc)
    	 * @see edu.uci.ics.jung.graph.decorators.VertexStringer#getLabel(edu.uci.ics.jung.graph.Vertex)
    	 */
    	public String getLabel(ArchetypeVertex v) {
    		StringTokenizer st = new StringTokenizer((String)map.get(v),",");
    		return st.nextToken();
    	}

    	//public String getLabel(ArchetypeVertex e){
    	//	return e.toString();
    	//}

    }

    /**
     * Atribui uma identificacao as arestas utilizadas
     * apenas no EntityViewEntity
     * @version 24, outubro, 2006
     * @see EntityViewEntity
     */
    class MyEdgeStringerEntity implements EdgeStringer{

    	private HashMap map = new HashMap();

    	@SuppressWarnings("unchecked")
		public MyEdgeStringerEntity(ArrayList edges, Grafo g) {
    		for(int i=0; i<edges.size(); i++) {
    			/*
    			 * Alterado 24, outubro, 2006
    			 * @see EntityViewEntity
    			 */
    			//map.put((Edge)edges.get(i), g.getArestas().get(i).getValor());
    			map.put((Edge)edges.get(i), g.getArestas().get(i).getN1().getArquivo());
    		}
    	}

    	public String getLabel(ArchetypeEdge e) {
    		return (String)map.get(e);
    	}
    }

    /**
     * Atribui uma identificacao as arestas 
     * (mostra a quantidade de arestas entre
     * um vertice e outro)
     * 
     * TODO calcular a frequencia de arestas de um
     * mesmo par de nodos.
     * 
     * @author fbarth
     * @version 24, outubro, 2006
     */
    class MyEdgeStringerEntityOnlyGraph implements EdgeStringer{

    	private HashMap map = new HashMap();
    	private HashSet setPairs = new HashSet();

    	@SuppressWarnings("unchecked")
		public MyEdgeStringerEntityOnlyGraph(ArrayList edges, Grafo g) {
    		for(int i=0; i<edges.size(); i++) {
    			//map.put((Edge)edges.get(i), g.getArestas().get(i).getValor());
    			setPairs.add(((Edge)edges.get(i)).getEndpoints());
    		}
    		Iterator it = setPairs.iterator();
    		while(it.hasNext()){
    			Pair p = (Pair)it.next();
    			Edge armazenado = null;
    			int cont=0;
    			for(int i=0; i<edges.size(); i++){
    				Edge e = (Edge)edges.get(i);
    				if(ehIgual(p,e.getEndpoints())){
    					//System.out.println("Encontrou!!!");
    					cont++;
    					armazenado = e;
    				}
    			}
    			map.put(armazenado, new Integer(cont).toString());
    		}
    	}

    	private boolean ehIgual(Pair p1, Pair p2){
    		Vertex id1P1 = ((Vertex)p1.getFirst());
    		Vertex id2P1 = ((Vertex)p1.getSecond());
    		Vertex id1P2 = ((Vertex)p2.getFirst());
    		Vertex id2P2 = ((Vertex)p2.getSecond());
    		if(id1P1.equals(id1P2) && id2P1.equals(id2P2)){
    			return true;
    		}else{
    			return false;
    		}
    	}

    	public String getLabel(ArchetypeEdge e) {
    		String rotulo = (String)map.get(e);
    		if(rotulo==null)
    			rotulo = "";
    		return rotulo; 
    	}
    }
    
    final PickedState ps = new MultiPickedState();

    class MyVertexPaintFunction implements VertexPaintFunction{

    	//public Paint getFillPaint(Vertex v){
    	//	if (v.equals(selecionado)) return Color.YELLOW; // [mcrb]
    	//	else return Color.RED;
    	//}

    	//public Paint getDrawPaint(Vertex v){
    	//	return Color.BLACK;
    	//}
    	
    	public Paint getFillPaint(Vertex v) {
			Color k = (Color) v.getUserDatum(DEMOKEY);
			if (k != null)
				return k;
			return Color.white;
		}

		public Paint getDrawPaint(Vertex v) {
			if(ps.isPicked(v)) {
				return Color.cyan;
			} else {
				return Color.BLACK;
			}
		}

    }

    /**
     * Responsvel por pintar arestas com diferentes tamanhos.
     * 
     * @author fbarth
     * @version 24, outubro, 2006
     */
    class MyEdgeStrokeFunction implements EdgeStrokeFunction{
    	private Stroke basic = new BasicStroke(1);
    	private Stroke heavy = new BasicStroke(2);
    	private Stroke heavyPlus = new BasicStroke(5);
    	private Stroke dotted = PluggableRenderer.DOTTED;

    	protected boolean weighted = true;

    	public void setWeighted(boolean weighted){
    		this.weighted = weighted;
    	}

    	public Stroke getStroke(Edge e){
    		int value = 0;
    		if (weighted){
    			try{
    				value = new Integer(getMyEdgeStringerEntityOnlyGraph().getLabel(e)).intValue();
    			}catch(java.lang.NumberFormatException err){
    				value = 0;
    			}

    			if(value<2){
    				return dotted;
    			}else if(value>=2 && value<5){
    				return heavy;
    			}else{
    				return heavyPlus;
    			}
    		}else
    			return basic;
    	}
    }

    class MyPickableEdgePaintFunction extends PickableEdgePaintFunction{

    	public MyPickableEdgePaintFunction(PickedInfo pi, Paint draw_paint, Paint picked_paint){
    		super(pi,draw_paint,picked_paint);
    	}

    	public Paint getDrawPaint(Edge e){
    		if(getMyEdgeStringerEntityOnlyGraph().getLabel(e).equals(""))
    			return PickableEdgePaintFunction.TRANSPARENT;
    		else
    			return draw_paint;
    	}
    }

    class ClickedPaintFunction implements VertexPaintFunction{

    	private ArrayList<Vertex> pintado = new ArrayList<Vertex>();

    	public ClickedPaintFunction(ArrayList<Vertex> v){
    		this.pintado = v;
    	}

    	public Paint getFillPaint(Vertex v){
    		for(int i=0; i<this.pintado.size(); i++){
    			if(v == this.pintado.get(i)){
    				return Color.BLUE;
    			}
    		}
    		return Color.RED;
    	}

    	public Paint getDrawPaint(Vertex v){
    		return Color.BLACK;
    	}
    }

    /**
     * Fornece o texto do Tooltip para os Vrtices (Vertex)
     * e Arestas (Edge)
     * 
     *@version 06, novembro, 2006
     */
    class MyToolTipFunction extends ToolTipFunctionAdapter{

    	private HashMap mapVertex = new HashMap();

    	@SuppressWarnings("unchecked")
		public MyToolTipFunction(Vertex[] vertices, Grafo g) {
    		for(int i=0; i<vertices.length; i++) {
    			mapVertex.put(vertices[i], g.getNodos().get(i).getNome());
    		}
    	}

    	public String getToolTipText(Edge e){
    		return getMyEdgeStringerEntityOnlyGraph().getLabel(e);
    	}

    	public String getToolTipText(MouseEvent e){
    		return "";
    	}

    	public String getToolTipText(Vertex v){
    		return (String)mapVertex.get(v);
    	}
    }

    /**
     * Responsvel por eliminar os nodos do grafo.
     * O usurio  quem seleciona e elimina os nodos.
     * 
     * @author Fabricio J. Barth
     * @version 07, novembro, 2006
     */
    class VertexDisplayPredicate implements Predicate{
    	private boolean filter = false;

    	public VertexDisplayPredicate(boolean filter)
    	{
    		this.filter = filter;
    	}

    	public void filter(boolean b)
    	{
    		this.filter = b;
    	}

    	public boolean evaluate(Object arg0){
    		if(filter){
    			Vertex v = (Vertex)arg0;
    			if(clicksFiltro.contains(v)){
    				return false;
    			}else{
    				return true;
    			}
    		}else{
    			return true;
    		}
    	}
    }

    /**
     * Responsvel por filtrar as arestas de acordo com a
     * sua espessura. 
     * 
     * @author Fabrcio J. Barth (fbarth@atech.br)
     * @version 08, novembro, 2006
     *
     */
    class EdgeDisplayPredicate implements Predicate{
    	private boolean filter = false;
    	private Object[] espessurasSelecionadas = {"0"};

    	public EdgeDisplayPredicate(boolean filter)
    	{
    		this.filter = filter;
    	}

    	public void filter(boolean b, Object[] espessurasSelecionadas)
    	{
    		this.filter = b;
    		this.espessurasSelecionadas = espessurasSelecionadas;
    	}

    	public boolean evaluate(Object arg0){
    		if(filter){
    			Edge e = (Edge)arg0;
    			String tamanho = getMyToolTipFunction().getToolTipText(e);
    			if (tamanho.equals("")) tamanho = "0";
    			int value = new Integer(tamanho).intValue();
    			for(int i=0; i<this.espessurasSelecionadas.length; i++) {
    				String obj = (String) espessurasSelecionadas[i];
    				int limit = new Integer(obj).intValue();
    				if (value <= limit)
    					return false;
    			}
    			return true;
    		} else {
    			return true;
    		}
    	}
    }
    
	private static final Object DEMOKEY = "DEMOKEY";
    
	/**
	 * TODO testando o algoritmo de clustering no grafo.
	 * 
	 * @param layout
	 * @param numEdgesToRemove
	 * @param colors
	 * @param groupClusters
	 */
    public void clusterAndRecolor(SubLayoutDecorator layout,
    		int numEdgesToRemove,
    		Color[] colors, boolean groupClusters) {

    	//Now cluster the vertices by removing the top 50 edges with highest betweenness
    	//		if (numEdgesToRemove == 0) {
    	//			colorCluster( g.getVertices(), colors[0] );
    	//		} else {

    	Graph g = layout.getGraph();
    	layout.removeAllSubLayouts();

    	//dgeBetweennessClusterer clusterer = new EdgeBetweennessClusterer(numEdgesToRemove);
    	//WeakComponentClusterer clusterer = new WeakComponentClusterer();
    	BicomponentClusterer clusterer = new BicomponentClusterer();
    	ClusterSet clusterSet = clusterer.extract(g);
    	//List edges = clusterer.getEdgesRemoved();

    	int i = 0;
    	//Set the colors of each node so that each cluster's vertices have the same color
    	for (Iterator cIt = clusterSet.iterator(); cIt.hasNext();) {

    		Set vertices = (Set) cIt.next();
    		
    		Color c = colors[i % colors.length];
    		
    		Iterator<Vertex> it = vertices.iterator();
    		while(it.hasNext()){
    			Vertex temp = it.next();
    			System.out.println("Vertex: "+temp.toString()+"  ,"+i);
    		}
    		
    		

    		colorCluster(vertices, c);
    		if(groupClusters == true) {
    			groupCluster(layout, vertices);
    		}
    		i++;
    	}
    	//for (Iterator it = g.getEdges().iterator(); it.hasNext();) {
    	//	Edge e = (Edge) it.next();
    	//	if (edges.contains(e)) {
    	//		e.setUserDatum(DEMOKEY, Color.LIGHT_GRAY, UserData.REMOVE);
    	//	} else {
    	//		e.setUserDatum(DEMOKEY, Color.BLACK, UserData.REMOVE);
    	//	}
    	//}
    }
    
	private void colorCluster(Set vertices, Color c) {
		for (Iterator iter = vertices.iterator(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			System.out.println("Vertex: "+v.toString()+"  Color: "+c.toString());
			if(!v.toString().equals("V0"))
				v.setUserDatum(DEMOKEY, c, UserData.REMOVE);
		}
	}
	
	private void groupCluster(SubLayoutDecorator layout, Set vertices) {
		if(vertices.size() < layout.getGraph().numVertices()) {
			Point2D center = layout.getLocation((ArchetypeVertex)vertices.iterator().next());
			SubLayout subLayout = new CircularSubLayout(vertices, 20, center);
			layout.addSubLayout(subLayout);
		}
	}
}