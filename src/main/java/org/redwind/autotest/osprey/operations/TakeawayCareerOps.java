package org.redwind.autotest.osprey.operations;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import org.redwind.autotest.osprey.pages.TakeawayCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import java.util.HashSet;
import java.util.List;

@Service
public class TakeawayCareerOps {

    @Autowired
    BaseActions baseActions;
    @Autowired
    TakeawayCareerPage takeawayCareerPage;
    private String jobCount;
    private String jobCountForCountry;
    private String oldPageTitle;
    private String oldUrl;

    public HashSet<String> getSetOfCountriesInSearchResult() {
        HashSet<String> setOfCountries = new HashSet<>();
        for (int i = 0; i <= baseActions.getElementsCount(takeawayCareerPage.searchResultPages()); i++) {
            List<String> listOfStrings = baseActions.getListOfAllTextFromElements(takeawayCareerPage.searchResultJobLocation());
            for (int j = 1; j < baseActions.getElementsCount(takeawayCareerPage.searchResultJobLocation()); j++) {
                setOfCountries.add(getCountryNameFromSearchedJob(listOfStrings.get(j)));
            }
            if (baseActions.getElementsCount(takeawayCareerPage.nextPage()) == 0) {
                System.out.println("Last Page in the search");
            } else {
                baseActions.clickOnWebElement(takeawayCareerPage.nextPage());
                baseActions.waitForPageLoad();
            }
        }
        return setOfCountries;
    }

    public String getCountryNameFromSearchedJob(String location) {
        if (location.contains("Location")) {
            location = location.replace("Location", "");
        }
        String[] country = location.split(",");
        int index = country.length - 1;
        location = country[index];
        return location.trim();
    }

    public String getJobCategoryFromSearchedJob(Locator locator) {
        String category = baseActions.getText(locator);
        if (category.contains("Category")) {
            category = category.replace("Category", "");
        }
        return category.trim();
    }

    @Step("Search for Custom Job")
    public void searchForCustomJob(String jobTitle) {
        baseActions.enterText(takeawayCareerPage.searchJobInputField(), jobTitle);
        baseActions.clickOnWebElement(takeawayCareerPage.searchButton());
    }

    @Step("Verify heading of search result")
    public void verifySearchResultHeading(String jobTitle) {
        baseActions.scrollToWebElement(takeawayCareerPage.searchResult());
        String expectedText = "\"" + jobTitle + "\"";
        String actualText = baseActions.getText(takeawayCareerPage.searchResultHeading(jobTitle)).replaceAll("\\s+", " ").trim();
        Assert.assertEquals(actualText, expectedText);
    }

    @Step("Verify resulted jobs are displayed globally")
    public void verifyJobResultsDisplayedGlobally() {
        HashSet<String> setOfCountries = getSetOfCountriesInSearchResult();
        Assert.assertTrue(setOfCountries.size() > 1, "Job from different countries are not shown in the result");
    }

    @Step("Filter job for specific country")
    public void filterJobForSpecificCountry(String country) {
        baseActions.scrollToWebElement(takeawayCareerPage.refineYourSearch());
        baseActions.clickOnWebElement(takeawayCareerPage.filterHeading("Country"));
        baseActions.scrollToWebElement(takeawayCareerPage.filterValue(country));
        baseActions.clickOnWebElement(takeawayCareerPage.filterValue(country));
        baseActions.waitForElement(takeawayCareerPage.clearAllFilter());
        baseActions.waitForTimeout(1000);
        boolean flag = baseActions.isElementChecked(takeawayCareerPage.filterValue(country));
        Assert.assertTrue(flag, "Country is not selected");
    }

    @Step("Verify resulted job is displayed for specific country")
    public void verifyJobResultsDisplayedForSpecifiedCountry(String country) {
        baseActions.scrollToWebElement(takeawayCareerPage.searchResult());
        HashSet<String> setOfCountries1 = getSetOfCountriesInSearchResult();
        Assert.assertTrue(setOfCountries1.size() == 1 && setOfCountries1.toString().contains(country), "Job from different countries are shown in the result");
    }

    @Step("Select the job category from Dropdown")
    public void selectJobCategoryFromDropdown(String jobCategory) {
        baseActions.clickOnWebElement(takeawayCareerPage.searchJobInputField());
        baseActions.scrollToWebElement(takeawayCareerPage.jobCategoryDropDown(jobCategory));
        jobCount = baseActions.getText(takeawayCareerPage.jobCategoryDropDown(jobCategory));
        baseActions.clickOnWebElement(takeawayCareerPage.jobCategoryDropDown(jobCategory));
    }

    @Step("Validate job category filter automatically selected")
    public void validateSelectionOfJobCategoryInFilterSection(String jobCategory) {
        baseActions.scrollToWebElement(takeawayCareerPage.refineYourSearch());
        if (baseActions.getAttribute(takeawayCareerPage.filterHeading("Category"), "aria-expanded").contains("false")) {
            baseActions.clickOnWebElement(takeawayCareerPage.filterHeading("Category"));
        }
        baseActions.scrollToWebElement(takeawayCareerPage.filterValue(jobCategory));
        boolean flag = baseActions.isElementChecked(takeawayCareerPage.filterValue(jobCategory));
        Assert.assertTrue(flag, "Job category is not selected automatically in search section");
    }

    @Step("Validate Job count matches")
    public void validateJobCountMatchesAsPerSelection(String jobCategory) {
        String actualJobCountInFilter = baseActions.getText(takeawayCareerPage.filterValue(jobCategory));
        Assert.assertTrue(jobCount.contains(actualJobCountInFilter), "Job count does not matched in filter");
        String actualJobCountInList = baseActions.getText(takeawayCareerPage.jobListCount());
        Assert.assertTrue(jobCount.contains(actualJobCountInList), "Job count does not matched in List");
    }

    @Step("Validate job count matches for the country")
    public void validateJobCountMatchesForCountry(String country) {
        baseActions.scrollToWebElement(takeawayCareerPage.filterValue(country));
        jobCountForCountry = baseActions.getAttribute(takeawayCareerPage.filterValue(country), "data-ph-at-count");
        baseActions.scrollToWebElement(takeawayCareerPage.jobListCount());
        String jobCountInList = baseActions.getText(takeawayCareerPage.jobListCount());
        Assert.assertTrue(jobCountForCountry.replace("jobs", "").trim().contains(jobCountInList.replace("jobs", "").trim()), "Search result does not matches the country count");
    }

    @Step("Validate search result has correct job category")
    public void validateSearchResultHasCorrectJobCategory(String jobCategory, String country) {
        baseActions.scrollToWebElement(takeawayCareerPage.searchResult());
        HashSet<String> setOfJobCategories = new HashSet<>();
        HashSet<String> setOfLocation = new HashSet<>();
        int jobCountInResult = 0;
        for (int i = 1; i <= baseActions.getElementsCount(takeawayCareerPage.searchResultPages()); i++) {
            List<String> listOfJobSearched = baseActions.getListOfAllTextFromElements(takeawayCareerPage.searchedJoblist());
            for (int j = 1; j <= listOfJobSearched.size(); j++) {
                setOfJobCategories.add(getJobCategoryFromSearchedJob(takeawayCareerPage.searchResultJobCatagory().nth(j)));
                setOfLocation.add(getCountryNameFromSearchedJob(baseActions.getListOfAllTextFromElements(takeawayCareerPage.searchResultJobLocation().nth(j)).toString()));
                jobCountInResult += 1;
            }
            if (baseActions.getElementsCount(takeawayCareerPage.nextPage()) == 0) {
                System.out.println("We are in last page of job search results");
            } else {
                baseActions.clickOnWebElement(takeawayCareerPage.nextPage());
                baseActions.waitForTimeout(1000);
            }
        }
        Assert.assertTrue(setOfJobCategories.size() == 1 && setOfJobCategories.toString().contains(jobCategory), "Different job Categories are shown in the result");
        Assert.assertTrue(setOfLocation.size() == 1 && setOfLocation.toString().contains(country), "Job from different countries are shown in the result");
        Assert.assertTrue(jobCountForCountry.contains(Integer.toString(jobCountInResult)), "Resulted job Count for the country do not match");
    }

    @Step("Click job category on widgets")
    public void clickJobCategoryOnWidget(String jobCategory) {
        baseActions.scrollToWebElement(takeawayCareerPage.jobCategoryWidgetHeading());
        baseActions.scrollToWebElement(takeawayCareerPage.jobCategoryWidget(jobCategory));
        oldPageTitle = baseActions.getPageTitle();
        oldUrl = baseActions.getPageURL();
        baseActions.scrollToWebElement(takeawayCareerPage.jobCategoryWidget(jobCategory));
        baseActions.clickOnWebElement(takeawayCareerPage.jobCategoryWidget(jobCategory));
        baseActions.waitForTimeout(3000);
        baseActions.switchToNewTab();
    }

    @Step("Validate widget is opened in new window")
    public void validateResultIsOpenedInNewWindow() {
        String newPageTitle = baseActions.getPageTitle();
        String newUrl = baseActions.getPageURL();
        Assert.assertNotEquals(oldPageTitle, newPageTitle, "Old Page title is same as of new");
        Assert.assertNotEquals(oldUrl, newUrl, "Search result is not opened in new tab");
    }

    @Step("Validate clear all filter")
    public void validateClearAllFilter(String jobCategory) {
        baseActions.waitForElementToBeVisible(takeawayCareerPage.refineYourSearch());
        baseActions.scrollToWebElement(takeawayCareerPage.refineYourSearch());
        baseActions.clickOnWebElement(takeawayCareerPage.clearAllFilter());

        baseActions.waitForElementToBeVisible(takeawayCareerPage.refineYourSearch());
        baseActions.scrollToWebElement(takeawayCareerPage.refineYourSearch());
        if (baseActions.getAttribute(takeawayCareerPage.filterHeading("Category"), "aria-expanded").contains("false")) {
            baseActions.clickOnWebElement(takeawayCareerPage.filterHeading("Category"));
        }
        boolean flag = baseActions.isElementChecked(takeawayCareerPage.filterValue(jobCategory));
        Assert.assertFalse(flag, "Job category is selected after clicking clear all filter");
    }


}


