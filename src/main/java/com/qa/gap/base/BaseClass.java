package com.qa.gap.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass {
    
	public WebDriver driver;
	public Properties prop;
	
	public WebDriver initialiseBrowser(String browserName){
		if(browserName.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "//C:/GmailTest_workspace/Gmail_Automation_Project/lib/chromedriver.exe");
			driver= new ChromeDriver();	
			driver.manage().window().maximize();
		}else{
			System.setProperty("webdriver.gecko.driver", "//C:/GmailTest_workspace/Gmail_Automation_Project/lib/geckodriver.exe");
			driver= new FirefoxDriver();
			driver.manage().window().maximize();
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
	
	
}
