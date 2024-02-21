package limundo;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.Instant;

public class LimundoPageLoadTime {
	
	private static String exePath = 
			"D:\\Fakultet\\Testiranje Softvera\\Seminarski rad\\chromedriver\\chromedriver.exe";
	
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", exePath);
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1920, 1040));
		
		/*POCETNA STRANICA*/
		Instant startTime = Instant.now();
		
		driver.get("https://www.limundo.com/");
		
		//driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/div/div/button")).click();
		
		Instant endTime = Instant.now();
		
		Duration duration = Duration.between(startTime, endTime);
		
		System.out.println("Vreme ucitavanja pocetne stanice: "+duration.toMillis()+"ms");
		
		/*KATEGORIJA RACUNARI I OPREMA*/
		startTime = Instant.now();
		
		driver.findElement(By.id("dropdownMenuButton")).click();
		driver.findElement(By.cssSelector("a[href='/Racunari-i-oprema/aukcije/g12']")).click();
		
		endTime = Instant.now();
		
		duration = Duration.between(startTime, endTime);
		
		System.out.println("Vreme ucitavanja stanice za racunare: "+duration.toMillis()+"ms");
		
		/*MOJ NALOG*/
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("veljkofajnisevic15");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("veljko123");
		driver.findElement(By.cssSelector("button[class='btn btn-green-40']")).click();
		
		startTime = Instant.now();
		driver.findElement(By.cssSelector("button[class='btn btn-user btn-transparent-40 dropdown-toggle dropdown-toggle--no-caret']")).click();
		driver.findElement(By.cssSelector("a[href='https://www.limundo.com/MojLimundo/MojNalog']")).click();
		
		endTime = Instant.now();
		
		duration = Duration.between(startTime, endTime);
		
		System.out.println("Vreme ucitavanja stanice za nalog: "+duration.toMillis()+"ms");
		
		/*PRETRAGA PROIZVODA*/
		startTime = Instant.now();
		driver.findElement(By.name("txtPretraga")).click();
		driver.findElement(By.name("txtPretraga")).sendKeys("Adidas Patike");
		driver.findElement(By.id("button-addon2")).click();
		endTime = Instant.now();
		
		duration = Duration.between(startTime, endTime);
		
		System.out.println("Vreme ucitavanja stanice za pretragu proizvoda: "+duration.toMillis()+"ms");
		
		driver.quit();
	}

}
