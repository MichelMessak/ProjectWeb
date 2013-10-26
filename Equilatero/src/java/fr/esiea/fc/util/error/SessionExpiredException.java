package fr.esiea.fc.util.error;

/**
 * @author Michel Messak
 */

public class SessionExpiredException extends RuntimeException
{

    public SessionExpiredException(String message)
    {
        super(message);
    }

    public SessionExpiredException(Throwable th)
    {
        super(th);
    }

}
