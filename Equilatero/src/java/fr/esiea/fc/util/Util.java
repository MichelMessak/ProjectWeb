package fr.esiea.fc.util;

import fr.esiea.fc.util.error.QueryParserException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.sql.ResultSet;

/**
 * Util for FileControl
 * @author Michel Messak
 */
public class Util {

    public static String GetSHA256(String data) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(data.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b : hash) {

                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static Object createClassInstance(String className, Object[] args) {
        if (args.length > 0) {
            Class[] argClasses = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argClasses[i] = args[i].getClass();
            }
            return createClassInstance(className, args, argClasses);
        } else {
            return createClassInstance(className, args, new Class[]{});
        }
    }

    public static Object createClassInstance(String className, Object[] args, Class[] argClasses) {
        Class esClass;

        try {
            esClass = Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {

            return null;
        }

        Constructor esInstConst;
        try {
            
            esInstConst = esClass.getConstructor(argClasses);
        } catch (java.lang.NoSuchMethodException e) {
           return null;
        }

        Object obj = null;
        try {
            obj = esInstConst.newInstance(args);
        } catch (java.lang.InstantiationException e) {
             return null;
        } catch (java.lang.IllegalAccessException e) {
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
           e.getTargetException().printStackTrace();
            return null;
        }

        return obj;
    }

    public static Object invokeClassMethod(Object obj, String methodName, Object[] args) {
        Class[] classArgs = null;
        if (args != null) {
            classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
        }
        Object returns = invokeClassMethod(obj, methodName, args, classArgs);
        classArgs = null;
        return returns;
    }

    public static Object invokeClassMethod(Object obj, String methodName, Object[] args, Class[] classArgs) {
        Class objClass;
        Object ret;
        objClass = obj.getClass();

        Method method;
        try {
            method = objClass.getMethod(methodName, classArgs);
        } catch (NoSuchMethodException e) {
            return null;
        }

        try {
            ret = method.invoke(obj, args);
        } catch (java.lang.IllegalAccessException e) {
            
            return null;
        } catch (java.lang.reflect.InvocationTargetException e) {
           
            return null;
        }
        return ret;
    }

    public static String GetQueryForTotal(String query) throws Exception {
        int ini = query.toUpperCase().indexOf("SELECT");
        int fin = query.toUpperCase().indexOf("FROM");

        if (ini >= 0 && fin >= 0) {
            return "SELECT count(*) " + query.substring(fin);
        } else {
            throw new QueryParserException("La phrase n'est pas suporté");
        }
    }

    public static String[] GetColumnNamesFromQuery(String query) {
        int ini = query.toUpperCase().indexOf("SELECT");
        int fin = query.toUpperCase().indexOf("FROM");
        if (ini >= 0 && fin >= 0) {
            String fields = query.substring(ini + 6, fin - 1).trim();
            return fields.split(",");
        }
        return null;
    }

    public static String replaceValuesHTML(String chain, ResultSet rs) throws Exception {
        try {
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                Object value = rs.getObject(i + 1);
                String sValue = null;
                if (value == null) {
                    sValue = "";
                } else {
                    sValue = value.toString();
                }
                chain = chain.replace("{" + i + "}", sValue);
            }
            return chain;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String Empty(Object obj)
    {
        if(obj==null) return ""; else return obj.toString();
    }
}
