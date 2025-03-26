package org.redwind.autotest.osprey.core;

import org.redwind.autotest.osprey.PlaywrightApplication;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

@SpringBootTest
@ContextConfiguration(classes = {PlaywrightApplication.class})
public class BaseTest extends AbstractTestNGSpringContextTests {
    @Autowired
    DriverFactory driverFactory;

    @BeforeClass(alwaysRun = true)
    @Parameters({"browserName", "headless"})
    public void setUp(@Optional("chrome") String browserName, @Optional("false") String headless) {
        driverFactory.initializeBrowser(browserName, headless);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driverFactory.closeBrowser();
    }


}
