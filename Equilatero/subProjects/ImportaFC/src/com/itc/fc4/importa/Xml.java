package com.itc.fc4.importa;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.itc.repository.fc4Repository;

/**
 *
 * @author JBM
 */

public class Xml
{

    public static Datos getValuesFromXML(String args[]) throws ParserConfigurationException, SAXException, IOException, ParseException
    {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document documento = null;
            Datos datosXml = new Datos();

            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-serie")) {
                    datosXml.setSerie(args[i+1]);
                }
                else if (args[i].equalsIgnoreCase("-folio")){
                    datosXml.setFolio(args[i+1]);
                }
                 else if (args[i].equalsIgnoreCase("-fecha")){
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                    Date date = (Date) formatter.parse(args[i]);
                    //String tmpFechaGen = attComprobante.getTextContent();
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
                    datosXml.setFechaGen(sdfDate.format(date));
                    datosXml.setHoraGen(sdfTime.format(date));
                }
                 else if (args[i].equalsIgnoreCase("-total")){
                    datosXml.setTotal(args[i+1]);
                }
                 else if (args[i].equalsIgnoreCase("-version")){
                    datosXml.setSubTipo(args[i+1]);
                    switch(args[i+1].charAt(0)){
                        case '3' :    {datosXml.setTipo("CFDI");break;}
                        default :   {datosXml.setTipo("CFD");break;}
                    }
                }
                 else if (args[i].equalsIgnoreCase("-moneda")){
                    datosXml.setMoneda(args[i+1]);
                }
                else if (args[i].equalsIgnoreCase("-rfcemisor")){
                    datosXml.setEmpresa(args[i+1]);
                }
                else if (args[i].equalsIgnoreCase("-rfcreceptor")){
                    datosXml.setEmpresaReceptora(args[i+1]);
                }
                else if (args[i].equalsIgnoreCase("-nombrereceptor")){
                    datosXml.setNombreReceptora(args[i+1]);
                }

                else if (args[i].equalsIgnoreCase("-UUID")) {
                    if (datosXml.getTipo().equals("CFD")){
                        datosXml.setId(datosXml.getSerie()+datosXml.getFolio());
                    }
                    else{
                        datosXml.setId(args[i+1]);
                    }

                }
                else if(args[i].equalsIgnoreCase("-ID2")){
                    datosXml.setId2(args[i+1]);
                }
            }

             datosXml.setStatus("O");

            Date fecha = new Date();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
            //Obtiene fecha y hora actal
            datosXml.setFechaMod(formatoFecha.format(fecha));
            datosXml.setHoraMod(formatoHora.format(fecha));

             DocumentBuilder builder = factory.newDocumentBuilder();

            for (int i = 0;i<args.length;i++)
                   if(args[i].contains(".xml")){
                        datosXml.setArchivoEntrada(args[i]);
                        documento = builder.parse(args[i].toString());
                   }

            Node nodoRaiz = documento.getFirstChild();
            NamedNodeMap comprobante = nodoRaiz.getAttributes();
            for (int i = 0; i < comprobante.getLength(); i++) {
                Node attComprobante = comprobante.item(i);

                if (attComprobante.getNodeName().endsWith("serie") && (datosXml.getSerie().equals(""))) {
                    datosXml.setSerie(attComprobante.getTextContent());
                    continue;
                }
                else if (attComprobante.getNodeName().endsWith("folio")&& (datosXml.getFolio().equals(""))) {
                    datosXml.setFolio(attComprobante.getTextContent());
                    continue;
                }

                else if (attComprobante.getNodeName().endsWith("fecha")&& (datosXml.getFechaGen().equals(""))) {
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                    Date date = (Date) formatter.parse(attComprobante.getTextContent());
                    //String tmpFechaGen = attComprobante.getTextContent();
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");
                    datosXml.setFechaGen(sdfDate.format(date));
                    datosXml.setHoraGen(sdfTime.format(date));
                } else if (attComprobante.getNodeName().endsWith("total")&& (datosXml.getTotal().equals(""))) {
                    datosXml.setTotal(attComprobante.getTextContent());
                    continue;
                } else if (attComprobante.getNodeName().endsWith("version")&& (datosXml.getSubTipo().equals(""))) {
                    String version = attComprobante.getTextContent();
                    datosXml.setSubTipo(version);
                    switch(version.charAt(0)){
                        case '3' :    {datosXml.setTipo("CFDI");break;}
                        default :   {datosXml.setTipo("CFD");break;}
                    }
                    
                    continue;
                    
                } else if (attComprobante.getNodeName().endsWith("Moneda")&& (datosXml.getMoneda().equals(""))) {
                    datosXml.setMoneda(attComprobante.getTextContent());
                }

                else if (attComprobante.getNodeName().endsWith("ID2")&& (datosXml.getId2().equals(""))) {
                    datosXml.setId2(attComprobante.getTextContent());
                }

                

            }
            //        Node serieNode = comprobante.getNamedItem("serie");
            //        serie = serieNode.getTextContent();
            NodeList listaNodosHijos = nodoRaiz.getChildNodes();
            for (int j = 0; j < listaNodosHijos.getLength(); j++) {
                Node unNodoHijo = listaNodosHijos.item(j);
                //if (unNodoHijo .getNodeName().equalsIgnoreCase("Addenda") ||unNodoHijo.getNodeName().equalsIgnoreCase("cfdi:Addenda") )
                if (unNodoHijo.getNodeName().endsWith("Emisor")&& (datosXml.getEmpresa().equals(""))) {
                    Node emisorNode = unNodoHijo.cloneNode(true);
                    NamedNodeMap attsEmisor = emisorNode.getAttributes();
                    for (int a = 0; a < attsEmisor.getLength(); a++) {
                        Node attEmisor = attsEmisor.item(a);
                        if (attEmisor.getNodeName().endsWith("rfc")) {
                            datosXml.setEmpresa(attEmisor.getTextContent());
                            continue;
                        }
                       /* if (attEmisor.getNodeName().endsWith("nombre")) {
                            datosXml.setNombre(attEmisor.getTextContent());
                            continue;
                        }*/
                    }
                } else if (unNodoHijo.getNodeName().endsWith("Receptor")) {
                    Node receptorNode = unNodoHijo.cloneNode(true);
                    NamedNodeMap attsReceptor = receptorNode.getAttributes();
                    for (int b = 0; b < attsReceptor.getLength(); b++) {
                        Node attReceptor = attsReceptor.item(b);
                        if (attReceptor.getNodeName().endsWith("rfc")&& (datosXml.getEmpresaReceptora().equals(""))) {
                            datosXml.setEmpresaReceptora(attReceptor.getTextContent());
                            continue;
                        }
                        if (attReceptor.getNodeName().endsWith("nombre")&& (datosXml.getNombreReceptora().equals(""))) {
                            datosXml.setNombreReceptora(attReceptor.getTextContent());
                            continue;
                        }
                    }
                } else if (unNodoHijo.getNodeName().endsWith("Complemento")) {
                    Node complementoNode = unNodoHijo.cloneNode(true);
                    NodeList compleNodes = complementoNode.getChildNodes();
                    for (int s = 0; s < compleNodes.getLength(); s++) {
                        if (compleNodes.item(s).getNodeName().endsWith("TimbreFiscalDigital")) {
                            NamedNodeMap attsTFD = compleNodes.item(s).getAttributes();
                            for (int t = 0; t < attsTFD.getLength(); t++) {
                                Node attTFD = attsTFD.item(t);
                                if (attTFD.getNodeName().endsWith("UUID")&& (datosXml.getId().equals(""))) {
                                    switch(datosXml.getSubTipo().charAt(0)){
                                        case '3' :    {datosXml.setId(attTFD.getTextContent());break;}
                                        default :   {datosXml.setId(datosXml.getSerie()+datosXml.getFolio());break;}
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(datosXml.getId2().equals(""))
               if(!datosXml.getSerie().equals(""))
                   if(!datosXml.getFolio().equals(""))
                        datosXml.setId2(datosXml.getSerie()+datosXml.getFolio());

                   datosXml.setRutaArchivoEntrada(fc4Repository.createEnterpriseTree(datosXml.getEmpresa()));
            return datosXml;
    }

}


