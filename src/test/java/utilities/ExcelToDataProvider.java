package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelToDataProvider {

	private static XSSFSheet excelWSheet;
	private static XSSFWorkbook excelWBook;
	private static XSSFCell cell;
	private static XSSFRow row;
	
	public static String[][] getTable(String filePath, String sheetName) throws FileNotFoundException,IOException{
		String[][] excelData = null;
			FileInputStream ExcelFile = new FileInputStream(filePath);
			excelWBook = new XSSFWorkbook(ExcelFile);
			excelWSheet = excelWBook.getSheet(sheetName);
			int startRow = 1;
			int startCol = 1;
			int ci,cj;
			int totalRows = excelWSheet.getLastRowNum();
			int totalCols = excelWSheet.getRow(0).getLastCellNum()-1;
			System.out.println(excelWSheet.getSheetName());
			System.out.println(totalRows);
			System.out.println(totalCols);
			excelData = new String[totalRows][totalCols];
			ci=0;
			for(int i=startRow; i<=totalRows;i++,ci++) {
				cj=0;
				for(int j=startCol; j<=totalCols; j++,cj++) {
					excelData[ci][cj]=getCellData(i,j);
				}
			}
		return excelData;
	}
	
	public static String getCellData(int rowNum, int colNum){
			
			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
			cell= excelWSheet.getRow(rowNum).getCell(colNum);
			String cellData= formatter.formatCellValue(cell);
			return cellData;
	}
	
	/*public static void writeExcel(String filePath, String sheetName, String[] dataToWrite) throws IOException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
		int rowCount = newSheet.getLastRowNum();
		XSSFRow row = newSheet.getRow(0);
		XSSFRow newRow = newSheet.createRow(rowCount+1);
		
		for(int i=1; i<row.getLastCellNum();i++) {
			XSSFCell newCell = newRow.getCell(i);
			newCell.setCellValue(dataToWrite[i]);
		}
		
		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(file);
		newWorkbook.write(outputStream);
		outputStream.close();
		
	}
	*/
}
