package org.redwind.autotest.osprey.test;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.redwind.autotest.osprey.core.BaseTest;
import org.testng.annotations.Test;

@Epic("Example Testing")
@Feature("Portfolio Test features")
public class Example extends BaseTest {

    @Test(description = "Sample Test Execution")
    public void sampleTest() {
        Allure.step("Opened the webpage");
        System.out.println("Sample Test is executing");
    }
}
