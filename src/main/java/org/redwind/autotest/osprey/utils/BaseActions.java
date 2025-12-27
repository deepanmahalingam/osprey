package org.redwind.autotest.osprey.utils;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Allure;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Component
public class BaseActions {
    @Autowired
    DriverFactory driverFactory;
    Page page;
    BrowserContext browserContext;

    public void clickOnWebElement(String selector) {
        page = driverFactory.getCurrentPage();
        page.click(selector);
    }

    public void clickOnWebElement(Locator locator) {
        locator.click();
    }

    public void openWebPage(String url) {
        page = driverFactory.getCurrentPage();
        page.navigate(url);
    }

    public void enterText(String selector, String value) {
        page = driverFactory.getCurrentPage();
        page.fill(selector, value);
    }

    public void enterText(Locator locator, String value) {
        locator.fill(value);
    }

    public void scrollToWebElement(String selector) {
        page = driverFactory.getCurrentPage();
        Locator locator = page.locator(selector);
        locator.scrollIntoViewIfNeeded();
    }

    public void scrollToWebElement(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    public String getText(String selector) {
        page = driverFactory.getCurrentPage();
        String text = "";
        text = page.textContent(selector);
        return text;
    }

    public String getText(Locator locator) {
        String text = "";
        text = locator.textContent();
        return text;
    }


    public String getAttribute(Locator locator, String parameterName) {
        String value = "";
        value = locator.getAttribute(parameterName);
        return value;
    }

    public String getPageTitle() {
        page = driverFactory.getCurrentPage();
        return page.title();
    }

    public String getPageURL() {
        page = driverFactory.getCurrentPage();
        return page.url();
    }


    public Integer getElementsCount(String selector) {
        page = driverFactory.getCurrentPage();
        Locator locator = page.locator(selector);
        return locator.count();
    }

    public Integer getElementsCount(Locator locator) {
        return locator.count();
    }


    public List<String> getListOfAllTextFromElements(Locator locator) {
        List<String> textFromElements;
        textFromElements = locator.allTextContents();
        return textFromElements;
    }


    public void waitForPageLoad() {
        page = driverFactory.getCurrentPage();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }


    public boolean isElementChecked(Locator locator) {
        return locator.isChecked();
    }


    public void waitForTimeout(double seconds) {
        page = driverFactory.getCurrentPage();
        page.waitForTimeout(seconds);
    }


    public void waitForElement(Locator locator) {
        locator.waitFor();
    }


    public void waitForElementToBeVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }


    public void switchToNewTab() {
        browserContext = driverFactory.getBrowserContext();
        List<Page> pages = browserContext.pages();
        if (!pages.isEmpty()) {
            driverFactory.setPage(pages.get(pages.size() - 1));
        } else {
            System.out.println("No new tab is opened");
        }
        page = driverFactory.getCurrentPage();
        page.bringToFront();
    }

    public void closeTheTab() {
        page = driverFactory.getCurrentPage();
        page.close();
    }

    public void getToFirstTab() {
        browserContext = driverFactory.getBrowserContext();
        List<Page> pages = browserContext.pages();
        driverFactory.setPage(pages.get(0));
        page = driverFactory.getCurrentPage();
        page.bringToFront();
    }

    public void takeFullPageScreenshot() throws IOException {
        page = driverFactory.getCurrentPage();
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment("Failure screenshot for full screen", new ByteArrayInputStream(screenshot));
        String screenshotName = Instant.now() + ".png";
        // Save the screenshot to the Screenshots folder.
        Path screenshotPath = Paths.get(System.getProperty("user.dir"), "screenshots").resolve(screenshotName);
        Files.write(screenshotPath, screenshot);
    }


    public void waitForTableStablizer(String selector) {
        page = driverFactory.getCurrentPage();
        Locator tableRows = page.locator(selector);
        String previousHTML = "";
        String currentHTML = "";
        long startTime = System.currentTimeMillis();

        do {
            previousHTML = tableRows.innerHTML();
            page.waitForTimeout(500); // wait half a second
            currentHTML = tableRows.innerHTML();
        } while (!previousHTML.equals(currentHTML)
                && (System.currentTimeMillis() - startTime) < 3000);
    }

}
