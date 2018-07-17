package restProj.restAutomation;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResourceFinder {

	static Properties prop = new Properties();

	public static Properties getRestProp() throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\files\\OR.properties");
		prop.load(fis);
		return prop;
	}
	public static String getSession() throws IOException
	{
		RestAssured.baseURI = getRestProp().getProperty("JiraHOST");
	    Response res =	given().header("Content-Type","application/json").body("{ \"username\":\"tarunjaiswal92\", \"password\":\"T@run123\"}").
		when().post("/rest/auth/1/session").
		then().statusCode(200).extract().response();
	    JsonPath jsonStr = RowDataConversion.rawJSON(res);
	    //System.out.println(jsonStr.get("session.value"));
	    return jsonStr.getString("session.name")+"="+jsonStr.getString("session.value");
	}
	
	public static String getIssueID() throws IOException
	{
		Response res = given().header("Content-Type","application/json").and().header("cookie",ResourceFinder.getSession()).
				body("{" +
			    "\"fields\": {"+
		        "\"project\": {"+
		            "\"key\": \"PT\""+
		       " },"+
		        "\"summary\": \"satellite system is running outof memory\","+
		        "\"description\":\"same as summary, will discces in meeting.\","+
		        "\"issuetype\": {"+
		            "\"name\": \"Bug\""+
		        "}"+
			    "}}").log().all().when().post("/rest/api/2/issue/").then().assertThat().statusCode(201).
				extract().response();
				
				JsonPath rowjson = RowDataConversion.rawJSON(res);
				String id = rowjson.get("id");
				System.out.println("issue id "+id);
				return id;
	}
	
	public static String getCommentID() throws IOException
	{
		 Response res = given().header("Content-Type","application/json").and().header("cookie",ResourceFinder.getSession()).
					body("{\r\n" + 
							"    \"body\": \"i am added comment through rest auto.\",\r\n" + 
							"    \"visibility\": {\r\n" + 
							"        \"type\": \"role\",\r\n" + 
							"        \"value\": \"Administrators\"\r\n" + 
							"    }\r\n" + 
							"}").when().post("/rest/api/2/issue/"+ResourceFinder.getIssueID()+"/comment").then().assertThat().
					statusCode(201).extract().response();
				   JsonPath rowdata = RowDataConversion.rawJSON(res);
				   String comntID = rowdata.get("id");
				   System.out.println("comment id "+comntID);
				   return comntID;
	}
	
}
