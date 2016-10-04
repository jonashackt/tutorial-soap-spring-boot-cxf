package de.jonashackt.tutorial.xmlvalidation;

import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.common.FaultType;
import de.codecentric.cxf.soaprawclient.SoapRawClient;
import de.codecentric.cxf.soaprawclient.SoapRawClientResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(
		classes=XmlErrorSystemTestApplication.class,
		webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT,
		properties = {"server.port=8087"}
)
public class WeatherServiceXmlErrorSystemTest {

	@Autowired
	private SoapRawClient soapRawClient;
	
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorNotXmlSchemeCompliantUnderRootElementTest.xml")
	private Resource xmlErrorNotXmlSchemeCompliantUnderRootElementTestXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorNotXmlSchemeCompliantRootElementTest.xml")
	private Resource xmlErrorNotXmlSchemeCompliantRootElementTestXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapHeaderMissingSlash.xml")
	private Resource xmlErrorSoapHeaderMissingSlashXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapBodyTagMissingBracketTest.xml")
	private Resource xmlErrorSoapBodyTagMissingBracketTestXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapHeaderTagMissingBracketTest.xml")
	private Resource xmlErrorSoapHeaderTagMissingBracketTestXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapEnvelopeTagMissingBracketTest.xml")
	private Resource xmlErrorSoapEnvelopeTagMissingBracketTestXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorXMLHeaderDefinitionMissingBracket.xml")
	private Resource xmlErrorXMLHeaderDefinitionMissingBracketXml;
	
	@Value(value="classpath:requests/xmlerrors/xmlErrorXMLTagNotClosedInsideBodyTest.xml")
	private Resource xmlErrorXMLTagNotClosedInsideBodyTestXml;
	
	
	
	/*
	 * Non-Scheme-compliant Errors
	 */
	
	@Test
	public void xmlErrorNotXmlSchemeCompliantUnderRootElementTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantUnderRootElementTestXml, FaultType.SCHEME_VALIDATION_ERROR);
	}
	
	@Test
	public void xmlErrorNotXmlSchemeCompliantRootElementTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantRootElementTestXml, FaultType.SCHEME_VALIDATION_ERROR);
	}
	
	@Test
	public void xmlErrorSoapHeaderMissingSlash() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorSoapHeaderMissingSlashXml, FaultType.SCHEME_VALIDATION_ERROR);
	}	
	
	/*
	 * Errors with syntactically incorrect XML
	 */
	
	@Test
	public void xmlErrorSoapBodyTagMissingBracketTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorSoapBodyTagMissingBracketTestXml, FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorSoapHeaderTagMissingBracketTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorSoapHeaderTagMissingBracketTestXml, FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorSoapEnvelopeTagMissingBracketTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorSoapEnvelopeTagMissingBracketTestXml, FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorXMLHeaderDefinitionMissingBracket() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorXMLHeaderDefinitionMissingBracketXml, FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}	
	
	@Test
	public void xmlErrorXMLTagNotClosedInsideBodyTest() throws IOException, BootStarterCxfException {
		checkXmlError(xmlErrorXMLTagNotClosedInsideBodyTestXml, FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	
	private void checkXmlError(Resource testFile, FaultType faultContent) throws IOException, BootStarterCxfException {
		// When
		SoapRawClientResponse soapRawResponse = soapRawClient.callSoapService(testFile.getInputStream());
		
		// Then
		assertNotNull(soapRawResponse);
		assertEquals("500 Internal Server Error expected", 500, soapRawResponse.getHttpStatusCode());
        assertEquals(faultContent.getMessage(), soapRawResponse.getFaultstringValue());
        
        de.codecentric.namespace.weatherservice.exception.WeatherException weatherException = soapRawResponse.getUnmarshalledObjectFromSoapMessage(de.codecentric.namespace.weatherservice.exception.WeatherException.class);		
		assertNotNull("<soap:Fault><detail> has to contain a de.codecentric.namespace.weatherservice.exception.WeatherException",  weatherException);
		
		assertEquals("ExtremeRandomNumber", weatherException.getUuid());
		assertEquals("The correct BusinessId is missing in WeatherException according to XML-scheme.", faultContent.getId(), weatherException.getBusinessErrorId());
	}
}
