package fr.esiea.fc.util.error;

/**
 * Periodo no encontrado en la base de datos
 * @author Segundo García Heras
 */

public class PeriodNotFoundException extends RuntimeException
{
    public PeriodNotFoundException(String message)
    {
        super(message);
    }

    public PeriodNotFoundException(Throwable th)
    {
        super(th);
    }

}
