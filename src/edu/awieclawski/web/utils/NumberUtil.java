package edu.awieclawski.web.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoCoPrimesException;
import edu.awieclawski.exceptions.NoPrimeNumberException;
import edu.awieclawski.web.service.MessageService;

/**
 * Used to check if isNumber, isPrime, converts String to Integer
 * 
 * @author AWieclawski
 *
 */
public class NumberUtil {
	private final static Logger LOGGER = Logger.getLogger(NumberUtil.class.getName());
	private Calculator calc = new Calculator();

	public MessageService getPrimeNumberAndMsgs(Integer number) throws ServletException, IOException {
		Integer x = -1;
		MessageService result = MessageService.getNewMessageService();

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

//		LOGGER.log(Level.INFO, "primeNumbersHandlingReturn=" + x + "|" + result.toString());

		return result;
	}

	private Integer isPrimeNumberCheck(Integer number) throws NoPrimeNumberException, NegativeNumberException {
		Integer x = -1;

		if (!calc.isPrimeNumber(number))
			throw new NoPrimeNumberException();

		if (number < 0)
			throw new NegativeNumberException();

		x = number;

//		LOGGER.log(Level.INFO, "primeNumberServiceReturn=" + x);

		return x;
	}

	public MessageService getIntFromStringAndMsg(String txt) {
		MessageService result = MessageService.getNewMessageService();

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

	private boolean isNumeric(String str) throws NumberFormatException {
		if (str != null)
			if (str.matches("[0-9.]+"))
				return true;
			else
				throw new NumberFormatException();
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
		return calc.phiEuler(n);
	}

	/**
	 * check if 'e' is co-prime number,
	 * 
	 * @param n
	 * @param e
	 * @return
	 */
	public MessageService getCoPrimeAndMsg(int n, int e, int phi) {
		MessageService result = MessageService.getNewMessageService();
		if (e >= n)
			result.setError("'e' must be lower number than 'n'=" + n);
		else {
			int x = -1;
			try {
				x = isCoPrimeCheck(phi, e);
				result.setIntResult(x);
			} catch (NoCoPrimesException ex) {
				String errMsg = ex.getMessage() + ":[" + n + "," + e + "]";
				result.setError(errMsg);
				LOGGER.log(Level.WARNING, errMsg);
				List<Integer> list = new ArrayList<>();
				list = calc.phiList(phi, e);
				if (list != null)
					result.setInfo("List of few closest coprime numbers for n=" + n + ":" + list.toString());
			}
		}
		return result;
	}

	/**
	 * Modification of edu.awieclawski.cmd.utils.Calculator.isCoPrime method with
	 * new NoCoPrimesException
	 * 
	 * @param n
	 * @param e
	 * @return int
	 * @throws NoCoPrimesException
	 */
	private int isCoPrimeCheck(int n, int e) throws NoCoPrimesException {
		int x = -1;
		x = calc.isCoPrime(n, e);
		if (x > 0)
			return x;
		else
			throw new NoCoPrimesException();
	}

	public int privateKeyGenerator(int n, int e) {
		return calc.privateKeyGenerator(n, e);
	}

	/**
	 * 
	 * @param n
	 * @param e
	 * @return
	 */
	public int[] autoSearchRSAkey(int n, int e) {
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
