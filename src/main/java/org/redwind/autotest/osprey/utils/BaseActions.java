package org.redwind.autotest.osprey.utils;

import com.microsoft.playwright.Page;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseActions {
    @Autowired
    DriverFactory driverFactory;
    Page page;

    public void clickOnWebElement(String locator) {
        page = driverFactory.getCurrentPage();
        page.click(locator);
    }

    public void openWebPage(String url) {
        page = driverFactory.getCurrentPage();
        page.navigate(url);
    }

}
