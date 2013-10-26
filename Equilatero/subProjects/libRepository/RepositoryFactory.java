package com.itc.repository;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author ANGEL
 */
public class RepositoryFactory extends Repository {

//    public static String rootPath;
//    public static String subPath;
//    public static String absolutePath;
//    public static String propFilePath;
    /**
     *
     * @param args The command line call arguments are -rootpath RootPath -subpath SubPath.
     *
     * Where:
     *
     * RootPath is the main directory path, is the directory under which will be created the new directory tree
     * and
     * SubPath is the directory or directory tree to locate under the
     * recent created directory tree (year,month and day directories).
     * Something like: RootPath->yyyy->mm->dd->SubPath
     */
//    public static void main(String[] args) {
//        try {
//            for (int i = 0; i < args.length; i++) {
//                if (args[i].equalsIgnoreCase("-propfile")) {
//                    propFilePath = new String(args[++i]);
//                }else if (args[i].equalsIgnoreCase("-rootpath")) {
//                    rootPath = new String(args[++i]);
//                } else if (args[i].equalsIgnoreCase("-subpath")) {
//                    subPath = new String(args[++i]);
//                }
//            }
//            if (propFilePath != null) {
//                Properties prop = new Properties();
//                prop.load(new FileInputStream(propFilePath));
//                rootPath = prop.getProperty("fc4.repository");
//            }
//            if (rootPath == null) {
//                rootPath = new String(System.getProperty("user.dir") + File.separator + "Repository");
//            }
//            if (subPath != null) {
//                absolutePath = createTodayDirTree(rootPath, subPath);
//            } else {
//                absolutePath = createTodayDirTree(rootPath);
//            }
////            return absolutePath;
//        } catch (Exception e) {
////            return e.toString();
//        }
//    }
    public static String storeFile(String empId, String filePath) {
        try {
            Repository repo = Repository.getInstance("fc4.properties");
            File file = new File(filePath);



            return absolutePath.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }

    public static String createTodayDirTree() {
        try {
            createTodayDirTree(rootPath);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param rootPath The main directory path
     * @return The recent created directory's path
     */
    public static String createTodayDirTree(String rootPath) {
        try {
            if (rootPath != null) {
                Calendar cal = GregorianCalendar.getInstance();
                File newDir = new File(rootPath
                        + File.separator + String.valueOf(cal.get(Calendar.YEAR))
                        + File.separator + String.valueOf(cal.get(Calendar.MONTH))
                        + File.separator + String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
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
     * @param rootPath The main directory path
     * @param subTreePath The directory or directories to locate under the
     * recent created directory tree
     * @return The recent created directory's path
     */
    public static String createTodayDirTree(String rootPath, String subTreePath) {
        try {
            if (rootPath != null) {
            } else if (subTreePath == null) {
                return null;
            }
            String newDirPath = createTodayDirTree(rootPath);
            if (newDirPath == null) {
                return null;
            }
            File newDir = new File(newDirPath
                    + (subTreePath != null ? (File.separator + subTreePath) : ""));
            if (!newDir.exists()) {
                newDir.mkdirs();
            }
            return newDir.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
