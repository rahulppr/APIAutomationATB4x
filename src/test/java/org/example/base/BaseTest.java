package org.example.base;

import io.restassured.path.json.JsonPath;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.actions.AssertActions;
import org.example.endpoints.APIConstants;
import org.example.modules.PayloadManager;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

    //Before Running a Method -
    // Rest Assured - given with Base URI, Path
    public static RequestSpecification requestSpecification;
    public static AssertActions actions;
    public static  PayloadManager payloadManager;
    public static JsonPath jsonPath;
    public static  Response response;
    public static ValidatableResponse validatableResponse;

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        //Reset the Rest Assured
        // Base URLs
        //Content - Type - ALL

        payloadManager = new PayloadManager();
        actions = new AssertActions();
//        requestSpecification = RestAssured.given()  //Two way to declare the requestSpecification
//                .baseUri(APIConstants.BASE_URL)
//                .contentType(ContentType.JSON);

        requestSpecification = new RequestSpecBuilder().setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type","application/json")
                .build().log().all();
    }

    //Get a Token - Extract the Token
    public String getToken() throws JsonProcessingException {
        //To get token

        requestSpecification = RestAssured.given().baseUri(APIConstants.BASE_URL).basePath("/auth");
        String payload = payloadManager.setToken();
        response = requestSpecification.contentType(ContentType.JSON)
                .body(payload)
                .when().post();
        jsonPath = new JsonPath(response.asString());
        return jsonPath.getString("token");
    }
}


