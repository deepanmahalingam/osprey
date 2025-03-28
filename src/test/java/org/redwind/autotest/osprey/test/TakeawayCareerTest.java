package org.redwind.autotest.osprey.test;

import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.pages.TakeawayCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
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


    @Test(description = "Verify that custom job title search and country filter works as expected")
    public void sampleTest() {
        String jobTitle = "Test";
        baseActions.enterText(TakeawayCareerPage.SEARCH_JOB_INPUT_FIELD.getLocator(), "Test");
        baseActions.clickOnWebElement(TakeawayCareerPage.SEARCH_BUTTON.getLocator());
        baseActions.scrollToWebElement(TakeawayCareerPage.SEARCH_RESULT_AREA.getLocator());
        String expactedText = "Showing Search results for " + "\"" + jobTitle + "\"";
        String actualText = baseActions.getText(TakeawayCareerPage.SEARCH_KEYWORD_HEADING.getLocator()).replaceAll("\\s+", " ").trim();
        Assert.assertEquals(actualText,expactedText);
    }


}
