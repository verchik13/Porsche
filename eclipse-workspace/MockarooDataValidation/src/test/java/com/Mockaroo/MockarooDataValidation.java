package com.Mockaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;
	WebDriverWait wait;
	List<String> Cities;
	List<String> Countries;
	Set<String> citiesSet;
	Set<String> countriesSet;
	
	static int numberOfField = 6;
	static String MOCK_DATAPath;


	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://mockaroo.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	
	@Test(priority = 1)
	public void MockarooDataValidationTest() throws InterruptedException {
		assertEquals(driver.getTitle(),
				"Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel");
		assertEquals(driver.findElement(By.xpath("//div[@class='brand']")).getText(), "mockaroo");
		assertEquals(driver.findElement(By.xpath("//div[@class='tagline']")).getText(), "realistic data generator");
		remoteAllExistingFields();
		assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-name']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-options']")).isDisplayed());
		assertTrue(driver.findElement(By.xpath("//a[.='Add another field']")).isEnabled());
		assertEquals(driver.findElement(By.id("num_rows")).getAttribute("value"), "1000");
		assertTrue(driver.findElement(By.xpath("//option[@value='csv']")).isSelected());
		assertTrue(driver.findElement(By.xpath("//option[@value='unix']")).isSelected());
		assertTrue(driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected());
		assertFalse(driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected());
		addAnotherField("City");
		addAnotherField("Country");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[.='Download Data']")).click();
	}

	@Test (priority = 2)
	public void ReadAllDataAndWorkWithCollectionTest() throws InterruptedException, IOException {
		
		Thread.sleep(5000);
		MOCK_DATAPath = "/Users/sofiianalizhyta/Downloads/MOCK_DATA.csv";
		readFileAndVerify();
		Collections.sort(Cities);
		Collections.sort(Countries);
		System.out.println( "\nThe Longest City is : " + finedTheLongestName(Cities) );
		System.out.println( "The Longest Country is : " + finedTheLongestName(Countries) );
		System.out.println( "\nThe Shortest City is : " + finedTheShortestName(Cities) );
		System.out.println( "The Shortest Country is : " + finedTheShortestName(Countries) +"\n" );
		findTimesEachStringIsMentionedPrint(Countries);
		readFileInHashSet();
		verifyCountInSetAndArrey(Cities,citiesSet);
		verifyCountInSetAndArrey(Countries,countriesSet);		
	}
	
	public void verifyCountInSetAndArrey(List<String> listNames, Set<String> setNames) {
		Set<String> tempSet = new HashSet <>(listNames);
		List<String> listUniqueNames = new ArrayList<>(tempSet);
		
		assertEquals(listUniqueNames.size(), setNames.size());	
	}
	
	public void findTimesEachStringIsMentionedPrint(List <String> arrName) {
		Set<String> arrNameSet = new HashSet <>(arrName);
		List<String> arrUniqueNames = new ArrayList<>(arrNameSet);
		Collections.sort(arrUniqueNames);
		
		for(int i=0; i < arrUniqueNames.size(); i++) {
			int times = 0;
			times = Collections.frequency(arrName, arrUniqueNames.get(i));
			System.out.println(arrUniqueNames.get(i) + " is mentioned " + times);
		}
		System.out.println();
	}
	
	public String finedTheLongestName(List <String> arrName ) {
		String temp = arrName.get(0);
		for(String each : arrName) {
			if(temp.length() < each.length() ) {
				temp = each;
			}
		}
		return temp;
	}
	
	public String finedTheShortestName(List <String> arrName ) {
		String temp = arrName.get(0);
		for(String each : arrName) {
			if(temp.length() > each.length() ) {
				temp = each;
			}
		}
		return temp;
	}
	
	
	
//	Set<String> citiesSet;
//	Set<String> countriesSet;
	public void readFileInHashSet() throws IOException {
		FileReader fileR = new FileReader(MOCK_DATAPath);
		BufferedReader buffR = new BufferedReader(fileR);
		citiesSet = new HashSet<>();
		countriesSet = new HashSet<>();
		String line = "";
		while ((line = buffR.readLine()) != null) {
			String tempWithComma = line;
			String tempOutComma[] = tempWithComma.split(",");
			citiesSet.add(tempOutComma[0]);
			countriesSet.add(tempOutComma[1]);
		}
	}

	// 18 - 21
	public void readFileAndVerify() throws IOException {

		FileReader fileR = new FileReader(MOCK_DATAPath);
		BufferedReader buffR = new BufferedReader(fileR);

		Cities = new ArrayList<>();
		Countries = new ArrayList<>();

		int count = -1; // because of "City" and "Countries" words
		String line = "";
		while ((line = buffR.readLine()) != null) {
			String tempWithComma = line;
			String tempOutComma[] = tempWithComma.split(",");
			Cities.add(tempOutComma[0]);
			Countries.add(tempOutComma[1]);
			count++;
		}
		assertEquals(count, 1000);
		assertTrue(Cities.get(0).equalsIgnoreCase("City"));
		assertTrue(Countries.get(0).equalsIgnoreCase("Country"));
	}

	public void addAnotherField(String keyWord) throws InterruptedException {
		numberOfField++;

		Thread.sleep(5000);
		WebElement addAnotherFieldEl = driver.findElement(By.xpath("(//div[@class='table-body'])/a"));
		addAnotherFieldEl.click();
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[" + numberOfField + "]"))
				.sendKeys(keyWord);
		driver.findElement(By.xpath("(//input[@data-action='type'])[" + numberOfField + "]")).click();

		Thread.sleep(5000);
		driver.findElement(By.id("type_search_field")).clear();
		driver.findElement(By.id("type_search_field")).sendKeys(keyWord);
		driver.findElement(By.xpath("//div[.='" + keyWord + "']")).click();
	}

	public void remoteAllExistingFields() {
		List<WebElement> Elements = new ArrayList<WebElement>();
		Elements = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for (WebElement each : Elements) {
			each.click();
		}
	}

}
