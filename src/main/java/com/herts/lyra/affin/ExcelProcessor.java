package com.herts.lyra.affin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelProcessor {
	
	String sourcePath; // path of the source excel file set by providing the s option in the command line
	
	public ExcelProcessor (String path) {
		this.sourcePath=path;
		
	}
	
	public List<Review> parseExcel()throws IOException{
		List <Review> reviewList = new ArrayList<Review>();
		FileInputStream fis = new FileInputStream(new File(sourcePath));
		XSSFWorkbook reviewsWorkbook = new XSSFWorkbook(fis);		
		XSSFSheet reviewsSheet = reviewsWorkbook.getSheetAt(0);
		int rowNumber = reviewsSheet.getLastRowNum();
		
		/*iterate through each row of the excel document 
		 * set values from specific cells from each row as values of a review object's fields
		 * if any cell is found blank during iteration, set it's value to "NONE", otherwise NullpointerExceprion is thrown. 
		 */
		
		for (int r=0;r<=rowNumber;r++) {
			
			
			reviewList.add(new Review());
			if(reviewsSheet.getRow(r).getCell(0)!=null) {
			reviewList.get(r).setDate(reviewsSheet.getRow(r).getCell(0).toString());
			}else {reviewList.get(r).setDate("NONE");}
			
			if(reviewsSheet.getRow(r).getCell(1)!=null) {
			reviewList.get(r).setUserName(reviewsSheet.getRow(r).getCell(1).toString());
			}else {reviewList.get(r).setUserName("NONE");}
			
			if(reviewsSheet.getRow(r).getCell(2)!=null) {
			reviewList.get(r).setHelpfulIndex(reviewsSheet.getRow(r).getCell(2).toString());
			}else {reviewList.get(r).setHelpfulIndex("NONE");}
			
			if(reviewsSheet.getRow(r).getCell(3)!=null) {
			reviewList.get(r).setComment(reviewsSheet.getRow(r).getCell(3).toString().toLowerCase());
			}else {reviewList.get(r).setComment("NONE");}
			//file is parsed correctly, all data can be printed to console for document of 1 line	
			
		}
		//all file contents are saved in object as needed
		return reviewList;	
		
	}

	
	
}
