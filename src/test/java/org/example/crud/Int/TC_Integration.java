package org.example.crud.Int;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.http.ContentType;
import org.example.endpoints.APIConstants;
import org.example.payloads.request.Booking;
import org.example.payloads.response.BookingResponse;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.assertj.core.api.Assertions.assertThat;

public class TC_Integration extends BaseTest {
    String token;
    String bookingId;
    String  bookingIdPojo;

      //1. Auth - API Key
      // Cookie Based Auth Side
      // OAuth 2.0 - Method how you can use the OAuth 2.0

    private static final Logger log = LogManager.getLogger(TC_Integration.class);

    //Create a Booking
    @Test(groups="P0")
    public void testCreateBooking() throws JsonProcessingException {
        token = getToken();
        System.out.println(token);

        assertThat(token).isNotNull().isNotEmpty();

        requestSpecification.basePath(APIConstants.CREATE_BOOKING);
        response = RestAssured.given().spec(requestSpecification)
                .when().body(payloadManager.createPayload()).post();
       // System.out.println("response--->"+response); O/P is Null
        //System.out.println("validatableResponse--->"+validatableResponse);

        validatableResponse = response.then().log().all();

        jsonPath = JsonPath.from(response.asString());
        validatableResponse.statusCode(200); // Error

        // Direct Extraction from json Path
        bookingId = jsonPath.getString("bookingid");

        // Booking Response Class
        BookingResponse bookingResponse = payloadManager.JsonToObject(response.asString());
        bookingIdPojo = bookingResponse.getBookingid().toString();
        System.out.println("JSON Booking Id :" + bookingId);
        System.out.println("BookingIdPojo :" + bookingIdPojo );

        log.info("This is my Booking ID" + bookingIdPojo);
        assertThat("Rahul").isEqualTo("Rahul");
    }

    //Update the Booking with Token and Booking ID
    @Test(groups="P0",dependsOnMethods = {"testCreateBooking"})

        public void testCreateAndUpdateBooking() throws JsonProcessingException {

            System.out.println("testCreateAndUpdateBooking ---->" + token);
            System.out.println("testCreateAndUpdateBooking ---->" + bookingId);
            System.out.println("testCreateAndUpdateBooking ---->" + bookingIdPojo);

            requestSpecification.basePath(APIConstants.UPDATE_BOOKING + "/" + bookingId);
            response = RestAssured.given().spec(requestSpecification).cookie("token",token)
                    .when().body(payloadManager.updatedPayload()).put();
           ValidatableResponse validatableResponse = response.then().log().all();
            validatableResponse.statusCode(200);
            validatableResponse.body("firstname", Matchers.is("Rahul"));

            Booking bookingResponse = payloadManager.JsonToObjectPUT(response.asString());
            assertThat(bookingResponse.getFirstname()).isEqualTo("Rahul").isNotNull();
            assertThat(bookingResponse.getLastname()).isNotNull();
            assertThat(bookingResponse.getDepositpaid()).isNotNull();
            assertThat(bookingResponse.getBookingdates().getCheckin()).isNotNull();
            assertThat(bookingResponse.getBookingdates().getCheckout()).isNotEmpty();

            assertThat("Rahul").isEqualTo("Rahul");
        }

        // Delete Also
        @Test(groups = "P0",dependsOnMethods = { "testCreateAndUpdateBooking"})
        public void testDeleteCreatedBooking(){
            requestSpecification.basePath(APIConstants.UPDATE_BOOKING + "/" + bookingId)
                    .cookie("token",token);
            ValidatableResponse validatableResponse = RestAssured.given().spec(requestSpecification)
                    .auth().basic("admin", "password123")
                    .when().delete().then().log().all();
            validatableResponse.statusCode(201);
        }

        @Test(groups = "P0",dependsOnMethods = { "testDeleteCreatedBooking"})
        public void testDeleteBookingByGet(){
            requestSpecification.basePath(APIConstants.UPDATE_BOOKING+"/"+bookingId);
            response = RestAssured.given().spec(requestSpecification)
                    .when().get();
          ValidatableResponse  validatableResponse = response.then().log().all();
            validatableResponse.statusCode(404);
        }
}


