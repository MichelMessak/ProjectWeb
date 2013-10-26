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
        <link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar.css"/>
	<link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar_dhx_skyblue.css"/>
	<script type="text/javascript" src="js/filterDocGen/dhtmlxcalendar.js"></script>
        <title>Filtre de rapport d'activité</title>
        <style type="text/css" title="currentStyle">
            @import "css/login/popup.css";
            @import "css/login/estilopopup.css";
            @import "css/Ayudas/estiloAyuda.css";
            @import "css/datatable/demo_page.css";
            @import "css/design.css";
            @import "css/filterDocGen/filterDocGen.css";
            @import "css/filterDocGen/form.css";
	</style>
      
    </head>
	
    <body onLoad="doOnLoad();">
        <%@ include file="header.jspf"%>
        <%@ include file="clearFilterAttrs.jspf"%>
        <center>
            <div id="mainContainer">
               <form:errors path="*" cssClass="errorBlock" element="div"/>
               <form method="post" action="">
                   <fieldset>
                   <div class="fieldgroup" align="right">
                            <label>Utilisateur</label>
                                <select name="user_id">
                                    <c:forEach var="user" items="${users}">
                                        <option value="${user.id}">${user.name}</option>
                                    </c:forEach>
                                </select>
                    </div>
                    <div class="fieldgroup">
                            <label>Date</label>
                            <input type="text" id="calendar" placeholder="Date Final" name="date_end"/>
                            <input type="text" id="calendar2" placeholder="Date Initial" name="date_init"/>
                    </div>
                    <div class="fieldgroup">
                            <label>Heure</label>
                            <input type="text" id="hour_end" placeholder="hh:ss" name="hour_end" onblur="hours()"/>
                            <input type="text" id="hour_init" placeholder="hh:ss" onblur="hours()" name="hour_init"/>
                    </div>
                   <p id="message"></p>
                        <div class="fieldgroup">
                            <input type="submit" name="isFilterSubmit" value="Consult" id ="consult" class="submit" title="Valider">
                        </div>
                </fieldset>
                </form>
        </div>
    </center>
        <div style="position: fixed;padding: 10px;color:green; bottom: 10px; right: 10px; z-index: 4;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Fenêtre de recherche de rapport d'activité</p></h3><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Ce menu nous facilite la recherche d'activités d'un utilisateur.</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Sélectioner l'utilisateur voulu. Cette requete va nous donner le rapport d'activité de l'utilisateur en question.</p>
        <center><p class="noteHelp">Pur sélectioner un utilisateur, cliquer sur le rectangle de texte pour afficher toutes les possibilités.</p></center>
        <center><p class="noteHelp"><img alt="HELP" src="images/selectUser.png" ><br></p></center>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;"></p>
        <center style="max-height: 5%;"><p class="descripcionHelp" >
                Pour une recherche avec plus de détails, il est necessaire de remplir tous les champs.</p></center>
        <center><p class="noteHelp"><img alt="HELP" style="height: 25%;"src="images/searchByDateHour.png"></p></center>
        <p class="descripcionHelp" style="padding-top: 30px;">Pour réaliser la recherche appuyer sur le boutton
            <img alt="HELP" class="imagesPopupHelp" src="images/consultar.png">
        </p>
        <br><br><center>_________________________________________________________</center><br><br>
    </div>
        <script type="text/javascript">
            var myCalendar;
            window.onload = function doOnLoad() {
                    myCalendar = new dhtmlXCalendarObject(["calendar","calendar2"]);
                        myCalendar.attachEvent("onClick", validateDates);
            }
</script>
        <script type="text/javascript" src="js/login/popup.js" ></script><script type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
</body>
</html>
