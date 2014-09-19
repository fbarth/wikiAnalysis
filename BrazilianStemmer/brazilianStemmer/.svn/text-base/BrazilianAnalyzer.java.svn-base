package brazilianStemmer;


/**
 * Copyright 2004-2005 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WordlistLoader;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Analyzer for Brazilian language. Supports an external list of stopwords
 * (words that will not be indexed at all) and an external list of exclusions
 * (word that will not be stemmed, but indexed).
 * 
 * @author Jo&atilde;o Kramer
 */
public final class BrazilianAnalyzer extends Analyzer
{   
    /**
     * List of typical Brazilian stopwords.
     */
    public final static String[] BRAZILIAN_STOP_WORDS = {
        "à", "a", "de", "afora", "ainda", "alem", "algo", "alguém", "algum", "alguma", "algumas", 
        "alguns", "algures", "alhures", "aliás", "ante", "anterior", "ao", "aonde", "aos", 
        "apenas", "apesar", "após", "apud", "aquela", "àquela", "aquelas", "àquelas", "àquele", 
        "aquele", "aqueles", "àqueles", "aquilo", "àquilo", "às", "as", "assim", "até", "bastante", 
        "bastantes", "bom", "bulhufas", "bus", "cada", "caso", "certa", "certas", "certo", 
        "certos", "chez", "com", "comigo", "como", "conforme", "conosco", "conquanto", "conseqüentemente", 
        "consigo", "consoante", "contanto", "contigo", "contra", "contudo", "convosco", "cuja", 
        "cujas", "cujo", "cujos", "da", "daí", "dali", "daquela", "daquelas", "daquele", 
        "daqueles", "daqui", "daquilo", "das", "de", "dela", "delas", "dele", "deles", "demais", 
        "dentre", "depois", "dês", "desde", "dessa", "dessas", "desse", "desses", "desta", 
        "destas", "deste", "destes", "diante", "diferentes", "disso", "disto", "diversos", 
        "do", "donde", "dos", "dum", "duma", "dumas", "duns", "durante", "e", "ela", "elas", 
        "ele", "eles", "em", "embora", "enfim", "enquanto", "entanto", "então", "entre", 
        "entretanto", "era", "eram", "essa", "essas", "esse", "esses", "esta", "estas", "este", 
        "estes", "eu", "excelência", "excelências", "excelentíssima", "excelentíssimas", 
        "excelentíssimo", "excelentíssimos", "exceto", "faz", "fez", "fora", "graças", "idem", 
        "ilustríssima", "ilustríssimas", "ilustríssimo", "ilustríssimos", "in", "ir", "isso", 
        "isto", "já", "la", "lha", "lhas", "lhe", "lhes", "lho", "lhos", "logo", "ma", "mais", 
        "mal", "malgrado", "mas", "me", "mediante", "menos", "mesma", "mesmas", "mesmo", 
        "mesmos", "meu", "meus", "mim", "minha", "minhas", "mo", "mos", "muita", "muitas", 
        "muito", "muitos", "na", "nada", "nadinha", "nalgum", "nalguma", "nalgumas", "nalguns", 
        "nao", "naquela", "naquelas", "naquele", "naqueles", "naquilo", "nas", "nela", "nelas", 
        "nele", "neles", "nem", "nenhum", "nenhuma", "nenhumas", "nenhuns", "nenhures", "nessa", 
        "nessas", "nesse", "nesses", "nesta", "nestas", "neste", "nestes", "ninguém", "nisso", 
        "nisto", "no", "no-la", "no-las", "no-lo", "no-los", "nos", "nós", "nossa", "nossas", 
        "nosso", "nossos", "noutra", "noutras", "noutro", "noutros", "num", "numa", "numas", 
        "nuns", "o", "onde", "ora", "os", "ou", "outra", "outras", "outrem", "outro", "outros", 
        "outrossim", "para", "patavina", "pela", "pelas", "pelo", "pelos", "per", "perante", 
        "pero", "picas", "pois", "por", "porém", "porquanto", "porque", "portanto", "posterior", 
        "posto", "pouca", "poucas", "pouco", "poucochinho", "poucos", "pra", "praquela", 
        "praquelas", "praquele", "praqueles", "praquilo", "pras", "prela", "prelas", "prele", 
        "preles", "pro", "própria", "próprias", "próprio", "próprios", "pros", "quais", "quaisquer", 
        "qual", "qualquer", "quando", "quanta", "quantas", "quanto", "quantos", "que", "quem", 
        "quer", "salvante", "salvo", "se", "segundo", "seja", "sem", "semelhante", "semelhantes", 
        "senão", "senhor", "senhora", "senhoras", "senhores", "senhorita", "senhoritas", 
        "seu", "seus", "si", "sim", "sob", "sobre", "sua", "suas", "ta", "tais", "tal", "também", 
        "tanta", "tantas", "tanto", "tantos", "tas", "te", "tem", "teu", "ti", "tirante", 
        "to", "toda", "todas", "todavia", "todo", "todos", "tos", "trás", "tu", "tua", "tuas", 
        "tudo", "um", "uma", "umas", "uns", "vária", "várias", "vário", "vários", "versus", 
        "vez", "via", "visto", "vo-la", "vo-las", "vo-lo", "vo-los", "você", "vocês", "vós", 
        "vos", "vossa", "vossas", "vosso", "vossos" }; 
    
    private Set englishStopWords = null; 

    /**
     * Contains the stopwords used with the StopFilter.
     */
    private Set stopWords = new HashSet();

    /**
     * Contains words that should be indexed but not stemmed.
     */
    private Set exclusionTable = new HashSet();

    /**
     * Builds an analyzer with the default stop words ({@link #BRAZILIAN_STOP_WORDS}).
     */
    public BrazilianAnalyzer()
    {
        /*
         * aparentemente tem algum bug com o metodo 
         * StopFilter.makeStopSet na versao 1.9.1 do Lucene
         * 
         * O metodo makeStopSet estah implementado nesta classe.
         * 
         */
    	//stopWords = StopFilter.makeStopSet(BRAZILIAN_STOP_WORDS);
    	stopWords = StopFilter.makeStopSet(BRAZILIAN_STOP_WORDS,false);
        init();
    }    
    
    private void init()
    {

        /*
         * aparentemente tem algum bug com o metodo 
         * StopFilter.makeStopSet na versao 1.9.1 do Lucene
         * 
         * O metodo makeStopSet estah implementado nesta classe.
         * 
         */
    	//englishStopWords = StopFilter.makeStopSet(StandardAnalyzer.STOP_WORDS);
    	englishStopWords = StopFilter.makeStopSet(StandardAnalyzer.STOP_WORDS,false);
        
        // By Fabricio
        //englishStopWords.add("back");
        //englishStopWords.add("part");
        //englishStopWords.add("about");
        //englishStopWords.add("against");
        //englishStopWords.add("abuse");
        //englishStopWords.add("download");
        
        /*englishStopWords.add("");
        englishStopWords.add("");
        englishStopWords.add("");
        englishStopWords.add("");
        englishStopWords.add("");
        */

        // By Fabricio
        //stopWords.add("teor");
        //stopWords.add("conteudo");
    }

    public BrazilianAnalyzer(String[] stopwords)
    {
    	
    	/*
         * aparentemente tem algum bug com o metodo 
         * StopFilter.makeStopSet na versao 1.9.1 do Lucene
         * 
         * O metodo makeStopSet estah implementado nesta classe.
         * 
         */
    	//stopWords = StopFilter.makeStopSet(stopwords);
    	stopWords = StopFilter.makeStopSet(stopwords,false);
        init();
    }

    public BrazilianAnalyzer(Hashtable stopwords)
    {
        stopWords = new HashSet(stopwords.keySet());
        init();
    }

    public BrazilianAnalyzer(File stopwords) 
        throws IOException
    {
        stopWords = WordlistLoader.getWordSet(stopwords);
        init();
    }

    /**
     * Creates a TokenStream which tokenizes all the text in the provided
     * Reader.
     * 
     * @return A TokenStream build from a StandardTokenizer filtered with
     *         StandardFilter, StopFilter, GermanStemFilter and LowerCaseFilter.
     */
    public final TokenStream tokenStream(String fieldName, Reader reader)
    {
        TokenStream result = new StandardTokenizer(reader);
        
        /** Convert to lowercase after stemming! */
        result = new LowerCaseFilter(result);
        result = new StopFilter(result, englishStopWords);
        
        result = new BrazilianAccentsFilter(result);
        result = new StopFilter(result, stopWords);
        
        result = new BrazilianStemFilter(result, stopWords);
        
        return result;
    }
    
    /**
     * Builds an exclusionlist from an array of Strings.
     */
    public void setStemExclusionTable(String[] exclusionlist)
    {
    	/*
         * aparentemente tem algum bug com o metodo 
         * StopFilter.makeStopSet na versao 1.9.1 do Lucene
         * 
         * O metodo makeStopSet estah implementado nesta classe.
         * 
         */
    	//exclusionTable = StopFilter.makeStopSet(exclusionlist);
    	exclusionTable = StopFilter.makeStopSet(exclusionlist,false);
    }

    /**
     * Builds an exclusionlist from a Hashtable.
     */
    public void setStemExclusionTable(Hashtable exclusionlist)
    {
        exclusionTable = new HashSet(exclusionlist.keySet());
    }

    /**
     * Builds an exclusionlist from the words contained in the given file.
     */
    public void setStemExclusionTable(File exclusionlist) throws IOException
    {
        exclusionTable = WordlistLoader.getWordSet(exclusionlist);
    }
}
