package org.redwind.autotest.osprey.test;

import io.qameta.allure.Description;
import org.redwind.autotest.osprey.core.BaseTest;
import org.redwind.autotest.osprey.operations.OsapiensCareerOps;
import org.redwind.autotest.osprey.pages.OsapiensCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest
public class OsapiensCareerTest extends BaseTest {

    @Autowired
    BaseActions baseActions;

    @Autowired
    OsapiensCareerOps osapiensCareerOps;

    @Value("${env.osapiens-career-url}")
    String osapiensCareerPage;

    @BeforeMethod(alwaysRun = true)
    public void testEntry() {
        baseActions.openWebPage(osapiensCareerPage);
        baseActions.clickOnWebElement(OsapiensCareerPage.ALLOW_COOKIES);
    }

    @Test
    @Description("Verify osapiens career page have open jobs for quality")
    public void verifyOsapiensCareerPageHasOpenJobsForQuality() {
        baseActions.clickOnWebElement(OsapiensCareerPage.VIEW_ALL_JOBS);
        int jobCount = osapiensCareerOps.checkForJobsByKeyword("Quality");
        if(jobCount == 0){
            Assert.fail("No Jobs is open with Quality keyword");
        } else {
            System.out.println("Total open jobs with Quality title is "+ jobCount);
        }
    }

    @Test
    @Description("Verify osapiens career page have open jobs for quality from search box")
    public void verifyOsapiensCarrerPageHasOpenJobsForQualityFromSearchBox(){
        baseActions.clickOnWebElement(OsapiensCareerPage.VIEW_ALL_JOBS);
        int jobCount = osapiensCareerOps.checkForJobTitleByKeywordUsingSearchBox("Quality");
        if(jobCount == 0){
            Assert.fail("No Jobs is open with Quality keyword");
        } else {
            System.out.println("Total open jobs with Quality title is "+ jobCount);
        }
    }

}
