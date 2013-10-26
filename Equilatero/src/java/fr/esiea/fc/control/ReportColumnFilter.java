package fr.esiea.fc.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handler for a reprt filter by column
 * @author Dispa Cécile
 */

public class ReportColumnFilter
{
    String[] colNames;
    HashMap filters;
    String search;

    public ReportColumnFilter(String[] colNames)
    {
        this.colNames=colNames;
        this.filters=new HashMap();
    }

    public void clearFilter()
    {
        this.filters.clear();
    }

    public void addFilter(String sColNumber, String sColValue)
    {
       this.filters.put(sColNumber, sColValue);
    }

    public String GenerateFilterSql()
    {
        try
        {
            String sql=null;
            Iterator iter=this.filters.entrySet().iterator();
            while(iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                String sColNumber=(String)entry.getKey();
                String sColValue=(String)entry.getValue();

                if(sColNumber!=null && sColValue!=null)
                {
                    int iColNumber=Integer.parseInt(sColNumber);
                    if(iColNumber>=0 && iColNumber<this.colNames.length)
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=this.colNames[iColNumber]+"::text like '"+sColValue+"%'";
                    }
                }
            }

            String sqlSearch="";
            if(search!=null && !"".equals(this.search))
            {
                for(int i=0;i<this.colNames.length;i++)
                {
                    if(!sqlSearch.equals("")) sqlSearch+=" or ";
                    sqlSearch+=this.colNames[i]+"::text like '%"+this.search+"%'";
                }
                if(!"".equals(sqlSearch))
                    sqlSearch="("+sqlSearch+")";
            }

            if(!"".equals(sqlSearch))
            {
                if(sql==null) sql=""; else sql+=" and ";
                sql+=" "+sqlSearch;
            }

            return sql;
        }
        catch(Exception ex)
        {
            return null;
        }
    }

    public void setSearch(String search)
    {
        this.search=search;
    }
}
