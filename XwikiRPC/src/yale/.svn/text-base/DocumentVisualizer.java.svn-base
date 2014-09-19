package yale;

import java.awt.BorderLayout;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import xwiki.model.PageUser;
import xwiki.model.User;

public class DocumentVisualizer extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	private JScrollPane jScrollPane1 = null;
	private JTextArea jTextArea = null;
	
	/**
	 * Construtor da classe
	 * @param arquivo documento selecionado. O conteudo deste arquivo irah aparecer 
	 * na caixa de texto deste frame.
	 * @param elementosCluster lista de elementos que pertencem ao mesmo cluster
	 * que o arquivo. Este atributo eh enviado para identificar quais sao os usuarios
	 * do agrupamento
	 */
	public DocumentVisualizer(String arquivo, Iterator elementosCluster) {
		super();
		this.conteudoArquivo = recuperaConteudoDoArquivo(arquivo);
		this.usuariosCluster = recuperaNomeUsuariosCluster(elementosCluster);
		this.usuariosDocumento = encontraUsuarios(arquivo);
		initialize();
	}
	
	private String conteudoArquivo;
	private HashSet<String> usuariosCluster;
	private HashSet<String> usuariosDocumento;

	private HashSet<String> recuperaNomeUsuariosCluster(Iterator elementosCluster){
		HashSet<String> retorno = new HashSet<String>();
		while(elementosCluster.hasNext()){
			retorno.addAll(encontraUsuarios((String)elementosCluster.next()));
		}
		return retorno;
	}
	
	private HashSet<String> encontraUsuarios(String arquivo){
    	HashSet<String> retorno = new HashSet<String>();
    	Iterator<User> users = PageUser.getInstance().getUsersByPage(pegaID(arquivo)).iterator();
    	while(users.hasNext()){
    		retorno.add(users.next().getName());
    	}
    	return retorno;
    }
    	
    private String pegaID(String arq){
    	File f = new File(arq);
    	String temp = f.getName().replace(".txt","");
    	//System.out.println(temp);
    	return temp;
    }
	
	private String recuperaConteudoDoArquivo(String nomeArquivo){
		String retorno = "";
		try{
			File f = new File(nomeArquivo);
			RandomAccessFile rf = new RandomAccessFile(f,"rw");
			String linha = rf.readLine();
			while(linha != null){
				retorno = retorno + linha + "\n";
				linha = rf.readLine();
			}
			return retorno;
		}catch(Exception e){
			System.out.println("Erro DocumentVisualizer.recuperaConteudoDoArquivo: "+e);
			return "";
		}
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 500);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Documento");
		this.setVisible(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(250);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane1());
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		if (jList == null) {
			ArrayList<String> c = new ArrayList<String>();
			c.add("Usuários do agrupamento:");
			c.add(" ");
			c.addAll(this.usuariosCluster);
			c.add(" ");
			c.add("Usuários do documento:");
			c.add(" ");
			c.addAll(this.usuariosDocumento);
			jList = new JList(c.toArray());
		}
		return jList;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getJTextArea());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.append(this.conteudoArquivo);
		}
		return jTextArea;
	}

}
