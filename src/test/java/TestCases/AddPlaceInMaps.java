package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.ITestContext;

public class AddPlaceInMaps {
	Faker faker;	
	public static String placeId;
	@Test
	public void addPlace(ITestContext context) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		faker = new Faker();
		Response response = (Response) given()
			.queryParam("key", "qaclick123")
			.header("Content-Type", "application/json")
			.body("{\r\n"
					+ "  \"location\": {\r\n"
					+ "    \"lat\": "+faker.address().latitude()+ ",\n" 
					+ "    \"lng\": "+faker.address().longitude() + "\n"
					+ "  },\r\n"
					+ "  \"accuracy\": \"60\",\r\n"
					+ "  \"name\": \""+faker.name().firstName() +  "\",\n"
					+ "  \"phone_number\": \""+faker.phoneNumber().cellPhone() +  "\",\n"
					+ "  \"address\": \""+faker.address().cityName() + "\",\n"
					+ "  \"types\": [\r\n"
					+ "    \"shoe park\",\r\n"
					+ "    \"shop\"\r\n"
					+ "  ],\r\n"
					+ "  \"website\": \"http://google.com\",\r\n"
					+ "  \"language\": \"French-IN\"\r\n"
					+ "}")			
			.log().all()
		.when()
			.post("/maps/api/place/add/json")
		.then()		
			//.log().all()
			//.log().headers()
			.log().body()
			.assertThat().statusCode(200)
			.body("scope", equalTo("APP"))
			.header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
			.header("Content-Type",equalTo("application/json;charset=UTF-8"))
			.extract()
            .response();
			//.log().all();
		
		String responseString = response.asString();
        JsonPath jsonPath = new JsonPath(responseString);
        String placeId = jsonPath.getString("place_id");
		
        // Print the place_id
       // System.out.println("Place ID extracted: " + placeId);
        context.getSuite().setAttribute("placeId", placeId);	
	}

}
