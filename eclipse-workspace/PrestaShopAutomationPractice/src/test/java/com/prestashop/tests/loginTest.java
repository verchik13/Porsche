package com.prestashop.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class loginTest {

	WebDriver driver;
	Faker faker;
	String email;
	String password;
	String firstName;
	String lastName;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		driver.get("http://automationpractice.com");
		faker = new Faker();
	}

	@Test
	public void registration() throws InterruptedException {
		driver.findElement(By.cssSelector(".login")).click();
		email = faker.name().firstName() + faker.number().digit() + "@gmail.com";
		password = "123456";
		firstName = faker.name().firstName();
		lastName = faker.name().lastName();
		driver.findElement(By.cssSelector("#email_create")).sendKeys(email);
		driver.findElement(By.cssSelector("#SubmitCreate")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("#customer_firstname")).sendKeys(firstName);
		driver.findElement(By.cssSelector("#customer_lastname")).sendKeys(lastName);

		driver.findElement(By.cssSelector("#address1")).sendKeys("7925 Jones Branch Dr");
		driver.findElement(By.cssSelector("#city")).sendKeys(" McLean");
		driver.findElement(By.cssSelector("#id_state")).sendKeys("Virginia", Keys.ENTER);
		driver.findElement(By.cssSelector("#postcode")).sendKeys("22102");
		driver.findElement(By.cssSelector("#phone_mobile")).sendKeys("7034567654");
		driver.findElement(By.cssSelector("#passwd")).sendKeys(password);

		driver.findElement(By.cssSelector("#submitAccount")).click();
		driver.findElement(By.cssSelector(".logout")).click();
		driver.findElement(By.cssSelector("#email")).sendKeys(email);
		driver.findElement(By.cssSelector("#passwd")).sendKeys(password);
		driver.findElement(By.cssSelector("#SubmitLogin")).click();
		String expected = firstName + " " + lastName;
		String actual = driver.findElement(By.cssSelector(".account")).getText();
		Assert.assertEquals(actual, expected);

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}

}