<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="include.jspf" %>
<html>
    <head>
        <%@ page language="java"%>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta name="Robots" content="all" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="css/reset.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/design.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/login/tooltips.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="css/Help/helpStyle.css"/>
        <link rel="stylesheet" type="text/css" href="css/login/popup.css"/>
        <link rel="stylesheet" type="text/css" href="css/login/popupStyle.css"/>
        <link type='text/css' href='css/login/loginTongue.css' rel='stylesheet' media='screen' />
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <title>Equilatero</title>

    <style type="text/css">
        
form input[type="submit"]{
    background: none;
    border: none;
    color: #6e9f7a;
    text-decoration: underline;
    cursor: pointer;
    position: fixed;
    margin-top: 2.9%;
    margin-left:-40%;
}
    </style>
<script type="text/javascript">
    function openDialog() {
                Avgrund.show( "#popupHelp" );
                document.getElementById("mainContainer").style.display="none";
        }
    function closeDialog() {
                Avgrund.hide();
                document.getElementById("mainContainer").style.display="";
        }
</script>
        <style type="text/css">td, th {
            border: 1px solid black !important;
        }</style>
</head>
<body onload="checkVersion()">
<div id="header"></div>
<div>
    <div id="help" style="top:-120%;" class="forkit-curtain" >
    <div id="close" class="close-button"></div>
        <div id="LogIn">
            <div id="mainContainer">
                <form action="auth.do" method="POST" name="addUserForm" id="myForm">
                   <br><span class="titFormLogin">Ajouter son ID d'utilisateur</span>
                    <div>
                    <fieldset>
                        <div class="fieldgroup">
                            <input class="init" name="user_id" style="background:url('images/icon-username.png')no-repeat left; border: 1px; background-color: #fff;" onFocus="siguienteCampo ='user_pwd';" class="field_string" placeholder="ID de l'utilisateur" title="ID de l'utilisateur" required="required" tabindex="1"/>
                        </div>
                        <div class="fieldgroup"><br>
                             <input name="user_pwd" style="background:url('images/icon-password.png')no-repeat left; border: 1px;" onFocus="siguienteCampo ='button_submit';" class="field_pass" id="password" placeholder="Mot de Passe"type="password" title="Le mot de passe peut contenir tous les caractères"tabindex="2"/>
                        </div><br><br><br>
                        <div class="fieldgroup" style="border: black">
                        <input value="init" type="image" src="images/FileControl4-IniciarSesion-Boton.png"name="button_submit" title="Cliquer pour démarrer la session" style="margin-top: 2.5%;margin-left: 24%;"tabindex="3"/>
                        </div>
                    </fieldset>
                    </div>
                 </form>
            </div>
        </div>
    </div>
</div>
<div id="Logo"></div>
<div id="page_container">
<a style="transform: translate(0px, 0px) rotate(0deg); display: block;" id="login" class="forkit" data-text="Login" data-text-detached="Tirez vers le bas" href="#">
    <span style="transform: translate(80px, 0px) rotate(172.875deg); width: 3.13437px;" class="string"></span>
    <span style="transform: translate(80px, -15px) rotate(45deg);" class="tag"></span>
</a>
  <div id="content">
          <div style="text-align:justify">
                
           <c:if test="${not empty error}">
                <div id="error">
                    <span>${error}</span>
                </div>
            </c:if>
           <c:if test="${not empty exception}">
                <div id="error">
                    <span>${exception}</span>
                </div>
            </c:if>
            <c:if test="${not empty from}">
                <div id="from">
                    <span>Vous avez été redirigé depuis ${from}</span>
                </div>
            </c:if>  
        </div>
  </div>
</div>
  
    <div style="position: fixed;padding: 10px;color:green; bottom: 10px; right: 10px; z-index: 1;">

            <a href="#" class="help" accesskey="h" onclick="javascript:openDialog();">
                <img alt="HELP" title="Help" src="images/help.png">
            </a>
    </div>
   <div id="popupHelp" class="avgrund-popup">
       <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Ecran Principal</p></h3><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Identification</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">
            Pour pouvoir s'enregistre, veuillez tirer sur la bandelette.
            <img alt="HELP" src="images/acces.png">
        </p>
        <center><p class="noteHelp">La deuxième page se chargera après avoir s'être connecté</p></center>
        <p class="descripcionHelp" style="padding-top: 20px;">
            Après avoir renseigner vos identifiants, appuyer sur le bouton
            <img alt="HELP" src="images/FileControl4-IniciarSesion-Boton.png">
        </p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Erreur d'Identification</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Si vous introduiser un nom d'utilisateur/mot de passe erronés, la page suivante vous l'indiquera</p>
        <center><p class="noteHelp">Par politique, l'utilisateur sera bloqué après 3 tentatives erronées</p></center>
        <p class="descripcionHelp" style="padding-top: 20px;">
            Après avoir fait les modifications necessaires, cliquer sur l'image
            <img alt="HELP" src="images/FileControl4-IniciarSesion-Boton.png">
        </p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Politique de chagement de mot de passe</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">&deg;Le systeme peut bliger l'utilisateur a changer de mot de passe.</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">&deg; Si votre compte est bloqué, vous devez vous imformze auprès de l'administrateur.</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">&deg; Vous pouvez changer votre mot de passe depuis le menu principal.</p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalHelp" style="padding-bottom: 20px;font-size: 15px;">Réception de mot de passe</p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Quand l'administrateur débloque votre compte, votre nouveau mot de passe vous sera envoyé par mail</p>
        <br><br><center>_________________________________________________________</center><br><br>
        <table border="0">
          <tr>
              <td style="width: 33%;">
                  <p class="normalHelp" style="padding-bottom: 15px;padding-left: 20px; font-size: 15px;">Session</p>
                  <p class="descripcionHelp" style="padding-bottom: 10px;padding-left: 20px;font-size: 10px;">
                      Une fois que l'utilisateur s'est enregistré, FileControl lui assigne une session de travail contenant toutes les données propres à l'utilisateurs</p>
                  <p class="noteHelp" style="font-size: 10px;padding-left: 20px;"></p>
              </td>
              <td style="width: 33%;">
                <p class="normalHelp" style="padding-bottom: 15px;padding-left: 20px; font-size: 15px;">Sécurité</p>
                <p class="descripcionHelp" style="padding-bottom: 10px;padding-left: 20px;font-size: 10px;">Pour plus de sécurité, FileControl gère le temps d'inactivité</p>
                <p class="noteHelp" style="font-size: 10px;padding-left: 20px;">Chaque session expirera après 30 secondes d'inactivité. </p>
              </td>
          </tr>
        </table>        
    </div>   
<script type='text/javascript' src='js/Helps/jquery.simplemodal.js'></script>
<script type='text/javascript' src='js/Helps/basic.js'></script>
<div id="ImgIni"></div>
<script type="text/javascript">
  $(function(){$("#myForm :input").tooltip({position: "center right", offset: [6, 10],effect: "slide",opacity: 0.7});});
</script>
<script type="text/javascript">
function error()
{  
var divs = document.getElementById("error");
var div2 = document.getElementById("from")
        if (divs != null || div2!= null)
            {
                document.getElementById('here').click();
            }
}
</script>
<script type="text/javascript" src="js/forkit.js" type="text/javascript"></script>
<script type="text/javascript" src="js/login/popup.js" type="text/javascript"></script>
<script type="text/javascript">
function body()
        {
               // alert("entro");
                document.body.style.backgroundImage = "url(images/backgeneral.jpg)";
                //alert("d");
                document.body.style.backgroundRepeat = "repeat-x";
                document.body.backgroundPosition = "top";
                document.body.style.top = "50px";
                document.body.style.opacity = "1";
                //alert("termino");
         }
</script>
</body>
</html>