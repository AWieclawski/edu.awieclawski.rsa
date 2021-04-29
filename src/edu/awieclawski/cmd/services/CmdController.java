package edu.awieclawski.cmd.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoPrimeNumberException;
import edu.awieclawski.exceptions.NumberOutOfListException;

public class CmdController {

	private Calculator c = new Calculator();
	private Scanner scanNumbers = new Scanner(System.in);
	private Scanner scanString = new Scanner(System.in);
	private int inputInt = -1;

	/**
	 * asks the user to enter a prime number.
	 * 
	 * @param desc
	 * @return int
	 * @throws NoPrimeNumberException
	 * @throws NegativeNumberException
	 */
	private int inputPrimeNumber(String desc) throws NoPrimeNumberException, NegativeNumberException {
		int x = 0;
		System.out.println("Please enter a prime number for " + desc + ": ");
		try {
			inputInt = scanNumbers.nextInt();
			x = inputInt;
		} catch (InputMismatchException e) {
			scanNumbers.next();
			throw new InputMismatchException();
		}
		if (!c.isPrimeNumber(x))
			throw new NoPrimeNumberException();
		if (x < 0)
			throw new NegativeNumberException();
		return x;
	}

	/**
	 * accesses the enterPrimeNumber method and asks the user for 'a'
	 * 
	 * @param desc
	 * @return
	 */
	public int getPrimeNumber(String desc) {
		boolean finish = false;
		int n = 0;
		int m = 0;
		while (!finish) {
			try {
				m = inputPrimeNumber(desc);
				n = m;
				finish = true;
			} catch (NoPrimeNumberException e) {
				print(e.getMessage());
				nearestPrimeNumbersPropositions();
			} catch (NegativeNumberException e) {
				print(e.getMessage());
			} catch (InputMismatchException e) {
				print(e.getMessage());
			}
		}
		return n;
	}

	/**
	 * 
	 * @param txt
	 * @return
	 */
	public int getAnyNumber(String txt) {
		boolean finish = false;
		int n = 0;
		while (!finish) {
			try {
				int m = inputAnyNumber(txt);
				n = m;
				finish = true;
			} catch (NegativeNumberException e) {
				print(e.getMessage());
			} catch (InputMismatchException e) {
				print("It is not correct input!");
			}
		}
		return n;
	}

	/**
	 * 
	 * @param desc
	 * @return
	 * @throws NegativeNumberException
	 */
	private int inputAnyNumber(String desc) throws NegativeNumberException {
		int x = 0;
		System.out.println("Please enter any number " + desc + ": ");
		try {
			int n = scanNumbers.nextInt();
			x = n;
		} catch (InputMismatchException e) {
			scanNumbers.next();
			throw new InputMismatchException();
		}
		if (x < 0)
			throw new NegativeNumberException();
		return x;
	}

	/**
	 * 
	 * @param txt
	 * @param list
	 * @return
	 */
	public int getNumberFromList(String txt, List<Integer> list) {
		boolean finish = false;
		int n = 0;
		while (!finish) {
			try {
				int m = inputNumberFromList(txt, list);
				n = m;
				finish = true;
			} catch (NegativeNumberException e) {
				print(e.getMessage());
			} catch (InputMismatchException e) {
				print(e.getMessage());
			} catch (NumberOutOfListException e) {
				print(e.getMessage());
			}
		}
		return n;
	}

	/**
	 * 
	 * @param desc
	 * @param list
	 * @return
	 * @throws NegativeNumberException
	 * @throws NumberOutOfListException
	 */
	private int inputNumberFromList(String desc, List<Integer> list)
			throws NegativeNumberException, NumberOutOfListException {
		int x = 0;
		int n = 0;
		System.out.println("Please enter any number " + desc + ": ");
		try {
			n = scanNumbers.nextInt();
			x = n;
		} catch (InputMismatchException e) {
			scanNumbers.next();
			throw new InputMismatchException();
		}
		if (x < 0)
			throw new NegativeNumberException();
		if (!isNumberOnTheList(n, list))
			throw new NumberOutOfListException();
		return x;
	}

	/**
	 * 
	 * @param n
	 * @param list
	 * @return
	 */
	private boolean isNumberOnTheList(int n, List<Integer> list) {
		boolean result = false;
		for (Integer tmp : list) {
			if (tmp.equals(n))
				return true;
		}
		return result;
	}

	/**
	 * Presents list of nearest prime numbers
	 */
	private void nearestPrimeNumbersPropositions() {
		if (inputInt > 0) {
			List<Integer> list = new Calculator().nearestPrimeNumberList(inputInt);
			if (list != null)
				print("Nearest prime number propositions: " + list.toString());
		} else
			print("Probably no input number: " + inputInt);
	}

	/**
	 * 
	 * @param txt
	 * @return String
	 * 
	 */
	public String getAnyString(String txt) {
		boolean finish = false;
		String n = "";
		while (!finish) {
			String m = inputAnyString(txt);
			n = m;
			finish = true;
		}
		return n;
	}

	/**
	 * 
	 * @param desc
	 * @return String
	 * 
	 */
	private String inputAnyString(String desc) {
		String txt = "";
		System.out.println("Please enter any text " + desc + ": ");
		String n = scanString.nextLine();
		txt = n;
		return txt;
	}

	/**
	 * Simple print line at console
	 * 
	 * @param txt
	 */
	private void print(String txt) {
		System.out.println(txt);
	}

}
