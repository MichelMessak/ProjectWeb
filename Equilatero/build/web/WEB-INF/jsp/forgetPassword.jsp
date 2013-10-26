<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE html>

<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <title>Oublie du Mot de Passe</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/design.css";
            @import "css/Help/helpStyle.css";
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
            @import "css/Form/bootstrap.css";
            @import "css/Form/form.css";
        </style>
         <style type="text/css">
        input:focus
            {
              border: 2px solid #000;
              background: #F3F3F3;
            }
        select:focus
            {
              border: 2px solid #000;
              background: #F3F3F3;
            }
        </style>
    </head>
    <body>
         <%@include file="header.jspf"%>
         <div class="taskTitle">Oublie du Mot de Passe</div>
         <center>
        <div id="mainContainer">
            <form:form method="POST" id="myForm" commandName="forgetPasswordForm">
               <fieldset>
                  <div class="fieldgroup">
                        <label>Nom d'utilisateur</label>
                    </div>
                    <div class="fieldgroup">
                        <label>Nouveau mot de passe</label>
                        <form:password path="password" maxlength="40" htmlEscape="" title="Nouveau mot de passe"/>
                        <form:errors path="password" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                    </div>
                    <div class="fieldgroup">
                        <label>Comfirmation du nouveau mot de passe</label>
                        <form:password path="confirmationPassword" maxlength="40" title="Comfirmation du nouveau mot de passe"/>
                        <form:errors path="confirmationPassword" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                    </div>
                    <div class="fieldgroup">
                        <input type="submit" value="Register" class="submit" title="Valider">
                    </div>
                        <input type="hidden" value="${per_id}" name="per_id"/>
                </fieldset>
            </form:form>
        </div>
         </center>
    <div style="bottom: 8px;position: absolute;right: 1%;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Nouveau mot de passe</p></h3><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Cette fenêtre nous permet de changer de mot de passe.</p>
        <p class="noteHelp" style="padding-bottom: 10px;">Vous devez renseigner votre mot de passe actuel.</p>
        <p class="descripcionHelp" style="padding-top: 30px;">
            Pour changer de mot de passe, appuyer sur le bouton
            <img alt="HELP" class="imagesPopupHelp" src="images/register.png"></p>
        <br><br><center>_________________________________________________________</center><br><br>
     </div>
     <script type="text/javascript">$(function() { $("#myForm :input").tooltip({position: "center right", offset: [-2, 10], effect: "fade",opacity: 0.7 }); });</script>
<script type="text/javascript" src="js/login/popup.js"></script><script  type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
</body>
</html>