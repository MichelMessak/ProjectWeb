package fr.esiea.fc.model;

import fr.esiea.fc.util.error.ConfigurationMissingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * Configuration de FileControl
 * @author Michel Messak
 */
public class Configuration {

    private static Properties prop;

    public static void Load() throws Exception {
        if (prop != null) {
            return;
        }

        String fcHome = System.getenv("FC_HOME");
        if (fcHome == null) {
            fcHome = System.getProperty("FC_HOME");
        }
        if (fcHome == null) {
            throw new ConfigurationMissingException("La variable FC_HOME n'est pas définie");
        }

        try {
            prop = new Properties();
            String propFile = fcHome + File.separator + "fc4.properties";
            File propf = new File(propFile);
            if (!propf.exists()) {
                throw new FileNotFoundException("L'archive de configuration " + propFile + " n'a pas été trouvé");
            }
            prop.load(new FileInputStream(propf));
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String getProperty(String name) {
        if (prop != null) {
            return prop.getProperty(name);
        }
        return null;
    }
}
