<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <title>Modification d'un utilisateur</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/design.css";
            @import "css/Ayudas/estiloAyuda.css";
            @import "css/login/popup.css";
            @import "css/login/estilopopup.css";
            @import "css/AltaUsuariosForm/bootstrap.css";
            @import "css/AltaUsuariosForm/form.css";
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
        <style type="text/css">
input[type=checkbox]{
        border: 1px solid #686868;
        height: 27px;
        width: 79px;
        -webkit-appearance: none;
        -webkit-border-radius: 13px;
        background-image: url(images/checkbox-background.png);
        background-position: -52px 0;
        background-repeat: no-repeat;
        -webkit-animation-timing-function: ease-in-out;
        -webkit-animation-duration: 400ms;
        -webkit-animation-name: checkbox-switch-off;
      }

      input:checked[type=checkbox] {
        background-position: 0 0;
        -webkit-animation-name: checkbox-switch-on;
      }


      @-webkit-keyframes checkbox-switch-on {
        from {
          background-position: -52px 0;
        }
        to {
          background-position: 0 0;
        }
      }

      @-webkit-keyframes checkbox-switch-off {
        from {
          background-position: 0 0;
        }
        to {
          background-position: -52px 0;
        }
      } 
</style>
    </head>
    <body>
         <%@include file="header.jspf"%>
         <div id="all"></div>
         <center>
             <div id="mainContainer">
            <form:form method="POST" id="myForm" commandName="modifyUserForm" >
               <fieldset>
                   <label>Mdifiez les données souhaitées</label>
                   <div class="fieldgroup">
                            <label>ID de l'utilisateur</label>
                            <form:input path="id" title="Modifiez l'ID de l'utilisateur" readonly="true"/>
                            <form:errors path="id" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                    </div>
                    <div class="fieldgroup">
                            <label>Nom</label>
                            <form:input path="name" title="Modifiez le nom de l'utilisateur"/>
                            <form:errors path="name" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                    </div>
                   <div class="fieldgroup">
                            <label>Courriel</label>
                            <form:input path="email" title="Modifiez le courriel de l'utilisateur"/>
                            <form:errors path="email" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                   </div>
                   <div class="fieldgroup">
                            <label>Estatus del Usuario</label>
                            <form:select path="status" items="${statuses}" title="Estado de usuario"></form:select>

                    </div>
                    <div class="fieldgroup">
                        <label>Modifier le Mot de Passe</label>
                        <form:checkbox path="resetPassword" cssClass="checkbox" title="Forcer la modification du mot de passe"/>
                    </div>
                    <div class="fieldgroup">
                            <label>Mot de Passe</label>
                            <form:password path="password" title="Ajouter le nouveau mot de passe" />
                            <label class="error"><form:errors path="password" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/></label>
                    </div>
                    <div class="fieldgroup">
                            <label>Comfirmation Mot de Passe</label>
                            <form:password path="confirmationPassword" title="Comfirmation du nouveau mot de passe"/>
                            <form:errors path="confirmationPassword" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                    </div>
                    <div class="fieldgroup">
                        <input type="submit" value="userModification" class="submit" title="Modifier l'utilisateur"/>
                    </div>
                    <div style="position: absolute;margin-left: 5px;margin-top: -65px;"><a href="reporteGenericoUsuarios.task?isSubmit=true">
                            <img alt="Retour" title="Retour"src="images/back.png" style="height: 60px;width: 60px;"/>
                        </a>
                    </div>
                    <input type="hidden"  name="isSubmit" value="true"/>
                </fieldset>
            </form:form>
        </div>
        </center>
        <div style="bottom: -90px;position: absolute;right: 1%;">
                <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
        </div>
     <div style="bottom: 8px;position: absolute;right: 1%;">

    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
         <h3 align="center" style="font-size: 15px;"><p class="topHelp">Modification d'un utilisateur</p></h3><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Cette fenêtre permet de modifier les données de l'utilisateur sélectionné.</p>
        <p class="descripcionHelp" style="padding-top: 30px;">L'ID de l'utilisateur ne peux être modifié</p>
        <p class="descripcionHelp" style="padding-top: 30px;"></p>
        <p class="noteHelp" style="padding-top: 30px;">Mot de passe: Si vous ne voulez pas changer le mot de passe, appuyer sur le bouton prévu à cette effet</p>
        <p class="descripcionHelp" style="padding-top: 30px;">
            Pour retourner au menu antérieur, appuyer sur le bouton
                <img alt="HELP" class="imagesPopupHelp" style="height: 35px;width: 35px;" src="images/back.png">
        </p>
        <p class="descripcionHelp" style="padding-top: 30px;">
            Pour modifier l'utilisateur, appuyer sur le bouton 
            <img alt="HELP" class="imagesPopupHelp" src="images/ModificaUsuario.png"></p>
        <br><br><center>_________________________________________________________</center><br><br>
     </div>
<script type="text/javascript" language="javascript">
  // execute your scripts when the DOM is ready. this is a good habit
  $(function() {

        // select all desired input fields and attach tooltips to them
      $("#myForm :input").tooltip({

      // place tooltip on the right edge
      position: "center right",

      // a little tweaking of the position
      offset: [-2, 10],

      // use the built-in fadeIn/fadeOut effect
      effect: "fade",

      // custom opacity setting
      opacity: 0.7

      });
    });
</script>

<script type="text/javascript" src="js/login/popup.js" ></script><script type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral2.jpg)"; document.body.style.backgroundRepeat = "repeat";document.body.backgroundPosition = "top";document.body.style.top = "1000px";document.body.style.opacity = "1";}</script>
</body>
</html>