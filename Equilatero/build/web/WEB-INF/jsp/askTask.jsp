<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%
    String error=(String)request.getAttribute("error");
    String correct=(String)request.getAttribute("correct");
    String user=(String)request.getAttribute("user");
    String action =(String)request.getAttribute("action");

    JSONObject result=new JSONObject();
    if(error!=null)
        result.put("error", error);
    if(correct!=null)
        result.put("correct", correct);
    if(user!=null)
        result.put("user", user);
    if(action!=null)
        result.put("action", action);
    
    out.print(result);
    response.setContentType("application/json; charset=UTF-8");
    // Returns all employees (active and terminated) as json.
    //response.setContentType("application/json");
%>