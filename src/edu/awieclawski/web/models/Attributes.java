package edu.awieclawski.web.models;

public enum Attributes {

	CTX_PTH_A("contextPath_A", "white", null), ERROR_A("error_A", "orange", null), INFO_A("info_A", "cyan", null),
	Q_NUM_A("qNumber_A", "lime", "qNumber_P"), P_NUM_A("pNumber_A", "lime", "pNumber_P"),
	N_A("nNumber_A", "lime", "nNumber_P"), PHI_A("fiNumber_A", "lime", "fiNumber_P"),
	E_A("eNumber_A", "lime", "eNumber_P"), D_A("dNumber_A", "lime", "dNumber_P"),
	D_SUCC_A("dSucces_A", "cyan", "dSucces_P"), FINISH_A("finish_A", "cyan", "finish_P"),
	TESTKEYS_A("keysTestStart_A", "lime", "keysTestStart_P"), AUTO_A("autoSearch_A", "lime", "autoSearch_P"),
	ASCII_EN_A("asciiEn_A", "lime", "asciiEn_P"), ASCII_DE_A("asciiDe_A", "cyan", "asciiDe_P"),
	RSA_EN_A("rsaEn_A", "lime", "rsaEn_P"), TEXT_EN_A("textEn_A", "lime", "textEn_P"),
	TEXT_DE_A("textDe_A", "cyan", "textDe_P"), PUBLIC_KEY_A("publicKey_A", "magenta", "publicKey_P"),
	PRIVATE_KEY_A("privateKey_A", "magenta", "privateKey_P"), RSA_SUCC_A("rsaSucces_A", "cyan", "rsaSucces_P");

	String color;
	String name;
	String param; // parameter name if any

	private Attributes(String thisName, String thisColor, String thisParam) {
		name = thisName;
		color = thisColor;
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