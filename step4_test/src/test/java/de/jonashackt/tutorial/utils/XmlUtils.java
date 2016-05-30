package de.jonashackt.tutorial.utils;

import java.io.InputStream;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;

import javax.jws.WebMethod;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlUtils {

	// private Constructor for Utility-Class
	private XmlUtils() {};
	
	public static <T> T readSoapMessageFromStreamAndUnmarshallBody2Object(InputStream fileStream, Class<T> jaxbClass) throws XmlUtilsException {
		T unmarshalledObject = null;
		try {
			Document soapMessage = parseFileStream2Document(fileStream);
			unmarshalledObject = getUnmarshalledObjectFromSoapMessage(soapMessage, jaxbClass);			
		} catch (Exception exception) {
			throw new XmlUtilsException("Problem beim unmarshalling des JAXBObjects " + jaxbClass.getSimpleName() + " aus der SoapMessage.", exception);
		}			
		return unmarshalledObject;
	}
	
	public static <T> T unmarshallXMLString(String xml, Class<T> jaxbClass) {
		return JAXB.unmarshal(new StringReader(xml), jaxbClass);
	}	
	
	public static <T> T getUnmarshalledObjectFromSoapMessage(Document httpBody, Class<T> jaxbClass) throws XmlUtilsException {
		T unmarshalledObject = null;
		try {
			String namespaceUri = getNamespaceUriFromJaxbClass(jaxbClass);
			Node nodeFromSoapMessage = httpBody.getElementsByTagNameNS(namespaceUri, getXmlTagNameFromJaxbClass(jaxbClass)).item(0);
			JAXBElement<T> jaxbElement = unmarshallNode(nodeFromSoapMessage, jaxbClass);
			unmarshalledObject = jaxbElement.getValue();
		} catch (Exception exception) {
			throw new XmlUtilsException("Die SoapMessage enthaelt keine Representation des JAXBObjects " + jaxbClass.getSimpleName(), exception);
		}
		return unmarshalledObject;
	}
	
	public static <T> JAXBElement<T> unmarshallNode(Node node, Class<T> jaxbClassName) throws XmlUtilsException {
		Objects.requireNonNull(node);
		JAXBElement<T> jaxbElement = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(jaxbClassName);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			jaxbElement = unmarshaller.unmarshal(new DOMSource(node), jaxbClassName);
		} catch (Exception exception) {
			throw new XmlUtilsException("Problem beim Unmarshalling der Node in das JAXBElement: " + exception.getMessage(), exception);
		}		
		return jaxbElement;
	}	
	
	public static <T> String getNamespaceUriFromJaxbClass(Class<T> jaxbClass) throws XmlUtilsException {
	    for(Annotation annotation: jaxbClass.getPackage().getAnnotations()){
	        if(annotation.annotationType() == XmlSchema.class){
	            return ((XmlSchema)annotation).namespace();
	        }
	    }
	    throw new XmlUtilsException("namespaceUri not found -> Is it really a JAXB-Class, thats used to call the method?");
	}
	
	public static <T> String getXmlTagNameFromJaxbClass(Class<T> jaxbClass) {
		String xmlTagName = "";
	    for(Annotation annotation: jaxbClass.getAnnotations()){
	        if(annotation.annotationType() == XmlRootElement.class){
	            xmlTagName = ((XmlRootElement)annotation).name();
	            break;
	        }
	    }
	    return xmlTagName;
	}
	
	public static <T> String getSoapActionFromJaxWsServiceInterface(Class<T> jaxWsServiceInterfaceClass, String jaxWsServiceInvokedMethodName) throws XmlUtilsException {
	    Method method = null;
        try {
            method = jaxWsServiceInterfaceClass.getDeclaredMethod(jaxWsServiceInvokedMethodName);
        } catch (Exception exception) {
            throw new XmlUtilsException("jaxWsServiceInvokedMethodName not found -> Is it really a Method of the JaxWsServiceInterfaceClass?");
        }       	    
	    return getSoapActionAnnotationFromMethod(method); 
    }
	
	public static <T> String getSoapActionFromJaxWsServiceInterface(Class<T> jaxWsServiceInterfaceClass) throws XmlUtilsException {
        Method method = null;
        try {
            // Getting any of the Webservice-Methods of the WebserviceInterface to get a valid SoapAction
            method = jaxWsServiceInterfaceClass.getDeclaredMethods()[0];
        } catch (Exception exception) {
            throw new XmlUtilsException("jaxWsServiceInvokedMethodName not found -> Is it really a Method of the JaxWsServiceInterfaceClass?");
        }            
        return getSoapActionAnnotationFromMethod(method); 
    }

    private static String getSoapActionAnnotationFromMethod(Method method) throws XmlUtilsException {
        for(Annotation annotation: method.getAnnotations()) {
            if(annotation.annotationType() == WebMethod.class) {
                return ((WebMethod)annotation).action();
            }
        }
        throw new XmlUtilsException("SoapAction from JaxWsServiceInterface not found");
    }
	
	
	
	public static Document parseFileStream2Document(InputStream contentAsStream) throws XmlUtilsException {
		Document parsedDoc = null;
		try {
	        parsedDoc = setUpDocumentBuilder().parse(contentAsStream);
		} catch (Exception exception) {
			throw new XmlUtilsException("Problem beim Parsen des InputStream in ein Document: " + exception.getMessage(), exception);
		}
		return parsedDoc;
	}

	private static DocumentBuilder setUpDocumentBuilder() throws XmlUtilsException {
		DocumentBuilder documentBuilder = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setNamespaceAware(true);
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException parserConfigurationException) {
			throw new XmlUtilsException("Problem beim Erstellen des DocumentBuilders: " + parserConfigurationException.getMessage(), parserConfigurationException);
		}
		return documentBuilder;
	}
	
	public static Document marhallJaxbElementIntoDocument(Object jaxbElement) throws XmlUtilsException {
		Document jaxbDoc = null;
		try {
			Marshaller marshaller = setUpMarshaller(jaxbElement.getClass());
			jaxbDoc = createNewDocument();        	
			marshaller.marshal(jaxbElement, jaxbDoc);			
		} catch (Exception exception) {
			throw new XmlUtilsException("Problem beim marshallen des JAXBElements in ein Document: " + exception.getMessage(), exception);
		}
        return jaxbDoc;
	}

	private static Document createNewDocument() throws XmlUtilsException {
		return setUpDocumentBuilder().newDocument();
	}

	private static <T> Marshaller setUpMarshaller(Class<T> jaxbElementClass) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(jaxbElementClass);
		return jaxbContext.createMarshaller();
	}
	
	
	public static Element appendAsChildElement2NewElement(Document document) throws XmlUtilsException {
		Document docWithDocumentAsChild = copyDocumentAsChildelementUnderNewDocument(document);
		return docWithDocumentAsChild.getDocumentElement();
	}

	private static Document copyDocumentAsChildelementUnderNewDocument(Document document) throws XmlUtilsException {
		Document docWithDocumentAsChild = createNewDocument();
		docWithDocumentAsChild.appendChild(docWithDocumentAsChild.createElement("root2kick"));			
		Node importedNode = docWithDocumentAsChild.importNode(document.getDocumentElement(), true);
		docWithDocumentAsChild.getDocumentElement().appendChild(importedNode);
		return docWithDocumentAsChild;
	}
	
}