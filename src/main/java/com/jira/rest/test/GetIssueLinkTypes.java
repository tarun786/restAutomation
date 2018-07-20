package com.jira.rest.test;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;

import static io.restassured.RestAssured.given;

import java.io.IOException;

public class GetIssueLinkTypes {

	@Test
	public void getIssueLinks() throws IOException
	{
		String session_ID = ResourceFinder.getSession();
	Response res =	given().header("Content-Type", "application/json").and().header("cookie", session_ID).when().
		get("/rest/api/2/issueLinkType").then().assertThat().statusCode(200).log().all().extract().response();
		
	}
	
}
