package de.jonashackt.tutorial.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.jonashackt.tutorial.SimpleBootCxfSystemTestApplication;
import de.jonashackt.tutorial.common.FaultConst;
import de.jonashackt.tutorial.common.InternalBusinessException;
import de.jonashackt.tutorial.utils.SoapRawClient;
import de.jonashackt.tutorial.utils.SoapRawClientResponse;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SimpleBootCxfSystemTestApplication.class)
@WebIntegrationTest("server.port:8090") 
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
	public void xmlErrorNotXmlSchemeCompliantUnderRootElementTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantUnderRootElementTestXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}
	
	@Test
	public void xmlErrorNotXmlSchemeCompliantRootElementTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantRootElementTestXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}
	
	@Test
	public void xmlErrorSoapHeaderMissingSlash() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorSoapHeaderMissingSlashXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}	
	
	/*
	 * Errors with syntactically incorrect XML
	 */
	
	@Test
	public void xmlErrorSoapBodyTagMissingBracketTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorSoapBodyTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorSoapHeaderTagMissingBracketTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorSoapHeaderTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorSoapEnvelopeTagMissingBracketTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorSoapEnvelopeTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	@Test
	public void xmlErrorXMLHeaderDefinitionMissingBracket() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorXMLHeaderDefinitionMissingBracketXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}	
	
	@Test
	public void xmlErrorXMLTagNotClosedInsideBodyTest() throws InternalBusinessException, IOException {
		checkXmlError(xmlErrorXMLTagNotClosedInsideBodyTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}
	
	
	private void checkXmlError(Resource testFile, FaultConst faultContent) throws InternalBusinessException, IOException {
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
