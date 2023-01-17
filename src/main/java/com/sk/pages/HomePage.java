package com.sk.pages;

import com.sk.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;
    private ElementUtil elementUtil;
    public HomePage(WebDriver driver){
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }
    private By SignIn = By.xpath("//span[@id='nav-link-accountList-nav-line-1']");

    public String getHomePageTitle(){
        return driver.getTitle();
    }

    public LoginPage doSignIn(){
        elementUtil.doClick(SignIn);
        return new LoginPage(driver);
    }

}
