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

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;

public class EntityViewEntity extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTree jTree = null;
	/**
	 * This is the default constructor
	 */
	//public EntityView() {
	//	super();
	//	initialize();
	//}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Vertex v, GraphViewEntity g) {
		this.setSize(300, 200);
		//this.setTitle("Ligaes da entidade "+GraphViewEntity.getInstance().getMyVertexStringer().getLabel(v));
		this.setTitle("Ligaes da entidade "+g.getMyToolTipFunction().getToolTipText(v));
		this.setContentPane(getJContentPane(v,g));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(Vertex v, GraphViewEntity g) {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(v,g), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane(Vertex v, GraphViewEntity g) {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTree(v,g));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	private JTree getJTree(Vertex v, GraphViewEntity g) {
		if (jTree == null) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(g.getMyToolTipFunction().getToolTipText(v));
			Iterator vertexs = v.getNeighbors().iterator();
			while(vertexs.hasNext()){
				Vertex vLocal = (Vertex)vertexs.next();
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(g.getMyToolTipFunction().getToolTipText(vLocal));
				root.add(child);
				Iterator edges = v.findEdgeSet(vLocal).iterator();
				while(edges.hasNext()){
					DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(g.getMyEdgeStringer().getLabel((Edge)edges.next()));
					child.add(child2);
				}
			}
			jTree = new JTree(root);
			jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			jTree.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					jTree.getLastSelectedPathComponent();
				}
			});
		}
		return jTree;
	}
	
	public EntityViewEntity(Vertex v, GraphViewEntity g){
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel: " + e.getMessage());
		}
		initialize(v,g);
	}
}