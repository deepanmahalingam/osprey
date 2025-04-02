package org.redwind.autotest.osprey.test;

import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Description;
import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.dataProviders.DataHolder;
import org.redwind.autotest.osprey.operations.TakeawayCareerOps;
import org.redwind.autotest.osprey.pages.TakeawayCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
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


    @Test(dataProvider = "jobTitleWithCountrySearch",
            dataProviderClass = DataHolder.class)
    @Description("Verify that custom job title search and country filter works as expected")
    public void validateJobTitleSearch(String jobTitle, String country) {
        takeawayCareerOps.searchForCustomJob(jobTitle);
        takeawayCareerOps.verifySearchResultHeading(jobTitle);
        takeawayCareerOps.verifyJobResultsDisplayedGlobally();
        takeawayCareerOps.filterJobForSpecificCountry(country);
        takeawayCareerOps.verifyJobResultsDisplayedForSpecifiedCountry(country);
    }

    @Test(dataProvider = "jobCategoryDropDownWithCountryFilter",
            dataProviderClass = DataHolder.class)
    @Description("Check that Job Category dropdown and country filter displays correct data on selection")
    public void validateJobCategoryDropdown(String jobCategory, String jobCountry) {
        takeawayCareerOps.selectJobCategoryFromDropdown(jobCategory);
        takeawayCareerOps.validateSelectionOfJobCategoryInFilterSection(jobCategory);
        takeawayCareerOps.validateJobCountMatchesAsPerSelection(jobCategory);
        takeawayCareerOps.filterJobForSpecificCountry(jobCountry);
        takeawayCareerOps.validateJobCountMatchesForCountry(jobCountry);
        takeawayCareerOps.validateSearchResultHasCorrectJobCategory(jobCategory, jobCountry);
    }

    @Test(dataProvider = "jobCategoryWidgetsCheck",
            dataProviderClass = DataHolder.class)
    @Description("Verify that Job Category widget displays correct information on selection")
    public void validateJobCategoryWidgets(String jobCategoryWidget) {
        try {
            takeawayCareerOps.clickJobCategoryOnWidget(jobCategoryWidget);
            takeawayCareerOps.validateResultIsOpenedInNewWindow();
            takeawayCareerOps.validateSelectionOfJobCategoryInFilterSection(jobCategoryWidget);
            takeawayCareerOps.verifyJobResultsDisplayedGlobally();
            takeawayCareerOps.validateClearAllFilter(jobCategoryWidget);
        } catch (TimeoutError e) {
            Assert.fail("Page Load issue");
        } finally {
            baseActions.closeTheTab();
            baseActions.getToFirstTab();
        }
    }

    @AfterMethod
    public void afterMethod() {
        baseActions.openWebPage(takeawayCareerPageURL);
    }

}
