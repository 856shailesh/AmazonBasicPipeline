package com.sk.test;

import com.sk.utils.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Epic("Epic 103 : Test Login Page")
@Story("US 1 : Login into app")
public class LoginPageTest extends BaseTest{
    @BeforeTest
    public void loginPageSetup(){
        loginPage = homePage.doSignIn();
    }

    @Description("Login Page URL Test")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void loginPageUrlTest() {
        Assert.assertEquals(loginPage.getLoginPageTitle(), Constants.LOGIN_PAGE_TITLE);
        System.out.println("Login Page Url Test Passed");
    }
    @Description("Enter Credentials Test")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void enterCredentials() throws InterruptedException {
        loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        Thread.sleep(1000);
        System.out.println("Entered Credentials , TC Passed");
    }
}
