package com.wynyard.webservice;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.wynyard.client.InitialiseProperty;
import com.wynyard.constant.SolrContants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author sachin_nandagawali
 * 
 * This class work as the restful web service provider . It will search the field on solr cores and send the search result in the form of Json object. 
 *
 */

@Path("/index")
public class IndexController {
	
	// logger object
	private static Logger logger = Logger.getLogger(IndexController.class);
	

	
	
	/*	@searchText : It is search string 
	 * 	@dataSource : It is the list of datasources in which search will be performed
	 *  @pageSelection: It is the number of result requested.
	 *  
	 *  @Response : It is jersy response object which send response back to UI
	 * 
	 */	
	@Path("searchsolr")
	@GET
	@Produces({ "application/json"})
	public Response getSearchDataFromJersy(@QueryParam("searchText") String searchText, @QueryParam("dataSource") String dataSource,@QueryParam("pageSelection") String pageSelection){
		try{
			
			if(searchText==null || dataSource==null || pageSelection==null){
				JSONObject finalResult = new JSONObject();				
				Response.status(200).entity(finalResult.toString()).build();
			}
			
			String query=SolrContants.COMMON_QUERY+constructQueryPara(searchText,dataSource,pageSelection);			
			
			Properties solrProperty=InitialiseProperty.getInstance().getProperty();
			
			if(solrProperty!=null){
				query=solrProperty.getProperty("solr.Server")+query;
			
				logger.debug("Solr Requested Query:"+query);
			
				Client client = Client.create();
		
				WebResource webResource = client.resource(query);
 
				ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
 
				if (response.getStatus() != 200) {
					JSONObject search = new JSONObject();			
					return Response.status(200).entity(search.toString()).build();
				}
 
				String output = response.getEntity(String.class);
		
				JSONObject finalResult=formatResponse(output);
		
				return Response.status(200).entity(finalResult.toString()).build();
			
			}
		}catch(Exception ex){
			ex.printStackTrace();			
			logger.error(ex);			
		}
		JSONObject search = new JSONObject();
		return Response.status(200).entity(search.toString()).build();
		
	}
	
	//Constructing query parameter
	private String constructQueryPara(String searchText,String selectedDataSource,String resultReq){
		String finalQuery="";
		try {
		
		String row="&rows="+resultReq;
		String filterquery="&fq=(dataSource:";
		String queryString="&q="+searchText.trim();
		
		logger.debug("Search String :"+queryString);
		logger.debug("Number of Result Requested :"+row);
		logger.debug("Data Sources selected :"+selectedDataSource);
		
		JSONArray dataSourceArray;
		dataSourceArray = new JSONArray(selectedDataSource);
				
		
		int length =dataSourceArray.length();
		for(int i=0;i<dataSourceArray.length();i++){
			
			if(dataSourceArray.getString(i).equals("Investigator")){
				filterquery=filterquery+"Investigator";
			}else if(dataSourceArray.getString(i).equals("OCG-Document")){
				
				filterquery=filterquery+"OCG";
			}else if(dataSourceArray.getString(i).equals("SharePoint")){					
				filterquery=filterquery+"SharePoint";
				
			}else{
				
			}
			if(length-1==i){
				filterquery=filterquery+")";
			}else{
				filterquery=filterquery+",";
			}
		}
		return finalQuery=finalQuery+row+queryString+filterquery;
	
	}catch (JSONException e) {
			e.printStackTrace();
			return finalQuery;
		}
	
	}
	
	// formating solr response to json object
	private JSONObject formatResponse(String solrResponse){
		JSONObject finalResult = new JSONObject();
		try {
		
		JSONObject 	jsonResponse = new JSONObject(solrResponse);
		
		JSONArray jsonArray = new JSONArray(jsonResponse.getJSONObject("response").getString("docs"));
		
		finalResult.put("searchResult",jsonArray);
		
		finalResult.put("docFound", jsonResponse.getJSONObject("response").getString("numFound"));
		
		finalResult.put("respTime",jsonResponse.getJSONObject("responseHeader").getString("QTime"));
		
		
		logger.debug(" Number of Document Found :"+jsonResponse.getJSONObject("response").getString("numFound"));
		logger.debug(" Response Time :"+jsonResponse.getJSONObject("responseHeader").getString("QTime"));
		
		return finalResult;
		
		}catch (JSONException e) {
			return finalResult;
		}
		
	}

	
}
