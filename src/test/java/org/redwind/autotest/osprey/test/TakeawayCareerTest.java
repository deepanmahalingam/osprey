package org.redwind.autotest.osprey.test;

import com.microsoft.playwright.Page;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.BeforeClass;

public class TakeawayCareerTest extends BaseTest {

    @Autowired
    DriverFactory driverFactory;

    @Autowired
    BaseActions baseActions;

    @Value("${env.takeaway-career-url}")
    private String takeawayCareerPage;

    Page page;

    @BeforeClass
    public void testEntry() {
        baseActions.clickOnWebElement(takeawayCareerPage);
    }
}
