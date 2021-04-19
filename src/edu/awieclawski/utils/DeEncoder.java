package edu.awieclawski.utils;

import java.util.ArrayList;
import java.util.List;

public class DeEncoder {

	/**
	 * simple Character to ASCII decoder
	 * 
	 * @param text
	 * @return List<Integer> list of decoded characters
	 */
	public List<Integer> getAsciiFromString(String text) {
		List<Integer> list = new ArrayList<>();

		for (int i = 0; i < text.length(); i++) {
			Integer a = (int) text.charAt(i);
			list.add(a);
		}
		return list;
	}

	/**
	 * 
	 * @param listInput, List<Integer> of source numbers
	 * @param e,         int public key part
	 * @param n,         int (p*q)
	 * @return List<Integer> list of encoded numbers
	 */
	public List<Integer> getRSAfromInt(List<Integer> listInput, int e, int n) {
		List<Integer> list = new ArrayList<>();

		for (Integer i : listInput) {
			int result = fastPowerModulo(i, e, n);
			list.add(result);
		}
		return list;
	}

	/**
	 * 
	 * @param listInput, List<Integer> of decoded numbers
	 * @return String, decoded text
	 */
	public String getStringFromAscii(List<Integer> listInput) {
		String result = "";
		for (int i : listInput) {
			result += Character.toString((char) i);
		}
		return result;
	}

	/**
	 * seems complicated but it works ;)
	 * http://www.algorytm.org/algorytmy-arytmetyczne/szybkie-potegowanie-modularne/spm-j.html
	 * 
	 * @param a, int source number
	 * @param e, int public key part
	 * @param n, int (p*q)
	 * @return int, encoded number
	 */
	private int fastPowerModulo(int a, int e, int n) {
		int i;
		int result = 1;
		long x = a % n;

		for (i = 1; i <= e; i <<= 1) {
			x %= n;
			if ((e & i) != 0) {
				result *= x;
				result %= n;
			}
			x *= x;
		}
		return result;
	}

}
