package restProj.restAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class restPostDatByXml {

	@Test
	public void postDataByXML() throws IOException
	{
		RestAssured.baseURI = "https://maps.googleapis.com";

	Response resp =	given().queryParam("key", "AIzaSyAtlCpHXQK-2itWjQWJD-R9tcJZmZCDM6g").log().all()
				.body(getDataConversionFromXMl(System.getProperty("user.dir")+"\\files\\postdata.xml"))
				.when().log().all().post("/maps/api/place/add/xml").then().assertThat().statusCode(200).and()
				.contentType(ContentType.XML).extract().response();
	String xdata = resp.asString();
	XmlPath xpath = new XmlPath(xdata);
	System.out.println(xpath.get("PlaceAddResponse.status"));
	
	
	}
	
	public String getDataConversionFromXMl(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
