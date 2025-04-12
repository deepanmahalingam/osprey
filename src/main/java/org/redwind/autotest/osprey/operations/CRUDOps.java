package org.redwind.autotest.osprey.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import io.qameta.allure.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.redwind.autotest.osprey.utils.APIHelper;
import org.redwind.autotest.osprey.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class CRUDOps extends APIHelper {

    @Value("${env.herokuApp-url}")
    private String baseURL;

    @Value("${env.heroku-Authentication}")
    private String auth;

    @Value("${env.heroku-Booking}")
    private String booking;

    @Autowired
    CommonUtils commonUtils;
    private APIResponse response;
    private String accessToken = "token=";
    private String firstName;
    private String lastName;
    private int generatedID;
    private String authorization_path = "src/test/resources/testData/TakeAwayAuth.json";
    private String bookingData = "src/test/resources/testData/CreateBooking.json";
    private String updateDate = "src/test/resources/testData/updateBooking.json";


    @Step("Generate an access token")
    public void generateAccessToken() throws IOException {
        String url = baseURL + auth;
        JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(authorization_path))));
        response = post(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Content-type", "application/json")
                .setData(jsonObject.toString()));
        Assert.assertEquals(response.status(), 200, "Assess token is not created since response code do not match");
        accessToken += new JSONObject(response.text()).getString("token");
    }

    @Step("Create a new booking details")
    public void createNewBooking() throws IOException {
        String url = baseURL + booking;
        JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(bookingData))));
        updateBookingDetailsJSON(System.getProperty("user.dir") + "/" + bookingData);
        response = post(url, RequestOptions.create()
                .setHeader("Cookie", accessToken)
                .setHeader("Authorization", "No Auth")
                .setHeader("Content-type", "application/json")
                .setData(jsonObject.toString()));
    }

    @Step("Validate new booking is created")
    public void validateNewBookingIsCreated() {
        Assert.assertEquals(response.status(), 200, "New booking might not be created since status code do not match");
        generatedID = new JSONObject(response.text()).getInt("bookingid");
        Assert.assertFalse(response.text().isEmpty(), "Booking ID is not generated");
    }

    @Step("Read all booking details")
    public void readAllBookingDetails() {
        String url = baseURL + booking;
        response = get(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Cookie", accessToken)
                .setHeader("Content-type", "application/json"));
        Assert.assertEquals(response.status(), 200, "booking details are not fetched since response code do not match");
    }

    @Step("Validate newly created booking details present in the list")
    public void validateNewBookingIsPresent() {
        Assert.assertEquals(response.status(), 200, "created is not present since status code do not match");
        JSONArray jsonArray = new JSONArray(response.text());
        ArrayList<Integer> listOfBookingID = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            listOfBookingID.add(jsonObject.getInt("bookingid"));
        }
        Assert.assertTrue(listOfBookingID.contains(generatedID), "Newly generated booking is not present in the list");
    }

    @Step("Update the details in newly created booking record")
    public void updateExistingBooking() throws IOException {
        updateBookingDetailsJSON(System.getProperty("user.dir") + "/" + updateDate);
        String url = baseURL + booking + generatedID;
        JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get(updateDate))));
        response = patch(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Cookie", accessToken)
                .setHeader("Content-type", "application/json")
                .setData(jsonObject.toString()));
        Assert.assertEquals(response.status(), 200, "Response code does not match");
        Assert.assertEquals(new JSONObject(response.text()).getString("firstname"), firstName, "First Name is not updated");
        Assert.assertEquals(new JSONObject(response.text()).getString("lastname"), lastName, "Last Name is not updated");
    }

    @Step("Read booking details of newly created record")
    public void readSpecificBooking() {
        String url = baseURL + booking + generatedID;
        response = get(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Cookie", accessToken)
                .setHeader("Content-type", "application/json"));
        Assert.assertEquals(response.status(), 200, "details for booking id is not fetched since status code do not match");
    }

    @Step("Validate details are modified in new record")
    public void validateUpdatedRecord() {
        Assert.assertEquals(response.status(), 200, "Response code does not match");
        Assert.assertEquals(new JSONObject(response.text()).getString("firstname"), firstName, "First Name is not updated");
        Assert.assertEquals(new JSONObject(response.text()).getString("lastname"), lastName, "Last Name is not updated");
    }

    @Step("Delete newly created record")
    public void deleteCreatedBooking() {
        String url = baseURL + booking + generatedID;
        response = delete(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Cookie", accessToken));
        Assert.assertEquals(response.status(), 201, "status code doesn't match and record is not deleted");
    }

    @Step("Validate newly created booking details are deleted from list")
    public void validateBookingDeleted() {
        String url = baseURL + booking + generatedID;
        response = get(url, RequestOptions.create()
                .setHeader("Authorization", "No Auth")
                .setHeader("Cookie", accessToken)
                .setHeader("Content-type", "application/json"));
        Assert.assertEquals(response.status(), 404, "Booking details are not deleted");
        Assert.assertEquals(response.text(), "Not Found", "Booking details are not deleted");
    }

    /**
     * Method helps to update the json file for the body (create and update records)
     */
    public void updateBookingDetailsJSON(String filePath) throws IOException {
        String suffix = commonUtils.getRandomNumber(299, 5600);
        firstName = "Deepak" + suffix;
        lastName = "Mahalingam" + suffix;
        JsonParser jsonParser = new JsonParser();
        JsonObject root = jsonParser.parse(new FileReader(filePath)).getAsJsonObject();
        root.addProperty("firstname", firstName);
        root.addProperty("lastname", lastName);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(root.toString());
        }
    }

}
