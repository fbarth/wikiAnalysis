package yale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.SparseTree;
import edu.uci.ics.jung.visualization.GraphMouseListener;
import edu.uci.ics.jung.visualization.ISOMLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.TreeLayout;
import edu.uci.ics.jung.visualization.control.GraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.control.ViewTranslatingGraphMousePlugin;
//1.7.6
//import edu.uci.ics.jung.visualization.control.ViewScalingGraphMousePlugin;
import edu.udo.cs.yale.clustering.core.ClusterNode;

/**
 * Visualization based on graphs via the JUNG package.
 * 
 * @author Michael Wurst
 * @version $Id: JUNGVisualization.java,v 1.3 2006/08/03 14:39:51 ingomierswa Exp $
 * 
 * Alterado por Fabricio J. Barth
 * @version 23, setembro, 2006
 * 
 * Mudanças no método para paint dos nodos
 * @version 21,fevereiro,2007
 *
 */
public abstract class MyJUNGVisualization extends JPanel {

    Vertex selectedVertex = null;

    JPanel selectorPane;

    VisualizationViewer vv;

    //LayoutChooser jcb;
    
    protected String documento = null;
    
    protected ClusterNode nodeClicked;

    public void init(Graph graph) {

        PluggableRenderer renderer = new PluggableRenderer();
        renderer.setVertexStringer(new VertexStringer() {

            public String getLabel(ArchetypeVertex arg0) {
                return arg0.toString();
            }

        });
        
        /**
         * Adicionado em 21, fevereiro, 2007
         * para identificar em que agrupamento encontra-se
         * determinado documento.
         */
        renderer.setVertexPaintFunction(new VertexPaintFunction(){

			public Paint getDrawPaint(Vertex arg0) {
				return Color.black;
			}

			public Paint getFillPaint(Vertex arg0) {
				if(((ClusterVertex)arg0).getNode()==nodeClicked){
					return Color.black;
				}else{
					if(documento!=null){
						if(((ClusterVertex)arg0).getNode().getObjectsInSubtree().contains(documento))
							return Color.yellow;
					}
					return Color.red;
				}
			}
        	
        });

        Layout lay = null;

        if (graph instanceof SparseTree)
            lay = new TreeLayout((SparseTree) graph);
        else
            lay = new ISOMLayout(graph);

        vv = new VisualizationViewer(lay, renderer, new Dimension(600,500));
        
        //vv = new VisualizationViewer(lay, renderer);
        
        //vv = new VisualizationViewer(lay, renderer, new Dimension(900,650));
        
        vv.setBackground(Color.white);
        vv.prerelax();
        
        /*
         * Permite a movimentação do grafo dentro do JPanel.
         */
        final ScalingControl scaler = new ViewScalingControl();
        final PluggableGraphMouse graphMouse = new PluggableGraphMouse();
		graphMouse.add(new ScalingGraphMousePlugin(scaler,1));
		graphMouse.add(new ViewTranslatingGraphMousePlugin());
		GraphMousePlugin pickSupport = new PickingGraphMousePlugin(
				MouseWheelEvent.ALT_DOWN_MASK, MouseWheelEvent.SHIFT_DOWN_MASK);
		graphMouse.add(pickSupport);
		vv.setGraphMouse(graphMouse);

        // final DefaultModalGraphMouse graphMouse = new
        // DefaultModalGraphMouse();
        //final PluggableGraphMouse graphMouse = new PluggableGraphMouse();
        //graphMouse.add(new TranslatingGraphMousePlugin());
        ////1.7.6
        ////graphMouse.add(new ScalingGraphMousePlugin());
        ////graphMouse.add(new ViewScalingGraphMousePlugin());
        //GraphMousePlugin pickSupport = new PickingGraphMousePlugin(InputEvent.ALT_DOWN_MASK,
        //        InputEvent.SHIFT_DOWN_MASK);
        //graphMouse.add(pickSupport);
        //// graphMouse.add(new CrossoverScalingGraphMousePlugin());

        //vv.setGraphMouse(graphMouse);

        vv.addGraphMouseListener(new GraphMouseListener() {
            public void graphReleased(Vertex arg0, MouseEvent arg1) {

                vv.getPickedState().clearPickedVertices();

                if (selectedVertex != null)
                    vv.getPickedState().pick(selectedVertex, true);

            }

            public void graphPressed(Vertex arg0, MouseEvent arg1) {

                vv.getPickedState().clearPickedVertices();

                if (selectedVertex != null)
                    vv.getPickedState().pick(selectedVertex, true);
            }

            public void graphClicked(Vertex arg0, MouseEvent arg1) {

                selectedVertex = arg0;
                vv.getPickedState().clearPickedVertices();
                vv.getPickedState().pick(selectedVertex, true);
                vertexClicked(arg0);
            }

        });

        //

        //JButton scalePlus = new JButton("+");
        //scalePlus.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        // call listener in GraphMouse instead of manipulating vv scale
        //        // directly
        //        // this is so the crossover from zoom to scale works with the
        //        // buttons
        //        // as well as with the mouse wheel
        //        Dimension d = vv.getSize();
        //        graphMouse.mouseWheelMoved(new MouseWheelEvent(vv, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(),
        //                0, d.width / 2, d.height / 2, 1, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, 1));

        //    }
        //});
        //JButton scaleMinus = new JButton("-");
        //scaleMinus.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        // call listener in graph mouse instead of manipulating vv scale
        //        // directly
                // this is so the crossover from zoom to scale works with the
                // buttons
                // as well as with the mouse wheel
        //        Dimension d = vv.getSize();
        //        graphMouse.mouseWheelMoved(new MouseWheelEvent(vv, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(),
        //                0, d.width / 2, d.height / 2, 1, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, -1));
        //    }
        //});

        //JPanel scalePanel = new JPanel(new FlowLayout());
        //scalePanel.add(scalePlus);
        //scalePanel.add(scaleMinus);
        //scalePanel.setBorder(BorderFactory.createTitledBorder("Escala"));

        //JButton plus = new JButton("+");
        //plus.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        // call listener in GraphMouse instead of manipulating vv scale
        //        // directly
        //        // this is so the crossover from zoom to scale works with the
        //        // buttons
        //        // as well as with the mouse wheel
        //        Dimension d = vv.getSize();
        //        graphMouse.mouseWheelMoved(new MouseWheelEvent(vv, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(),
        //                InputEvent.CTRL_DOWN_MASK, d.width / 2, d.height / 2, 1, false,
        //                MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, 1));

        //    }
        //});
        //JButton minus = new JButton("-");
        //minus.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
        //        // call listener in graph mouse instead of manipulating vv scale
        //        // directly
        //        // this is so the crossover from zoom to scale works with the
        //        // buttons
        //        // as well as with the mouse wheel
        //        Dimension d = vv.getSize();
        //        graphMouse.mouseWheelMoved(new MouseWheelEvent(vv, MouseEvent.MOUSE_WHEEL, System.currentTimeMillis(),
        //                InputEvent.CTRL_DOWN_MASK, d.width / 2, d.height / 2, 1, false,
        //                MouseWheelEvent.WHEEL_UNIT_SCROLL, 1, -1));
        //    }
        //});

        /*
         * Botões de zoom.
         */
        
        JButton plus = new JButton("Ampliar");
		plus.setToolTipText("Ampliar");
		plus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		//plus.setIcon(SwingResourceManager.getIcon
		//		(ClusteringFrame.class, "/br/atech/smartsearch/view/images/zoom_ampliar.gif"));
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });

        JButton minus = new JButton("Reduzir");
		minus.setToolTipText("Reduzir");
		minus.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		//minus.setIcon(SwingResourceManager.getIcon
		//		(ClusteringFrame.class, "/br/atech/smartsearch/view/images/zoom_menos.gif"));       
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JPanel zoomPanel = new JPanel(new FlowLayout());
        zoomPanel.add(plus);
        zoomPanel.add(minus);
        zoomPanel.setBorder(BorderFactory.createTitledBorder("Zoom"));

        //jcb = new LayoutChooser(vv, graph);

        JPanel selector = new JPanel(new FlowLayout());
        //selector.add(scalePanel);
        selector.add(zoomPanel);
        //selector.add(new JLabel("Choose the layout :"), BorderLayout.WEST);
        //selector.add(jcb, BorderLayout.CENTER);

        selectorPane = new JPanel(new BorderLayout());
        selectorPane.add(selector, BorderLayout.WEST);
        
    }

    protected VisualizationViewer getMainPanel() {

        return vv;
    }

    protected JPanel getControlPanel() {

        return selectorPane;
    }

    public abstract void vertexClicked(Vertex v);

    /*
    public void updateGraph(Graph graph) {

        jcb.updateGraph(graph);

    }*/
}