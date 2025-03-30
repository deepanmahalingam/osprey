package org.redwind.autotest.osprey.test;

import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.operations.TakeawayCareerOps;
import org.redwind.autotest.osprey.pages.TakeawayCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TakeawayCareerTest extends BaseTest {


    @Autowired
    BaseActions baseActions;

    @Value("${env.takeaway-career-url}")
    private String takeawayCareerPageURL;

    @Autowired
    TakeawayCareerOps takeawayCareerOps;


    @BeforeClass
    public void testEntry() {
        baseActions.openWebPage(takeawayCareerPageURL);
        baseActions.clickOnWebElement(TakeawayCareerPage.ALLOW_COOKIES);
    }


    @Test(description = "Verify that custom job title search and country filter works as expected")
    public void sampleTest() {
        String jobTitle = "Test";
        String country = "Netherlands";
        takeawayCareerOps.searchForCustomJob(jobTitle);
        takeawayCareerOps.verifySearchResultHeading(jobTitle);
        takeawayCareerOps.verifyJobResultsDisplayedGlobally();
        takeawayCareerOps.filterJobForSpecificCountry(country);
        takeawayCareerOps.verifyJobResultsDisplayedForSpecifiedCountry(country);
    }

    @Test(description = "Check that Job Category dropdown and country filter displays correct data on selection")
    public void secondTest() {
        String jobCategory = "Sales";
        takeawayCareerOps.selectJobCategoryFromDropdown(jobCategory);
        takeawayCareerOps.validateSelectionOfJobCategoryInFilterSection(jobCategory);
        takeawayCareerOps.validateJobCountMatchesAsPerSelection(jobCategory);
        String jobCountry = "Germany";
        takeawayCareerOps.filterJobForSpecificCountry(jobCountry);
        takeawayCareerOps.validateJobCountMatchesForCountry(jobCountry);
        takeawayCareerOps.validateSearchResultHasCorrectJobCategory(jobCategory, jobCountry);
    }

    @Test(description = "Verify that Job Category widget displays correct information on selection")
    public void thirdTest() {
        String[] jobCategory = {"Corporate", "Customer Service", "Finance", "Human Resources"};
        for (int i = 0; i < jobCategory.length; i++) {
            takeawayCareerOps.clickJobCategoryOnWidget(jobCategory[i]);
            takeawayCareerOps.validateResultIsOpenedInNewWindow();
            takeawayCareerOps.validateSelectionOfJobCategoryInFilterSection(jobCategory[i]);
            takeawayCareerOps.verifyJobResultsDisplayedGlobally();
            takeawayCareerOps.validateClearAllFilter(jobCategory[i]);
            baseActions.closeTheTab();
            baseActions.getToFirstTab();
        }
    }

    @AfterMethod
    public void afterMethod() {
        baseActions.openWebPage(takeawayCareerPageURL);
    }

}
