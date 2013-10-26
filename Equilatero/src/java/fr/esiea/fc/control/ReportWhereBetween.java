package fr.esiea.fc.control;

/**
 * Handler filter type Between
 * @author Dispa Cécile
 */

public class ReportWhereBetween
{
    String from, to;
    public ReportWhereBetween(String from, String to)
    {
        this.from=from;
        this.to=to;
    }

    public String getFrom()
    {
        return this.from;
    }

    public String getTo()
    {
        return this.to;
    }
}
