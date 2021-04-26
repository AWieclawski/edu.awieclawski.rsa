package edu.awieclawski.web.models;

public enum Attributes {

	CTX_PTH_A("contextPath_A", "white", null), ERROR_A("error_A", "orange", null), INFO_A("info_A", "cyan", null),
	Q_NUM_A("qNumber_A", "lime", "qNumber_P"), P_NUM_A("pNumber_A", "lime", "pNumber_P"),
	N_A("nNumber_A", "lime", "nNumber_P"), PHI_A("fiNumber_A", "lime", "fiNumber_P"),
	E_A("eNumber_A", "lime", "eNumber_P"), D_A("dNumber_A", "lime", "dNumber_P");

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

	public String getParam() {
		return param;
	}

}