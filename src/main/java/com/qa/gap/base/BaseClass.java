package com.qa.gap.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//import com.qa.gap.logs.LoggerClass;

public class BaseClass {
    
	public WebDriver driver;
	public Properties prop;
	
	//LoggerClass loggerClass;
	Logger logger= Logger.getLogger("BaseClass");
	
	public WebDriver initialiseBrowser(String browserName){
		if(browserName.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "//C:/GmailTest_workspace/Gmail_Automation_Project/lib/chromedriver.exe");
			driver= new ChromeDriver();	
			logger.info("opening Chrome browser");
			driver.manage().window().maximize();
			logger.info("Maximising window");
		}else{
			System.setProperty("webdriver.gecko.driver", "//C:/GmailTest_workspace/Gmail_Automation_Project/lib/geckodriver.exe");
			driver= new FirefoxDriver();
			logger.info("opening Firefox browser");
			driver.manage().window().maximize();
			logger.info("Maximising window");
		}
		return driver;
	}
	
  public Properties intialiseProperties() throws FileNotFoundException{
	   prop= new Properties();
	   try{
	   FileInputStream ip= new FileInputStream("C:/GmailTest_workspace/Gmail_Automation_Project/src/main/java/com/qa/gap/config/config.properties");
	   prop.load(ip);
	   }catch(FileNotFoundException ae){
		    ae.printStackTrace();
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return prop;
	   
   }
	
  private void PropertyConfigurat() {
        PropertyConfigurator.configure("log4j.properties");
	}
}
