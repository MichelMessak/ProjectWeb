package fr.esiea.fc.util.error;

/**
 * Excepción alcanzada cuando no hay periodos corrientes configurados en la base de datos
 * @author  Segundo García Heras
 * @since   0.0.0.2
 */
public class CurrentPeriodNotFoundException extends RuntimeException
{
    public CurrentPeriodNotFoundException(String message)
    {
        super(message);
    }

    public CurrentPeriodNotFoundException(Throwable th)
    {
        super(th);
    }

}
