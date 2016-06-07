package de.jonashackt.tutorial.common;

public class XmlUtilsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public XmlUtilsException(Throwable cause) {
		super(cause);
	}

	public XmlUtilsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public XmlUtilsException(String message) {
		super(message);
	}

}
