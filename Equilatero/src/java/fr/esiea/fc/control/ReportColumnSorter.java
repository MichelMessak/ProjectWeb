package fr.esiea.fc.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handler for report filter
 * @author Michel Messak
 */

public class ReportColumnSorter
{
    String[] colNames;
    HashMap sorts;

    public ReportColumnSorter(String[] colNames)
    {
        this.colNames=colNames;
        this.sorts=new HashMap();
    }

    public void clearSort()
    {
        this.sorts.clear();
    }

    public void addSort(String colNumber, String colDir)
    {
        this.sorts.put(colNumber, colDir);
    }

    public String GenerateSortSql()
    {
        String sql=null;
        Iterator iter=this.sorts.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry=(Map.Entry)iter.next();
            String sColNumber=(String)entry.getKey();
            String sColDir=(String)entry.getValue();

            if(sColNumber!=null && sColDir!=null)
            {
                int iColNumber=Integer.parseInt(sColNumber);
                if(iColNumber>=0 && iColNumber<this.colNames.length)
                {
                    if(sql==null) sql="";
                    if(!sql.equals("")) sql+=", ";
                    sql+=this.colNames[iColNumber]+" "+sColDir;
                }
            }
        }
        if(sql!=null) sql=" order by "+sql;
        return sql;
    }
}
