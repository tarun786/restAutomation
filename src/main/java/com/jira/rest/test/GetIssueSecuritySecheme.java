package com.jira.rest.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;

public class GetIssueSecuritySecheme {
    String ID;
    String sessionID;
	@Test(priority = 1)
	public void getIssueSecurityID() throws IOException
	{
		sessionID = ResourceFinder.getSession();
     	Response res =	given().header("Content-Type", "application/json").and().header("cookie", sessionID).when().
		get("/rest/api/2/issuesecurityschemes").then().log().all().assertThat().statusCode(200).extract().response();
	    JsonPath rowData = RowDataConversion.rawJSON(res);
	    ID = rowData.get("issueSecuritySchemes[0].id").toString();
	    System.out.println("ID "+ID);
	}
	
	@Test(priority = 2)
	public void getIssueSecurityFullView()
	{
	Response res =	given().header("Content-Type", "application/json").and().header("cookie", sessionID).when().
		get("/rest/api/2/issuesecurityschemes/"+ID+"").then().log().all().assertThat().statusCode(200).extract().response();
	Assert.assertEquals("Most Critical", RowDataConversion.rawJSON(res).get("levels[0].name").toString());
	}
	
}
