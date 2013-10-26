package fr.esiea.fc.util.error;

/**
 *
 * @author Michel Messak
 *
 */

public class ControllerActionUnknown extends RuntimeException
{
    public ControllerActionUnknown(String message)
    {
        super(message);
    }
}
