package com.sk.test;

import com.sk.utils.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest{
    @Description("Home Page Test")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void HomePageTitleTest(){
        String title = homePage.getHomePageTitle();
        Assert.assertEquals(title, Constants.HOME_PAGE_TITLE);
        System.out.println("Home Page Title Test Pass");
    }
}
