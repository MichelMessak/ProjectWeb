package fr.esiea.fc.util.error;

/**
 *
 * @author Dispa Cécile
 *
 */

public class ConfigurationMissingException extends RuntimeException
{
    public ConfigurationMissingException(String message)
    {
        super(message);
    }
}
