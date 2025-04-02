package org.redwind.autotest.osprey.operations;

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
        for (int i = 1; i <= baseActions.getElementsCount(TakeawayCareerPage.SEARCH_RESULT_PAGES); i++) {
            List<String> listOfStrings = baseActions.getListOfAllTextFromElements(TakeawayCareerPage.JOB_LOCATION_FROM_SEARCH_RESULT);
            for (int j = 1; j < baseActions.getElementsCount(TakeawayCareerPage.JOB_LOCATION_FROM_SEARCH_RESULT); j++) {
                setOfCountries.add(getCountryNameFromSearchedJob(listOfStrings.get(j)));
            }
            if (baseActions.getElementsCount(TakeawayCareerPage.NEXT_PAGE_IN_SEARCH_RESULT) == 0) {
                System.out.println("Last Page in the search");
            } else {
                baseActions.clickOnWebElement(TakeawayCareerPage.NEXT_PAGE_IN_SEARCH_RESULT);
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

    public String getJobCategoryFromSearchedJob(String locator) {
        String category = baseActions.getText(locator);
        if (category.contains("Category")) {
            category = category.replace("Category", "");
        }
        return category.trim();
    }

    @Step("Search for Custom Job")
    public void searchForCustomJob(String jobTitle) {
        baseActions.enterText(TakeawayCareerPage.SEARCH_JOB_INPUT_FIELD, jobTitle);
        baseActions.clickOnWebElement(TakeawayCareerPage.SEARCH_BUTTON);
    }

    @Step("Verify heading of search result")
    public void verifySearchResultHeading(String jobTitle) {
        baseActions.scrollToWebElement(TakeawayCareerPage.SEARCH_RESULT_AREA);
        String expectedText = "Showing Search results for " + "\"" + jobTitle + "\"";
        String actualText = baseActions.getText(TakeawayCareerPage.SEARCH_KEYWORD_HEADING).replaceAll("\\s+", " ").trim();
        Assert.assertEquals(actualText, expectedText);
    }

    @Step("Verify resulted jobs are displayed globally")
    public void verifyJobResultsDisplayedGlobally() {
        HashSet<String> setOfCountries = getSetOfCountriesInSearchResult();
        Assert.assertTrue(setOfCountries.size() > 1, "Job from different countries are not shown in the result");
    }

    @Step("Filter job for specific country")
    public void filterJobForSpecificCountry(String country) {
        baseActions.scrollToWebElement(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        baseActions.clickOnWebElement(TakeawayCareerPage.filterBy("Country"));
        baseActions.scrollToWebElement(TakeawayCareerPage.FILTER_BY_VALUE(country));
        baseActions.clickOnWebElement(TakeawayCareerPage.FILTER_BY_VALUE(country));
        baseActions.waitForElement(TakeawayCareerPage.CLEAR_ALL_FILTER);
        baseActions.waitForTimeout(1000);
        boolean flag = baseActions.isElementChecked(TakeawayCareerPage.CHECKBOX_FOR_FILTER(country));
        Assert.assertTrue(flag, "Country is not selected");
    }

    @Step("Verify resulted job is displayed for specific country")
    public void verifyJobResultsDisplayedForSpecifiedCountry(String country) {
        baseActions.scrollToWebElement(TakeawayCareerPage.SEARCH_RESULT_AREA);
        HashSet<String> setOfCountries1 = getSetOfCountriesInSearchResult();
        Assert.assertTrue(setOfCountries1.size() == 1 && setOfCountries1.toString().contains(country), "Job from different countries are shown in the result");
    }

    @Step("Select the job category from Dropdown")
    public void selectJobCategoryFromDropdown(String jobCategory) {
        baseActions.clickOnWebElement(TakeawayCareerPage.SEARCH_JOB_INPUT_FIELD);
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_CATEGORY_DROPDOWN(jobCategory));
        jobCount = baseActions.getText(TakeawayCareerPage.JOB_CATEGORY_DROPDOWN_COUNT(jobCategory));
        baseActions.clickOnWebElement(TakeawayCareerPage.JOB_CATEGORY_DROPDOWN(jobCategory));
    }

    @Step("Validate job category filter automatically selected")
    public void validateSelectionOfJobCategoryInFilterSection(String jobCategory) {
        baseActions.scrollToWebElement(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        if (baseActions.getAttribute(TakeawayCareerPage.filterBy("Category"), "aria-expanded").contains("false")) {
            baseActions.clickOnWebElement(TakeawayCareerPage.filterBy("Category"));
        }
        baseActions.scrollToWebElement(TakeawayCareerPage.FILTER_BY_VALUE(jobCategory));
        boolean flag = baseActions.isElementChecked(TakeawayCareerPage.CHECKBOX_FOR_FILTER(jobCategory));
        Assert.assertTrue(flag, "Job category is not selected automatically in search section");
    }

    @Step("Validate Job count matches")
    public void validateJobCountMatchesAsPerSelection(String jobCategory) {
        String actualJobCountInFilter = baseActions.getText(TakeawayCareerPage.JOB_COUNT_IN_FILTER(jobCategory));
        Assert.assertTrue(actualJobCountInFilter.contains(jobCount), "Job count does not matched in filter");
        String actualJobCountInList = baseActions.getText(TakeawayCareerPage.JOB_LIST_COUNT);
        Assert.assertTrue(actualJobCountInList.contains(jobCount), "Job count does not matched in List");
    }

    @Step("Validate job count matches for the country")
    public void validateJobCountMatchesForCountry(String country) {
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_COUNT_IN_FILTER(country));
        jobCountForCountry = baseActions.getText(TakeawayCareerPage.JOB_COUNT_IN_FILTER(country));
        jobCountForCountry = jobCountForCountry.replace("(", "");
        jobCountForCountry = jobCountForCountry.replace(")", "");
        jobCountForCountry = jobCountForCountry.trim();
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_LIST_COUNT);
        String jobCountInList = baseActions.getText(TakeawayCareerPage.JOB_LIST_COUNT);
        Assert.assertTrue(jobCountForCountry.replace("jobs", "").trim().contains(jobCountInList.replace("jobs", "").trim()), "Search result does not matches the country count");
    }

    @Step("Validate search result has correct job category")
    public void validateSearchResultHasCorrectJobCategory(String jobCategory, String country) {
        baseActions.scrollToWebElement(TakeawayCareerPage.SEARCH_RESULT_AREA);
        HashSet<String> setOfJobCategories = new HashSet<>();
        HashSet<String> setOfLocation = new HashSet<>();
        int jobCountInResult = 0;
        for (int i = 1; i <= baseActions.getElementsCount(TakeawayCareerPage.SEARCH_RESULT_PAGES); i++) {
            List<String> listOfJobSearched = baseActions.getListOfAllTextFromElements(TakeawayCareerPage.LIST_OF_JOB_SEARCHED);
            for (int j = 1; j <= listOfJobSearched.size(); j++) {
                setOfJobCategories.add(getJobCategoryFromSearchedJob(TakeawayCareerPage.getJobCategoryFromSearchResult(j)));
                setOfLocation.add(getCountryNameFromSearchedJob(baseActions.getListOfAllTextFromElements(TakeawayCareerPage.getJobLocationFromSearchResult(j)).toString()));
                jobCountInResult += 1;
            }
            if (baseActions.getElementsCount(TakeawayCareerPage.NEXT_PAGE_IN_SEARCH_RESULT) == 0) {
                System.out.println("We are in last page of job search results");
            } else {
                baseActions.clickOnWebElement(TakeawayCareerPage.NEXT_PAGE_IN_SEARCH_RESULT);
                baseActions.waitForTimeout(1000);
            }
        }
        Assert.assertTrue(setOfJobCategories.size() == 1 && setOfJobCategories.toString().contains(jobCategory), "Different job Categories are shown in the result");
        Assert.assertTrue(setOfLocation.size() == 1 && setOfLocation.toString().contains(country), "Job from different countries are shown in the result");
        Assert.assertTrue(jobCountForCountry.contains(Integer.toString(jobCountInResult)), "Resulted job Count for the country do not match");
    }

    @Step("Click job category on widgets")
    public void clickJobCategoryOnWidget(String jobCategory) {
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_CATEGORY_WIDGET_HEADING);
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_CATEGORY_WIDGET(jobCategory));
        oldPageTitle = baseActions.getPageTitle();
        oldUrl = baseActions.getPageURL();
        baseActions.scrollToWebElement(TakeawayCareerPage.JOB_CATEGORY_WIDGET(jobCategory));
        baseActions.clickOnWebElement(TakeawayCareerPage.JOB_CATEGORY_WIDGET(jobCategory));
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
        baseActions.waitForElementToBeVisible(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        baseActions.scrollToWebElement(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        baseActions.clickOnWebElement(TakeawayCareerPage.CLEAR_ALL_FILTER);

        baseActions.waitForElementToBeVisible(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        baseActions.scrollToWebElement(TakeawayCareerPage.REFINE_YOUR_SEARCH);
        if (baseActions.getAttribute(TakeawayCareerPage.filterBy("Category"), "aria-expanded").contains("false")) {
            baseActions.clickOnWebElement(TakeawayCareerPage.filterBy("Category"));
        }
        boolean flag = baseActions.isElementChecked(TakeawayCareerPage.CHECKBOX_FOR_FILTER(jobCategory));
        Assert.assertFalse(flag, "Job category is selected after clicking clear all filter");
    }


}


