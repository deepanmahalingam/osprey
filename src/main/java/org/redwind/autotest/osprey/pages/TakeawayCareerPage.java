package org.redwind.autotest.osprey.pages;

import org.springframework.stereotype.Component;

@Component
public class TakeawayCareerPage {

    public static final String ALLOW_COOKIES = "//button[ppc-content[contains(text(),'Allow')]]";
    public static final String DENY_COOKIES = "//button[ppc-content[contains(text(),'Deny')]]";
    public static final String SEARCH_JOB_INPUT_FIELD = "//input[@id='typehead']";
    public static final String SEARCH_JOB_LOCATION_FIELD = "//input[@name='location']";
    public static final String SEARCH_BUTTON = "//button[@type='submit']";
    public static final String SEARCH_RESULT_AREA = "//div[contains(@class,'ph-search-results-area')]";
    public static final String SEARCH_KEYWORD_HEADING = "//h2[@show.bind='searchKeyword']";
    public static final String LIST_OF_JOB_SEARCHED = "//div[contains(@class,'phs-jobs-list')]/div[contains(@class,'content-block')]/ul/li";
    public static final String SEARCH_RESULT_PAGES = "//div[contains(@class,'search-bottom-count')]/following-sibling::ul/li/a[contains(@aria-label,'Page')]";
    public static final String JOB_LOCATION_FROM_SEARCH_RESULT = "//span[contains(@class,'job-location')]";
    public static final String NEXT_PAGE_IN_SEARCH_RESULT = "//li[a[contains(@aria-label,'Page') and @aria-current='true']]/following-sibling::li[1]/a[contains(@aria-label,'Page')]";
    public static final String REFINE_YOUR_SEARCH = "//h2//*[contains(text(),'Refine your search')]";
    public static final String CLEAR_ALL_FILTER = "//a[contains(@class,'clearall')]";
    public static final String JOB_LIST_COUNT = "//div[contains(@class,'jobs-list-count')]";
    public static final String JOB_CATEGORY_WIDGET_HEADING = "//h2//*[contains(text(),'find your fit')]";

    public static String filterBy(String filterKey) {
        String replaceXpath = "//button[contains(text(),'" + filterKey + "')]";
        return replaceXpath;
    }

    public static String FILTER_BY_VALUE(String name) {
        String replaceXpath = "//label[span[span[contains(text(),'" + name + "')]]]";
        return replaceXpath;
    }

    public static final String CHECKBOX_FOR_FILTER(String filterName) {
        String replaceXpath = "//span[span[contains(text(),'" + filterName + "')]]/preceding-sibling::input";
        return replaceXpath;
    }

    public static final String JOB_CATEGORY_DROPDOWN(String value) {
        String replaceXpath = "//a[span[contains(text(),'" + value + "')]]";
        return replaceXpath;
    }

    public static final String JOB_CATEGORY_DROPDOWN_COUNT(String value) {
        String replaceXpath = "//a[span[contains(text(),'" + value + "')]]/span[contains(@class,'count')]";
        return replaceXpath;
    }

    public static final String JOB_COUNT_IN_FILTER(String value) {
        String replaceXpath = "//span[contains(text(),'" + value + "')]/following-sibling::span[contains(@class,'result-jobs-count')]";
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
        String replaceXpath = "//div[div[div[ppc-container[span[ppc-content[div[font[span[text()='" + value + "']]]]]]]]]/preceding-sibling::a";
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
