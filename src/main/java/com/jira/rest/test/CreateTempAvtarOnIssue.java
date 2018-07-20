package com.jira.rest.test;

import org.testng.annotations.Test;

import restProj.restAutomation.ResourceFinder;

import static io.restassured.RestAssured.given;

import java.io.IOException;

public class CreateTempAvtarOnIssue {

	/**
	 * visit more info on Jira Api
	 * https://docs.atlassian.com/software/jira/docs/api/REST/7.6.1/#api/2/issuetype-createAvatarFromTemporary
	 * @throws IOException
	 */
	@Test
	public void createAvtar() throws IOException
	{
		
		String sessionID = ResourceFinder.getSession();
		String issueID = ResourceFinder.getIssueID();
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).
		body("{\r\n" + 
				"    \"cropperWidth\": 120,\r\n" + 
				"    \"cropperOffsetX\": 50,\r\n" + 
				"    \"cropperOffsetY\": 50,\r\n" + 
				"    \"needsCropping\": false\r\n" + 
				"}").when().
		post("/rest/api/2/issuetype/"+issueID+"/avatar").then().log().all().assertThat().statusCode(201);
		
		
	}
	
}
