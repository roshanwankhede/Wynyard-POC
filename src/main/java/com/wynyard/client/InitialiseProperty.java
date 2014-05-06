package com.wynyard.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * This class loads the property file.
 */

public class InitialiseProperty {
	private static InitialiseProperty intialiseProperty=null;
	private Properties properties;

	
	private InitialiseProperty(){
		try {
			InputStream inputStream=this.getClass().getResourceAsStream("/SolrServer.properties");
			properties= new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//returning InitialiseProperty object
	public static InitialiseProperty getInstance() {
		if(intialiseProperty==null){
			intialiseProperty=new InitialiseProperty();	
		}
		return intialiseProperty;
	}

	//returning property object
	public Properties getProperty() {
		return properties;
	}
}
