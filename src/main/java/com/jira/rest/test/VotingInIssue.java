package com.jira.rest.test;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

public class VotingInIssue {
	String sessionID;
	String issueID;
	String globalID;
	@Test(priority = 1)
	public void addVote() throws IOException
	{
		sessionID = ResourceFinder.getSession();
		issueID = ResourceFinder.getIssueID();
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).
		when().post("/rest/api/2/issue/"+issueID+"/votes").then().assertThat().statusCode(204);
		
	}
	
	@Test(priority = 2)
	public void getVote()
	{
	Response res = given().header("Content-Type", "application/json").and().header("cookie", sessionID).
		when().get("/rest/api/2/issue/"+issueID+"/votes").then().log().body().assertThat().statusCode(200).extract().response();
	 JsonPath rowdata = RowDataConversion.rawJSON(res);
	 System.out.println(rowdata.get("votes"));
	 Assert.assertTrue((Boolean) rowdata.get("hasVoted"));
	}
}
