/**
 * 
 */
package com.mobiquityinc.exception;

/**
 * Exception class will be thrown 
 * if line content was not match
 * @author aeltayary
 *
 */
public class InvalidLineContentException extends Exception {

	public InvalidLineContentException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InvalidLineContentException(String message) {
		super(message);
		
	}

}
