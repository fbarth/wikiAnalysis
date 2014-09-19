package xwiki.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import document.AnalisadorTexto;
import frequency.Absoluta;

public class Page extends xwiki.model.URI {

	private static final long serialVersionUID = 42L;
	
	private String title; 	//the title of the page
	//private Integer version; //the version number of this page
	private Date created; //timestamp page was created
	private String creator; //username of the creator
	private Date modified; //timestamp page was modified
	private String modifier; //username of the page's last modifier
	private String homePage; //whether or not this page is the space's homepage
	private Integer locks; //the number of locks current on this page
	private String contentStatus; //status of the page (eg. current or deleted)
	private String current; //whether the page is current and not deleted 
	
	/*
	 * Contem os radicais das palavras encontradas em content,
	 * exceto aquelas palavras consideradas stop-word
	 */
	private ArrayList<String> atomos;
	
	public void setAtomos(String content){
		atomos = new ArrayList<String>();
		AnalisadorTexto at = new AnalisadorTexto(content);
		atomos.addAll(at.getTextoAnalisado());
	}
	
	public ArrayList<String> getAtomos(){
		return this.atomos;
	}
	
	/*
	 * Alterado em 10, Abril, 2008
	 */
	//public Page(Hashtable p) throws NullPointerException{
	public Page(HashMap p) throws NullPointerException{
		super((String)p.get("id"), (String)p.get("url"), (String)p.get("content"));
		this.setAtomos((String)p.get("content"));
		this.setVetor(this.getAtomos());
		this.setTitle((String)p.get("title"));
		//this.setVersion((Integer)p.get("version"));
		this.setCreated((Date)p.get("created"));
		this.setCreator((String)p.get("creator"));
		this.setModified((Date)p.get("modified"));
		this.setModifier((String)p.get("modifier"));
		//this.setHomePage((String)p.get("homePage"));
		this.setLocks((Integer)p.get("locks"));
		this.setContentStatus((String)p.get("contentStatus"));
		//this.setCurrent((String)p.get("current"));
	}

	/**
	 * Representa as palavras encontradas no documento
	 * e as suas respectivas frequencias
	 */
	private HashMap vetor;
	
	/**
	 * Calcula o vetor de frequencia das palavras da pagina
	 * 
	 * @param atomos lista de palavras da pagina, eliminando as
	 * stop-words
	 */
	@SuppressWarnings("unchecked")
	private void setVetor(ArrayList<String> atomos){
		vetor = new HashMap();
		/*
		 * Calculando a frequencia...
		 */
		Absoluta abs = new Absoluta(atomos);
		
		HashSet hs = new HashSet(atomos);
		Iterator it = hs.iterator();
		while(it.hasNext()){
			String pal = (String)it.next();
			vetor.put(pal,abs.tfIJ(pal));
		}
	}
	
	/**
	 * Retorna o vetor de frequencia das palavras da pagina
	 * 
	 * @return HashMap(palavra,frequencia)
	 */
	public HashMap getVetor(){
		return this.vetor;
	}
	
	private ArrayList<Attachment> attachments;
	private ArrayList<ExternalLink> externalLinks;
	private ArrayList<Page> internalLinks;
	
	/**
	 * @uml.property  name="version"
	 * @uml.associationEnd  multiplicity="(0 -1)" dimension="1" ordering="true" inverse="page:xwiki.model.Version"
	 * @uml.association  name="have"
	 */
	private ArrayList<Version> versions;

	/**
	 * Getter of the property <tt>version</tt>
	 * @return  Returns the versions.
	 * @uml.property  name="version"
	 */
	public ArrayList<Version> getVersion() {
		return versions;
	}

	/**
	 * Setter of the property <tt>version</tt>
	 * @param version  The versions to set.
	 * @uml.property  name="version"
	 */
	public void setVersion(ArrayList<Version> version) {
		versions = version;
	}

	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(ArrayList<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public ArrayList<ExternalLink> getExternalLinks() {
		return externalLinks;
	}

	public void setExternalLinks(ArrayList<ExternalLink> externalLinks) {
		this.externalLinks = externalLinks;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public ArrayList<Page> getInternalLinks() {
		return internalLinks;
	}

	public void setInternalLinks(ArrayList<Page> internalLinks) {
		this.internalLinks = internalLinks;
	}

	public Integer getLocks() {
		return locks;
	}

	public void setLocks(Integer locks) {
		this.locks = locks;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Version> getVersions() {
		return versions;
	}

	public void setVersions(ArrayList<Version> versions) {
		this.versions = versions;
	}

	//public void setVersion(Integer version) {
	//	this.version = version;
	//}
}
