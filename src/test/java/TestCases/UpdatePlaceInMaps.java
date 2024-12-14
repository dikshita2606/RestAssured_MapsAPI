package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;

public class UpdatePlaceInMaps {

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
		
		Faker faker = new Faker();
		given()
			.queryParam("place_id", placeId)
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+placeId+"\",\r\n"
					+ "\"address\":\""+faker.address().city()+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}")			
			.log().all()
		.when()
			.put("/maps/api/place/update/json")
		.then()		
			.log().body()
			.assertThat().statusCode(200)
			.body("msg", equalTo("Address successfully updated"))
			.header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
			.header("Content-Type",equalTo("application/json;charset=UTF-8"));
	}
}
