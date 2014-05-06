package com.wynyard.client;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.json.impl.provider.entity.JSONObjectProvider;

public class JersySolrClient {
	
	public static void main(String args[]) {
		try {
			 
			Client client = Client.create();
			
			String query="http://10.88.36.44:9090/solr/Investigator_Core/select?indent=on&version=2.2&q=*:*&fq=&start=0&rows=200&fl=*,score&wt=json&explainOther=&hl.fl=&qt=dismax&fq=dataSource:OCG,Investigator";
			
			WebResource webResource = client.resource(query);
	 
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			//ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
	 
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	 
			String output = response.getEntity(String.class);
			
			JSONObject jsonResponse = new JSONObject(output);
			
			//jsonResponse.put("resp", output);
			//System.out.println(output);
			System.out.println(jsonResponse.getJSONObject("responseHeader").getString("QTime"));
			System.out.println(jsonResponse.getJSONObject("response").getString("numFound"));
			
			//System.out.println(jsonResponse.getJSONObject("response").getString("docs"));
			
			JSONArray jsonArray = new JSONArray(jsonResponse.getJSONObject("response").getString("docs"));
			
			/*JSONObject finalResult = new JSONObject();
			
			finalResult.put("searchResult",jsonArray);
			
			finalResult.put("docFound", jsonResponse.getJSONObject("response").getString("numFound"));
			
			finalResult.put("respTime",jsonResponse.getJSONObject("responseHeader").getString("QTime"));*/
			
			//return Response.status(200).entity(finalResult.toString()).build();
			
			//System.out.println(((JSONObject)jsonArray.get(0)).getString("content"));
			//System.out.println(array);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
		
	}

}
