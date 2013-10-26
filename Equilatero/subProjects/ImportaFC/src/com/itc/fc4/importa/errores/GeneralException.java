package com.itc.fc4.importa.errores;

/**
 *
 * @author J. Jahir Barojas M.
 */
public class GeneralException extends Exception
{
    int codeError=0;

    public GeneralException(int codeError, String msg)
    {
        super(msg);
        this.codeError=codeError;
    }
    public GeneralException(String msg)
    {
        super(msg);
    }

    public int getErrorException()
    {
        return this.codeError;
    }
}
