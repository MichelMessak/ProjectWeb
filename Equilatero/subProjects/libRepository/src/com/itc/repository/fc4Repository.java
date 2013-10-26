/**
 * 
 */
package com.itc.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author ANGEL
 */
public class fc4Repository extends Repository {

    private static String fc4RepoDir = null;
    private static String fc4MailPath = null;
    private static String fc4PropFileName = null;
    private static String empId = null;
    public static Integer DEFAULT_MAX_FAIL_ATTEMPTS = 3;
    public static Properties fc4Properties = null;
    public static String fc4Home = null;

//    public static void main(String args[]) throws Exception {
////        Repository uniqueRepo = Repository.getInstance("fc4.properties");
//        fc4Repository.Init();
//        String rutaArchivo = fc4Repository.storeFile("RFC01", "c:\\onearchive.txt");
//        String rutaEmpresa = fc4Repository.createEnterpriseTree("RFC02");
//    }
    /**
     *
     * @return filaPath from fc4.properties
     */
    public static String getPropFilePath() {
        return fc4PropFileName;
    }

    /**
     *
     * @return fc4.repository
     */
    public static String getRootPath() {
        return fc4RepoDir;
    }

    /**
     *
     * @return fc4.repository.mail.path
     */
    public static String getMailPath() {
        return fc4MailPath;
    }

    /**
     * Obtiene el maximo numero de intentos fallidos para deshabilitar un usuario. Esta propiedades se encuentra en el archivo fc4.properties
     * @return fc4.maxFailAttempts
     */
    public static Integer getMaxFailAttempts() {

        String sMaxAttempts = (String) fc4Properties.getProperty("fc4.maxFailAttemps");
        int maxAttempts = DEFAULT_MAX_FAIL_ATTEMPTS;
        if (sMaxAttempts != null) {
            maxAttempts = parseInt(sMaxAttempts);
        }
        return maxAttempts;
    }

    public static String getNotificationSenderAddress() {
        String email = (String) fc4Properties.getProperty("fc4.notification.sender.address");
        return email;
    }

    public static String getNotificationSenderHost() {
        String host = (String) fc4Properties.getProperty("fc4.notification.sender.host");
        return host;
    }

    public static String getNotificationSenderPort() {
        String port = (String) fc4Properties.getProperty("fc4.notification.sender.port");
        return port;
    }

    public static String getNotificationSenderAuthentication() {
        String auth = (String) fc4Properties.getProperty("fc4.notification.sender.auth");
        return auth;
    }

    public static String getNotificationSenderSSLEnabled() {
        String ssl = (String) fc4Properties.getProperty("fc4.notification.sender.ssl");
        return ssl;
    }

    public static String getNotificationSenderUser() {
        String user = (String) fc4Properties.getProperty("fc4.notification.sender.user");
        return user;
    }

    public static String getNotificationSenderPassword() {
        String password = (String) fc4Properties.getProperty("fc4.notification.sender.password");
        return password;
    }

    public static String getNotificationPathTemplate() {
        return (String) fc4Properties.getProperty("fc4.notification.path.template");
    }

    /**
     * Initialization method. Gets FC_HOME and search the file
     * fc4.properties, reads it and gets the value fc4.repository
     */
    public static synchronized void Init() throws FileNotFoundException, IOException, FC4RepositoryException {
        if (fc4Home == null) {
            fc4Home = System.getenv("FC_HOME");
            if (fc4Home == null) {
                fc4Home = System.getProperty("FC_HOME");
            }
            if (fc4Home == null) {
                throw new FC4RepositoryException("No se ha definido variable FC_HOME");
            }
        }
        if (fc4PropFileName == null) {
            fc4PropFileName = fc4Home + File.separator + "fc4.properties";
        }

        if (fc4Properties == null) {
            fc4Properties = new Properties();
            fc4Properties.load(new FileInputStream(fc4PropFileName));
        }
        if (fc4RepoDir == null) {
            if (File.separatorChar == '/') {
                fc4RepoDir = fc4Properties.getProperty("fc4.repository");
            } else {
                fc4RepoDir = fc4Properties.getProperty("fc4.repository").replace("/", "\\");
            }

            if (fc4RepoDir == null || "".equals(fc4RepoDir.trim())) {
                throw new FC4RepositoryException("Propiedad fc4.repository no existe.");
            }
        }
        if (fc4MailPath == null) {
            if (File.separatorChar == '/') {
                fc4MailPath = fc4Properties.getProperty("fc4.notification.path.template");
            } else {
                fc4MailPath = fc4Properties.getProperty("fc4.notification.path.template").replace("/", "\\");
            }

            if (fc4MailPath == null || "".equals(fc4MailPath.trim())) {
                throw new FC4RepositoryException("Propiedad fc4.notification.path.template no existe.");
            }
        }
    }

    /**
     *
     * @param rootFolder    root directory of the document owner.
     * @param filePath      absolute path of the document to be stored in the repository. This file will be moved to repository
     * @return  relative path of the document stored in the repository. The base folder used must be configured in configuration file.
     */
    public static String storeFile(String rootFolder, String filePath) throws FC4RepositoryException {
        if (rootFolder == null || filePath == null) {
            return null;
        }
        if (fc4RepoDir == null) {
            throw new FC4RepositoryException("Directorio de repositorio no ha sido configurado.");
        }
        String parentTree = fc4RepoDir + File.separator + rootFolder;
        String dirPath = Repository.createNowDirTree(parentTree);

        return getRelativePath(moveFile(dirPath, filePath));
    }

    /**
     * Get the absolute path of the document in the repository given his relative path
     * @param relativePath  Relative path of document in the repository
     * @return  Absolute path of document in the repository
     */
    public static String getAbsolutePath(String relativePath) {
        try {
            if (!relativePath.startsWith(fc4RepoDir + File.separator)) {
                return fc4RepoDir + File.separator + relativePath;
            }
            return relativePath;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the relative path of the document stored in the repository given his absolute path
     * @param absolutePath  Absolute path of the document in the repository
     * @return  Relative path of the document
     */
    public static String getRelativePath(String absolutePath) {
        try {
            if (absolutePath.startsWith(fc4RepoDir + File.separator)) {
                return absolutePath.replace(fc4RepoDir + File.separator, "");
            }
            return absolutePath;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates the directory tree in the repository before the document can be stored
     * @param enterprise    Enterprise ownwer of the document
     * @return  Relative path of the diretory tree created. This path turns in the root directory of the document owner
     */
    public static String createEnterpriseTree(String enterprise) {
        if (enterprise == null || fc4RepoDir == null) {
            return null;
        }
        empId = enterprise;
        return getRelativePath(createNowDirTree(getAbsolutePath(empId)));
    }

    public static String getEnterpriseTree(String enterprise) {
        if (enterprise == null || fc4RepoDir == null) {
            return null;
        }
        empId = enterprise;
        return getAbsolutePath(getNowDirTree(getAbsolutePath(empId)));
    }

    /**
     * Move file from his source to the repository directory tree
     * @param relativeDirPath   Root directory of the document owner
     * @param fileFromPath  Absolute Path of the document being stored
     * @return  Relative Path of the document stored in the repository. This path can be used for retrieving purposes
     */
    public static String fc4MoveFile(String relativeDirPath, String fileFromPath) throws Exception {
        try {
            return getRelativePath(moveFile(getAbsolutePath(relativeDirPath), fileFromPath));
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Integer parseInt(String snumber) {
        Integer number = null;
        try {
            number = Integer.parseInt(snumber);
            return number;
        } catch (Exception ex) {
            return null;
        }
    }
}
