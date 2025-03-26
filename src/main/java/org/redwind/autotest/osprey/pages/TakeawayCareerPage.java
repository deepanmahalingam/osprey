package org.redwind.autotest.osprey.pages;

public enum TakeawayCareerPage {

    ALLOW_COOKIES("//button[ppc-content[contains(text(),'Allow')]]"),
    DENY_COOKIES("//button[ppc-content[contains(text(),'Deny')]]");

    private final String locator;

    TakeawayCareerPage(String locator) {
        this.locator = locator;
    }

    public String getLocator() {
        return locator;
    }

}
