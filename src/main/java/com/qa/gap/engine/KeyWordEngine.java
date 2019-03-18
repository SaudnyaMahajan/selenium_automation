package com.qa.gap.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.activation.ActivationException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.gap.base.BaseClass;

public class KeyWordEngine {
      
	public WebDriver driver;
	public Properties prop;
	
	public static Workbook workbook;
	public static Sheet sheet;
	
	public BaseClass base= new BaseClass();
	
	public WebElement we;
	
	WebDriverWait wait ; 
	
	
	public final String testScenarioFile_Path= "C:/GmailTest_workspace/Gmail_Automation_Project/src/main/java/com/qa/gap/testScenarios/keywords.xlsx";
	
	public void startExecution(String sheetName) throws ActivationException{
		String locatorName= null;
		String locatorValue= null;
		boolean result =false;
		 
		FileInputStream inputFile= null;
	
		try {
			inputFile= new FileInputStream(testScenarioFile_Path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			workbook=WorkbookFactory.create(inputFile);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet=workbook.getSheet(sheetName);
		
		int colNo=0;
		for(int rowNo=0;rowNo<sheet.getLastRowNum();rowNo++){
		try{
			String test_step=sheet.getRow(rowNo+1).getCell(colNo).toString();
			System.out.println("test_step "+ test_step);
			
			String locatorCol_Value=sheet.getRow(rowNo+1).getCell(colNo+1).toString().trim();
			
		
			if(!locatorCol_Value.equalsIgnoreCase("NA")){
				locatorName=locatorCol_Value.split(":-")[0].trim();
				locatorValue=locatorCol_Value.split(":-")[1].trim();
			}else{
				locatorName="NA";
			}
			
			String actions= sheet.getRow(rowNo+1).getCell(colNo+2).toString().trim();
			String value= sheet.getRow(rowNo+1).getCell(colNo+3).toString().trim();
			
			//switch (actions){
			if( actions.equalsIgnoreCase("openBrowser")){
				try {
					prop= base.intialiseProperties();
					if(value.isEmpty()|| value.equals("NA")){
						driver= base.initialiseBrowser(prop.getProperty("browserCh"));
					}else{
						driver=base.initialiseBrowser(value);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}else if(actions.equalsIgnoreCase("enter Url")){
				
            	if(value.isEmpty()||value.equals("NA")){
            		driver.get(prop.getProperty("url"));
            		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            	}else{
            		driver.get(value);
            	}		
			}else if(actions.equalsIgnoreCase("quit")){
				driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
				int Counter=0;
						do
						{
						try
						{
				
						if(driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf' and @name='password']")).isEnabled() && driver.findElement(By.xpath("//span[@class='RveJvd snByac' and contains(., 'Next')]")).isDisplayed() )
						{
						Counter=Counter+1;
						driver.close();
		            	driver.quit();
						break;
						}
						}
						catch(Exception ex)
						{
                          ex.printStackTrace();
                          throw new StaleElementReferenceException("Throwing stale element exception");
						}
					}
						while(Counter == 0);
				
			}
			
			
	if(locatorName.equalsIgnoreCase("id")){
			we=driver.findElement(By.id(locatorValue));
				if(actions.equalsIgnoreCase("sendKeys")){
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					we.clear();
					we.sendKeys(value);
				}else if(actions.equalsIgnoreCase("Click")){
				  try{
					we.click();
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
				  }catch(Exception e){
					  int Counter=0;
					do{
					   try{
						if(we.isEnabled()){
						Counter=Counter+1;
						we.click();
						break;
						}
					   }catch(Exception ex){
                     ex.printStackTrace();
                     throw new StaleElementReferenceException("Throwing stale element exception");
						}
					}while(Counter == 0);
				  }
				}
				locatorName=null;
	}else if(locatorName.equalsIgnoreCase("class")){
		we=driver.findElement(By.className(locatorValue));
		if(actions.equalsIgnoreCase("sendKeys")){
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			we.clear();
			we.sendKeys(value);
		}else if(actions.equalsIgnoreCase("Click")){
			we.click();
		}
		locatorName=null;
}
	else if(locatorName.equalsIgnoreCase("xpath")){
		
        	 we=driver.findElement(By.xpath(locatorValue));
        	 if(actions.equalsIgnoreCase("sendKeys")){
        		 try{
        		 driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        		 we.clear();
        		 //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);   
				 we.sendKeys(value);
        		 }catch(Exception e){
        			 try{
        			 wait= new WebDriverWait(driver, 30);
        			 wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(we)));
        			 we.clear();
        			 we.sendKeys(value);
        			 }catch(Exception ex){
                         ex.printStackTrace();
                        throw new StaleElementReferenceException("Throwing stale element exception");
  						}
        			 
        		 }
					
				}else if(actions.equalsIgnoreCase("Click")){
					we.click();
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				}else if(actions.equalsIgnoreCase("Check existance")&& test_step.equalsIgnoreCase("Verify successful login of user")){
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					try{
					result=we.isDisplayed();
					}catch(Exception e){
							   try{
								driver.navigate().refresh();
								result=we.isDisplayed();
								}catch(Exception ex){
		                     ex.printStackTrace();
		                     throw new StaleElementReferenceException("Throwing stale element exception");
								}
							}
					if(result){
						System.out.println("user logged in successfully");
						File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
						   File targetFile = new File("C:/GmailTest_workspace/Gmail_Automation_Project/src/main/java/com/qa/gap/screenshots" + "ScreenPrint.jpg");
						   FileUtils.copyFile(screenshot, targetFile);
					}else{
						System.out.println("error in login operation");
					}
				}else if(actions.equalsIgnoreCase("Check existance")&& test_step.equalsIgnoreCase("confirm email sent")){
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					result=we.isDisplayed();
					if(result){
						System.out.println("Message sent successfully");
					}else{
						System.out.println("error in snding Message");
					}
				}else if(actions.equalsIgnoreCase("Check existance")&& test_step.equalsIgnoreCase("Check mail from first user in inbox")){
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					result=we.isDisplayed();
					if(result){
						System.out.println("Mail recieved from first user");
					}else{
						driver.findElement(By.xpath("//div[@class='asa']/div[@class='asf T-I-J3 J-J5-Ji']")).click();
						boolean tempRes=we.isDisplayed();
						if(tempRes){
							System.out.println("Mail recieved from first user");
						}else{
						System.out.println("error in receiving Mail");
						}
					}
				}else if(actions.equalsIgnoreCase("Check existance")&& test_step.equalsIgnoreCase("Check subject of email")){
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					result=we.isDisplayed();
					if(result){
						System.out.println("subject matches with first users mail subject");
					}else{
						System.out.println("subject content doesnt match");
					}
				}else if(actions.equalsIgnoreCase("getText")){
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					String text=we.getText().trim();
					if(text.equals(value.trim())){
						System.out.println("Mail body matches");
					}else{
						System.out.println("Mail body doesnt match");
					}
				}
        	 locatorName=null;
	    }else if(locatorName.equalsIgnoreCase("name")){
				
        	 we=driver.findElement(By.name(locatorValue));
        	 if(actions.equalsIgnoreCase("sendKeys")){
					we.sendKeys(value);
				}else if(actions.equalsIgnoreCase("Click")){
					we.click();
				}
        	 locatorName=null;
			
			}else if(locatorName.equalsIgnoreCase("NA")){
				
	        	 locatorName=null;
				
				}
		}catch(Exception e){
			e.printStackTrace();
			
		}
			
			
			
		}

	
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
