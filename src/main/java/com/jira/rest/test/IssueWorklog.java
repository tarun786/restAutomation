package com.jira.rest.test;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;
public class IssueWorklog {
	
	@Test
	public void getIssueWorklog() throws IOException
	{
		String sessionID = ResourceFinder.getSession();
		//create the bug
		String issueID = ResourceFinder.getIssueID();
		// Created comments on bug
		Response res = given().header("Content-Type", "application/json").and().header("cookie", sessionID)
				.body("{\r\n" + "    \"body\": \"Newly added comment through rest automation.\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}")
				.when().post("/rest/api/2/issue/" + issueID + "/comment").then().assertThat().statusCode(201).extract()
				.response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		String comntID = rowdata.get("id");
		System.out.println("comment id " + comntID);
       // updated the new comments
		given().header("Content-Type","application/json").header("cookie",sessionID).
		body("{\r\n" + 
				"    \"body\": \"we are updatig this comment.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").when().put("/rest/api/2/issue/"+issueID+"/comment/"+comntID).then().assertThat().
		statusCode(200);
		
		//add worklog history in this bug
		given().header("Content-Type","application/json").header("cookie",sessionID).
		when().
		body("{\r\n" + 
				"    \"comment\": \"I did some work here.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"group\",\r\n" + 
				"        \"value\": \"jira-developers\"\r\n" + 
				"    },\r\n" + 
				"    \"started\": \"2017-12-07T09:23:19.552+0000\",\r\n" + 
				"    \"timeSpentSeconds\": 12000\r\n" + 
				"}")
		.post("/rest/api/2/issue/"+issueID+"/worklog").then().
	  assertThat().statusCode(201);
		
	   // we are getting bug work logs
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).
		when().get("/rest/api/2/issue/"+issueID+"/worklog").then().log().all().assertThat().statusCode(200);
	}

}
