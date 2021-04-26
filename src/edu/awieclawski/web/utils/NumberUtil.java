package edu.awieclawski.web.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoPrimeNumberException;
import edu.awieclawski.utils.Calculator;
import edu.awieclawski.web.service.MessageService;

/**
 * Used to check if isNumber, isPrime, converts String to Integer
 * 
 * @author AWieclawski
 *
 */
public class NumberUtil {
	private final static Logger LOGGER = Logger.getLogger(NumberUtil.class.getName());

	public MessageService getPrimeNumberAndMsgs(Integer number) throws ServletException, IOException {
		Integer x = -1;
		MessageService result = new MessageService(x, null, null);

		if (number != null) {
			try {
				x = isPrimeNumberCheck(number);
				result.setIntResult(x);
			} catch (NoPrimeNumberException e) {
				result.setError(e.getMessage() + "=" + number);
				LOGGER.log(Level.SEVERE, e.getMessage() + "=" + number, e);
			} catch (NegativeNumberException e) {
				result.setError(e.getMessage() + "=" + number);
				LOGGER.log(Level.SEVERE, e.getMessage() + "=" + number, e);
			}
		}

//		LOGGER.log(Level.INFO, "primeNumbersHandlingReturn=" + x);

		return result;
	}

	private Integer isPrimeNumberCheck(Integer number) throws NoPrimeNumberException, NegativeNumberException {
		Calculator c = new Calculator();
		Integer x = -1;

		if (!c.isPrimeNumber(number))
			throw new NoPrimeNumberException();

		if (number < 0)
			throw new NegativeNumberException();

		x = number;

//		LOGGER.log(Level.INFO, "primeNumberServiceReturn=" + x);

		return x;
	}

	public MessageService getIntFromStringAndMsg(String txt) {
		MessageService result = new MessageService(-1, null, null);

		if (isNumeric(txt))
			try {
				Integer number = Integer.valueOf(txt);
				result.setIntResult(number);
			} catch (NumberFormatException ex) {
				result.setError(ex.getMessage() + "=" + txt);
				LOGGER.log(Level.SEVERE, "getIntFromString failed", ex);
			}
		return result;
	}

	private boolean isNumeric(String str) {
		if (str != null)
			return str != null && str.matches("[0-9.]+");
		return false;
	}

}
