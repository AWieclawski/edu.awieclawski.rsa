package edu.awieclawski.rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoPrimeNumberException;
import edu.awieclawski.services.CmdController;
import edu.awieclawski.utils.Calculator;
import edu.awieclawski.utils.Encoder;

public class RsaGenEncoder {

	private static List<Integer> results = new ArrayList<>();
	private static List<Integer> asciiList = new ArrayList<>();
	private static List<Integer> encodedList = new ArrayList<>();

	private static int[] publicKey = new int[2];
	private static int[] privateKey = new int[2];

	public static void main(String[] args)
			throws NoPrimeNumberException, NegativeNumberException, InputMismatchException {
		CmdController cmdo = new CmdController();
		Encoder enc = new Encoder();
		Calculator calc = new Calculator();

		int p = cmdo.getPrimeNumber("p");
		int q = cmdo.getPrimeNumber("q");
		int n = p * q;
		int phin = calc.phiEuler(n);
		System.out.println("The value of the Euler function for n=" + n + ": phi(n)=" + phin);
		if (publicKey != null && privateKey != null)
			publicKey[1] = privateKey[1] = n;

		System.out.println("Remember, n=" + n + " must be greater than the largest value of the encoded numbers");
		System.out.println("The first part of the public key 'e' should be chosen");
		int e = cmdo.getAnyNumber("'e' candidate");
		if (calc.isCoPrime(n, e) < 0) {
			results = calc.phiList(phin, e);
			System.out.println("List of few closest coprime numbers");
			if (results != null)
				System.out.println(results.toString());
			e = cmdo.getNumberFromList(" from above list ", results);
		} else
			System.out.println("OK." + e + " is co-prime");
		publicKey[0] = e;
		int d = calc.privateKeyGenerator(e, n);
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
			encodedList = enc.getRSAfromInt(asciiList, e, n);
		}
		if (encodedList != null) {
			System.out.println("Encoded RSA=" + encodedList + " with public key " + Arrays.toString(publicKey));
		}
	}

}
