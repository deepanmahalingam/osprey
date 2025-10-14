package org.redwind.autotest.osprey.pages;

import org.springframework.stereotype.Component;

@Component
public class OsapiensCareerPage {
    public static final String ALLOW_COOKIES="//button[span[contains(text(),'Accept all')]]";
    public static final String VIEW_ALL_JOBS = "//span/a[contains(text(),'View Jobs')]";
    public static final String SEARCH_INPUT_BOX="//input[@id='search']";
    public static final String JOB_ITEMS="//div[contains(@class,'tbody')]/div";
    public static final String JOB_ITEMS_BODY="//div[contains(@class,'tbody')]";
    public static String getTitle(int index) {
        return "//div[contains(@class,'tbody')]/div[contains(@class,'group')]["+index+"]/div//a[1]";
    }
}
