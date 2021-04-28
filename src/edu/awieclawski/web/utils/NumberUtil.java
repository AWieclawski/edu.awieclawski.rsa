package edu.awieclawski.web.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
				result.setInfo(nearestPrimeNumbersProposition(number));
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

	/**
	 * Presents list of nearest prime numbers
	 * 
	 * @param inputInt, int @return, List<Integer>
	 */
	private String nearestPrimeNumbersProposition(int inputInt) {
		List<Integer> list = null;
		String result = "";
		if (inputInt > 0) {
			list = new Calculator().nearestPrimeNumberList(inputInt);
			if (list != null) {
				result = "Nearest prime number propositions for " + inputInt + ":" + list.toString();

//				LOGGER.log(Level.INFO, result);

			}
		} else
			LOGGER.log(Level.SEVERE, "Probably no input number: " + inputInt);
		return result;
	}

	public int phiEuler(int n) {
		Calculator c = new Calculator();
		return c.phiEuler(n);
	}

	/**
	 * check if 'e' is co-prime number,
	 * 
	 * @param n
	 * @param e
	 * @return
	 */
	public MessageService getCoPrimeAndMsg(int n, int e) {
		MessageService result = new MessageService(-1, null, null);
		int x = -1;
		Calculator c = new Calculator();
		x = c.isCoPrime(n, e);
		if (x > 0)
			result.setIntResult(x);
		else {
			result.setError("Not coprime number!=" + e);
			List<Integer> list = new ArrayList<>();
			list = c.phiList(phiEuler(n), e);
			if (list != null)
				result.setInfo("List of few closest coprime numbers:" + list.toString());
		}
		return result;
	}

	public int privateKeyGenerator(int n, int e) {
		Calculator c = new Calculator();
		return c.privateKeyGenerator(n, e);
	}

	/**
	 * 
	 * @param n
	 * @param e
	 * @return
	 */
	public int[] autoSearchRSAkey(int n, int e) {
		Calculator calc = new Calculator();
		int[] key = new int[3];
		int d = -1;
		int ip = -1;

		while (ip < 0 || e > n) {
			ip = calc.isCoPrime(n, e);
			if (ip < 0)
				e++;
//			System.out.println("e=" + e);
		}
		while (d < 0 || e > n) {
			d = calc.privateKeyGenerator(n, e);
			if (d < 0)
				e++;
//			System.out.println("e=" + d + ",d=" + e);
		}
		if (d > 0) {
			key[0] = n; // 'n' not changed but...
			key[1] = e; // 'e' must be updated after all iterations
			key[2] = d; // 'd' must be saved if found any
		}
		return key;
	}

}
