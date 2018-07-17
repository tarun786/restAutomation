package com.jira.rest.test;

import java.io.IOException;

import org.testng.annotations.Test;

import restProj.restAutomation.ResourceFinder;
import static io.restassured.RestAssured.given;
public class AssignBugToUser {
	String sessionID;
	String issueID;
@Test
public void assignBug() throws IOException
{
  sessionID = ResourceFinder.getSession();
  issueID = ResourceFinder.getIssueID();
  System.out.println("issue id is "+issueID);
  given().header("Content-Type","application/json").and().header("cookie",sessionID).
  body("{\r\n" + 
  		"    \"name\": \"vikram92\"\r\n" + 
  		"}").when().put("/rest/api/2/issue/"+issueID+"/assignee").then().assertThat().statusCode(204);
}
	
}
