package org.redwind.autotest.osprey.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.redwind.autotest.osprey.config.DriverFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TakeawayCareerPage {

    @Autowired
    DriverFactory driverFactory;

    private Page page() {
        return driverFactory.getCurrentPage();
    }


    public Locator allowCookies() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Accept all"));
    }

    public Locator searchJobInputField() {
        return page().getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Search for job title"));
    }

    public Locator searchButton() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search").setExact(true));
    }

    public Locator searchResult() {
        return page().getByRole(AriaRole.REGION, new Page.GetByRoleOptions().setName("Search results").setExact(true));
    }

    public Locator searchResultHeading(String text) {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2)).getByText(text, new Locator.GetByTextOptions().setExact(false));
    }

    public Locator searchResultPages() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Page").setExact(false));
    }

    public Locator searchResultJobLocation() {
        return page().locator("[data-ph-at-id='job-location']");
    }

    public Locator searchResultJobCatagory() {
        return page().locator("[data-ph-at-id='job-category']");
    }

    public Locator nextPage() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Next").setExact(true));
    }

    public Locator refineYourSearch() {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2).setName("Refine your search").setExact(true));
    }

    public Locator clearAllFilter() {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear all filters"));
    }

    public Locator jobListCount() {
        return page().locator("[data-ph-at-id='search-page-top-job-count']");
    }

    public Locator jobCategoryWidgetHeading() {
        return page().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setLevel(2).setName("Explore all careers").setExact(true));
    }

    public Locator searchedJoblist() {
        return page().locator("[data-ph-at-id='jobs-list-item']");
    }

    public Locator filterHeading(String filterKey) {
        return page().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(filterKey).setExact(true));
    }

    public Locator filterValue(String value) {
        return page().getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(value).setExact(false));
    }

    public Locator jobCategoryDropDown(String value) {
        return page().getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(value).setExact(false));
    }

    public Locator jobCategoryWidget(String value) {
        return page().getByRole(AriaRole.LISTITEM, new Page.GetByRoleOptions().setName(value).setExact(false));
    }
}
