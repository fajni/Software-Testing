package limundo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileWriter;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.*;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LimundoTest {

	private WebDriver driver;
	private WebDriverWait wait;
	private String exePath = 
			"D:\\Fakultet\\Testiranje Softvera\\Seminarski rad\\chromedriver\\chromedriver.exe";
	private Map<String, Object> vars;
	private JavascriptExecutor js;
	
	public void sacekaj() {
		//Sacekati dok se ocita stranica
		driver.manage().timeouts().pageLoadTimeout(400, TimeUnit.SECONDS); 
		driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Rule
	public TestName name = new TestName();
	Logger log = Logger.getLogger(LimundoTest.class.getName());
	String report = "";
	String testName = "";
	String opis = "";
	boolean success = false;
	
	@Before
	public void setUp() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		driver.get("https://www.limundo.com/");
		driver.navigate().refresh();
		driver.manage().window().setSize(new Dimension(1920, 1040));
		//driver.findElement(By.xpath("/html/body/div[4]/div[1]/div/div/div/div/button")).click(); //zatvaranje Kontraponuda prozora (obavestenje)
		
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
		System.setProperty("webdriver.chrome.driver", exePath);
	}
	
	@After
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	//URL CHECK:
	@Test
	public void urlCheckTest() {
		assertEquals("URL not expected!", driver.getCurrentUrl(), "https://www.limundo.com/");
		success = true;
		opis = "Provera URL sajta!";
	}
	
	//TITLE CHECK:
	@Test
	public void titleTest(){
		assertEquals("Not expected title!", driver.getTitle(), "Limundo - Sigurna kupoprodaja putem Internet aukcija");
		success = true;
		opis = "Provera Naslova sajta!";
	}
	
	//SOURCE SIZE:
	@Test
	public void lengthPageTest() {
		assertTrue("Size of a page is less that 0!", driver.getPageSource().length() > 0);
		success = true;
		opis = "Provera Velicine sajta!";
	}
	
	//===============================================
	
	//1. REGISTRATION:
	@Test
	@Ignore
	public void registrationTest() {
		
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
		driver.findElement(By.cssSelector("a[href='#registracija']")).click();
		
		driver.findElement(By.cssSelector("label[for='inputCheckPunoletan']")).click();
		driver.findElement(By.id("inputIme")).click();
		driver.findElement(By.id("inputIme")).sendKeys("Veljko");
		driver.findElement(By.id("inputPrezime")).click();
		driver.findElement(By.id("inputPrezime")).sendKeys("Second");
		driver.findElement(By.id("inputEmail")).click();
		driver.findElement(By.id("inputEmail")).sendKeys("fajnisecond@gmail.com");
		driver.findElement(By.id("inputUsername")).click();
		driver.findElement(By.id("inputUsername")).sendKeys("fajnisecond");
		driver.findElement(By.id("inputPassword")).click();
		driver.findElement(By.id("inputPassword")).sendKeys("second123");
		
		driver.findElement(By.id("btnConfirmEmailCode")).click();
		sacekaj();
		System.out.println(driver.getCurrentUrl());
		assertTrue(driver.getPageSource().contains("E-mail kod"));
		success = true;
		opis = "Provera Registracije novog korisnika!";
	}

	//2. LOGIN
	@Test
	public void loginTest() {
		
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
	
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("veljkofajnisevic15");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("veljko123");
		driver.findElement(By.cssSelector("button[class='btn btn-green-40']")).click();
		
		assertTrue(driver.getPageSource().contains("veljkofajnisevic15"));
		success = true;
		opis = "Provera Prijave korisnika!";
	}
	
	//3. ACCOUNT INFORMATION:
	@Test
	public void accountInformationTest() {
		
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
		
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("veljkofajnisevic15");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("veljko123");
		driver.findElement(By.cssSelector("button[class='btn btn-green-40']")).click();
		
		driver.findElement(By.cssSelector("button[class='btn btn-user btn-transparent-40 dropdown-toggle dropdown-toggle--no-caret']")).click();
		driver.findElement(By.cssSelector("a[href='https://www.limundo.com/MojLimundo/MojNalog']")).click();
		
		{
			String ime = driver.findElement(By.id("nameContent")).getText();
			assertEquals(ime, "Veljko");
		}
		
		{
			String prezime = driver.findElement(By.id("surnameContent")).getText();
			assertEquals(prezime, "Fajnisevic");
		}
		
		{
			String email = driver.findElement(By.id("emailMain")).getText();
			assertEquals(email, "veljko.fajnisevic.15@gmail.com");
		}
		
		{
			String telefon = driver.findElement(By.id("phoneMain")).getText();
			assertEquals(telefon, "381641438440");
		}
		
		{
			String ulica = driver.findElement(By.id("mainUlica")).getText();
			assertEquals(ulica, "Karadjordjeva 12");
		}
		
		{
			driver.manage().window().setSize(new Dimension(1920, 1040));
			js.executeScript("window.scrollBy(0, 350)", "");
			String broj = driver.findElement(By.id("mainPostBRMesto")).getText();
			String mesto = driver.findElement(By.xpath("/html/body/div[4]/section/div/div/div/div/div/div[2]/div[3]/div[3]/div[3]/div[1]/div/div[2]/span[1]")).getText();
			assertEquals(mesto, "Bor");
			assertTrue(broj.contains("19210"));
		}
		
		assertTrue(driver.getCurrentUrl().contains("https://www.limundo.com/MojLimundo/MojNalog"));
		success = true;
		opis = "Provera Informacija o nalogu!";
	}
	
	//4. & 5. ITEMS:
	@Test
	public void addItemsTest() {
		
		//Prijava:
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("veljkofajnisevic15");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("veljko123");
		driver.findElement(By.cssSelector("button[class='btn btn-green-40']")).click();
		driver.navigate().refresh();
		
		//Uzimanje naziv proizvoda i njegove cene:
		String naziv1 = "";
		double cena1 = 0.0;
		String naziv2 = "";
		double cena2 = 0.0;
		String naziv3 = "";
		double cena3 = 0.0;
		double sum = 0.0;
		
		{
			driver.findElement(By.id("dropdownMenuButton")).click();
			driver.findElement(By.cssSelector("a[href='/Racunari-i-oprema/aukcije/g12']")).click();
			
			//1. Proizvod u kategoriji Racunari
			naziv1 = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/h3")).getText();
			String cena = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/div[1]/ul/li[2]/span[2]/strong")).getText();
			cena1 = Double.valueOf(cena.substring(0, cena.length()-3))*1000;
			
			System.out.println(naziv1);
			System.out.println(cena1);
			
			driver.findElement(By.id("addToWishlist-130508669")).click();
			
			assertTrue(naziv1.contains("Samsung"));
			assertTrue(cena1 > 0);
		}
		
		{
			driver.findElement(By.id("dropdownMenuButton")).click();
			driver.findElement(By.cssSelector("a[href='/Mobilni-telefoni/aukcije/g31']")).click();
			
			//2. Proizvod u kategoriji Mobilni telefoni
			naziv2 = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/h3")).getText();
			//naziv2 = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[2]/div/div/div[2]/div/div[1]/h3")).getText();
			String cena = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/div[1]/ul/li[2]/span[2]/strong")).getText();
			//String cena = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[2]/div/div/div[2]/div/div[1]/div[1]/ul/li[2]/span[2]/strong")).getText();
			cena2 = Double.valueOf(cena.substring(0, cena.length()-3))*1000;
			
			System.out.println(naziv2);
			System.out.println(cena2);
			
			driver.findElement(By.id("addToWishlist-130622177")).click();
			
			assertTrue(naziv2.contains("Samsung"));
			assertTrue(cena2 > 0); //cena se stalno menja
		}
		
		{
			driver.findElement(By.id("dropdownMenuButton")).click();
			driver.findElement(By.cssSelector("a[href='/Auto-i-moto/aukcije/g1']")).click();
			
			//2. Proizvod u Auto i moto
			naziv3 = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/h3")).getText();
			String cena = driver.findElement(By.xpath("/html/body/div[5]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/div[1]/ul/li[2]/span[2]/strong")).getText();
			cena3 = Double.valueOf(cena.substring(0, cena.length()-3))*1000;
			
			System.out.println(naziv3);
			System.out.println(cena3);
			
			driver.findElement(By.id("addToWishlist-130870413")).click();
			
			assertTrue(naziv3.contains("Kaciga"));
			assertTrue(cena3 > 0);
		}
		
		driver.findElement(By.cssSelector("button[class='btn btn-user btn-transparent-40 dropdown-toggle dropdown-toggle--no-caret']")).click();
		driver.findElement(By.cssSelector("a[href='https://www.limundo.com/MojLimundo/ListaZelja']")).click();
		js.executeScript("window.scrollBy(0, 550)", "");
		
		sacekaj();
		
		{
			String sumString = driver.findElement(By.xpath("/html/body/div[4]/section/div/div/div[2]/div/p/span[2]")).getText();
			sum = Double.valueOf(sumString.substring(0, sumString.length()-3))*1000;
			System.out.println("Ukupna cena: "+sum);
			//assertEquals(sum, cena1+cena2+cena3, 0.0);
			assertTrue(cena1+cena2+cena3 > 0);
		}
		
		{
			sacekaj();
			String brojPredmeta = driver.findElement(By.id("totalHeader")).getText();
			System.out.println("Ukupno predmeta: "+brojPredmeta);
			assertEquals(brojPredmeta, "3");
		}
		
		//Obrisati sve predmete:
		driver.findElement(By.xpath("/html/body/div[4]/section/div/div/div[4]/div[3]/div/div[10]/div")).click();
		driver.findElement(By.id("obrisiPredmeteMultiple")).click();
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[3]/button[2]")).click();
		driver.navigate().refresh();
		assertEquals(driver.findElement(By.id("totalHeader")).getText(), "");
		success = true;
		opis = "Provera Dodavanja 3 proizvoda u korpu!";
	}
	
	//6. ABOUT:
	@Test
	public void aboutTest() {
		
		Logger l = Logger.getLogger(LimundoTest.class.toString());
		
		js.executeScript("window.scrollBy(0, 1550)","");
		
		driver.findElement(By.linkText("USLOVI KORIŠĆENJA")).click();
		driver.findElement(By.linkText("Podaci o LimundoGrad d.o.o.")).click();
		
		//html/body/div[3]/div[2]/div/div/div[2]/div/ul/li[1]/span[2]
		String sediste = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/ul/li[1]/span[2]")).getText();
		String kontakt = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/ul/li[2]/span[2]")).getText();
		String email = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/ul/li[2]/span[2]/a")).getText();
		String opis_sajta = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/ul/li[3]/span[2]")).getText();
		String url = driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div/div[2]/div/ul/li[8]/a")).getText();
		
		l.info("Sediste: "+sediste);
		l.info("Kontakt: "+kontakt);
		l.info("Email: "+email);
		l.info("Opis: "+opis_sajta);
		l.info("URL: "+url);
		
		{
			assertTrue("Greska kod sedista!",sediste.contains("Vladimira Popovića 6"));
		}
		
		{
			assertTrue("Greska kod kontakta!",kontakt.contains("011 404 78 00"));
		}
		
		{
			assertTrue("Greska kod email!",email.contains("spajalica@Limundo.com"));
		}
		
		{
			assertTrue("Greska kod opisa", opis_sajta.contains("Trgovina na malo posredstvom pošte ili interneta"));
		}
		
		{
			assertTrue("Greska kod URL!", url.contains("limundo.com"));
		}
		
		assertTrue(driver.getCurrentUrl().toLowerCase().contains("podaci"));
		success = true;
		opis = "Provera Podataka o sajtu/kompaniji".toUpperCase() + "\n Sediste: "+sediste+"\n Kontakt: "+kontakt+"\n Email: "+email+"\n Opis sajta/kompanije: "+opis_sajta+"\n URL: "+url;
	}
	
	//===============================================
	
	//7. SOCIALS:
	@Test
	public void socialsTest() {
		js.executeScript("window.scrollBy(0, 1550)","");
		String pocetniProzor = driver.getWindowHandle();
		driver.findElement(By.linkText("O NAMA")).click();
		driver.get(driver.findElement(By.linkText("O NAMA")).getAttribute("href"));
		js.executeScript("window.scrollBy(0, 1550)","");
		
		//Facebook:
		driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[1]/a")).click();
		
		{
			System.out.println(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[1]/a")).getAttribute("title"));
			assertTrue(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[1]/a")).getAttribute("href").contains("https://www.facebook.com/Limundo"));
		}
		
		driver.switchTo().window(pocetniProzor);
		
		//Youtube:
		driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[3]/a")).click();
		{
			System.out.println(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[3]/a")).getAttribute("title"));
			assertTrue(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[3]/a")).getAttribute("href").contains("https://www.youtube.com/user/LimundoVideo"));
		}
		
		driver.switchTo().window(pocetniProzor);
		
		//Twitter:
		driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[2]/a")).click();
		{
			System.out.println(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[2]/a")).getAttribute("title"));
			assertTrue(driver.findElement(By.xpath("/html/body/footer/div[1]/div[1]/ul/li[2]/a")).getAttribute("href").contains("https://twitter.com/Limundo"));
		}
		
		System.out.println("Trenutni link: "+driver.getCurrentUrl());
		assertTrue(driver.getCurrentUrl().contains("https://www.limundograd.com/?utm_source=ZendeskFooterONama"));
		success = true;
		opis = "Provera Drustvenih mreza!";
	}

	//8. POSTAVI U PRIPREMI:
	@Test
	public void publishItemTest() {
		
		driver.findElement(By.cssSelector("button[title='Ulaz']")).click();
		
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).sendKeys("veljkofajnisevic15");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).sendKeys("veljko123");
		driver.findElement(By.cssSelector("button[class='btn btn-green-40']")).click();
		
		driver.findElement(By.cssSelector("a[href='https://www.limundo.com/MojLimundo/NovaAukcija']")).click();
		
		driver.findElement(By.xpath("/html/body/div[7]/div/div/div/button")).click();
		
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[2]/div/div/div[2]/input")).click();
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[2]/div/div/div[2]/input")).sendKeys("Predmet Testiranje");
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[3]/div/div/div[2]/textarea")).click();
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[3]/div/div/div[2]/textarea")).sendKeys("Testiranje Postavljanje Predmeta");
		js.executeScript("window.scrollBy(0, 550)","");
		driver.findElement(By.id("Stanje-2")).click();
		js.executeScript("window.scrollBy(0, 1000)","");
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[7]/div/div/div[2]/div[1]/div/a[14]")).click();
		driver.findElement(By.id("btnFiksna")).click();
		js.executeScript("window.scrollBy(0, 1550)","");
		driver.findElement(By.id("FiksnaCena")).click();
		driver.findElement(By.id("FiksnaCena")).sendKeys("120");
		sacekaj();
		driver.findElement(By.id("potkategorija52")).click();
		driver.findElement(By.xpath("/html/body/div[9]/form/div/section[12]/div/div/div[2]/div[1]/div[5]/label")).click();
		driver.findElement(By.id("btnCuvajUPripremi")).click();
		
		//driver.findElement(By.xpath("/html/body/div[8]/div/div/div/button")).click();
		driver.navigate().refresh();
		sacekaj();
		driver.navigate().refresh();
		driver.findElement(By.cssSelector("button[class='btn btn-user btn-transparent-40 dropdown-toggle dropdown-toggle--no-caret']")).click();
		driver.findElement(By.cssSelector("a[href='https://www.limundo.com/MojLimundo/UPripremi']")).click();
		
		{
			sacekaj();
			//driver.navigate().refresh();
			assertEquals(driver.findElement(By.id("totalHeader")).getText(), "1");
		}
		
		//BRISANJE:
		driver.findElement(By.xpath("/html/body/div[3]/section/div/div/div[4]/div[1]/div[3]/div[6]/div")).click();
		driver.findElement(By.id("obrisiPredmeteMultiple")).click();
		driver.findElement(By.id("obrisiPredmeteConfirm")).click();
		
		driver.navigate().refresh();
		sacekaj();
		assertEquals(driver.findElement(By.id("totalHeader")).getText(), "0");
		success = true;
		opis = "Provera Postavljanja novog proizvoda na sajtu!";
	}
	
	//9. PRETRAGA PROIZVODA:
	@Test
	public void searchTest() {
		//driver.findElement(By.cssSelector("input=[name='txtPretraga']")).click();
		//driver.findElement(By.cssSelector("input=[name='txtPretraga']")).sendKeys("Adidas Patike");
		//driver.findElement(By.xpath("/html/body/div[3]/div[1]/header/div/div/div[3]/div/form/div[1]/input[13]")).click();
		//driver.findElement(By.xpath("/html/body/div[3]/div[1]/header/div/div/div[3]/div/form/div[1]/input[13]")).sendKeys("Adidas Patike");
		driver.findElement(By.name("txtPretraga")).click();
		driver.findElement(By.name("txtPretraga")).sendKeys("Adidas Patike");
		driver.findElement(By.id("button-addon2")).click();
		
		System.out.println("Prvi pronadjeni proizvod: "+driver.findElement(By.xpath("/html/body/div[6]/section/div/div/div/div[6]/div/div[1]/div/div/div[2]/div/div[1]/h3")).getText());
		
		{
			assertTrue(driver.findElement(By.linkText("Vrhunske Adidas (mi) Chris Prirodna koza/Ostalo 45-29")).getText().toLowerCase().contains("adidas"));
			success = true;
			opis = "Provera Pretrage na sajtu!";
		}
	}
	
	@After
	public void WriteToReport() {
		report += "\n *** \n";
		report += "\n Naziv testa: "+name.getMethodName();
		report += "\n Opis testa: "+opis;
		report += "\n Test prosao uspesno: "+success+"\n";
		log.info(report);
		
		try {
			FileWriter fw = new FileWriter("test-report.txt", true);
			fw.write(report);
			fw.flush();
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
