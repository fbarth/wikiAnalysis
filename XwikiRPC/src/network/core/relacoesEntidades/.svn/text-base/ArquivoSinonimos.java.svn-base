package network.core.relacoesEntidades;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import xwiki.XwikiConf;

/**
 * Classe responsavel por recuperar as informacoes
 * sobre sinonimos a partir de um arquivo modo texto.
 * 
 * Cada linha do arquivo representa um conjunto de sinonimos.
 * 
 * @author Fabricio J. Barth (fabricio.barth@gmail.com)
 * @version 15, Setembro, 20006
 *
 */
public class ArquivoSinonimos {

	private static String arquivo = XwikiConf.getInstance().get("arquivoSinonimos");
	
	/**
	 * Recupera as informacoes sobre os sinonimos
	 * 
	 * @return ArrayList com os sinonimos
	 * @throws IOException
	 */
	public static ArrayList<HashSet> recuperaSinonimos()throws IOException{
		ArrayList<HashSet> retorno = new ArrayList<HashSet>();
		File f = new File(arquivo);
		RandomAccessFile rf = new RandomAccessFile(f,"rw");
		String linha = rf.readLine();
		while(linha != null){
			HashSet<String> sinonimos = new HashSet<String>();
			StringTokenizer st = new StringTokenizer(linha,"#");
			while(st.hasMoreElements())
				sinonimos.add(st.nextToken().toLowerCase());
			retorno.add(sinonimos);
			linha = rf.readLine();
		}
		rf.close();
		return retorno;
	}
	
    /**
     * 
     * @param elementos
     * @throws Exception
     */
    public static void gravarSinonimos(ArrayList<String> elementos) throws Exception{
    	File f = new File(XwikiConf.getInstance().get("arquivoSinonimos"));
    	RandomAccessFile rf = new RandomAccessFile(f,"rw");
    	File fOut = new File("temp.txt");
    	RandomAccessFile rfOut = new RandomAccessFile(fOut,"rw");
    	String ln = rf.readLine();
    	while(ln!=null){
    		int cont=0;
    		for(int i=0; i<elementos.size(); i++){
    			if(!possui(ln,elementos.get(i))){
    				cont++;
    			}
    		}
    		if(cont==elementos.size())
				rfOut.writeBytes(ln+"\n");
    		
    		ln = rf.readLine();
    	}
    	rf.close();
    	
    	String linha = elementos.get(0).replaceAll("\\s+,\\s+", "#");
    	for(int i=1; i<elementos.size(); i++){
    		String temp = elementos.get(i).replaceAll("\\s+,\\s+", "#"); 
    		linha = linha + "#" + temp;
    	}
    	rfOut.writeBytes(linha+"\n");
    	rfOut.close();
    	
    	f.delete();
    	fOut.renameTo(new File(XwikiConf.getInstance().get("arquivoSinonimos")));
    }
    
    private static boolean possui(String linha, String elementos){
    	StringTokenizer st = new StringTokenizer(linha,"#");
    	while(st.hasMoreTokens())
    		if(elementos.contains(st.nextToken()))
    			return true;
    	return false;
    }
}