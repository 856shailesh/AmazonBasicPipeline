package com.sk.pages;

import com.sk.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private ElementUtil elementUtil;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    private By email = By.id("ap_email");
    private By continueBtn = By.id("continue");
    private By passwordField = By.id("ap_password");
    private By signInBtn = By.id("signInSubmit");

    @Step("Getting Login Page Title")
    public String getLoginPageTitle(){
        return driver.getTitle();
    }

    public void doLogin(String uName,String pwd){
        elementUtil.doSendKeys(email,uName);
        elementUtil.doClick(continueBtn);
        elementUtil.doSendKeys(passwordField,pwd);
        elementUtil.doClick(signInBtn);
    }
}
