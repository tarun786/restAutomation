

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import restProj.restAutomation.RowDataConversion;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WelcomeRest {

	public static void main(String[] args) {
		RestAssured.baseURI="https://maps.googleapis.com";
	    Response	res = given().log().all().
		param("location","-33.8670522,151.1957362").
		param("radius","500").
		param("key","AIzaSyAtlCpHXQK-2itWjQWJD-R9tcJZmZCDM6g").
		when().get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		extract().response();
	    JsonPath rowdata = RowDataConversion.rawJSON(res);
		int count = rowdata.get("results.size()");
		System.out.println(count);
		for(int i =0 ;i<count; i++)
		{
			System.out.println(rowdata.get("results["+i+"].name"));
		}
		

	}

}
