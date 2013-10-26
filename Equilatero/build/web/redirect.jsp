<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- response.sendRedirect("index.htm"); --%>

<% boolean is_IE10 = request.getHeader("user-agent").indexOf("MSIE 10") > -1;
   boolean is_IE9 = request.getHeader("user-agent").indexOf("MSIE 9.") > -1;
   boolean is_IE8 = request.getHeader("user-agent").indexOf("MSIE 8.") > -1;
   boolean is_IE7 = request.getHeader("user-agent").indexOf("MSIE 7.") > -1;
   boolean is_IE6 = request.getHeader("user-agent").indexOf("MSIE 6.") > -1;
   if(is_IE10)
   {
       response.sendRedirect("index.htm");
   }
   else if(is_IE9)
   {
       response.sendRedirect("index.htm");
   }
   else if(is_IE8)
   {
       response.sendRedirect("indexBasic.htm");
   }
   else if(is_IE7)
   {
       response.sendRedirect("indexBasic.htm");
   }
   else if(is_IE6)
   {
       response.sendRedirect("indexBasic.htm");
   }
   else
   {
      response.sendRedirect("index.htm");
   }
%>