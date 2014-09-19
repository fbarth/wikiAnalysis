package xwiki.retrieval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import xwiki.XwikiConf;
import xwiki.model.Page;
import xwiki.model.PageUser;
import xwiki.model.Space;

public class Storage {

	public void armazenaArquivosLocal(Space s, String diretorio){
		try{
			ArrayList<Page> pages = s.getPages();
			for(int i=0; i<pages.size(); i++){
				File f = new File(diretorio+pages.get(i).getId()+".txt");
				RandomAccessFile rf = new RandomAccessFile(f,"rw");
				try{
					rf.writeBytes(pages.get(i).getContent());
				}catch(IOException ioe){
					System.out.println("Nao foi possivel recuperar o arquivo "+f.getName());
				}
				rf.close();
			}
		}catch(Exception e){
			System.out.println("Erro Storage.armazenaArquivosLocal: "+e);
		}
	}
	
	public void excluiArquivosLocal(String diretorio){
		try{
			File f = new File(diretorio);
			if(f.isDirectory()){
				File files[] = f.listFiles();
				for(int i=0; i<files.length; i++)
					files[i].delete();
			}
		}catch(Exception e){
			System.out.println("Erro Storage.excluiArquivosLocal: "+e);
		}
	}

	public void armazenarObjetos(Spaces spaces){
		try{
			FileOutputStream out = new FileOutputStream(XwikiConf.getInstance().get("objetosSerializados"));
			ObjectOutputStream s = new ObjectOutputStream(out);
			s.writeObject(PageUser.getInstance());
			s.writeObject(spaces);
			s.flush();
			out.close();
		}catch(Exception e){
			System.out.println("Erro Storage.armazenarObjetos: "+e);
		}
	}
	
	public Spaces recuperarObjetos(){
		try{
			FileInputStream in = new FileInputStream(XwikiConf.getInstance().get("objetosSerializados"));
			ObjectInputStream s = new ObjectInputStream(in);
			PageUser.setInstance((PageUser)s.readObject());
			return (Spaces)s.readObject();
		}catch(Exception e){
			System.out.println("Erro Storage.recuperarObjetos: "+e);
			return null;
		}
	}
}
