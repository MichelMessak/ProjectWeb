package fr.esiea.fc.util.error;

/**
 *
 * @author Michel Messak
 *
 */

public class QueryParserException extends RuntimeException
{
    public QueryParserException(String message)
    {
        super(message);
    }

    public QueryParserException(Throwable th)
    {
        super(th);
    }
}
