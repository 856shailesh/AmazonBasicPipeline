package com.sk.test;

import com.sk.factory.DriverFactory;
import com.sk.pages.HomePage;
import com.sk.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    Properties prop;
    DriverFactory driverFactory;
    HomePage homePage;
    LoginPage loginPage;

    @BeforeTest
    public void setUp() {
        driverFactory = new DriverFactory();
        prop = driverFactory.init_prop();
        driver = driverFactory.init_driver(prop);
        homePage = new HomePage(driver);
    }
    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
