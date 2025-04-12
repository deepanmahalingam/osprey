package org.redwind.autotest.osprey.test.apiTest;

import io.qameta.allure.Description;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.redwind.autotest.osprey.operations.CRUDOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

@SpringBootTest
public class CRUDTest extends AbstractTestNGSpringContextTests {
    @Autowired
    CRUDOps crudOps;

    @Autowired
    DriverFactory driverFactory;

    @BeforeMethod
    public void initialize() {
        System.out.println("inside initialize");
        driverFactory.initializeApiRequest();
    }

    @Test
    @Description("Test covers Create, Read, Update and Delete operations on environment")
    public void test() throws IOException {
        crudOps.generateAccessToken();
        crudOps.createNewBooking();
        crudOps.validateNewBookingIsCreated();
        crudOps.readAllBookingDetails();
        crudOps.validateNewBookingIsPresent();
        crudOps.updateExistingBooking();
        crudOps.readSpecificBooking();
        crudOps.validateUpdatedRecord();
        crudOps.deleteCreatedBooking();
        crudOps.validateBookingDeleted();
    }
}
