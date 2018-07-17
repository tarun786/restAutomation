package com.rest.twiter.test;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

public class TweetRetrive {
	Properties prop;
	long twtID;
	public long[] twtIDS;
	@Test(priority =1)
	public void getLatestTweet() throws IOException {
		prop = ResourceFinder.getRestProp();
		RestAssured.baseURI = prop.getProperty("twtHOST");
		AuthenticationResource ar = new AuthenticationResource();
		// System.out.println("consumer key "+ar.getConsumerKey());
		Response res = given().auth()
				.oauth(ar.getConsumerKey(), ar.getConsumerSerertKey(), ar.getAccessTokenKey(),
						ar.getAccessTokenSecertKey())
				.when().get("/1.1/statuses/home_timeline.json").then().log().all().extract().response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		System.out.println("total array "+rowdata.get("array.size()"));
		twtIDS = new long[rowdata.get("array.size()")];
		for(int i = 0; i<twtIDS.length;i++)
		{
			twtIDS[i] = rowdata.get("id["+i+"]"); 
		}
	}

	@Test(priority =2)
	
	public void postNewTweet() throws IOException
	{
		System.out.println("twwet id "+twtID);
		prop = ResourceFinder.getRestProp();
		RestAssured.baseURI = prop.getProperty("twtHOST");
		AuthenticationResource ar = new AuthenticationResource();
		// System.out.println("consumer key "+ar.getConsumerKey());
		Response res = given().auth()
				.oauth(ar.getConsumerKey(), ar.getConsumerSerertKey(), ar.getAccessTokenKey(),
						ar.getAccessTokenSecertKey())
				.queryParam("status", "Hi this tweet from rest automation").when().post("/1.1/statuses/update.json").then().log().all().extract().response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		System.out.println(rowdata.get("id"));
		twtID = rowdata.get("id");
		//deleteTweet(twtID);
	}
	
	
	@Test(priority = 3)
	public void deleteAllTweets() throws IOException
	{
		prop = ResourceFinder.getRestProp();
		RestAssured.baseURI = prop.getProperty("twtHOST");
		for(int i =0 ;i<twtIDS.length;i++){
		System.out.println("delete tweeeet method have twtID "+twtIDS[i]);
		AuthenticationResource ar = new AuthenticationResource();
		Response res = given().auth().oauth(ar.getConsumerKey(), ar.getConsumerSerertKey(), ar.getAccessTokenKey(), ar.getAccessTokenSecertKey()).when().
		post("/1.1/statuses/destroy/"+twtIDS[i]+".json").then().log().all().extract().response();
		try{
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		boolean trucatedStatus = rowdata.get("truncated");
		Assert.assertEquals(trucatedStatus, false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	
	@Test(priority = 4)
	public void deleteSingleTweet() throws IOException{
		prop = ResourceFinder.getRestProp();
		RestAssured.baseURI = prop.getProperty("twtHOST");
		System.out.println("delete tweeeet method have twtID "+twtID);
		AuthenticationResource ar = new AuthenticationResource();
		Response res = given().auth().oauth(ar.getConsumerKey(), ar.getConsumerSerertKey(), ar.getAccessTokenKey(), ar.getAccessTokenSecertKey()).when().
		post("/1.1/statuses/destroy/"+twtID+".json").then().log().all().extract().response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		boolean trucatedStatus = rowdata.get("truncated");
		Assert.assertEquals(trucatedStatus, false);
	}
	
}
