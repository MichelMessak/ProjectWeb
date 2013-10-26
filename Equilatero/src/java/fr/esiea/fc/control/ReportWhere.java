package fr.esiea.fc.control;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Handler for report filter
 * @author Dispas Cécile
 */

public class ReportWhere
{
    HashMap where;

    public ReportWhere()
    {
        this.where=new HashMap();
    }

    public void clearWhere()
    {
        this.where.clear();
    }

    public void addWhere(String sColName, String sColValue)
    {
        this.where.put(sColName, sColValue);
    }

    public void addWhereBetween(String sColName, String from, String to)
    {
        this.where.put(sColName, new ReportWhereBetween(from, to));
    }

    public String GenerateWhereSql() throws Exception
    {
        try
        {
            String sql=null;
            Iterator iter=this.where.entrySet().iterator();
            while(iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                String sColName=(String)entry.getKey();
                Object value=entry.getValue();
                String sColValue=null;

                if(value==null || value.toString().trim().equals("")) continue;
                if(value.getClass().getName().equals("java.lang.String"))
                {
                    sColValue=(String)value;
                    if(sql==null) sql="";
                    if(!sql.equals("")) sql+=" and ";
                    sql+=sColName+"='"+sColValue+"'";
                }
                else if(value.getClass().getName().equals("fr.esiea.fc.control.ReportWhereBetween"))
                {
                    ReportWhereBetween bet=(ReportWhereBetween)value;
                    if(!"".equals(bet.getFrom()) && !"".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" between '"+bet.getFrom()+"' and '"+bet.getTo()+"'";
                    }
                    else if("".equals(bet.getFrom()) && !"".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" <= '"+bet.getTo()+"'";
                    }
                    else if(!"".equals(bet.getFrom()) && "".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" >= '"+bet.getFrom()+"'";
                    }
                }
            }

            return sql;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    public String GenerateWhereSqlDocuments(HttpServletRequest request) throws Exception
    {
        try
        {
            String sql=null;
            Iterator iter=this.where.entrySet().iterator();
            while(iter.hasNext())
            {
                Map.Entry entry=(Map.Entry)iter.next();
                String sColName="period.documents."+(String)entry.getKey();
                Object value=entry.getValue();
                String sColValue=null;

                if(value==null || value.toString().trim().equals("")) continue;
                if(value.getClass().getName().equals("java.lang.String"))
                {
                    sColValue=(String)value;
                    if(sql==null) sql="";
                    if(!sql.equals("")) sql+=" and ";
                    sql+=sColName+"='"+sColValue+"'";
                }
                else if(value.getClass().getName().equals("fr.esiea.fc.control.ReportWhereBetween"))
                {
                    ReportWhereBetween bet=(ReportWhereBetween)value;
                    if(!"".equals(bet.getFrom()) && !"".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" between '"+bet.getFrom()+"' and '"+bet.getTo()+"'";
                    }
                    else if("".equals(bet.getFrom()) && !"".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" <= '"+bet.getTo()+"'";
                    }
                    else if(!"".equals(bet.getFrom()) && "".equals(bet.getTo()))
                    {
                        if(sql==null) sql="";
                        if(!sql.equals("")) sql+=" and ";
                        sql+=sColName+" >= '"+bet.getFrom()+"'";
                    }
                }
            }

            return sql;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

}
