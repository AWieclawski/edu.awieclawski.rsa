package edu.awieclawski.web.models;

public enum Attributes {

	CTX_PTH_A("contextPath_A", "white", null), ERROR_A("error_A", "red", null), INFO_A("info_A", "blue", null);

	String color;
	String name;
	String param; // parameter name if any

	private Attributes(String thisName, String thisColor, String thisParam) {
		color = thisColor;
		name = thisName;
		param = thisParam;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

}