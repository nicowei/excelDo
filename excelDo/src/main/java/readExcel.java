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
			FileInputStream fs=new FileInputStream("d://test.xls");  //��ȡd://test.xls  
			
			 POIFSFileSystem ps=new POIFSFileSystem(fs);  //ʹ��POI�ṩ�ķ����õ�excel����Ϣ  

				HSSFWorkbook wb=new HSSFWorkbook(ps);

				HSSFSheet sheet=wb.getSheetAt(0);  //��ȡ����������Ϊһ��excel�����ж��������  
				
				HSSFRow row =null;

			int n =sheet.getLastRowNum() ;
			// HSSFRow row=sheet.getRow(1);  //��ȡ��һ�У�excel�е���Ĭ�ϴ�0��ʼ�����������Ϊʲô��һ��excel�������ֶ���ͷ���������ֶ���ͷ�����ڸ�ֵ  
			//System.out.println(sheet.getLastRowNum()+" "+row.getLastCellNum());  //�ֱ�õ����һ�е��кţ���һ����¼�����һ����Ԫ��  
			System.out.println(n);
			FileOutputStream out=new FileOutputStream("d://test2.xls");  //��d://test.xls��д����  
			//row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //�������кź�׷������  
			//row.createCell(0).setCellValue("leilei"); //���õ�һ������0��ʼ����Ԫ�������  
			//row.createCell(1).setCellValue(24); //���õڶ�������0��ʼ����Ԫ�������  
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
