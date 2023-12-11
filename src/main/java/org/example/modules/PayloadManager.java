package org.example.modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Utils.FakerUtils;
import org.example.payloads.request.Auth;
import org.example.payloads.request.Booking;
import org.example.payloads.request.Bookingdates;
import org.example.payloads.response.BookingResponse;


public class PayloadManager {

    // JAVA - JSON to that when give it that .body()?
    //Jr. QA - All the payload will keep it here

    ObjectMapper objectMapper;
    public String createPayload() throws JsonProcessingException {

        objectMapper = new ObjectMapper();
        Booking booking = new Booking();
        booking.setFirstname(FakerUtils.getUsername());
        booking.setLastname("Singh");
        booking.setTotalprice(1022);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Tea");

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2023-11-23");
        bookingdates.setCheckout("2023-11-24");
        booking.setBookingdates(bookingdates);

   String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
   return payload;
    }

    public String updatedPayload() throws JsonProcessingException {

        objectMapper = new ObjectMapper();
        Booking booking = new Booking();
        booking.setFirstname("Rahul");
        booking.setLastname("Jain");
        booking.setTotalprice(1024);
        booking.setDepositpaid(true);
        booking.setAdditionalneeds("Milk");

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2023-11-29");
        bookingdates.setCheckout("2023-11-29");
        booking.setBookingdates(bookingdates);

        String payload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);
        return payload;
    }

    public String setToken() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(auth);
    }

    public BookingResponse JsonToObject(String jsonString) throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        BookingResponse bookingResponse = objectMapper.readValue(jsonString, BookingResponse.class);
        return bookingResponse;
    }


    public Booking JsonToObjectPUT(String jsonString) throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        Booking bookingResponse = objectMapper.readValue(jsonString, Booking.class);
        return bookingResponse;
    }


}


