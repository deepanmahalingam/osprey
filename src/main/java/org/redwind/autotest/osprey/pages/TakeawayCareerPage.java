package org.redwind.autotest.osprey.pages;

public enum TakeawayCareerPage {

    ALLOW_COOKIES("//button[ppc-content[contains(text(),'Allow')]]"),
    DENY_COOKIES("//button[ppc-content[contains(text(),'Deny')]]"),
    SEARCH_JOB_INPUT_FIELD("//input[@id='typehead']"),
    SEARCH_JOB_LOCATION_FIELD("//input[@name='location']"),
    SEARCH_BUTTON("//button[@type='submit']"),
    SEARCH_RESULT_AREA("//div[contains(@class,'ph-search-results-area')]"),
    SEARCH_KEYWORD_HEADING("//h2[@show.bind='searchKeyword']");
    private final String locator;

    TakeawayCareerPage(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

}
