package edu.awieclawski.exceptions;

public class NumberOutOfListException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4839959776262710445L;
	private String messageTxt = "The number entered is out of the list!";
    @Override
    public String getMessage() {
        return messageTxt;
    }
	
}
