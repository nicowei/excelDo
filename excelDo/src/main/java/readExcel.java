package main.java;
import java.io.FileInputStream;
import java.io.FileOutputStream;
//import org.apache.poi.hssf.usermodel.HSSFRow;    
//import org.apache.poi.hssf.usermodel.HSSFSheet;    
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.usermodel.*;

public class readExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		     Workbook book = null;
//		        try {
//		            book = new XSSFWorkbook(excelFile);
//		        } catch (Exception ex) {
//		            book = new HSSFWorkbook(new FileInputStream(excelFile));
//		        }
		try{
			String c ="";
			FileInputStream fs=new FileInputStream("d://test.xls");  //获取d://test.xls  
			
			 POIFSFileSystem ps=new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息  

				HSSFWorkbook wb=new HSSFWorkbook(ps);

				HSSFSheet sheet=wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表  
				
				HSSFRow row =null;

			int n =sheet.getLastRowNum() ;
			// HSSFRow row=sheet.getRow(1);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值  
			//System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格  
			System.out.println(n);
			FileOutputStream out=new FileOutputStream("d://test2.xls");  //向d://test.xls中写数据  
			//row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据  
			//row.createCell(0).setCellValue("leilei"); //设置第一个（从0开始）单元格的数据  
			//row.createCell(1).setCellValue(24); //设置第二个（从0开始）单元格的数据  
			for(int i =1;i<=n;i++){
				row=sheet.getRow(i);
				
//				System.out.println(i);
				if(row==null||row.getCell(3)==null) continue;
				c=row.getCell(3).toString().trim();
				c=c.substring(0,c.indexOf(".")>0?c.indexOf("."):c.length());
			
//				System.out.println(c);
				//HSSFCell r =row.getCell(4);
				if(row.getCell(4)==null){
				row.createCell(4).setCellValue(MD5.stringMd5(c));
				}else{
					row.getCell(4).setCellValue(MD5.stringMd5(c));
				}
			}
			out.flush();  
			wb.write(out);
		
			wb.close();
			out.close(); 
			
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}

}
