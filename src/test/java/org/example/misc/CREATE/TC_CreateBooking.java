package org.example.misc.CREATE;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import org.example.Utils.YamlReader;
import org.example.base.BaseTest;
import org.example.endpoints.APIConstants;
import org.example.payloads.response.BookingResponse;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TC_CreateBooking extends BaseTest {

    @Test(groups={"stage","P0"})
    @Owner("Rahul")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that Create Booking works and ID is generated")
public void testCreateBooking() throws JsonProcessingException {
    //Call the Request Block

    //Call the Payload Block

    //Call the AssertBlock


requestSpecification.basePath(APIConstants.CREATE_BOOKING);
response = requestSpecification.when().body(payloadManager.createPayload()).post();
 validatableResponse = response.then().log().all();
 validatableResponse.statusCode(200);

 //new YamlReader().readKey().get("username");
 // new YamlReader().readKey().get("username");
        // new PropertyReader().readKey().get("username");
        // new JSONReader().readKey().get("username");
        // new ExcelReader().readKey().get("username");
        // new ENVReader().readKey().get("username");
        // new XMLReader().readKey().get("username");

//Direct Extraction from json Path
jsonPath = JsonPath.from(response.asString());
String bookingId = jsonPath.getString("bookingid");


// Booking Response Class
BookingResponse bookingResponse = payloadManager.JsonToObject(response.asString());
//bookingIdPojo = bookingResponse.getBookingid().toString();
//log.info("This is My Booking ID"+bookingIdPojo);
        assertThat(bookingResponse.getBookingid().toString()).isNotNull().isNotEmpty();
}

}

