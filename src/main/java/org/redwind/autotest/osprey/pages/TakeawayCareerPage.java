package org.redwind.autotest.osprey.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TakeawayCareerPage {

    @Autowired
    DriverFactory driverFactory;

    private Page page() {
        return driverFactory.getCurrentPage();
    }


    public Locator allowCookies() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept all"));
    }

    public Locator searchJobInputField() {
        return page().getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Search for job title"));
    }

    public Locator searchButton() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search").setExact(true));
    }

    public Locator searchResult() {
        return page().getByRole(AriaRole.REGION, new Page.GetByRoleOptions().setName("Search results").setExact(true));
    }

    public Locator searchResultHeading(String text) {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2)).getByText(text, new Locator.GetByTextOptions().setExact(false));
    }

    public Locator searchResultPages() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Page").setExact(false));
    }

    public Locator searchResultJobLocation() {
        return page().locator("[data-ph-at-id='job-location']");
    }

    public Locator searchResultJobCatagory() {
        return page().locator("[data-ph-at-id='job-category']");
    }

    public Locator nextPage() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next").setExact(true));
    }

    public Locator refineYourSearch() {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2).setName("Refine your search").setExact(true));
    }

    public Locator clearAllFilter() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear all filters"));
    }

    public Locator jobListCount() {
        return page().locator("[data-ph-at-id='search-page-top-job-count']");
    }

    public Locator jobCategoryWidgetHeading() {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2).setName("Explore all careers").setExact(true));
    }

    public Locator searchedJoblist() {
        return page().getByRole(AriaRole.LISTITEM);
    }

    public Locator filterHeading(String filterKey) {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(filterKey).setExact(true));
    }

    public Locator filterValue(String value) {
        return page().getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(value).setExact(false));
    }

    public Locator jobCategoryDropDown(String value) {
        return page().getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(value).setExact(false));
    }

    public static final String LIST_OF_JOB_SEARCHED = "//div[contains(@class,'phs-jobs-list')]/div[contains(@class,'content-block')]/ul/li";
    public static final String SEARCH_RESULT_PAGES = "//div[contains(@aria-label,'pagination')]/ul/li/a[contains(@aria-label,'Page')]";
    public static final String JOB_LOCATION_FROM_SEARCH_RESULT = "//*[contains(@class,'job-location')]";
    public static final String NEXT_PAGE_IN_SEARCH_RESULT = "//li[a[contains(@aria-label,'Page') and @aria-current='true']]/following-sibling::li[1]/a[contains(@aria-label,'Page')]";
    public static final String REFINE_YOUR_SEARCH = "//h2[contains(text(),'Refine your search')]";
    public static final String CLEAR_ALL_FILTER = "//a[contains(@class,'clearall')]";
    public static final String JOB_LIST_COUNT = "//div[contains(@class,'jobs-list-count')]";
    public static final String JOB_CATEGORY_WIDGET_HEADING = "//h2[contains(text(),'Explore all careers')]";

    public static String filterBy(String filterKey) {
        String replaceXpath = "//button[span[contains(text(),'" + filterKey + "')]]";
        return replaceXpath;
    }

    public static String FILTER_BY_VALUE(String name) {
        String replaceXpath = "//label[contains(text(),'" + name + "')]";
        return replaceXpath;
    }

    public static final String CHECKBOX_FOR_FILTER(String filterName) {
        String replaceXpath = "//label[contains(text(),'" + filterName + "')]/preceding-sibling::input";
        return replaceXpath;
    }

    public static final String JOB_CATEGORY_DROPDOWN(String value) {
        String replaceXpath = "//span[span[contains(text(),'" + value + "')]]";
        return replaceXpath;
    }

    public static final String JOB_CATEGORY_DROPDOWN_COUNT(String value) {
        String replaceXpath = "//span[span[contains(text(),'" + value + "')]]/span[2]";
        return replaceXpath;
    }

    public static final String JOB_COUNT_IN_FILTER(String value) {
        String replaceXpath = "//label[contains(text(),'" + value + "')]/span/span[1]";
        return replaceXpath;
    }

    public static final String getJobCategoryFromSearchResult(int index) {
        String replaceXpath = "//li[" + index + "]//span[@class='job-category au-target']";
        return replaceXpath;
    }

    public static final String getJobLocationFromSearchResult(int index) {
        String replaceXpath = "//li[" + index + "]//span[contains(@class,'job-location')]";
        return replaceXpath;
    }

    public static final String JOB_CATEGORY_WIDGET(String value) {
        String replaceXpath = "//a[span[text()='" + value + "']]";
        return replaceXpath;
    }
//    public final String locator;
//
//    TakeawayCareerPage(String locator) {
//        this.locator = locator;
//    }
//
//    public String getLocator() {
//        return locator;
//    }

}
