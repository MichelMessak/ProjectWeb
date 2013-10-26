package fr.esiea.fc.model;

import fr.esiea.fc.util.Log;
import fr.esiea.fc.util.Util;
import fr.esiea.fc.util.error.DBConnectionNotFound;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Handler for the pool conexion
 * @author Michel Messak
 */
public class PoolConnection {

    private static DataSource ds;
    private static PoolProperties p;

    public static Connection getPoolConnection()
    {
        try {
            if (ds == null)
            {
                createPool();
            }

            Connection conn = ds.getConnection();
            if (conn == null) {
                throw new DBConnectionNotFound("Aucune connexion n'a pu être récupéré.");
            }
            return conn;
        } catch (SQLException ex) {
            throw new DBConnectionNotFound(ex);
        } catch (Exception ex) {
            throw new DBConnectionNotFound(ex);
        }
    }

    public static void destroyPool()
    {
        if(ds!=null)
        {
            ds.close();
            ds=null;
        }
        Log.write("Destruction du pool de connexion de File Controle avec  "+Util.Empty(p.getInitialSize())+" avec un maximum de "+Util.Empty(p.getMaxActive()));
    }

    public static void createPool()
    {
        try
        {
            if(ds!=null) return;
            
            Class.forName("org.postgresql.Driver");
            p = new PoolProperties();

            Configuration.Load();
            //JDBC properties
            setPoolProperty(p, "setUrl", "fc4.jdbcUrl", String.class, true);
            setPoolProperty(p, "setDriverClassName", "fc4.jdbcDriverClass", String.class, true);
            setPoolProperty(p, "setUsername", "fc4.jdbcUser", String.class, true);
            setPoolProperty(p, "setPassword", "fc4.jdbcPassword", String.class, true);

            //Pool properties
            setPoolProperty(p, "setMaxActive", "fc4.poolMaxActive", int.class, false);
            setPoolProperty(p, "setInitialSize", "fc4.poolInitialSize", int.class, false);
            setPoolProperty(p, "setJmxEnabled", "fc4.poolJmxEnabled", boolean.class, false);
            setPoolProperty(p, "setTestWhileIdle", "fc4.poolTestWhileIdle", boolean.class, false);
            setPoolProperty(p, "setTestOnBorrow", "fc4.poolTestOnBorrow", boolean.class, false);
            setPoolProperty(p, "setTestOnReturn", "fc4.poolTestOnReturn", boolean.class, false);
            setPoolProperty(p, "setValidationQuery", "fc4.poolValidationQuery", String.class, false);
            setPoolProperty(p, "setValidationInterval", "fc4.poolValidationInterval", int.class, false);
            setPoolProperty(p, "setTimeBetweenEvictionRunsMillis", "fc4.poolTimeBetweenEvictionRunsMillis", int.class, false);
            setPoolProperty(p, "setMaxWait", "fc4.poolMaxWait", int.class, false);
            setPoolProperty(p, "setRemoveAbandonedTimeout", "fc4.poolRemoveAbandonedTimeout", int.class, false);
            setPoolProperty(p, "setMinEvictableIdleTimeMillis", "fc4.poolMinEvictableIdleTimeMillis", int.class, false);
            setPoolProperty(p, "setMinIdle", "fc4.poolMinIdle", int.class, false);
            setPoolProperty(p, "setLogAbandoned", "fc4.poolLogAbandoned", boolean.class, false);
            setPoolProperty(p, "setRemoveAbandoned", "fc4.poolRemoveAbandoned", boolean.class, false);
            setPoolProperty(p, "setJdbcInterceptors", "fc4.poolJdbcInterceptors", String.class, false);

            ds = new DataSource();
            ds.setPoolProperties(p);
            Log.write("Inicializando el pool de conexiones de Equilátero con "+Util.Empty(p.getInitialSize())+" a un maximo de "+Util.Empty(p.getMaxActive()));
        }
        catch(Exception ex)
        {
            throw new DBConnectionNotFound(ex);
        }
    }
   
    private static void setPoolProperty(PoolProperties p, String methodName, String key, Class propertyClass, boolean mandatory) throws Exception {

        try {
            String strProp = Configuration.getProperty(key);
            if (strProp == null || strProp.equalsIgnoreCase("")) {
                if (mandatory) {
                    String logMsg = "ERROR: Le parametre de configuration obligatoire " + key + " n'a pas été rencontré.";
                    Log.write(logMsg);
                    throw new Exception(logMsg);
                }
            } else {
                try {
                    if (propertyClass == int.class) {
                        int prop = Integer.parseInt(strProp);
                        fr.esiea.fc.util.Util.invokeClassMethod(p, methodName, new Object[]{prop}, new Class[]{int.class});

                    } else if (propertyClass == boolean.class) {
                        boolean prop = Boolean.parseBoolean(strProp);
                        fr.esiea.fc.util.Util.invokeClassMethod(p, methodName, new Object[]{prop}, new Class[]{boolean.class});

                    } else if (propertyClass == String.class) {
                        fr.esiea.fc.util.Util.invokeClassMethod(p, methodName, new String[]{strProp});
                    } else {
                        throw new Exception("ERROR: Class incorrect: [" + propertyClass + "]");
                    }

                } catch (Exception e) {
                    String logMsg = "ERROR: Argument de configuration invalide: [" + strProp + "].\n"
                            + "\tException: " + e.getMessage() + ";\n Stack: " + e.getStackTrace();
                    Log.write(logMsg);
                    throw new Exception(logMsg);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
