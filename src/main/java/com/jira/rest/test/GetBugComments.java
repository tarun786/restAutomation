package com.jira.rest.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;

public class GetBugComments {
    String session_ID;
    String issueID;
    String comntID;
	@Test(priority = 1)
	public void getComments() throws IOException {
		session_ID = ResourceFinder.getSession();
		issueID = ResourceFinder.getIssueID();
		Response res = given().header("Content-Type", "application/json").and().header("cookie", session_ID)
				.body("{\r\n" + "    \"body\": \"Newly added comment through rest automation.\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}")
				.when().post("/rest/api/2/issue/" + issueID + "/comment").then().assertThat().statusCode(201).extract()
				.response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		comntID = rowdata.get("id");
		System.out.println("comment id " + comntID);

		Response res1 = given().header("Content-Type", "application/json").header("cookie", session_ID).when()
				.get("/rest/api/2/issue/" + issueID + "/comment").then().log().body().assertThat().statusCode(200)
				.extract().response();
		rowdata = RowDataConversion.rawJSON(res1);
		String actualComentType = rowdata.get("comments[0].visibility.value").toString();
		System.out.println("Comment type " + actualComentType);
		Assert.assertEquals(actualComentType, "Administrators");

	}
	@Test(priority = 2)
	public void getSingleComment()
	{
		given().header("Content-Type", "application/json").header("cookie", session_ID).when()
		.get("/rest/api/2/issue/"+issueID+"/comment/"+comntID+"").then().log().body().assertThat().statusCode(200);
	}
	
	@Test(priority = 3)
	public void deleteComment()
	{
		 given().header("Content-Type", "application/json").and().header("cookie", session_ID)
			.when().delete("/rest/api/2/issue/"+issueID+"/comment/"+comntID+"").then().assertThat().statusCode(204);
	}
}
