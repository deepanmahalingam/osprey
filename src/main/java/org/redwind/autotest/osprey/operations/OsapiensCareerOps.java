package org.redwind.autotest.osprey.operations;

import org.redwind.autotest.osprey.pages.OsapiensCareerPage;
import org.redwind.autotest.osprey.utils.BaseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OsapiensCareerOps {

    @Autowired
    BaseActions baseActions;

    @Autowired
    OsapiensCareerPage osapiensCareerPage;

    private Integer jobCount;
    // Prints the number of open jobs
    // Checks and fails the test if none of the job titles contains “Quality”

    public Integer getTotalJobsCountInCareerPage() {
        jobCount = baseActions.getElementsCount(OsapiensCareerPage.JOB_ITEMS);
        System.out.println("List of open Jobs "+ jobCount);
        return jobCount;
    }

    public Integer checkForJobsByKeyword(String keyword) {
        ArrayList<String> listOfJobTitles = new ArrayList<>();
        int countOfTitlesHaveTitleAsQuality = 0;
        int totalJobs = getTotalJobsCountInCareerPage();
        for(int i=1;i<=totalJobs;i++) {
            listOfJobTitles.add(baseActions.getText(OsapiensCareerPage.getTitle(i)));
            if(baseActions.getText(OsapiensCareerPage.getTitle(i)).contains(keyword)) {
                countOfTitlesHaveTitleAsQuality+=1;
            }
        }
        System.out.println("Count of jobs with Quality title "+ countOfTitlesHaveTitleAsQuality);
        return  countOfTitlesHaveTitleAsQuality;
    }

    public Integer checkForJobTitleByKeywordUsingSearchBox(String keyword) {
        baseActions.scrollToWebElement(OsapiensCareerPage.SEARCH_INPUT_BOX);
        baseActions.clickOnWebElement(OsapiensCareerPage.SEARCH_INPUT_BOX);
        baseActions.enterText(OsapiensCareerPage.SEARCH_INPUT_BOX, keyword);
        baseActions.waitForTableStablizer(OsapiensCareerPage.JOB_ITEMS_BODY);
        int totalSearchedJobCount = getTotalJobsCountInCareerPage();
        System.out.println("Found jobs for keyword "+ keyword.toUpperCase() +" ===> "+ totalSearchedJobCount);
        for(int i=1;i<=totalSearchedJobCount;i++) {
            System.out.println(baseActions.getText(OsapiensCareerPage.getTitle(i)));
        }
        return totalSearchedJobCount;
    }
}
