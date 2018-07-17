package restProj.restAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class restAddAndDeletePlace {

	@Test
	public void addAndDelete() {
		RestAssured.baseURI = "https://maps.googleapis.com";

		Response responce = given().queryParam("key", "AIzaSyAtlCpHXQK-2itWjQWJD-R9tcJZmZCDM6g").log().all().
				body("{" + "\"location\": {" + "\"lat\": -33.8669710," + "\"lng\": 151.1958750" + "},"
						+ "\"accuracy\": 50," + "\"name\": \"Google Shoes!\"," + "\"phone_number\": \"(02) 9374 4000\","
						+ "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","
						+ "\"types\": [\"shoe_store\"]," + "\"website\": \"http://www.google.com.au/\","
						+ "\"language\": \"en-AU\"" + "}")
				.when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK")).extract().response();
		String extRes = responce.asString();
		System.out.println("Place add responce " + extRes);
		String[] arr = extRes.split(":");
		String place_id = "";
		for (String val : arr) {
			// System.out.println(val);
			place_id = arr[2];
			place_id = place_id.replaceAll("[reference[,\"]]", "").trim();
			// System.out.println(t);
		}
		// System.out.println("place id "+place_id);
		JsonPath jsonpath = new JsonPath(extRes);
		String place_ID = jsonpath.getString("place_id");
		// System.out.println(place_ID);
		// delete place
		// String deleteParam = "{\"place_id\": \"place ID\"}"
		Response deleteRes = given().log().all().queryParam("key", "AIzaSyAtlCpHXQK-2itWjQWJD-R9tcJZmZCDM6g")
				.body("{" + "\"place_id\": \"" + place_ID + "\"" + "}").when().post("/maps/api/place/delete/json")
				.then().log().all().assertThat().statusCode(200).and().contentType(ContentType.JSON).body("status", equalTo("OK"))
				.extract().response();
		System.out.println("Delete responce " + deleteRes.asString());
	}

}
