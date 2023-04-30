package com.apar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.apar.base.TestBase;




public class TestUtil extends TestBase {

    public static long PAGE_LOAD_TIMEOUT = 30; //20


    public static void takeScreenshotAtEndOfTest() throws IOException {
	File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scrFile, new File("AutoReport/" + System.currentTimeMillis() + ".png"));
    }

    public static void clickByJS(WebElement element) {

	JavascriptExecutor js = ((JavascriptExecutor) driver);
	js.executeScript("arguments[0].click();", element);

    }

    public static String TakeSnapshot(WebDriver driver, String pictureName) throws IOException {

	File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	String Destination = pictureName + ".png";
	File FinalDestination = new File("AutoReport/" + Destination);
	FileUtils.copyFile(src, FinalDestination);
	return Destination;

    }

    public static void getConsoleLogs(WebDriver driver1, ITestResult result) throws Throwable, Throwable {

	logs = driver1.manage().logs();
	logEntries = logs.get(LogType.BROWSER);
	writer = new PrintWriter("AutoReport/" + result.getName() + ".txt", "UTF-8");

	for (LogEntry logEntry : logEntries) {
	    writer.println("Console log found in Test- " + result.getName());

	    writer.println("__________________________________________________________");

	    if (logEntry.getMessage().toLowerCase().contains("error")) {
		writer.println("Error Message in Console:" + logEntry.getMessage());

	    } else if (logEntry.getMessage().toLowerCase().contains("warning")) {
		writer.println("Warning Message in Console:" + logEntry.getMessage());

	    } else {
		writer.println("Information Message in Console:" + logEntry.getMessage());

	    }
	}
	writer.close();
    }


    public static void clickOn(WebDriver driver, WebElement locator, int timeout) {
	new WebDriverWait(driver, timeout).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(locator));
	locator.click();
    }

    public static void waitElement(WebDriver driver, WebElement locator, int timeout) {
	new WebDriverWait(driver, timeout).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(locator));

    }
    public static String readFromExcelFile (String inputFileName, String sheetName, String desiredColumnName, int rowN) throws IOException {
	Row row;
	Cell cell;
	String value = "";
	//Path of the excel file
	FileInputStream fExcel = new FileInputStream(inputFileName);
	//Creating a workbook
	@SuppressWarnings("resource")
	XSSFWorkbook workbook = new XSSFWorkbook(fExcel);
	XSSFSheet workSheet = workbook.getSheet(sheetName);
	int lastcell=workSheet.getRow(0).getLastCellNum();
	System.out.println("lastcell = "+lastcell);
	//Non empty Last cell Number or index return
	for(int i=0;i<=lastcell;i++)
	{
	    row = workSheet.getRow(0);
	    cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	    System.out.println("convert = "+cell.getStringCellValue());  //CellReference.convertNumToColString(i)
	    System.out.println("desiredColumnName = "+desiredColumnName);
	    if (cell.getStringCellValue().equals(desiredColumnName))
	    {
		row = workSheet.getRow(rowN);
		cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		System.out.println("getStringCellValue = "+cell.toString());
		try{
		    if(cell!=null) {
			switch(cell.getCellType()){
			case STRING:
			    value = String.valueOf(cell.getStringCellValue());
			    return value;
			case NUMERIC:
			    value = String.valueOf(cell.getNumericCellValue());
			    return value;
			case BLANK:
			    value="";
			    return value;
			case _NONE:
			    value="";
			    return value;
			default:
			    break;
			}
		    }
		}catch(NullPointerException NPE)
		{
		    value="";
		    return value;
		}
	    }
	}
	return value;
    }
}
