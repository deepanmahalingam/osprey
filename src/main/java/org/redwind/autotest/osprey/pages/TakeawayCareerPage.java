package org.redwind.autotest.osprey.pages;

public enum TakeawayCareerPage {

    ALLOW_COOKIES("//button[ppc-content[contains(text(),'Allow')]]"),
    DENY_COOKIES("//button[ppc-content[contains(text(),'Deny')]]"),
    SEARCH_JOB_INPUT_FIELD("//input[@id='typehead']"),
    SEARCH_JOB_LOCATION_FIELD("//input[@name='location']"),
    SEARCH_BUTTON("//button[@type='submit']");

    private final String locator;

    TakeawayCareerPage(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

}
