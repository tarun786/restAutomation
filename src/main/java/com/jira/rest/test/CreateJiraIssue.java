package com.jira.rest.test;


import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;
public class CreateJiraIssue {

	@Test
	public static String createIssue() throws IOException
	{
		String session_ID = ResourceFinder.getSession();
		String issueID = ResourceFinder.getIssueID();
		Response res = given().header("Content-Type","application/json").and().header("cookie",session_ID).
				body("{\r\n" + 
						"    \"body\": \"Newly added comment through rest automation.\",\r\n" + 
						"    \"visibility\": {\r\n" + 
						"        \"type\": \"role\",\r\n" + 
						"        \"value\": \"Administrators\"\r\n" + 
						"    }\r\n" + 
						"}").when().post("/rest/api/2/issue/"+issueID+"/comment").then().assertThat().
				statusCode(201).extract().response();
			   JsonPath rowdata = RowDataConversion.rawJSON(res);
			   String comntID = rowdata.get("id");
			   System.out.println("comment id "+comntID);
		
		//System.out.println("/rest/api/2/issue/"+ResourceFinder.getIssueID()+"/comment/"+ResourceFinder.getCommentID());
	   given().header("Content-Type","application/json").header("cookie",session_ID).
		body("{\r\n" + 
				"    \"body\": \"we are updatig this comment.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").when().put("/rest/api/2/issue/"+issueID+"/comment/"+comntID).then().assertThat().
		statusCode(200);
	   
	   return issueID;
	}
	
}
