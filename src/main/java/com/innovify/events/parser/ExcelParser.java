package com.innovify.events.parser;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.innovify.events.dto.DocFile;
import com.innovify.events.dto.Row;
import com.innovify.events.exception.EventException;

public class ExcelParser{

	public DocFile parseFile(ParserRequest request) {

		DocFile file = new DocFile();
		try {
			
			String fileType = request.getFileType();
			String filename = request.getFilename();

			if (StringUtils.isEmpty(filename)) {
				return file;
			}

			switch (fileType) {
			case "XLS":
				file = parseFileXLS(request);
				break;
			case "XLSX":
				file = parseFileXLSX(request);
				break;
			default:
				break;
			}
		}catch (Exception e) {
			throw new EventException("Error in Parsing File",e);
		}
		return file;
	}

	public static DocFile parseFileXLSX(ParserRequest request) throws Exception {

		DocFile file = new DocFile();

		String filename = request.getFilename();
		int headerIndex = request.getHeaderIndex();
		int rowStartIndex = request.getRowStartIndex();
		InputStream ExcelFileToRead = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		Map<Integer, String> headerMap = new HashMap<>();
		int counter = 0;
		file.setHeaders(headerMap);
		while (rows.hasNext()) {
			System.out.println("Row count => " + counter);
			row = (XSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			Row fileRow = new Row();
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();
				String value = getCellValue(cell);
				System.out.println("Cell Value = " + value);

				if (StringUtils.isEmpty(value)) {
					continue;
				}

				if (counter == headerIndex && StringUtils.isNotEmpty(value)) {
					headerMap.put(cell.getColumnIndex(), value);
				} else if (counter >= rowStartIndex && StringUtils.isNotEmpty(value)
						&& headerMap.containsKey(cell.getColumnIndex())) {
					fileRow.setValue(headerMap.get(cell.getColumnIndex()), value);
				}
			}
			if (counter >= rowStartIndex) {
				file.addRow(fileRow);
			}
			counter++;
		}

		if (wb != null) {
			wb.close();
		}
		return file;
	}

	private static DocFile parseFileXLS(ParserRequest request) throws Exception {
		DocFile file = new DocFile();

		String filename = request.getFilename();
		int headerIndex = request.getHeaderIndex();
		int rowStartIndex = request.getRowStartIndex();
		InputStream ExcelFileToRead = new FileInputStream(filename);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row;
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();
		Map<Integer, String> headerMap = new HashMap<>();
		int counter = 0;
		file.setHeaders(headerMap);
		while (rows.hasNext()) {
			System.out.println("Row count => " + counter);
			counter++;
			row = (HSSFRow) rows.next();
			Iterator<Cell> cells = row.cellIterator();
			Row fileRow = new Row();
			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				String value = getCellValue(cell);

				if (StringUtils.isEmpty(value)) {
					continue;
				}

				if (counter == headerIndex && StringUtils.isNotEmpty(value)) {
					headerMap.put(cell.getColumnIndex(), value);
				} else if (counter >= rowStartIndex && StringUtils.isNotEmpty(value)
						&& headerMap.containsKey(cell.getColumnIndex())) {
					fileRow.setValue(headerMap.get(cell.getColumnIndex()), value);
				}
			}
			if (counter >= rowStartIndex) {
				file.addRow(fileRow);
			}
		}

		if (wb != null) {
			wb.close();
		}
		return file;
	}

	private static String getCellValue(Cell cell) throws Exception {

		String value = null;
		if (cell.getCellType() == CellType.STRING) {
			DataFormatter dataFormatter = new DataFormatter();
			value = dataFormatter.formatCellValue(cell);
		} else if (cell.getCellType() == CellType.NUMERIC) {
			DataFormatter dataFormatter = new DataFormatter();
			value = dataFormatter.formatCellValue(cell);
		} else if (cell.getCellType() == CellType.FORMULA) {
			DataFormatter dataFormatter = new DataFormatter();
			value = dataFormatter.formatCellValue(cell);
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			DataFormatter dataFormatter = new DataFormatter();
			value = dataFormatter.formatCellValue(cell);
			
		} else if (cell.getCellType() == CellType.BLANK) {
		} else {
			System.out.println("new cell type => " + cell.getCellType());
			throw new Exception("New type of cell format encountered. need to check");
		}
		return value;
	}

}
