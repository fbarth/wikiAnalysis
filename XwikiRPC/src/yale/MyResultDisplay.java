/*
 *  YALE - Yet Another Learning Environment
 *
 *  Copyright (C) 2001-2006
 *
 *      Mainly written by members of the
 *      Artificial Intelligence Unit
 *      Computer Science Department
 *      University of Dortmund
 *      44221 Dortmund,  Germany
 *
 *  Project administrator: Ingo Mierswa
 *  Complete list of YALE developers available at our web site:
 *
 *       http://yale.sf.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as 
 *  published by the Free Software Foundation; either version 2 of the
 *  License, or (at your option) any later version. 
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 *  USA.
 */

package yale;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import edu.udo.cs.yale.Statistics;
import edu.udo.cs.yale.gui.ExperimentEditor;
import edu.udo.cs.yale.gui.PlotterPanel;
import edu.udo.cs.yale.operator.IOContainer;
import edu.udo.cs.yale.operator.MissingIOObjectException;
import edu.udo.cs.yale.operator.Operator;
import edu.udo.cs.yale.operator.ResultObject;

/** The result display is the view of the Yale GUI which refers to (intermediate) results. It can display
 *  all IOObjects which are deliverd, each in a tab which is displayed on the right side. If the experiment
 *  produces some statistics (e.g. performance against generation), these are plotted online.
 *
 *  @author Ingo Mierswa, Simon Fischer
 *  @version $Id: ResultDisplay.java,v 2.14 2006/03/27 13:21:58 ingomierswa Exp $
 *  
 *  @author Fabricio J. Barth (fabricio.barth@gmail.com)
 *  @version 2006/06/19
 *  Versao para o portugues do Brasil
 *  Alteracao do comportamento dos botoes e abertura de documentos
 *  
 */
public class MyResultDisplay extends JPanel implements ExperimentEditor {

	private static final long serialVersionUID = 1L;

	private int size;
	private List results;
	private JTabbedPane tabs = new JTabbedPane(JTabbedPane.RIGHT);
	private JLabel label = new JLabel("Resultados");;
	private Collection statistics = new LinkedList();

	public MyResultDisplay() {
		super(new BorderLayout());
		add(tabs, BorderLayout.CENTER);
		add(label, BorderLayout.NORTH);
		setData(null, "Resultados");
	}

	public void clear() {
		tabs.removeAll();
		setStatistics(new LinkedList());
		setData(null, null);
		repaint();
	}

	public void setStatistics(Collection statistics) {
		this.statistics = statistics;
		addStatistics();
	}

	private void addStatistics() {
		Iterator i = statistics.iterator();
		while (i.hasNext()) {
			Statistics stats = (Statistics)i.next();
			tabs.addTab(stats.getName(), 
					new PlotterPanel(stats));
		}
	}

	public void setData(IOContainer results, String message) {
		int selectedIndex = tabs.getSelectedIndex();

		for (int i = tabs.getTabCount()-1; i >= 0; i--) {
			Component c = tabs.getComponentAt(i);
			if (!(c instanceof PlotterPanel)) {
				tabs.removeTabAt(i);
			}
		}

		label.setText(message);

		this.results = convertToList(results);
		if (this.results.size() > 0) {
			Iterator i = this.results.iterator();
			while (i.hasNext()) {
				ResultObject result = (ResultObject)i.next();

				JPanel resultPanel = new JPanel(new BorderLayout());
				Component visualisationComponent = result.getVisualisationComponent();
				JScrollPane scrollPane = new JScrollPane(visualisationComponent);
				resultPanel.putClientProperty("main.component", visualisationComponent);
				resultPanel.add(scrollPane, BorderLayout.CENTER);
				JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
				Iterator action = result.getActions().iterator();
				while (action.hasNext()) {
					System.out.println("Foi selecionado um dos elementos");
					buttonPanel.add(new JButton((Action)action.next()));
				}
				resultPanel.add(buttonPanel, BorderLayout.SOUTH);
				tabs.addTab(result.getName(), resultPanel);
			}
		} else {
			label.setText("Nenhum resultado produzido.");
		}

		if (selectedIndex < tabs.getTabCount()) {
			tabs.setSelectedIndex(selectedIndex);
		} else {
			if (tabs.getTabCount() > 0) 
				tabs.setSelectedIndex(0);
		}
	}

	public static JDialog createDialog(IOContainer results, Component parent, List additionalButtons) {
		final JDialog dialog = new JDialog();
		dialog.setTitle("Resultados");
		dialog.setModal(false);
		MyResultDisplay display = new MyResultDisplay();
		display.setData(results, "Resultados:");

		if (display.size != 0) {
			display.setBorder(BorderFactory.createEmptyBorder(11,11,11,11));
			dialog.getContentPane().add(display, BorderLayout.CENTER);
		} else {
			dialog.getContentPane().add(new JLabel("Nenhum resultado produzido."), BorderLayout.CENTER);
		}
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		if (additionalButtons != null) {
			Iterator i = additionalButtons.iterator();
			while (i.hasNext()) {
				JButton button = (JButton)i.next();
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
				buttonPanel.add(button);
			}
		}

		JButton close = new JButton("Fechar");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		buttonPanel.add(close);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}


	private static List<ResultObject> convertToList(IOContainer container) {
		List<ResultObject> list = new LinkedList<ResultObject>();
		if (container != null) {
			ResultObject result = null;
			do {
				try {
					result = (ResultObject)container.get(ResultObject.class, list.size());
					list.add(result);
				} catch (MissingIOObjectException e) {
					break;
				}
			} while (result != null);
		}
		return list;
	}

	public void experimentChanged(Operator operator) {}

	public void validateExperiment() throws Exception {}

	public Component getMainComponent() {
		JComponent c = (JComponent)tabs.getSelectedComponent();
		Component main = (Component)c.getClientProperty("main.component");
		if (main != null) return main;
		else return c;
	}

	public void showSomething() {
		if (tabs.getSelectedIndex() == -1)
			if (tabs.getTabCount() > 0)
				tabs.setSelectedIndex(0);
	}
}

