package com.qa.restapiTest;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;




public class GetApiTest extends TestBase{
	
	TestBase  testBase ;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
	testBase = new TestBase ();
    serviceUrl = prop.getProperty("URL");
    apiUrl = prop.getProperty("serviceURL");
  //https://https://reqres.in/api/uses
 
   url = serviceUrl+apiUrl;
	}
	

@Test
public void getAPITest() throws ClientProtocolException, IOException{
	restClient = new RestClient();
	CloseableHttpResponse closeableHttpResponce =restClient.get(url);;
	int StatusCode = closeableHttpResponce.getStatusLine().getStatusCode();
	System.out.println("Status Code"+StatusCode);
	Assert.assertEquals(StatusCode, RESPONSE_STATUS_CODE_200 ,"Status code is not 200");;

//3.Json String
String reponseString = EntityUtils.toString( closeableHttpResponce.getEntity(),"UTF_8");
JSONObject responseJson = new JSONObject(reponseString);
System.out.println("Response Json from API-------->"+responseJson);

//Single value assertion
 //per_page
String getValueByJpath = TestUtil.getValueByJpath(responseJson,"/per_page");
System.out.println("value of per page is "+getValueByJpath);
Assert.assertEquals(Integer.parseInt(getValueByJpath), 3);

//total:
String totalValue = TestUtil.getValueByJpath(responseJson,"/total");
System.out.println("value of total  is "+totalValue);
Assert.assertEquals(Integer.parseInt(totalValue), 12);

//total_pages
String total_pages = TestUtil.getValueByJpath(responseJson,"/total_pages");
System.out.println("value of total_ page is "+total_pages);
Assert.assertEquals(Integer.parseInt(total_pages), 4);

//get the value from Json Array

String id =TestUtil.getValueByJpath(responseJson, "/data[0]/id");
String name =TestUtil.getValueByJpath(responseJson, "/data[0]/name");
String year =TestUtil.getValueByJpath(responseJson, "/data[0]/year");
String color =TestUtil.getValueByJpath(responseJson, "/data[0]/color");
String pantone_value =TestUtil.getValueByJpath(responseJson, "/data[0]/pantone_value");

System.out.println(id);
System.out.println(name);
System.out.println(year);
System.out.println(color);
System.out.println(pantone_value);

//All Header
Header[] headerArray =closeableHttpResponce.getAllHeaders();

HashMap<String ,String>allHeaders = new HashMap<String,String>();
 for(Header header: headerArray){
	 allHeaders.put(header.getName(),header.getValue());
 }
System.out.println("HEADER ARRAY------->"+allHeaders);

}


	
	
	
	
}









