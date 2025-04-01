package org.redwind.autotest.osprey.dataProviders;

import org.testng.annotations.DataProvider;

public class DataHolder {

    @DataProvider(name = "jobTitleWithCountrySearch")
    protected Object[][] jobTitleSearch() {
        return new Object[][]{
                {"Test", "Netherlands"}
        };
    }

    @DataProvider(name = "jobCategoryDropDownWithCountryFilter")
    protected Object[][] jobCategorySearch() {
        return new Object[][]{
                {"Sales", "Germany"}
        };
    }

    @DataProvider(name = "jobCategoryWidgetsCheck")
    protected Object[][] jobWidgetSearch() {
        return new Object[][]{
                {"Corporate"},
                {"Customer Service"},
                {"Finance"},
                {"Human Resources"},
                {"Marketing"},
                {"Operations & Logistic"},
                {"Tech & Product"},
                {"Sales"}
        };
    }

}
