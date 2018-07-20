package com.jira.rest.test;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import restProj.restAutomation.ResourceFinder;
public class GetNotificationScheme {
    
	String sessionID;
	@Test(priority = 1)
	public void getNotificationScheme() throws IOException
	{
		sessionID = ResourceFinder.getSession();
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).
		and().queryParam("startAt", "1").when().get("/rest/api/2/notificationscheme").then().and().
		log().body().assertThat().statusCode(200);
	}
	
	@Test(priority = 2)
	
	public void getAllPriority()
	{
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).when().
		get("/rest/api/2/priority").then().log().all().assertThat().statusCode(200);
		
	}
	
	@Test(priority = 3)
	public void getProjectIssueStatus()
	{
		given().headers("Content-Type","application/json").header("cookie",sessionID).
		when().get("/rest/api/2/project/"+"PT"+"/statuses").then().log().all().assertThat().statusCode(200);
	}
	
	@Test(priority = 4)
	public void getProjectRoles()
	{
		given().headers("Content-Type","application/json").header("cookie",sessionID).
		when().get("/rest/api/2/project/"+"PT"+"/role").then().log().all().assertThat().statusCode(200);	
	}
	
	@Test(priority = 5)
	public void createProjectCategory()
	{
		given().headers("Content-Type","application/json").header("cookie",sessionID).body("{\r\n" + 
				"    \"name\": \"Risk Based Project\",\r\n" + 
				"    \"description\": \"Highly Risk Based , DOn't panic to customer\"\r\n" + 
				"}").when().
		post("/rest/api/2/projectCategory").then().log().all().assertThat().statusCode(201);
		
	}
	
	@Test(priority = 6)
	
	public void getProjectCategory()
	{
		given().headers("Content-Type","application/json").header("cookie",sessionID).
		when().get("/rest/api/2/projectCategory").then().log().all().assertThat().statusCode(200);	

	}
}
