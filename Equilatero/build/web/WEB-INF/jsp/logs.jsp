<%@page import="fr.esiea.fc.util.Util"%>
<%@page import="java.io.FileInputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type='text/javascript' src='http://code.jquery.com/jquery-1.6.js'></script>
        <title>Logs</title>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/design.css";
        </style>
    </head>
    <body>
        <%@ include file="header.jspf" %>
        <div id="logs">
            ${chain}
        </div>
    </body>
</html>