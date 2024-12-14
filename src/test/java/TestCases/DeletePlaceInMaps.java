package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class DeletePlaceInMaps {
	
	@Test
	public void DeletePlace(ITestContext context) { 
	 // Print the place_id change data type of PlaceId from object to String 
		Object placeIdObject = context.getSuite().getAttribute("placeId");
		if (placeIdObject == null) {
		    throw new RuntimeException("Place ID is not set in the test context!");
		}
		String placeId = placeIdObject.toString();
		System.out.println("Place ID extracted: " + placeId );
	
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		
		given()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\r\n" +
				      "    \"place_id\": \"" + placeId + "\"\r\n" +
				      "}")			
			.log().all()
		.when()
			.delete("/maps/api/place/delete/json")
		.then()		
			//.log().all()
			//.log().headers()
			.log().body()
			.assertThat().statusCode(200)
			.body("status", equalTo("OK"))
			.header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
			.header("Content-Type",equalTo("application/json;charset=UTF-8"));
	}
}
