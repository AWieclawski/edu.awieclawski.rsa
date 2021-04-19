package edu.awieclawski.utils;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

	private int surplus = 9;
	private int fewNumbers = 5;

	/**
	 * determines if a given number 'n' is a prime or not.
	 * 
	 * @param n
	 * @return boolean
	 */
	public boolean isPrimeNumber(int n) {
		if (n == 1 || n == 0)
			return false;
		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * presents few nearest primenumbers
	 * 
	 * @param number
	 * @return
	 */
	public List<Integer> nearestPrimeNumberList(int number) {
		List<Integer> results = new ArrayList<>();
		int count = 0;
		if (number == 1)
			results.add(2);
		else
			for (int i = number + surplus; i > 0; i--) {
				if (isPrimeNumber(i)) {
					results.add(i);
					count++;
				}
				if (count > fewNumbers)
					return results;
			}
		return results;
	}

	/**
	 * determines the greatest common divisor of 'a' and 'b' numbers.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private int gcd(int a, int b) {
		if (a == b || b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	/**
	 * Euler's phi function specifies the number of coprime numbers for 'n'.
	 * 
	 * @param n
	 * @return int
	 */
	public int phiEuler(int n) {
		if (n == 1)
			return 1;
		int count = 0;
		for (int i = 1; i < n; i++) {
			if (gcd(i, n) == 1)
				count++;
		}
		return count;
	}

	/**
	 * Presents list of few coprime numbers closest to 'max'
	 * 
	 * @param n
	 * @param max
	 * @return List<Integer>
	 */
	public List<Integer> phiList(int n, int max) {
		List<Integer> results = new ArrayList<>();
		int count = 0;
		if (n == 1)
			results.add(n);
		else
			for (int i = max + surplus; i > 0; i--) {
				if (gcd(i, n) == 1) {
					results.add(i);
					count++;
				}
				if (count > fewNumbers)
					return results;
			}
		return results;
	}

	/**
	 * check if 'e' is coprime number. returns int > 0 if true.
	 * 
	 * @param n
	 * @param e, public key part
	 * @return int,
	 */
	public int isCoPrime(int n, int e) {
		int result = -1;
		if (n == 1)
			result = n;
		else if (gcd(e, n) == 1) {
			result = e;
		}
//		System.out.println(">>n=" + n + "e=" + e);
		return result;
	}

	/**
	 * generates a private key 'd' using the public key 'e' and the product the
	 * initial prime numbers 'p' and 'q' .
	 * 
	 * @param e
	 * @param n
	 * @return ind, d
	 */
	public int privateKeyGenerator(int e, int n) {
		int result = -1;
		for (int i = 2; i < phiEuler(n); i++) {
			// progress points
			if (i % 100 == 0)
				System.out.print(".");
			if (i % 10000 == 0)
				System.out.println(".");
			if (i * e % phiEuler(n) == 1)
				result = i;
		}
		System.out.println(".");
		return result;
	}

}
