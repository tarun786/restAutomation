package com.jira.rest.test;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.ResourceFinder;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;

import java.io.IOException;

public class WatchersIssue {
	String sessionID;
	String issueID;

	@Test(priority = 1)
	public void addWatcher() {
		try {
			sessionID = ResourceFinder.getSession();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			issueID = ResourceFinder.getIssueID();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).and().when()
		.body("vikram92").post("/rest/api/2/issue/" + issueID + "/watchers").then().assertThat().statusCode(204);

	}

	@Test(priority = 2)
	public void getWatcher() {
		Response res = given().header("Content-Type", "application/json").and().header("cookie", sessionID).when()
				.get("/rest/api/2/issue/"+issueID+"/watchers").then().assertThat().statusCode(200).extract()
				.response();
		JsonPath rowdata = RowDataConversion.rawJSON(res);
		String watch_count = rowdata.get("watchCount").toString();
		System.out.println("Total watchers " + watch_count);
	}

	@Test(priority = 3)
	public void removeWatcher() {
		given().header("Content-Type", "application/json").and().header("cookie", sessionID).and()
				.queryParam("username", "vikram92").when().delete("/rest/api/2/issue/" + issueID + "/watchers").then()
				.log().all().assertThat().statusCode(204);
	}
}
