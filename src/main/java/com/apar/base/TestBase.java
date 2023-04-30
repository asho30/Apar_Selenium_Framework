package com.apar.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.apar.utils.TestUtil;
import com.apar.utils.WebEventListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;
    public static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static Logs logs;
    public static LogEntries logEntries;
    public static PrintWriter writer;
    public static ExtentReports extent;
    public static ExtentTest logger;

    public TestBase() {
	try {

	    prop = new Properties();
	    InputStream stream = new FileInputStream("src/main/java/com/apar/config/config.properties");
	    prop.load(stream);

	} catch (FileNotFoundException e) {

	    e.printStackTrace();

	} catch (IOException e) {

	    e.printStackTrace();
	}

    }

    public static void initialization(String url) {

	String browserName = prop.getProperty("browser");

	if (browserName.equals("chrome")) {

	    WebDriverManager.chromedriver().setup();
	    driver = new ChromeDriver();

	} else if (browserName.equals("firefox")) {

	    WebDriverManager.firefoxdriver().setup();
	    driver = new FirefoxDriver();

	} else if (browserName.equals("ie")) {

	    WebDriverManager.iedriver().setup();
	    driver = new InternetExplorerDriver();

	} else if (browserName.equals("edge")) {

	    WebDriverManager.iedriver().setup();
	    driver = new EdgeDriver();
	}

	e_driver = new EventFiringWebDriver(driver);
	eventListener = new com.apar.utils.WebEventListener();
	e_driver.register(eventListener);
	driver = e_driver;

	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //2
	driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	driver.get(url);

    }

}
