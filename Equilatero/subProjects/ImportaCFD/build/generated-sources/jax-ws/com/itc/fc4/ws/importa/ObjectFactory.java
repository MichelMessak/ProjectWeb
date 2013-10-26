
package com.itc.fc4.ws.importa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.itc.fc4.ws.importa package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ImportarCFDResponse_QNAME = new QName("http://ws.fc4.itc.com/", "ImportarCFDResponse");
    private final static QName _ImportarCFD_QNAME = new QName("http://ws.fc4.itc.com/", "ImportarCFD");
    private final static QName _ImportarCFDErrorResponse_QNAME = new QName("http://ws.fc4.itc.com/", "ImportarCFDErrorResponse");
    private final static QName _ImportarCFDError_QNAME = new QName("http://ws.fc4.itc.com/", "ImportarCFDError");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.itc.fc4.ws.importa
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImportarCFD }
     * 
     */
    public ImportarCFD createImportarCFD() {
        return new ImportarCFD();
    }

    /**
     * Create an instance of {@link ImportarCFDErrorResponse }
     * 
     */
    public ImportarCFDErrorResponse createImportarCFDErrorResponse() {
        return new ImportarCFDErrorResponse();
    }

    /**
     * Create an instance of {@link ImportarCFDError }
     * 
     */
    public ImportarCFDError createImportarCFDError() {
        return new ImportarCFDError();
    }

    /**
     * Create an instance of {@link ImportarCFDResponse }
     * 
     */
    public ImportarCFDResponse createImportarCFDResponse() {
        return new ImportarCFDResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportarCFDResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.fc4.itc.com/", name = "ImportarCFDResponse")
    public JAXBElement<ImportarCFDResponse> createImportarCFDResponse(ImportarCFDResponse value) {
        return new JAXBElement<ImportarCFDResponse>(_ImportarCFDResponse_QNAME, ImportarCFDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportarCFD }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.fc4.itc.com/", name = "ImportarCFD")
    public JAXBElement<ImportarCFD> createImportarCFD(ImportarCFD value) {
        return new JAXBElement<ImportarCFD>(_ImportarCFD_QNAME, ImportarCFD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportarCFDErrorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.fc4.itc.com/", name = "ImportarCFDErrorResponse")
    public JAXBElement<ImportarCFDErrorResponse> createImportarCFDErrorResponse(ImportarCFDErrorResponse value) {
        return new JAXBElement<ImportarCFDErrorResponse>(_ImportarCFDErrorResponse_QNAME, ImportarCFDErrorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportarCFDError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.fc4.itc.com/", name = "ImportarCFDError")
    public JAXBElement<ImportarCFDError> createImportarCFDError(ImportarCFDError value) {
        return new JAXBElement<ImportarCFDError>(_ImportarCFDError_QNAME, ImportarCFDError.class, null, value);
    }

}
