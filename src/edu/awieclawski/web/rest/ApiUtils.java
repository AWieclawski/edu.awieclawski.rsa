package edu.awieclawski.web.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import edu.awieclawski.cmd.utils.Calculator;
//import edu.awieclawski.web.models.Prime;
import edu.awieclawski.web.service.MessageService;
import edu.awieclawski.web.utils.NumberUtil;

@Singleton
public class ApiUtils {

	private final static Logger LOGGER = Logger.getLogger(ApiUtils.class.getName());

	public Integer isPrime(String number) {
		Calculator calc = new Calculator();
		NumberUtil nUtil = new NumberUtil();
		MessageService thisMsgServ = MessageService.getNewMessageService();
		int numberInt = -1;
		try {
			thisMsgServ = nUtil.getIntFromStringAndMsg(number);
		} catch (NumberFormatException ex) {
			LOGGER.log(Level.SEVERE, "get Integer from Input failed. Received: " + number);
		}
		if (thisMsgServ != null) {
			numberInt = thisMsgServ.getIntResult();
			if (numberInt < 0) { // not integer
				LOGGER.log(Level.SEVERE, Response.Status.NOT_ACCEPTABLE.toString() + "| Received:" + number);
			} else { // not prime
				if (!calc.isPrimeNumber(numberInt)) {
					LOGGER.log(Level.SEVERE, Response.Status.EXPECTATION_FAILED.toString() + "| Received:" + number);
					numberInt = 0;
				}
			}
		}
		return numberInt;
	}

}
