package main.java.exception;

import org.json.JSONException;

public class InputException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String mErrorCode;

	public InputException(String errorCode, String message) throws JSONException {
		super(message);
		setErrorCode(errorCode);
	}
	  
	  public String getErrorCode() {
		  return mErrorCode;
	  }
	  
	  public void setErrorCode(String errorCode) {
		  this.mErrorCode = errorCode;
	  }

}
