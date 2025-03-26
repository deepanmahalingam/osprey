package org.redwind.autotest.osprey.test;

import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.pages.TakeawayCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TakeawayCareerTest extends BaseTest {


    @Autowired
    BaseActions baseActions;

    @Value("${env.takeaway-career-url}")
    private String takeawayCareerPageURL;


    @BeforeClass
    public void testEntry() {
        baseActions.openWebPage(takeawayCareerPageURL);
        baseActions.clickOnWebElement(TakeawayCareerPage.ALLOW_COOKIES.getLocator());
    }


    @Test(description = "Sample Test Execution")
    public void sampleTest() {
        System.out.println("Sample Test is executing");
    }


}
