<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>        
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type='text/javascript' src='http://code.jquery.com/jquery-1.6.js'></script>
        <%@include file="htmlhead.jspf" %>
        <title>Filtre des documents</title>

<style type="text/css" title="currentStyle">
    @import "css/reset.css";
    @import "css/datatable/demo_page.css";
    @import "css/Help/helpStyle.css";
    @import "css/login/popup.css";
    @import "css/login/popupStyle.css";
    @import "css/design.css";
    @import "css/filterDocGen/filterDocGen.css";
    @import "css/filterDocGen/form.css";
</style>
        <link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar.css"/>
	<link rel="stylesheet" type="text/css" href="css/filterDocGen/dhtmlxcalendar_dhx_skyblue.css"/>
	<script type="text/javascript" src="js/filterDocGen/dhtmlxcalendar.js"></script>
    </head>
    <body>
        
        <%@ include file="header.jspf" %>
        <%@ include file="clearFilterAttrs.jspf" %>
        <center>
            <div id="mainContainer">
               <form:errors path="*" cssClass="errorBlock" element="div" />
               <form method="post" action="" name="form">
                   <fieldset>
                        <div class="fieldgroup">
                            <label>Entreprise</label>
                                <select id="officeItemList" name="emp_id">
                                <c:forEach var="enterprise" items="${enterprises}">
                                        <option value="${enterprise.id}">${enterprise.id}   |   ${enterprise.name}</option>
                                </c:forEach>
                                </select>
                        </div>
                        <div class="fieldgroup">
                            <label>Date</label>
                            <input type="text" id="calendar" placeholder="Date Final" name="date_end"/>
                            <input type="text" id="calendar2" placeholder="Date Inicial" name="date_init"/>
                        </div>
                        <div class="fieldgroup">
                            <label>ID</label>
                            <input type="text" id="filterFinal" maxlength="10" size="2" placeholder="ID Final"name="ID_fin" value="" onblur="filterDoc()">
                            <input type="text" id="filterInit" maxlength="10" size="2" placeholder="ID Inicial" name="ID_ini" value="" onblur="filterDoc()">
                        </div>         
                       <div class="fieldgroup">
                            <label>UUID</label>
                            <input type="text" placeholder="UUID"name="UUID" value="" id="UUID" onblur="filterDoc()">
                       </div>
                        <p id="message"></p>
                   <div class="fieldgroup">
                        <input id="consult" name="isFilterSubmit" type="submit" value="Valider" class="submit" title="Valider">
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
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Fenêtre de recherche de documents</p></h3><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Ce menu nous facilite la recherche des documents.</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">En premier lieu, il faut sélectioner l'entreprise dont on veut consulter les documents.</p>
        <center><p class="noteHelp">Pour sélectioner une entreprise, cliquer sur les cadre adéquat pur faire dérouler toutes les entreprises disponibles.</p></center>
        <center><img alt="HELP" class="imagesPopupHelp" src="images/helpReport.png" ><br></center>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Recherche par dates</p>
        <center style="max-height: 5%;"><p class="descripcionHelp" >Pour réaliser une recherche plus exacte vous pouvez completer les champs correspondants aux dates désirées.</p></center>
        <center><p class="noteHelp"><img alt="HELP" class="imagesPopupHelp" src="images/searchByDate.png"></p></center>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Recherche par ID</p>
        <center style="max-height: 5%;"><p class="descripcionHelp" >Pour réaliser une recherche plus exacte vous pouvez completer les champs correspondants aux ID désirés.</p></center>
        <center><p class="noteHelp"><img alt="HELP" class="imagesPopupHelp" src="images/help-comodin.png"></p></center>
        <p class="descripcionHelp" style="padding-top: 30px;">Pour valider la recherche appyer sur le bouton
            <img alt="HELP" style="-webkit-border-radius: 6px;-moz-border-radius: 6px;border-radius: 6px;" src="images/consult.png">
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
