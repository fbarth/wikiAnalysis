package xwiki;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class XwikiConf {

	private static final String PROPERTIES_FILE_NAME = "xwiki.properties";
    private Properties properties;
    private String docsAgrupamentoPath;
    private String perfilPath;
    
    private static XwikiConf INSTANCE = null;

    public static XwikiConf getInstance()
    {
    	if(INSTANCE == null) {
            INSTANCE = new XwikiConf();
        }
        return INSTANCE;
    }

    private XwikiConf()
    {
        try {
            properties = new Properties();
            properties.load(XwikiConf.class.getResourceAsStream(PROPERTIES_FILE_NAME));
            this.docsAgrupamentoPath = properties.getProperty("docsAgrupamentoPath");
            this.perfilPath = properties.getProperty("perfilPath");

            File propertiesFile = new File(PROPERTIES_FILE_NAME);
            if(!propertiesFile.exists()) {
                return;
            }

            properties.load(new FileInputStream(propertiesFile));
            this.docsAgrupamentoPath = properties.getProperty("docsAgrupamentoPath");
            this.perfilPath = properties.getProperty("perfilPath");

        } catch (IOException ioe) {
            System.out.println("Could not open properties file '"+PROPERTIES_FILE_NAME+"'"+ ioe);
        }
    }

    public String getPerfilPath(){
    	return this.perfilPath;
    }
    
    public String getDocsAgrupamentoPath(){
    	return this.docsAgrupamentoPath;
    }

    public String get(String key)
    {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultIfNotFound)
    {
        return properties.getProperty(key) == null ? defaultIfNotFound : properties.getProperty(key);
    }
}
