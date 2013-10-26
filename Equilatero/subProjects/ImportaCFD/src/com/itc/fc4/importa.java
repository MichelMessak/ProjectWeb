package com.itc.fc4;

import com.itc.fc4.ws.importa.ImportarDocumentos;
import com.itc.fc4.ws.importa.ImportarDocumentosService;
import com.itc.util.file.FileUtil;
import com.itc.util.file.Log;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Cliente de Web Service para importar
 * @author Segundo García Heras
 */
public class importa {

    public static final int DEFAULT_WAIT_TIME = 1000;
    public static final int MODO_UNKNOWN = 0;
    public static final int MODO_OK = 1;
    public static final int MODO_ERROR = 2;
    private static int modo = MODO_UNKNOWN;
    private static String inputFile = null;
    private static String outputFile = "importaCFD.log";
    private static String userId = null;
    private static String userPass = null;
    private static String rfc = null;
    private static String docType = null;
    private static String docSubType = null;
    private static String ID = null;
    private static String tipoCFD = null;
    private static String stackError = null;
    private static String desError = null;
    private static String codError = null;
    private static String severidad = null;
    private static String serviceFolder = null;
    private static String urlWS = "http://172.16.1.107:8080/Equilatero/ImportarDocumentosWS";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            ShowAbout();
            return;
        }
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-ok")) {
                    modo = MODO_OK;
                } else if (args[i].equals("-error")) {
                    modo = MODO_ERROR;
                } else if (args[i].equals("-entrada")) {
                    if (i < args.length - 1) {
                        inputFile = args[++i];
                    }
                } else if (args[i].equals("-salida")) {
                    if (i < args.length - 1) {
                        outputFile = args[++i];
                    }
                } else if (args[i].equals("-usuario")) {
                    if (i < args.length - 1) {
                        userId = args[++i];
                    }
                } else if (args[i].equals("-contrasena")) {
                    if (i < args.length - 1) {
                        userPass = args[++i];
                    }
                } else if (args[i].equals("-url")) {
                    if (i < args.length - 1) {
                        urlWS = args[++i];
                    }
                } else if (args[i].equals("-rfc")) {
                    if (i < args.length - 1) {
                        rfc = args[++i];
                    }
                } else if (args[i].equals("-tipodoc")) {
                    if (i < args.length - 1) {
                        docType = args[++i];
                    }
                } else if (args[i].equals("-subtipodoc")) {
                    if (i < args.length - 1) {
                        docSubType = args[++i];
                    }
                } else if (args[i].equals("-id")) {
                    if (i < args.length - 1) {
                        ID = args[++i];
                    }
                } else if (args[i].equals("-tipocfd")) {
                    if (i < args.length - 1) {
                        tipoCFD = args[++i];
                    }
                } else if (args[i].equals("-coderror")) {
                    if (i < args.length - 1) {
                        codError = args[++i];
                    }
                } else if (args[i].equals("-severror")) {
                    if (i < args.length - 1) {
                        severidad = args[++i];
                    }
                } else if (args[i].equals("-deserror")) {
                    if (i < args.length - 1) {
                        desError = args[++i];
                    }
                } else if (args[i].equals("-pilaerror")) {
                    if (i < args.length - 1) {
                        stackError = args[++i];
                    }
                } else if (args[i].equals("-service")) {
                    if (i < args.length - 1) {
                        serviceFolder = args[++i];
                    }
                }
            }

            if (userId == null || userId.trim().equals("")) {
                throw new RuntimeException("Usuario para autenticación no ha sido especificado");
            }
            if (userPass == null || userPass.trim().equals("")) {
                throw new RuntimeException("Contraseña para autenticación no ha sido especificada");
            }
            if (serviceFolder != null) {
                startService();
            }
            if (inputFile == null) {
                throw new RuntimeException("Archivo de entrada no ha sido especificado");
            }
            if (outputFile == null) {
                throw new RuntimeException("Archivo de salida no ha sido especificado");
            }
            File file = new File(inputFile);
            if (!file.exists()) {
                throw new RuntimeException("Archivo " + inputFile + " no existe");
            }
            if (modo == MODO_OK) {
                String xmlContent = FileUtil.loadFile(inputFile, "UTF-8");
                if (xmlContent == null || xmlContent.trim().equals("")) {
                    throw new RuntimeException("Archivo " + inputFile + " no existe o esta vacío");
                }
                ImportarDocumentosService service = new ImportarDocumentosService(new URL(urlWS));
                ImportarDocumentos port = service.getImportarDocumentosPort();
                String xmlResponse = port.importarCFD(xmlContent, userId, userPass);
                if (xmlResponse == null) {
                    throw new RuntimeException("Respuesta fué vacía");
                }
                FileUtil.saveFile(xmlResponse.getBytes("UTF-8"), outputFile);
                System.exit(0);
            } else if (modo == MODO_ERROR) {
                if (rfc == null || rfc.trim().equals("")) {
                    throw new RuntimeException("RFC no ha sido especificado");
                }
                if (docType == null || docType.trim().equals("")) {
                    throw new RuntimeException("Tipo de Documento no ha sido especificado");
                }
                if (docSubType == null || docSubType.trim().equals("")) {
                    throw new RuntimeException("Subtipo de Documento no ha sido especificado");
                }
                if (ID == null || ID.trim().equals("")) {
                    throw new RuntimeException("ID de Documento no ha sido especificado");
                }
                if (tipoCFD == null || tipoCFD.trim().equals("")) {
                    throw new RuntimeException("Tipo de CFD no ha sido especificado");
                }
                if (codError == null || codError.trim().equals("")) {
                    throw new RuntimeException("código de Error no ha sido especificado");
                }
                if (severidad == null || severidad.trim().equals("")) {
                    throw new RuntimeException("severidad de Error no ha sido especificado");
                }
                if (desError == null || desError.trim().equals("")) {
                    throw new RuntimeException("Descripción de Error no ha sido especificado");
                }
                if (stackError == null || stackError.trim().equals("")) {
                    throw new RuntimeException("pila de Error no ha sido especificado");
                }
                ImportarDocumentosService service = new ImportarDocumentosService(new URL(urlWS));
                ImportarDocumentos port = service.getImportarDocumentosPort();
                List<String> lsargs = Arrays.asList(args);
                LinkedList<String> largs = new LinkedList(lsargs);
                String xmlContent = FileUtil.loadFile(inputFile, "UTF-8");
                if (xmlContent == null || xmlContent.trim().equals("")) {
                    throw new RuntimeException("Archivo " + inputFile + " no existe o esta vacío");
                }
                largs.add("-entradaContenido");
                largs.add(xmlContent);
                String xmlResponse = port.importarCFDError(largs);
                if (xmlResponse == null) {
                    throw new RuntimeException("Respuesta fué vacía");
                }
                FileUtil.saveFile(xmlResponse.getBytes("UTF-8"), outputFile);
                System.exit(0);
            } else {
                throw new RuntimeException("Modo desconocido. Usee -ok o -error");
            }
        } catch (Exception ex) {
            try {
                Logger.getLogger(importa.class.getName()).log(Level.SEVERE, null, ex);
                String xmlResponse =
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?><equilatero version=\"1.0\"><ImportarCFDRespuesta>"
                        + "<fecha>"
                        + (new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss")).format(new Date())
                        + "</fecha>" + "<codigo>99999</codigo><descripcion>"
                        + ex.getMessage()
                        + "</descripcion></ImportarCFDRespuesta></equilatero>";
                FileUtil.saveFile(xmlResponse.getBytes("UTF-8"), outputFile);
                System.exit(99999);
            } catch (Exception ex1) {
                Logger.getLogger(importa.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private static void ShowUsage() {
        System.out.println("Usar java -jar ImportaCFD.jar -ok -entrada <entrada> -salida <salida> -usuario <usuario> -contrasena <contrasena>");
    }

    private static void ShowAbout() {
        JDialog about = new JDialog((JFrame) null, "Acerca de ImportaCFD", true);
        JEditorPane content = new JEditorPane();
        JScrollPane sp = new JScrollPane(content);
        content.setContentType("text/html");
        content.setText("<h1>ImportaCFD versión 0.0.0.2</h1><p>Powered by ITComplements</p>");
        about.add(sp);
        about.setSize(400, 300);
        about.setVisible(true);
    }

    private static void startService() {
        while (true) {
            try {
                File dir = new File(serviceFolder);
                String[] files = dir.list();
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    if (!isCFD(file)) {
                        continue;
                    }
                    String xmlContent = FileUtil.loadFile(file, "UTF-8");
                    if (xmlContent == null || xmlContent.trim().equals("")) {
                        throw new RuntimeException("Archivo " + file + " no existe o esta vacío");
                    }
                    ImportarDocumentosService service = new ImportarDocumentosService(new URL(urlWS));
                    ImportarDocumentos port = service.getImportarDocumentosPort();
                    String xmlResponse = port.importarCFD(xmlContent, userId, userPass);
                    if (xmlResponse == null) {
                        throw new RuntimeException("Respuesta fué vacía");
                    }
                    FileUtil.saveFile(xmlResponse.getBytes("UTF-8"), getResponseFile(file));

                }
                Log.close(); //Para que cambie el nombre cuando haya cambio de dia
                Thread.sleep(DEFAULT_WAIT_TIME);
            } catch (Exception ex) {
                Log.write("Excepción :" + ex.getMessage());
            }
        }
    }

    private static boolean isCFD(String file) {
        return (file.endsWith(".xml"));
    }

    private static String getResponseFile(String file) {
        int pos = file.lastIndexOf(".");
        if (pos > 0) {
            return file.substring(0, pos - 1) + ".resp.xml";
        }
        return file + ".resp.xml";
    }
}
