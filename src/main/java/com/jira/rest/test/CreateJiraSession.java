package com.jira.rest.test;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

public class CreateJiraSession {
    Properties prop;
	@Test
	
	public void setSession() throws IOException
	{
		RestAssured.baseURI = ResourceFinder.getRestProp().getProperty("JiraHOST");
	Response res =	given().header("Content-Type","application/json").body("{ \"username\":\"tarunjaiswal92\", \"password\":\"T@run123\"}").
		when().post("/rest/auth/1/session").
		then().statusCode(200).extract().response();
	    JsonPath jsonStr = RowDataConversion.rawJSON(res);
	    System.out.println(jsonStr.get("session.value"));
	}
}
