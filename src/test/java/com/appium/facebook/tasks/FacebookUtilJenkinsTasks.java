package com.appium.facebook.tasks;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class FacebookUtilJenkinsTasks {

	File appDirectory;
	File appFile;
	DesiredCapabilities capabilities;
	AndroidDriver<AndroidElement> driver; 
	TouchAction action1;
	
	//Método para setar o diretório do arquivo apk
	public void setAppDirectory(String directory, String appName){
		appDirectory = new File(directory);
		appFile = new File(appDirectory, appName);
	}	
	
	//Método para setar as capabilities para instalação/inicializaçao do apk
	public void setCapabilities(String platform, String version, String device, String appPackage, String activity) throws MalformedURLException{
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("platformVersion", version);
		capabilities.setCapability("deviceName", device);
		capabilities.setCapability("app", appFile.getAbsolutePath());
		//capabilities.setCapability("app", "C:\\Users\\IBM_ADMIN\\Downloads\\Facebook\\facebook-103.0.0.20.72-x86.apk");
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", activity);
		driver = new AndroidDriver<AndroidElement>(new URL ("http://127.0.0.1:4723/wd/hub"), capabilities);
		//WebDriver driver = new RemoteWebDriver(new URL ("http://127.0.0.1:4723/wd/hub"), capabilities);
		//AndroidDriver driver = new AndroidDriver(new URL ("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
	
	//Método para fazer Login no Facebook
	public void loginFB(String username, String password) throws InterruptedException{
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_username\")").sendKeys(username);
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_password\")").click();
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_password\")").sendKeys(password);
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_login\").text(\"LOG IN\")").click();
		//Verificando pelo texto What's on your mind? para ter a certeza que o home screen do Facebook foi mostrado
		Assert.assertEquals("What's on your mind?", driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/feed_composer_hint\").text(\"What's on your mind?\")").getText());
		Thread.sleep(10000);
	}
	
	//Método para publicar um Post no Facebook
	public void publishPost(String content) throws InterruptedException{
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/feed_composer_status_button\").text(\"STATUS\")").click();
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/status_text\")").sendKeys(content);
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/primary_named_button\").text(\"POST\")").click();
		// Verificando o post publicado
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/feed_story_message\").description(\"" + content + "\")").isDisplayed());
		Thread.sleep(10000);
	}
	
	//Métodos para deletar um post no Facebook
	public void deletePost(String content) throws InterruptedException{
		if (driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/feed_story_message\").description(\"" + content + "\")").isDisplayed()) {
			action1 = new TouchAction((MobileDriver<AndroidElement>)driver).press(1020,835).release().perform();
			Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/bottomsheet_list_item_title\").text(\"Delete\")").click();
			Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/button1\").text(\"Delete\")").click();
			Thread.sleep(10000);
		}
	}
	
	//Método para fazer Log Out no Facebook
	public void logoutFB(){
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/bookmarks_tab\").description(\"More\")").click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().descriptionContains(\"Log Out\").instance(0))").click();
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/button1\").text(\"Log Out\")").click();
		Assert.assertTrue(driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_login\").text(\"LOG IN\")").isDisplayed());
	}
	
	//Método para finalizar Driver
	public void quitDriver(){
		if (driver != null) {
			driver.quit();
		}
	}
}
