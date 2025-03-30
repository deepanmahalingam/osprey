package org.redwind.autotest.osprey.config;

import com.microsoft.playwright.*;
import org.springframework.stereotype.Component;

@Component
public class DriverFactory {
    protected static ThreadLocal<Page> currentPage = new ThreadLocal<>();
    protected static ThreadLocal<BrowserContext> currentBrowserContext = new ThreadLocal<>();
    protected Page page = null;
    protected Playwright playwright = null;
    protected Browser browser = null;
    protected BrowserContext browserContext = null;

    public Page getCurrentPage() {
        page = currentPage.get();
        if (page != null) {
            return page;
        } else {
            return null;
        }
    }

    public BrowserContext getBrowserContext() {
        browserContext = currentBrowserContext.get();
        if (browserContext != null) {
            return browserContext;
        } else {
            return null;
        }
    }

    public Page getPage(String browserName, String headless) {
        boolean isHeadless = Boolean.parseBoolean(headless);
        playwright = Playwright.create();
        switch (browserName.toLowerCase()) {
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "edge":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(isHeadless));
                break;
            case "safari":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            default:
                throw new IllegalArgumentException("Please provide a valid browser");
        }
        browserContext = browser.newContext();
        currentBrowserContext.set(browserContext);
        return browserContext.newPage();
    }

    public void initializeBrowser(String browserName, String headless) {
        page = getPage(browserName, headless);
        currentPage.set(page);
    }

    public void closeBrowser() {
        currentBrowserContext.get().close();
        playwright.close();
    }

    public void setPage(Page page) {
        currentPage.set(page);
    }


}
