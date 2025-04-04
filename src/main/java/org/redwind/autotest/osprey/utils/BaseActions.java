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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void openWebPage(String url) {
        page = driverFactory.getCurrentPage();
        page.navigate(url);
    }

    public void enterText(String selector, String value) {
        page = driverFactory.getCurrentPage();
        page.fill(selector, value);
    }

    public Boolean isElementPresent(String selector) {
        page = driverFactory.getCurrentPage();
        boolean flag = false;
        flag = page.isVisible(selector);
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

    public String getAttribute(String selector, String parameterName) {
        page = driverFactory.getCurrentPage();
        String value = "";
        value = page.getAttribute(selector, parameterName);
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

    public void refreshPage() {
        page = driverFactory.getCurrentPage();
        page.reload();
    }

    public Integer getElementsCount(String selector) {
        page = driverFactory.getCurrentPage();
        Locator locator = page.locator(selector);
        return locator.count();
    }

    public List<String> getListOfAllTextFromElements(String selector) {
        List<String> textFromElements;
        page = driverFactory.getCurrentPage();
        Locator locator = page.locator(selector);
        textFromElements = locator.allTextContents();
        return textFromElements;
    }

    public void waitForPageLoad() {
        page = driverFactory.getCurrentPage();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    public boolean isElementChecked(String selector) {
        page = driverFactory.getCurrentPage();
        return page.isChecked(selector);
    }

    public void waitForTimeout(double seconds) {
        page = driverFactory.getCurrentPage();
        page.waitForTimeout(seconds);
    }

    public void waitForElement(String selector) {
        page = driverFactory.getCurrentPage();
        page.waitForSelector(selector);
    }

    public void waitForElementToBeVisible(String selector) {
        page = driverFactory.getCurrentPage();
        page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public void waitForElementWithIncreasedTimeOut(String selector, double seconds) {
        page = driverFactory.getCurrentPage();
        page.locator(selector).waitFor(new Locator.WaitForOptions().setTimeout(seconds));
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

    public void takeScreenshot() throws IOException {
        page = driverFactory.getCurrentPage();
        byte[] screenshot = page.screenshot();
        Allure.addAttachment("Failure screenshot", new ByteArrayInputStream(screenshot));
        String currentTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String screenshotName = currentTimestamp + ".png";
        // Save the screenshot to the Screenshots folder.
        Path screenshotPath = Paths.get(System.getProperty("user.dir"), "screenshots").resolve(screenshotName);
        Files.write(screenshotPath, screenshot);
    }

}
