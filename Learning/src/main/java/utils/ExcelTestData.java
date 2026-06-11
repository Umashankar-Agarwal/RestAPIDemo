package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelTestData {

    // Identifying the test Column by scanning the entire first row
    // Identify the Purchase Test Case row
    // pull the data & feed into the test to apply the actions

    public ArrayList<String> getData(String SheetName , String testCaseName) throws IOException {

        ArrayList<String> arrayData = new ArrayList<>();
        FileInputStream fis = new FileInputStream("C:\\Users\\335418\\Downloads\\TestData.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        int wbNumberOfSheets = wb.getNumberOfSheets();

        for (int i = 0; i < wbNumberOfSheets; i++) {

            if (wb.getSheetName(i).equalsIgnoreCase(SheetName)) {
                // get access to the sheet
                XSSFSheet sheet = wb.getSheetAt(i);
                // get access to all the rows --> Sheet is a collection of rows
                Iterator<Row> rows = sheet.iterator();
                // Iterate to the first coloumn to get the required row
                Row firstRow = rows.next();
                // Get access to all the cells --> Row is the collection of cells
                Iterator<Cell> ce = firstRow.cellIterator();
                // get the exact testcase column
                int k = 0;
                int coloumn = 0;
                while (ce.hasNext()) {
                    Cell value = ce.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Testcases")) {

                        coloumn = k;
                    }
                    k++;
                }
                System.out.println(coloumn);


//                Identify the Purchase Test Case row
                while (rows.hasNext()) {
                    Row r = rows.next();
                    if (r.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                        Iterator<Cell> cv = r.cellIterator();
                        while (cv.hasNext()) {
                            Cell c = cv.next();
                            if(c.getCellType()== CellType.STRING) {
                                arrayData.add(c.getStringCellValue());
                            }else {
                                arrayData.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }
                        }

                    }
                }
            }
        }

        return arrayData;
    }
}
