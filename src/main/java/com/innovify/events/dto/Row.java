package com.innovify.events.dto;

import java.util.HashMap;

public class Row {

	private HashMap<String, Object> rowData = new HashMap<>();

	/**
	 * @return the rowData
	 */
	public HashMap<String, Object> getRowData() {
		return rowData;
	}

	/**
	 * @param rowData the rowData to set
	 */
	public void setRowData(HashMap<String, Object> rowData) {
		if (rowData != null) {
			this.rowData = rowData;
		}
	}

	public Object getValue(String header) {
		return rowData.get(header);
	}

	public void setValue(String header, String value) throws Exception {
		if (header.contains(".")) {
			header = header.replaceAll("\\.", "_");
		}
		this.rowData.put(header, value);
	}

	public int size() {
		return rowData.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Row [rowData=" + rowData + "]";
	}

}
