<%@page isErrorPage = "true" contentType="text/html" pageEncoding="UTF-8" import="java.io.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="include.jspf" %>
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erreurs</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.21/jquery-ui.min.js"></script>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/design.css";
            @import "css/AltaUsuariosForm/bootstrap.css";
            @import "css/error/error.css";
	</style>
        <style type="text/css" media="print"> #trace, #tree, #vars { display: block; }</style>
    </head>
    <body onLoad="show5()">
            <%@ include file="header.jspf" %>
            <div id="error_format">
                <center>
                    <h1>Pardon du dérangement ...</h1><span style="color: #585858;"> &quot;Le programme&quot; a détecté une erreur.&nbsp;&nbsp;&nbsp;
                        <br>
                        Pour plus d'information consulter l'admin.
                        <br>
                    </span>
                </center>
            </div>
            <span style="margin-left: 20px; color: #585858;"><% out.println(request.getAttribute("javax.servlet.error.message")); %></span>
            <br>

            <span id="efect" style="margin-left: 20px; color: #585858;"><a href="#">[+ -] Mostrar Detalles</a></span>

                <div id="trace" class="grayBox" style="display: none; color: #585858;">
                <%

                    //Exception ex=(Exception)request.getAttribute("exception");
                    Throwable ex=(Throwable)request.getAttribute("javax.servlet.error.exception");
                    Integer code=(Integer)request.getAttribute("javax.servlet.error.status_code");
                    if(ex==null)
                        ex=exception;
                    //out.println("Exception: "+ex);
                    if(ex!=null)
                    {
                        StringWriter errors = new StringWriter();
                        ex.printStackTrace(new PrintWriter(errors));
                        out.println(errors.toString());
                    }
                    else if(code!=null)
                    {
                        out.println("Error HTTP: "+code+"<br/>");
                        out.println("Resources: "+request.getAttribute("javax.servlet.error.request_uri")+"<br/>");
                    }
                    else
                    {
                        out.println("Erreur Inconnue");
                    }
                %>
                </div>
            <br>
                <div style="position: absolute;padding: 10px;color:green; bottom: 3px; right: 10px; z-index: 4;">
                     Erreur généré le jour :
                <script type="text/javascript">
                    var month = new Array ("Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre");
                    var day = new Array("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche");
                    var f=new Date();
                    document.write(day[f.getDay()] + ", " + f.getDate() + " de " + month[f.getMonth()] + " de " + f.getFullYear());
                </script>
                    <script type="text/javascript">
                        var d = new Date();
                        document.write('at '+d.getHours(),
                        ':'+d.getMinutes(),
                        ':'+d.getSeconds());
                    </script>
                </div>
                  <script type="text/javascript">
                      $("#efect").toggle(
                        function() {
                            $("#trace").show('drop', {}, 600);
                        },
                        function() {
                            $("#trace").hide('drop', {}, 300);
                        }
                     );
                </script>
    </body>
</html>