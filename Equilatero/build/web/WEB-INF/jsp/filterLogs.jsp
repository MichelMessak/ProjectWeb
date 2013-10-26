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
        <title>Filtre des logs</title>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/datatable/demo_page.css";
            @import "css/Ayudas/estiloAyuda.css";
            @import "css/login/popup.css";
            @import "css/login/estilopopup.css";
            @import "css/design.css";
            @import "css/filterDocGen/filterDocGen.css";
            @import "css/filterDocGen/form.css";
        </style>
        <link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar.css"/>
	<link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar_dhx_skyblue.css"/>
	<script type="text/javascript" src="js/filterDocGen/dhtmlxcalendar.js"></script>
    </head>
    <body>
	<script type="text/javascript" src="js/filterDocGen/dhtmlxcalendar.js"></script>
        <%@ include file="header.jspf" %>
        <%@ include file="clearFilterAttrs.jspf" %>
        <center>
            <div id="mainContainer">
               <form:errors path="*" cssClass="errorBlock" element="div" />
               <form method="post" action="" id="filterForm">
                   <fieldset>
                        <div class="fieldgroup" >
                            <label>Date</label>
                            <input type="text" id="isLogExist" onblur="isLogExist()" name="date_init"/>
                        </div>
                           <p id ="message"></p>
                           <input type="submit" name="isFilterSubmit" disabled value="Consult" id ="consult" class="submit" title="Valider">
                    </fieldset>
                    
                </form>
            </div><br><br><br><br><br><br><br><br><br>
            
            <c:if test="${not empty error}">
                <div id="error">
                    <span>${error}</span>
                </div>
            </c:if>
            
        </center>

    <div style="bottom: 8px;position: absolute;right: 1%;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;">Filtre des logs</h3><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">
            Le filtre de logs nus permet de visualiser les logs du serveur.</p>
        <p class="noteHelp">Pour voir les logs il suffit de renseigner la date et de valider le choix.</p>
        <br><br><center>_________________________________________________________</center><br><br>        
    </div>
<script type="text/javascript">
            var myCalendar;
            window.onload = function doOnLoad() {
                    myCalendar = new dhtmlXCalendarObject(["isLogExist"]);

                         myCalendar.attachEvent("onClick", isLogExist);
 
            }
</script>
<script type="text/javascript" src="js/login/popup.js" ></script>
<script type="text/javascript">function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script>
<script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>               
    </body>
</html>