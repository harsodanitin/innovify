package com.innovify.events.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DocFile {

	String filename;

	private Map<Integer, String> headers = new HashMap<>();

	private List<Row> rows = new ArrayList<>();

	/**
	 * @return the headers
	 */
	public Map<Integer, String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<Integer, String> headers) {
		this.headers = headers;
	}

	/**
	 * @return the rows
	 */
	public List<Row> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public void addRow(Row row) {
		this.rows.add(row);
	}

	public Row getRow(int rowNum) {
		if (rows == null || rows.size() < rowNum) {
			return null;
		}

		return rows.get(rowNum);
	}

	public Object getValue(int rowNum, String header) {
		if (rows == null || rows.size() < rowNum || headers == null || !headers.containsKey(header)) {
			return null;
		}

		Row row = rows.get(rowNum);
		return row.getValue(header);
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DocFile [filename=" + filename + ", headers=" + headers + ", rows=" + rows + "]";
	}
	
}
