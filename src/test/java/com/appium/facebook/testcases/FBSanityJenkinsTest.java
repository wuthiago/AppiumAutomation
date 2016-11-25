package com.appium.facebook.testcases;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.appium.facebook.tasks.FacebookUtilJenkinsTasks;

public class FBSanityJenkinsTest {
	
	private FacebookUtilJenkinsTasks tasks = new FacebookUtilJenkinsTasks();

	@Before
	public void Setup(){
		try {
			tasks.setAppDirectory("C:\\Users\\IBM_ADMIN\\Downloads\\Facebook", "facebook-89.0.0.17.70-x86.apk");
			tasks.setCapabilities(System.getProperty("PlatformName"),System.getProperty("PlatformVersion"),"Android Emulator","com.facebook.katana","dbl.activity.FacebookLoginActivity");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void Test(){
		try {
			tasks.loginFB("tfcautomation01@gmail.com", "appiumautomation");
			tasks.publishPost("Posting via Appium");
			tasks.deletePost("Posting via Appium");
			tasks.logoutFB();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@After
	public void TearDown(){
		tasks.quitDriver();
	}
}
