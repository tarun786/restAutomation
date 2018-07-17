package com.jira.rest.test;

import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;

public class CreateBulkIssue {
	String[] ids;
    String sessionID;
	@Test(priority = 1)
	
	public void createBulkIssues() throws IOException
	{
		sessionID = ResourceFinder.getSession();
	Response res =	given().header("Content-Type","application/json").and().header("cookie",sessionID).
		body("{\r\n" + 
				"    \"issueUpdates\": [\r\n" + 
				"        {\r\n" + 
				"            \r\n" + 
				"            \"fields\": {\r\n" + 
				"                \"project\": {\r\n" + 
				"                    \"key\": \"PT\"\r\n" + 
				"                },\r\n" + 
				"                \"summary\": \"something's wrong\",\r\n" + 
				"                \"issuetype\": {\r\n" + 
				"                    \"name\": \"Bug\"\r\n" + 
				"                },\r\n" + 
				"                \"description\": \"this is the first bug\"\r\n" + 
				"         \r\n" + 
				"                \r\n" + 
				"            }\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"update\": {},\r\n" + 
				"            \"fields\": {\r\n" + 
				"                \"project\": {\r\n" + 
				"                    \"key\": \"PT\"\r\n" + 
				"                },\r\n" + 
				"                \"summary\": \"something's very wrong\",\r\n" + 
				"                \"issuetype\": {\r\n" + 
				"                    \"name\": \"Bug\"\r\n" + 
				"                },\r\n" + 
				"               \r\n" + 
				"                \"description\": \"this is the second bug\"\r\n" + 
				"         \r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}").when().post("/rest/api/2/issue/bulk").then().assertThat().statusCode(201).extract().response();
	     JsonPath rowData = RowDataConversion.rawJSON(res);
	     int bug_ids_count = rowData.get("issues.size()");
	     System.out.println("total ids "+bug_ids_count);
	     ids = new String[bug_ids_count];
	     for(int i =0; i<ids.length;i++)
	     {
	    	 ids[i] = rowData.get("issues.id["+i+"]");
	    	 System.out.println("Created Bug Id is "+ids[i]);
	     }
	}
	@Test(priority = 2)
	public void getAllIssues()
	{
		for(int i = 0; i<ids.length;i++){
			String idv = ids[i];
			System.out.println("get ids is "+idv);
		given().header("Content-Type","application/json").and().header("cookie",sessionID).and().
		queryParam("fields", "name,description,summary").
		when().get("/rest/api/2/issue/"+idv+"").then().log().all().assertThat().statusCode(200);
	}}
	
}
