package edu.awieclawski.cmd.rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import edu.awieclawski.cmd.services.CmdController;
import edu.awieclawski.cmd.utils.Calculator;
import edu.awieclawski.cmd.utils.DeEncoder;
import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoPrimeNumberException;

public class MainManager {

	private static List<Integer> results = new ArrayList<>();
	private static List<Integer> asciiList = new ArrayList<>();
	private static List<Integer> encodedList = new ArrayList<>();

	private static int[] publicKey = new int[2];
	private static int[] privateKey = new int[2];

	private static Calculator calc = new Calculator();

	public static void main(String[] args)
			throws NoPrimeNumberException, NegativeNumberException, InputMismatchException {
		CmdController cmdo = new CmdController();
		DeEncoder enc = new DeEncoder();

		int p = cmdo.getPrimeNumber("p");
		int q = cmdo.getPrimeNumber("q");
		int n = p * q;
		int phin = calc.phiEuler(n);
		System.out.println("The value of the Euler function for n=" + n + ": phi(n)=" + phin);
		if (publicKey != null && privateKey != null)
			publicKey[1] = privateKey[1] = n;
		System.out.println("Remember, n=" + n + " must be greater than the largest value of the encoded numbers");
		System.out.println("The first part of the public key 'e' should be chosen");
		int e = -1;
		int d = -1;
		while (d < 0) {
			e = cmdo.getAnyNumber("'e' candidate");
			if (calc.isCoPrime(n, e) < 0) {
				results = calc.phiList(phin, e);
				System.out.println("List of few closest coprime numbers");
				if (results != null)
					System.out.println(results.toString());
				e = cmdo.getNumberFromList(" from above list ", results);
			} else
				System.out.println("OK." + e + " is co-prime");
			publicKey[0] = e;
			d = calc.privateKeyGenerator(n, e);
			if (d < 0) {
				System.out.println("Ops! privateKey can not be calculated for e=" + e);
				if (cmdo.getAnyString(", or 'y' to auto-calculate").equals("y"))
					d = autoSearchRSAkey(n, e + 1);
			}
		}
		privateKey[0] = d;
		if (publicKey != null)
			System.out.println("publicKey=" + Arrays.toString(publicKey));
		if (privateKey != null)
			System.out.println("privateKey=" + Arrays.toString(privateKey));

		String toASCIItxt = cmdo.getAnyString(" to encode ");
		if (toASCIItxt != null)
			asciiList = enc.getAsciiFromString(toASCIItxt);
		if (asciiList != null) {
			System.out.println("Decoded ASCII=" + asciiList.toString());
			encodedList = enc.getRSAfromInt(asciiList, publicKey[0], n);
		}
		if (encodedList != null) {
			System.out.println("Encoded RSA=" + encodedList + " with public key " + Arrays.toString(publicKey));
			asciiList = enc.getRSAfromInt(encodedList, privateKey[0], n);
			if (asciiList != null) {
				System.out.println(
						"Decoded ASCII=" + asciiList.toString() + " with private key " + Arrays.toString(privateKey));
				String encodedTxt = enc.getStringFromAscii(asciiList);
				System.out.println("Encoded RSA=" + encodedTxt);
			}
		}

	}

	private static int autoSearchRSAkey(int n, int e) {
		int result = -1;
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
		publicKey[0] = e; // 'e' must be saved after all iterations
		if (d > 0)
			result = d;
		return result;
	}
}
