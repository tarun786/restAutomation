package com.jira.rest.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;

public class GetRemoteLinks {

	String sessionID;
	String issueID;
	String globalID;

	@Test(priority = 1)
	public void createRenoteIssueLinks() throws IOException {
		sessionID = ResourceFinder.getSession();
		issueID = ResourceFinder.getIssueID();
		Response res = given().header("Content-Type", "application/json").and().header("cookie", sessionID).
				body("{\r\n" + 
						"    \"globalId\": \"system=http://www.mycompany.com/support&id=1\",\r\n" + 
						"    \"application\": {\r\n" + 
						"        \"type\": \"com.acme.tracker\",\r\n" + 
						"        \"name\": \"My Acme Tracker\"\r\n" + 
						"    },\r\n" + 
						"    \"relationship\": \"causes\",\r\n" + 
						"    \"object\": {\r\n" + 
						"        \"url\": \"http://www.mycompany.com/support?id=1\",\r\n" + 
						"        \"title\": \"TSTSUP-111\",\r\n" + 
						"        \"summary\": \"Crazy customer support issue\",\r\n" + 
						"        \"icon\": {\r\n" + 
						"            \"url16x16\": \"http://www.mycompany.com/support/ticket.png\",\r\n" + 
						"            \"title\": \"Support Ticket\"\r\n" + 
						"        },\r\n" + 
						"        \"status\": {\r\n" + 
						"            \"resolved\": true,\r\n" + 
						"            \"icon\": {\r\n" + 
						"                \"url16x16\": \"http://www.mycompany.com/support/resolved.png\",\r\n" + 
						"                \"title\": \"Case Closed\",\r\n" + 
						"                \"link\": \"http://www.mycompany.com/support?id=1&details=closed\"\r\n" + 
						"            }\r\n" + 
						"        }\r\n" + 
						"    }\r\n" + 
						"}").when().post("/rest/api/2/issue/" + issueID + "/remotelink").then().log().body().assertThat().statusCode(200)
				.extract().response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		globalID = rowdata.get("globalId");
		System.out.println("global id " + globalID);
	}

	@Test(priority = 2)
	public void getRemoteIssueLinks() throws IOException {

		System.out.println("issue id is " + issueID);
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).and()
				.queryParam("globalId", globalID).when().get("/rest/api/2/issue/" + issueID + "/remotelink").then()
				.log().all().assertThat().statusCode(200);

	}
}
