package de.jonashackt.tutorial.common;

/**
 * Ids and Messages for generation of SoapFaults according to WeatherException
 */
public enum FaultConst {

	SUCCESSFULL_PROCESSING("01", "The Weather-Call was successfully processed by the backend."),
	SCHEME_VALIDATION_ERROR("07", "XML-Scheme-validiation failed."),
	SYNTACTICALLY_INCORRECT_XML_ERROR("08", "Syntactically incorrect XML."),	
	BACKEND_PROCESSING_FAILED("04", "Backend processing failed.");

	private String id;
	private String message;
	
	private FaultConst(String id, String text) {
		this.id = id;
		this.message = text;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getId() {
		return id;
	}
	
}
