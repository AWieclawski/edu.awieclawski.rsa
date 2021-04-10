package edu.awieclawski.rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import edu.awieclawski.exceptions.NegativeNumberException;
import edu.awieclawski.exceptions.NoPrimeNumberException;
import edu.awieclawski.services.ComandlineController;
import edu.awieclawski.utils.Calculator;

public class RSAKeyPairGenerator {

	private static List<Integer> results = new ArrayList<>();
	private static int[] publicKey = new int[2];
	private static int[] privateKey = new int[2];

	public static void main(String[] args)
			throws NoPrimeNumberException, NegativeNumberException, InputMismatchException {
		ComandlineController cmdo = new ComandlineController();
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
		int e = cmdo.getAnyNumber(", that seems to be close to 'e'");
		System.out.println("List of few closest coprime numbers");
		results = calc.phiList(phin, e);
		if (results != null)
			System.out.println(results.toString());
		e = cmdo.getNumberFromList(" from above list ", results);
		publicKey[0] = e;
		int d = calc.privateKeyGenerator(e, n);
		privateKey[0] = d;
		if (publicKey != null)
			System.out.println("publicKey=" + Arrays.toString(publicKey));
		if (privateKey != null)
			System.out.println("privateKey=" + Arrays.toString(privateKey));
	}

}
