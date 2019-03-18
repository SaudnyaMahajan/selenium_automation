package com.qa.gap.testExecution;

import java.rmi.activation.ActivationException;

import org.testng.annotations.Test;

import com.qa.gap.engine.KeyWordEngine;

public class GmailTest {
	
	public KeyWordEngine keyWordEngine;
	
	@Test
	public void gmailTest() throws ActivationException{
	try{	
		keyWordEngine= new KeyWordEngine();
		keyWordEngine.startExecution("TestData");
	}catch(ActivationException ae){
		ae.printStackTrace();
	}
		
	}
	
	
	
	
	
	
	
	

}
