package edu.awieclawski.cmd.utils;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

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
		int tmpPrim = -1;
		if (number == 1)
			results.add(2);
		else {
			tmpPrim = previousPrime(number);
			if (previousPrime(number) > 0) {
				results.add(tmpPrim);
				tmpPrim--;
			}
			tmpPrim = nextPrime(number);
			if (tmpPrim > 0) {
				results.add(tmpPrim);
				tmpPrim++;
			}
		}
		return results;
	}

	/**
	 * https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
	 * 
	 * @param N
	 * @return
	 */
	private int nextPrime(int N) {

		// Base case
		if (N <= 1)
			return 2;

		int prime = N;
		boolean found = false;

		// Loop continuously until isPrime returns
		// true for a number greater than n
		while (!found) {
			prime++;

			if (isPrimeNumber(prime))
				found = true;
		}

		return prime;
	}

	/**
	 * https://www.geeksforgeeks.org/program-to-find-the-next-prime-number/
	 * 
	 * @param N
	 * @return
	 */
	private int previousPrime(int N) {

		// Base case
		if (N <= 1)
			return 2;

		int prime = N;
		boolean found = false;

		// Loop continuously until isPrime returns
		// true for a number lower than n
		while (!found) {
			prime--;

			if (isPrimeNumber(prime))
				found = true;
		}

		return prime;
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
	 * http://www.algorytm.org/algorytmy-arytmetyczne/algorytm-euklidesa/euklides-j.html
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private int nwd(int a, int b) {
		while (a != b) {
			if (a > b)
				a -= b;
			else
				b -= a;
		}
		return a;
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
//			if (gcd(i, n) == 1)
			if (nwd(i, n) == 1)
				count++;
		}
		return count;
	}

	/**
	 * Presents list of few coprime numbers closest to 'max'
	 * 
	 * @param n
	 * @param number
	 * @return List<Integer>
	 */
	public List<Integer> phiList(int n, int number) {
		List<Integer> results = new ArrayList<>();
		boolean found = false;
		int coPrime = number;
		if (n == 1)
			results.add(n);
		else {
			while (!found) {
				coPrime--;
				if (gcd(coPrime, n) == 1) {
					results.add(coPrime);
					found = true;
				}
			}
			found = false;
			coPrime = number;
			while (!found) {
				coPrime++;
				if (gcd(coPrime, n) == 1) {
					results.add(coPrime);
					found = true;
				}
			}
		}
		return results;
	}

	/**
	 * check if 'e' is co-prime number. returns int > 0 if true.
	 * 
	 * @param n
	 * @param e, public key part
	 * @return int,
	 */
	public int isCoPrime(int n, int e) {
		int result = -1;
		if (n == 1)
			result = n;
		else if (gcd(n, e) == 1) {
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
	 * @return int, d
	 */
	public int privateKeyGenerator(int n, int e) {
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

	/**
	 * Returns about 10 % of the number
	 * 
	 * @param number, int @return, int
	 */
	private int getSurplus(int number) {
		int result = -1;
		if (number > 0)
			result = number / 10;
		return result;
	}

}
