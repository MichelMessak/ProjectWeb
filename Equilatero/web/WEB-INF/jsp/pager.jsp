<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="fr.esiea.fc.control.Report"%>
<%
    JSONObject result=new JSONObject();
    Report report=(Report)request.getAttribute("data");
    Exception ex=(Exception)request.getAttribute("exception");
    if(ex!=null)
    {
        JSONArray data=new JSONArray();
        JSONArray row=new JSONArray();
        row.add("Exeption rencontrée: "+ex.getMessage());
        data.add(row);
        result.put("iTotalRecords", 1);
        result.put("iTotalDisplayRecords", 1);
        result.put("aaData", data);
        out.print(result);
    }
    else
    {
        if(report!=null)
        {
            JSONArray data=report.getData();
            result.put("iTotalRecords", report.getRowFilterCount());
            result.put("iTotalDisplayRecords", report.getRowCount());
            result.put("aaData", data);
            out.print(result);
        }
        else
        {
            result.put("iTotalRecords", 0);
            result.put("iTotalDisplayRecords", 0);
            JSONArray data=new JSONArray();
            result.put("aaData", data);
            out.print(result);
        }
    }
    response.setContentType("application/json; charset=UTF-8");
    // Returns all employees (active and terminated) as json.
    //response.setContentType("application/json");
%>
