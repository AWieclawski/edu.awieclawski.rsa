package edu.awieclawski.exceptions;

public class NegativeNumberException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9021244546350237746L;
	private String messageTxt = "The number entered must not be negative!";
	
    @Override
    public String getMessage() {
        return messageTxt;
    }
}
