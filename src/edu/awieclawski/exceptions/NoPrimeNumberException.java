package edu.awieclawski.exceptions;

public class NoPrimeNumberException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5155608441561869921L;
	private String messageTxt = "The number entered is not a prime number!";
	
    @Override
    public String getMessage() {
        return messageTxt;
    }

}
