package com.apar.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



public class RestClient {
	
	//Get Method
	public String[] get(String url) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);  //http get request
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); //hit Get url
		
		//Status Code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code -->" + statusCode);
		
		//JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		System.out.println("Response body from API -->" + responseString);
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API -->" + responseJson);
		
		//All Headers
		Header[] headerArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array -->" + allHeaders);
		
		String[] Response = new String[] {Integer.toString(statusCode),responseString};
		
		return Response;
		
		
	}

}
