
package com.itc.fc4.ws.importa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImportarCFD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImportarCFD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xmlContent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userPass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportarCFD", propOrder = {
    "xmlContent",
    "userId",
    "userPass"
})
public class ImportarCFD {

    protected String xmlContent;
    protected String userId;
    protected String userPass;

    /**
     * Gets the value of the xmlContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlContent() {
        return xmlContent;
    }

    /**
     * Sets the value of the xmlContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlContent(String value) {
        this.xmlContent = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the userPass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * Sets the value of the userPass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserPass(String value) {
        this.userPass = value;
    }

}
