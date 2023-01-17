package com.sk.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {
    public WebDriver driver;
    public Properties prop;
    public static String highlight;
    public OptionsManager optionsManager;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver init_driver(Properties prop) {
        String browserName = prop.getProperty("browser");
        System.out.println("Running on browser " + browserName);
        optionsManager = new OptionsManager(prop);
        highlight = prop.getProperty("highlight");

        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                init_remoteDriver("chrome");
            } else {
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            }
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            //driver = new FirefoxDriver();
            //driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
            //tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                init_remoteDriver("firefox");
            } else {
                tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            }
        } else
            System.out.println("Please pass the correct Browswer Name");
        getDriver().get(prop.getProperty("url"));
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public void init_remoteDriver(String browser) {
        System.out.println("Running test on Grid : " + browser);

        if (browser.equals("chrome")) {
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            //ChromeOptions cap = new ChromeOptions();
            cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
            cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                    UnexpectedAlertBehaviour.IGNORE);
            System.out.println("Got caps : " + cap);
            try {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (browser.equals("firefox")) {
            DesiredCapabilities cap = DesiredCapabilities.firefox();
            cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
            try {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public Properties init_prop() {
        Properties prop = new Properties();
        FileInputStream fp = null;
        String envName = System.getProperty("env");

        if (envName == null) {
            System.out.println("Running on Environmane on PROD env");
            try {
                fp = new FileInputStream("./src/test/resources/config/config.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Running on environment: " + envName);
            try {
                switch (envName) {
                    case "headless":
                        fp = new FileInputStream("./src/test/resources/config/headless.config.properties");
                        break;
                    case "firefox":
                        fp = new FileInputStream("./src/test/resources/config/firefox.config.properties");
                        break;
                    case "incognito":
                        fp = new FileInputStream("./src/test/resources/config/incognito.config.properties");
                        break;
                    default:
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            prop.load(fp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
