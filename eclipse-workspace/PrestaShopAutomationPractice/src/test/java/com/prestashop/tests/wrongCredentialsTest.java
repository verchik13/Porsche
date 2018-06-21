package com.prestashop.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class wrongCredentialsTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		driver.get("http://automationpractice.com");
	}

	@Test
	public void wrongLogin() {
		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("dfhg@gail.com");
		driver.findElement(By.cssSelector("#passwd")).sendKeys("45673");
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Assert.assertTrue(driver.getPageSource().contains("Authentication failed"));

	}

	@Test
	public void invalidEmailTest() {
		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("fshjk");
		driver.findElement(By.cssSelector("#passwd")).sendKeys("3546");
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Assert.assertTrue(driver.getPageSource().contains("Invalid email address"));

	}

	@Test
	public void blankEmailTest() {
		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("");
		driver.findElement(By.cssSelector("#passwd")).sendKeys("45656");
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Assert.assertTrue(driver.getPageSource().contains("An email address required"));
	}

	@Test
	public void blanckPassword() {
		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys("ergf45@mai.com");
		driver.findElement(By.cssSelector("#passwd")).sendKeys("");
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		Assert.assertTrue(driver.getPageSource().contains("Password is required"));

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}
