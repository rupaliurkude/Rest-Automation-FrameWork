package com.qa.restapiTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApiTest extends TestBase {
	
	TestBase  testBase ;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponce;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
	testBase = new TestBase ();
    serviceUrl = prop.getProperty("URL");
    apiUrl = prop.getProperty("serviceURL");
  //https://https://reqres.in/api/uses

 
   url = serviceUrl+apiUrl;
	}
	

	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException{
		restClient = new RestClient();
		HashMap<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Content-Type", "application/json");
		//jackson API:
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus","leader");//expected users object
		
		//object to json file//marshalling
		mapper.writeValue(new File("/Users/rupaliurkude/Documents/workspace4/resapi/src/main/java/com/qa/data/users.json"), users );
		
		//object to json in String//marshalling
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println( usersJsonString);
		closeableHttpResponce =	restClient.post(url, usersJsonString, headerMap);
	
		//1.status code
		int statusCode = closeableHttpResponce .getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode,testBase.RESPONSE_STATUS_CODE_201);
		
		//2.JSONSTRING
		String responseString= EntityUtils.toString(closeableHttpResponce.getEntity(),"UTF-8");

		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("the responce from api is:"+responseJson);
		//json to java object
		Users usersResponceObj = mapper.readValue(responseString,Users.class);//actual users object
		System.out.println(usersResponceObj );
		Assert.assertTrue(users.getName().equals(usersResponceObj .getName()));//unmarsheled it
		Assert.assertTrue(users.getJob().equals(usersResponceObj.getJob()));
		System.out.println(usersResponceObj.getId());
		System.out.println(usersResponceObj.getCreatedAt());
	}	
	
}
