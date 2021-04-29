package edu.awieclawski.exceptions;

public class NoCoPrimesException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1156531783046863551L;
	
	private String messageTxt = "The numbers entered are not Coprime Numbers!";
	
    @Override
    public String getMessage() {
        return messageTxt;
    }

}
