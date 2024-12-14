package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GetPlaceInMaps {

	@Test
	public void GetPlace(ITestContext context) { 
	 // Print the place_id change data type of PlaceId from object to String 
		Object placeIdObject = context.getSuite().getAttribute("placeId");
		if (placeIdObject == null) {
		    throw new RuntimeException("Place ID is not set in the test context!");
		}
		String placeId = placeIdObject.toString();
		System.out.println("Place ID extracted: " + placeId );
	
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		
		given()
			.queryParam("place_id", placeId)
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\r\n" +
				      "    \"place_id\": \"" + placeId + "\"\r\n" +
				      "}")			
			.log().all()
		.when()
			.get("/maps/api/place/get/json")
		.then()		
			.log().body()
			.assertThat().statusCode(200)
			.body("language", equalTo("French-IN"))
			.body("accuracy", equalTo("60"))
			.header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
			.header("Content-Type",equalTo("application/json;charset=UTF-8"));
	}
}
