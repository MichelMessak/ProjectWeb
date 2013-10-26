package fr.esiea.fc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**Clase para manejo de archivos XML.
 *
 * @author Ángel García Alcántara
 * @version 1.0.0
 * 
 */
public class XMLManager {

    private Document xmlDoc;
    private DocumentBuilderFactory bFactory;
    private DocumentBuilder builder;
    private XPathFactory xpathfactory;
    private XPath xpath;
    private XPathExpression expr;
    private TransformerFactory transformerFactory;
    private Transformer transformerXML;

    /**Constructor.
     * 
     */
    public XMLManager() {
    }

    public XPathExpression getExpr() {
        return expr;
    }

    public Document getXmlDoc() {
        return xmlDoc;
    }
    
    
    public void readXML(String xml) throws Exception {
        readXML(xml,"UTF-8");
    }

    /**Read xml from memory String line.
     *
     * @param xml String that contain the serialized XML.
     * @throws Exception
     */
    public void readXML(String xml, String encoding) throws Exception {
        try {
            if (bFactory == null) {
                bFactory = DocumentBuilderFactory.newInstance();
            }
            if (builder == null) {
                builder = bFactory.newDocumentBuilder();
            }
            xmlDoc = builder.parse(new ByteArrayInputStream(xml.getBytes(encoding)));
        } catch (Exception e) {
            throw e;
        }
    }

    /**Load a XML file.
     * Only need the string path where is the XML file.
     * Use default encoding.
     * 
     * @param xmlPath Path where is the XML file.
     * @throws Exception
     */
    public void loadXML(String xmlPath) throws Exception {
        try {
            loadXML(xmlPath, null);
        } catch (Exception e) {
            throw e;
        }
    }

    /**Load a XML file.
     * 
     * @param xmlPath
     * @param encoding
     * @throws Exception
     */
    public void loadXML(String xmlPath, String encoding) throws Exception {
        try {
            if (bFactory == null) {
                bFactory = DocumentBuilderFactory.newInstance();
            }
            if (builder == null) {
                builder = bFactory.newDocumentBuilder();
            }
            if (encoding != null) {
                InputStreamReader in = new InputStreamReader(
                        new FileInputStream(xmlPath), encoding);
                BufferedReader reader = new BufferedReader(in);
                String line, strXML = null;
                while ((line = reader.readLine()) != null) {
                    strXML = (strXML == null ? "" : strXML) + (line == null ? "" : line);
                }
                xmlDoc = builder.parse(new InputSource(new StringReader(strXML)));
            } else {
                xmlDoc = builder.parse(xmlPath);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**Load a XML file.
     * Use default encoding.
     * 
     * @param xmlFile 
     * @throws Exception
     */
    public void loadXML(File xmlFile) throws Exception {
        try {
            loadXML(xmlFile.getPath());
        } catch (Exception e) {
            throw e;
        }
    }

    /**Load a XML file.
     * 
     * @param xmlFile
     * @param encoding
     * @throws Exception
     */
    public void loadXML(File xmlFile, String encoding) throws Exception {
        try {
            loadXML(xmlFile.getPath(), encoding);
        } catch (Exception e) {
            throw e;
        }
    }

    /**Query into a XML for XPath and returns the result.
     * As returns a object, this one can cast to needed class.
     * Need specify return class and XPathConstant
     * 
     * @param xmlDoc 
     * @param xpathExpression
     * @param XPathConstant @see{javax.xml.xpath.XPathConstants}
     * @return Result of xpathExpression query into xmlDoc
     * @throws Exception
     */
    public Object getXPathResult(String xpathExpression, QName XPathConstant) throws Exception {
        try {
            if (xpathfactory == null) {
                xpathfactory = XPathFactory.newInstance();
            }
            if (xpath == null) {
                xpath = xpathfactory.newXPath();
            }
            expr = xpath.compile(xpathExpression);
            return expr.evaluate(xmlDoc, XPathConstant);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**Save {@link org.w3c.dom.Document} to a string pathfilename given.
     * Non-tested.
     *
     * @param xmlDoc
     * @param fileOutput
     * @throws Exception
     */
    public boolean saveXML(String fileOutput) {
        try {
            if (transformerFactory == null) {
                transformerFactory = TransformerFactory.newInstance();
            }
            if (transformerXML == null) {
                transformerXML = transformerFactory.newTransformer();
            }
            DOMSource source = new DOMSource(xmlDoc);
            StreamResult result = new StreamResult(fileOutput);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformerXML.transform(source, result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
