package porsche;

import java.time.LocalDateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BuyPorsche {

	static String basePrice;
	static String basePrice2;
	static String taxAndDelivery;
	static String equipmentPrice;
	static String totalPrice;
	static String miamiPrice;
	static String wheelPrice;
	static String seatsPrice;
	static String interiorPrice;
	static String PDKPrice;
	static String brakesPrice;

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		// 1. Open browser
		WebDriver driver = new ChromeDriver();
		// 2. Go to url “https://www.porsche.com/usa/modelstart/”
		System.out.println("1.Open browser");
		System.out.println("2.Go to url “https://www.porsche.com/usa/modelstart/”");

		driver.get("https://www.porsche.com/usa/modelstart/");

		// 3. Select model 718
		System.out.println("3.Select model 718");
		driver.findElement(By.className("b-teaser-caption")).click();
		Thread.sleep(3000);

		// 4. Remember the price of 718Cayman
		basePrice = driver.findElement(By.xpath("//*[@id=\"m982120\"]/div[1]/div[2]/div[2]")).getText();
		System.out.println("4.Remember the price of 718Cayman: $59000");

		// 5. Click on Build & Price under 718Cayman
		driver.findElement(By.className("m-14-quick-link")).click();
		System.out.println("5. Click on Build & Price under 718Cayman");

		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.className("m-14-quick-link")).click();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		// 6. Verify that Base price displayed on the page is same as the price from
		// step 4
		System.out.print("6.Verify that Base price displayed on the page is same as the price from step 4: ");
		Thread.sleep(5000);
		basePrice2 = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[1]/div[2]")).getText();
		countSum(basePrice2, "56900");

		// 7. Verify that Price for Equipment is 0
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		System.out.print("7. Verify that Price for Equipment is 0: ");
		countSum(equipmentPrice, "0");

		// 8. Verify that total price is the sum of base price + Delivery, Processing
		// and Handling Fee
		taxAndDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		totalPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		System.out.print("8. Verify that total price is the sum: ");
		countSum(totalPrice, taxAndDelivery, basePrice2);

		// 9. Select color “Miami Blue”
		driver.findElement(By.id("s_exterieur_x_FJ5")).click();
		System.out.println("9. Select color “Miami Blue”");

		// 10. Verify that Price for Equipment is Equal to Miami Blue price
		System.out.print("10. Verify that Price for Equipment is Equal to Miami Blue price: ");
		miamiPrice = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IAF\"]/div[2]/div[1]/div/div[2]")).getText();
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		countSum(miamiPrice, equipmentPrice);

		// 11.Verify that total price is the sum of base price + Price for Equipment
		// + Delivery, Processing and Handling Fee
		totalPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		System.out.print("11. Verify that total price is the sum: ");
		countSum(totalPrice, basePrice2, equipmentPrice, taxAndDelivery);

		// 12.Select 20" Carrera Sport Wheels
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,500)", "");
		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_MXRD\"]/span")).click();
		System.out.println("12.Select 20\" Carrera Sport Wheels");

		// 13.Verify that Price for Equipment is the sum of Miami Blue price + 20"
		// Carrera Sport Wheels
		System.out.print("13.Verify that Price for Equipment is the sum: ");
		wheelPrice = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]")).getText();
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		countSum(equipmentPrice, wheelPrice, miamiPrice);

		// 14.Verify that total price is the sum of base price + Price for Equipment +
		// Delivery, Processing and Handling Fee
		System.out.print("14.Verify that total price is the sum: ");
		countSum(equipmentPrice, wheelPrice, miamiPrice);

		// 15.Select seats ‘Power Sport Seats (14-way) with Memory Package
		jse.executeScript("window.scrollBy(0,1000)", "");
		driver.findElement(By.xpath("//*[@id=\"s_interieur_x_PP06\"]")).click();
		System.out.println("15.Select seats ‘Power Sport Seats (14-way) with Memory Package");

		// 16.Verify that Price for Equipment is the sum of Miami Blue price
		// + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		seatsPrice = driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div")).getText();
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		System.out.print("16.Verify that Price for Equipment is the sum:");
		countSum(equipmentPrice, miamiPrice, wheelPrice, seatsPrice);

		// 17.Verify that total price is the sum of base price +
		// Price for Equipment + Delivery, Processing and Handling Fee
		System.out.print("17.Verify that total price is the sum: ");
		totalPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		taxAndDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		countSum(totalPrice, basePrice2, equipmentPrice, taxAndDelivery);

		// 18.Click on Interior Carbon Fiber
		jse.executeScript("window.scrollBy(0,800)", "");
		driver.findElement(By.xpath("//*[@id=\"IIC_subHdl\"]")).click();
		System.out.println("18.Click on Interior Carbon Fiber");

		// 19.Select Interior Trim in Carbon Fiber i.c.w. Standard Interior
		driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH_x_c01_PEKH\"]")).click();
		System.out.println("19.Select Interior Trim in Carbon Fiber");

		// 20.Verify that Price for Equipment is the sum of Miami Blue price +
		// 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		// + Interior Trim in Carbon Fiber i.c.w. Standard Interior
		interiorPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH\"]/div[1]/div[2]/div")).getText();
		System.out.print("20.Verify that Price for Equipment is the sum: ");
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		countSum(equipmentPrice, wheelPrice, miamiPrice, seatsPrice, interiorPrice);

		// 21.Verify that total price is the sum of base price
		// + Price for Equipment + Delivery, Processing and Handling Fee
		System.out.print("21.Verify that total price is the sum: ");
		totalPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		taxAndDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		countSum(totalPrice, basePrice2, equipmentPrice, taxAndDelivery);

		// 22.Click on Performance
		jse.executeScript("window.scrollBy(0,-200)", "");
		driver.findElement(By.xpath("//*[@id=\"IMG_subHdl\"]")).click();
		System.out.println("22.Click on Performance");

		// 23.Select 7-speed Porsche Doppelkupplung (PDK)
		driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250_x_c11_M250\"]")).click();
		System.out.println("23.Select 7-speed Porsche Doppelkupplung (PDK)");
		Thread.sleep(2000);

		// 24.Select Porsche Ceramic Composite Brakes (PCCB)
		jse.executeScript("window.scrollBy(0,500)", "");
		driver.findElement(By.id("vs_table_IMG_x_M450_x_c94_M450_x_shorttext")).click();
		Thread.sleep(1000);
		System.out.println("24.Select Porsche Ceramic Composite Brakes (PCCB)");

		// 25.Verify that Price for Equipment is the sum of Miami Blue price
		// + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
		// + Interior Trim in Carbon Fiber i.c.w. Standard Interior
		// + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes
		// (PCCB)
		PDKPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250\"]/div[1]/div[2]/div")).getText();
		brakesPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450\"]/div[1]/div[2]/div")).getText();
		System.out.print("25.Verify that Price for Equipment is the sum: ");
		equipmentPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
		countSum(equipmentPrice, wheelPrice, miamiPrice, seatsPrice, interiorPrice, PDKPrice, brakesPrice);

		// 26.Verify that total price is the sum of base price + Price for Equipment
		// + Delivery, Processing and Handling Fee
		System.out.print("26.Verify that total price is the sum: ");
		totalPrice = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[4]/div[2]")).getText();
		taxAndDelivery = driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[3]/div[2]")).getText();
		countSum(totalPrice, basePrice2, equipmentPrice, taxAndDelivery);

		driver.close();

		System.out.println();
		System.out.println("TEST COMPLITED - " + LocalDateTime.now());
	}
public static void countSum(String price1, String... prices) {
		double d = Integer.parseInt(price1.replaceAll("[^0-9]", ""));
		double sum = 0;
		for (String price : prices) {
			double each = Integer.parseInt(price.replaceAll("[^0-9]", ""));
			sum += each;
		}
		if (d == sum) {
			System.out.println("VERIFIED");
		} else {
			System.out.println("NOT VERIFIED");
		}
	}
}

	
