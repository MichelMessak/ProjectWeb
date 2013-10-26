package com.itc.repository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 *
 * @author Angel Garcia
 *
 */
public class Repository {

//    /*** Singleton Constructor. Don't change modifier. ***/
//    private Repository() {
//        try {
//            String fc4Home = System.getenv("FC4_HOME");
//            if (fc4Home == null) {
//                fc4Home = System.getProperty("FC4_HOME");
//            }
//            if (fc4Home == null) {
//                throw new Exception("No se ha definido variable FC4_HOME");
//            }
//
//            propFilePath = fc4Home + File.separator + "fc4.properties";
//            Properties prop = new Properties();
//            prop.load(new FileInputStream(propFilePath));
//            rootPath = prop.getProperty("fc4.repository");
//
//        } catch (Exception e) {
//        }
//    }
//    //Singleton
//    /**
//     * Singleton Method, which provides a unique instance of this class.
//     * @return Repository unique instance.
//     */
//    public static Repository getInstance() {
//        try {
//            if (instance == null) {
//                instance = new Repository();
//            }
//            return instance;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//    private static String fc4createNowDirTree(String parentTree){
//        return createNowDirTree(parentTree);
//    }
//
//     private static String fc4createNowDirTree(String parentTree, String subTree){
//        return createNowDirTree(parentTree, subTree);
//    }
//
//     public String fc4moveFile(String dirPath, String fileFromPath) {
//        return moveFile(dirPath, fileFromPath);
//     }
    /**
     *
     * @param parentTree The main directory path
     * @return The recent created directory's path
     */
    public static String createNowDirTree(String parentTree) {
        try {
            if (parentTree != null) {
                File newDir = new File(getNowDirTree(parentTree));
                if (!newDir.exists()) {
                    newDir.mkdirs();
                }
                return newDir.getAbsolutePath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNowDirTree(String parentTree) {
        try {
            if (parentTree != null) {
//                Calendar cal = GregorianCalendar.getInstance();
//                File newDir = new File(parentTree
//                        + File.separator + String.valueOf(cal.get(Calendar.YEAR))
//                        + File.separator + String.valueOf(cal.get(Calendar.MONTH))
//                        + File.separator + String.valueOf(cal.get(Calendar.DAY_OF_MONTH))
//                        + File.separator + String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
                File newDir = new File(parentTree
                        + File.separator + String.valueOf(getAnio())
                        + File.separator + String.valueOf(getMes())
                        + File.separator + String.valueOf(getDia())
                        + File.separator + String.valueOf(getHora()));
                return newDir.getAbsolutePath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param parentTree The main directory path
     * @param subTree The directory or directories to locate under the
     * recent created directory tree
     * @return The recent created directory's path
     */
    public static String createNowDirTree(String parentTree, String subTree) {
        try {
            if (parentTree == null || subTree == null) {
                return null;
            }
            String newDirPath = createNowDirTree(parentTree);
            if (newDirPath == null) {
                return null;
            }
            File newDir = new File(newDirPath
                    + (subTree != null ? (File.separator + subTree) : ""));
            if (!newDir.exists()) {
                newDir.mkdirs();
            }
            return newDir.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param parentTree The main directory path
     * where the YY/MM/DD tree will be created
     * @return The recent created directory's path
     */
    public static String createTodayDirTree(String parentTree) {
        try {
            if (parentTree != null) {
                File newDir = new File(parentTree
                        + File.separator + String.valueOf(getAnio())
                        + File.separator + String.valueOf(getMes())
                        + File.separator + String.valueOf(getDia()));
                if (!newDir.exists()) {
                    newDir.mkdirs();
                }
                return newDir.getAbsolutePath();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param dirPath
     * @param fileFromPath
     * @return
     */
    public static String moveFile(String dirPath, String fileFromPath) {
        if (dirPath == null || fileFromPath == null) {
            return null;
        }
        File fileFrom = new File(fileFromPath);
        File fileTo = new File(dirPath + File.separator + fileFrom.getName());
        if (!fileTo.exists()) {
            fileTo.mkdirs();
        }
        if (new File(fileTo.getAbsolutePath()).exists()) {
            new File(fileTo.getAbsolutePath()).delete();
        }
        fileFrom.renameTo(fileTo);
        return fileTo.getAbsolutePath();
    }

    public static String getAnio() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static String getMes() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");

        return sdf.format(new Date());
    }

    public static String getDia() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");

        return sdf.format(new Date());
    }

    public static String getHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");

        return sdf.format(new Date());
    }
}
