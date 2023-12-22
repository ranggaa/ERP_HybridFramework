package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
//method for launching bowser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load file
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser value is Not matching",true);
	}
	return driver;
}
//method for launch url
public static void openUrl(WebDriver driver)
{
	driver.get(conpro.getProperty("Url"));
}
//method for wait for element
public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String TestData)
{
	WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));	
	}
}
//method for textboxes
public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
}
//method for buttons,checkboxes,radio buttons,links and images
public static void clickAction(WebDriver driver,String Locator_Type,String Locator_value)
{
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_value)).click();
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_value)).click();
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
	}
}
//method for validating title
public static void validateTitle(WebDriver driver,String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Expected_Title, Actual_Title, "Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for closing browser
public static void closeBrowser(WebDriver driver)
{
	driver.quit();
}
public static void moseClick(WebDriver driver)throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[starts-with(text(),'Stock Items ')]"))).perform();
	Thread.sleep(2000);
	ac.moveToElement(driver.findElement(By.xpath("(//a[contains(text(),'Stock Categories')])[2]")));
	ac.pause(3000).click().perform();
	
}
//method for catrgory table
public static void categoryTable(WebDriver driver,String Exp_Data)throws Throwable
{
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//if search textbox not displayed clcick search panel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	Reporter.log(Exp_Data+"     "+Act_data,true);
	try {
	Assert.assertEquals(Exp_Data, Act_data, "Category Name is Not Found In table");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for drop down
public static void dropDownAction(WebDriver driver,String Locator_Type,String Locator_value,
		String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
	int value =Integer.parseInt(Test_Data);
	WebElement element = driver.findElement(By.xpath(Locator_value));
	Select select = new Select(element);
	select.selectByIndex(value);
	
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		int value =Integer.parseInt(Test_Data);
		WebElement element = driver.findElement(By.id(Locator_value));
		Select select = new Select(element);
		select.selectByIndex(value);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		int value =Integer.parseInt(Test_Data);
		WebElement element = driver.findElement(By.name(Locator_value));
		Select select = new Select(element);
		select.selectByIndex(value);
	}
}
//method for capturing stock number into note pad
public static void captureStock(WebDriver driver,String Locator_Type,String Locator_value)throws Throwable

{
	String stockNumber ="";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		stockNumber = driver.findElement(By.id(Locator_value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		stockNumber = driver.findElement(By.name(Locator_value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		stockNumber = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
	}
	//create notepad and write stocknumner into  it
	FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(stockNumber);
	bw.flush();
	bw.close();
}
//method for stock table
public static void stockTable(WebDriver driver)throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data =br.readLine();
	
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//if search textbox not displayed clcick search panel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
		String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"    "+Act_data,true);
		Assert.assertEquals(Exp_Data, Act_data, "Stock Number is Not Found in Table");
		
}
//method for capture supplier number into note pad
public static void captureSupplier(WebDriver driver,String Locator_Type,String Locator_Value)throws Throwable
{
	String supplierNum ="";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		supplierNum =driver.findElement(By.id(Locator_Value)).getAttribute("value");
		
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		supplierNum =driver.findElement(By.name(Locator_Value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		supplierNum =driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./CaptureData/suppliernum.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(supplierNum);
	bw.flush();
	bw.close();
	
			
}
//method for supplier table
public static void suppliertable(WebDriver driver)throws Throwable
{
	FileReader fr = new FileReader("./CaptureData/suppliernum.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		//if search textbox not displayed clcick search panel button
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(3000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Exp_Data+"    "+Act_data,true);
	try {
	Assert.assertEquals(Exp_Data, Act_data, "Supplier Number Not Found in Table");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
	
}
//method for date generate
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
	return df.format(date);
}
}










