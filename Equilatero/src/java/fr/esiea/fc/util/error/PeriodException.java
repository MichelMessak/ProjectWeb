package fr.esiea.fc.util.error;

/**
 * Excepción en periodos
 * @author  Segundo García Heras
 * @since   4.0.0.0
 */
public class PeriodException extends RuntimeException
{
    public PeriodException(String message)
    {
        super(message);
    }

    public PeriodException(Throwable th)
    {
        super(th);
    }

}
