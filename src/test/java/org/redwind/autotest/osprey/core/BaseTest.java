package org.redwind.autotest.osprey.core;

import org.redwind.autotest.osprey.PlaywrightApplication;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

@SpringBootTest
@ContextConfiguration(classes = {PlaywrightApplication.class})
public class BaseTest extends AbstractTestNGSpringContextTests {
    @Autowired
    DriverFactory driverFactory;

    @Value("${env.portfolio-url}")
    String portfolio_url;

    @BeforeClass
    @Parameters({"browserName", "headless"})
    public void setUp(@Optional("chrome") String browserName, @Optional("false") String headless) {
        driverFactory.initializeBrowser(browserName, headless);
    }

    @BeforeMethod
    public void open() {
        driverFactory.getCurrentPage().navigate(portfolio_url);
    }

    @AfterTest
    public void tearDown() {
        driverFactory.closeBrowser();
    }


}
