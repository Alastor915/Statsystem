package com.statsystem.utils;

import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by User on 24.11.2016.
 *
 */
public class Parser {
    public static List<Sample> parse(String file) throws ParseException, IOException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
        ArrayList<Date> dates = new ArrayList<>();
        ArrayList<Sample> samples = new ArrayList<>();
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet excelSheet = excelBook.getSheetAt(0);
        Iterator<Row> rowIterator = excelSheet.iterator();
        rowIterator.next();
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                String date = row.getCell(0).getStringCellValue();
                dates.add(format.parse(date));
            }
        }
        rowIterator = excelSheet.iterator();
        rowIterator.next();
        int columnCount;
        if(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            columnCount = row.getLastCellNum();
        }
        else return Collections.EMPTY_LIST;

        System.out.println(columnCount);

        for(int i = 1; i < columnCount; ++i) {
            String sampleName = "";
            rowIterator = excelSheet.iterator();
            ArrayList<Unit> units = new ArrayList<>();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(i) != null && row.getCell(i).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    double value = row.getCell(i).getNumericCellValue();
                    Unit unit = new Unit();
                    unit.setDate(new Long(dates.get(row.getRowNum() - 1).getTime()).doubleValue());
                    unit.setValue(value);
                    units.add(unit);
                }
                if (row.getCell(i) != null && row.getCell(i).getCellType() == XSSFCell.CELL_TYPE_STRING) {
                    sampleName = row.getCell(i).getStringCellValue();
                }
            }
            Sample sample = new Sample();
            sample.setData(units);
            sample.setName(sampleName);
            samples.add(sample);
        }
        System.out.println(samples.size());
        return samples;
    }
}
