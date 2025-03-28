package org.redwind.autotest.osprey.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseActions {
    @Autowired
    DriverFactory driverFactory;
    Page page;

    public void clickOnWebElement(String selector) {
        page = driverFactory.getCurrentPage();
        page.click(selector);
    }

    public void openWebPage(String url) {
        page = driverFactory.getCurrentPage();
        page.navigate(url);
    }

    public void enterText(String selector, String value) {
        page = driverFactory.getCurrentPage();
        page.fill(selector,value);
    }

    public Boolean isElementPresent(String selector) {
        page = driverFactory.getCurrentPage();
        boolean flag = false;
        flag=page.isVisible(selector);
        return flag;
    }

    public void scrollToWebElement(String selector) {
        page = driverFactory.getCurrentPage();
        Locator locator = page.locator(selector);
        locator.scrollIntoViewIfNeeded();
    }

    public String getText(String selector) {
        page = driverFactory.getCurrentPage();
        String text = "";
        text = page.textContent(selector);
        return text;
    }

}
