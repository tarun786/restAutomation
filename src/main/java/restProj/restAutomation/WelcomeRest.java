package restProj.restAutomation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WelcomeRest {

	public static void main(String[] args) {
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
		param("location","-33.8670522,151.1957362").
		param("radius","500").
		param("key","AIzaSyAtlCpHXQK-2itWjQWJD-R9tcJZmZCDM6g").
		when().get("/maps/api/place/nearbysearch/xml").
		then().assertThat().statusCode(200).and().contentType(ContentType.XML).and().
		body("results[0].name",equalTo("Sydney"));
		
		
		

	}

}
