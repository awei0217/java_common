package tools;

/**
 * @创建人 sunpengwei
 * @创建时间 2018/10/25
 * @描述
 * @联系邮箱
 */


import java.io.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * 多个xls 文件合并成一个xlsx文件
 */
public class PoiUtils {

    public static void main(String[] args) throws Exception {
        /**
         * 第一个参数，原目录
         * 第二个参数，合并后的文件
         */
       // merger("E:\\oss_oss\\3_2\\","E://merger3_2.xlsx");
        readCellType("D:\\20194.xls");
    }

    public static void merger(String sourceDir,String targetFile) throws Exception {
        File file = new File(sourceDir);
        File[] fileNameList = file.listFiles();
        //将所有类型的尽调excel文件合并成一个excel文件
        XSSFWorkbook newExcelCreat = new XSSFWorkbook();
        XSSFSheet newSheet = newExcelCreat.createSheet("sheet1");
        int m = 0;
        for(int j =0;j<fileNameList.length;j++) {//遍历每个源excel文件，fileNameList为源文件的名称集合
            File file1 = fileNameList[j];
            try {
                System.out.println(sourceDir+".."+j);
                InputStream in = new FileInputStream(file1);
                HSSFWorkbook fromExcel = new HSSFWorkbook(in);
                if (j==0){
                    for(int i = 0; i < fromExcel.getNumberOfSheets(); i++) {//遍历每个sheet
                        HSSFSheet oldSheet = fromExcel.getSheetAt(i);
                        for (int o=0;o<=oldSheet.getLastRowNum();o++){
                            HSSFRow oldRow = (HSSFRow) oldSheet.getRow(o);
                            XSSFRow newRow = newSheet.createRow(m);
                            m++;
                            for (int n =0;n<oldRow.getLastCellNum();n++){
                                if (n ==2) {
                                    HSSFCell tmpCell = oldRow.getCell(n);
                                    XSSFCell newCell = newRow.createCell(0);
                                    newCell.setCellValue(tmpCell.getStringCellValue());
                                    break;
                                }
                            }
                        }
                    }
                }else{
                    for(int i = 0; i < fromExcel.getNumberOfSheets(); i++) {//遍历每个sheet
                        HSSFSheet oldSheet = fromExcel.getSheetAt(i);
                        for (int o=1;o<=oldSheet.getLastRowNum();o++){
                            HSSFRow oldRow = oldSheet.getRow(o);
                            XSSFRow newRow = newSheet.createRow(m);
                            m++;
                            for (int n =0;n<oldRow.getLastCellNum();n++){
                                if (n ==2) {
                                    HSSFCell tmpCell = oldRow.getCell(n);
                                    XSSFCell newCell = newRow.createCell(0);
                                    newCell.setCellValue(tmpCell.getStringCellValue());
                                    break;
                                }
                            }
                        }
                    }
                }
            }catch (Exception e ){
                System.out.println(sourceDir+"===="+file1.getName());
                e.printStackTrace();
            }
        }
        String allFileName=targetFile;
        FileOutputStream fileOut = new FileOutputStream(allFileName);
        newExcelCreat.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }



    public static void readCellType(String fileName) throws IOException {
        File file = new File(fileName);
        InputStream in = new FileInputStream(file);
        HSSFWorkbook fromExcel = new HSSFWorkbook(in);
        for(int i = 0; i < fromExcel.getNumberOfSheets(); i++) {//遍历每个sheet
            HSSFSheet oldSheet = fromExcel.getSheetAt(i);
            for (int o=0;o<=oldSheet.getLastRowNum();o++){
                HSSFRow oldRow = (HSSFRow) oldSheet.getRow(o);
                for (int n =0;n<oldRow.getLastCellNum();n++){
                    HSSFCell tmpCell = oldRow.getCell(n);

                   if(tmpCell  != null){
                       if(tmpCell.getCellType() != 0 && tmpCell.getCellType() != 1  && tmpCell.getCellType() != 4 ){
                           System.out.println(tmpCell.getCellType());
                       }
                   }
                }
            }
        }

    }
}
