<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="include.jspf" %>
<html>
    <head>
        <%@ page language="java"%>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta name="Keywords" content="File, Control, 4, www.itcomplements.com, itcomplements, gestor, documental" >
        <meta name="Robots" content="all" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="css/reset.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/design.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/login/tooltips.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/Help/helpStyle.css"/>
        <link rel="stylesheet" type="text/css" href="css/login/popup.css"/>
        <link rel="stylesheet" type="text/css" href="css/login/popupStyle.css"/>
        <link type='text/css' href='css/login/loginTongue.css' rel='stylesheet' media='screen' />
        
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
    margin-left: -40%;
}
    </style>
<script>
</script>
        <style>td, th {
            border: 1px solid black !important;
        }</style>
</head>
<body onload="checkVersion()">
<div id="header"></div>
<div><br>
    <center><br>
    <div id="close" class="close-button"></div>
        <div id="LogIn">
            <div id="frm"><br>
                <form action="auth.do" method="POST" name="altaUsuarioForm" id="myform">
                   <br><span class="titFormLogin">Ingrese sus Datos de Usuario</span>
                    <div>
                    <fieldset>
                        <div class="fieldgroup">
                            <label><strong style="padding-top: -110px; font-weight: bold;"><%--Ingresa tus datos--%></strong></label><br><br>
                             <input id="inicio" name="user_id" style="background:url('images/icon-username2.png')no-repeat left; border: 1px; background-color: #fff;" onFocus="siguienteCampo ='user_pwd';" class="field_string" placeholder="Equilatero ID" title="Nombre de usuario, ingresar el ID del usuario con el que fue dado de alta" required="required" tabindex="1"/>
                            <%--<span class="hint">Nombre de usuario, este debe contener una extension mayor a 6 caracteres.</span>--%>
                        </div>
                        <div class="fieldgroup"><br>
                             <label><%--Contrase&ntilde;a:--%></label>
                             <input name="user_pwd" style="background:url('images/icon-password.png')no-repeat left; border: 1px;" onFocus="siguienteCampo ='button_submit';" class="field_pass" id="password" placeholder="Contrase&ntilde;a"type="password" title="Contrase&ntilde;a puede contener cualquier caracter."tabindex="2"/>
                            <%--<span class="hint">Contrase&ntilde;a puede contener cualquier caracter.</span>--%>
                        </div><br><br><br><%--¿Olvidó su contraseña?--%>
                        <div class="fieldgroup" style="border: black">

                        <input value="inicio" type="image" src="images/FileControl4-IniciarSesion-Boton.png"name="button_submit" title="Da click para iniciar sesion."  style="margin-top: 2.5%;margin-left: 24%;"tabindex="3"/>
                        <input name="olvido" style=""type="submit" value="¿Olvidó su contraseña?" title="Da click para iniciar sesion."/>
                       </div>
                    </fieldset>
                    </div>
                 </form>
            </div>
        </div>
</center>
</div>
<div id="Logo"></div>
  <div id="content">
          
          <div style="text-align:justify">
            <noscript>
                <p style="color: red; font-size: 24px;">Bienvenido a equilátero</p>
                <p style="color: red; font-size: 19px;">La p&aacute;gina que est&aacute;s viendo requiere para su funcionamiento el uso de JavaScript.
                    Si lo has deshabilitado intencionadamente, por favor vuelve a activarlo.</p>
            </noscript>

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
                    <span>Se redirigió a esta página desde ${from}</span>
                </div>
            </c:if>
        </div>
  </div>
   
   <div id="default-popup" class="avgrund-popup">
       <button id="cerrar" onclick="javascript:closeDialog();">Cerrar</button>
        <h3 align="center" style="font-size: 15px;"><p class="topAyuda">Pantalla Principal</p></h3><br><br>
        <p class="normalAyuda" style="padding-bottom: 20px;font-size: 15px;">Autenticaci&oacute;n</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">Para desplegar la pantalla de ingreso de datos de click en la imagen <img alt="AYUDA" src="images/acces.png"></p>
        <center><p class="notaAyuda">Se motrara la siguiente pantalla en la cual ingresara sus datos de usuario</p></center>
        <p class="descripcionAyuda" style="padding-top: 20px;">Despues de ingresar sus datos de click en la imagen <img alt="AYUDA" src="images/FileControl4-IniciarSesion-Boton.png"></p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalAyuda" style="padding-bottom: 20px;font-size: 15px;">Autenticaci&oacute;n Erronea</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">Si introduce su nombre de usuario o contraseña incorrectamente se mostrara la siguiente pantalla indicando el error</p>
        <center><p class="notaAyuda">Por politica despues de 5 reintentos fallidos se bloqueara su cuenta</p></center>
        <p class="descripcionAyuda" style="padding-top: 20px;">Despues de corregir sus datos de click en la imagen <img alt="AYUDA" src="images/FileControl4-IniciarSesion-Boton.png"></p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalAyuda" style="padding-bottom: 20px;font-size: 15px;">Politica de cambio de Contraseña</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">&deg;El sistema puede obligar al usuario a cambiar su contraseña en determinado.</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">&deg; Si su cuenta ha sido bloqueada debe solicitar al administrador que le reactive su cuenta.</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">&deg; Puede cambiar su contraseña desde el menu de Usuarios.</p>
        <br><br><center>_________________________________________________________</center><br><br>
        <p class="normalAyuda" style="padding-bottom: 20px;font-size: 15px;">Recepci&oacute;n de contraseña</p>
        <p class="descripcionAyuda" style="padding-bottom: 10px;">Cuando su administrador reestablesca su cuenta, su nueva contraseña le sera enviada via correo electronico.</p>
        <br><br><center>_________________________________________________________</center><br><br>
        <table border="0">
          <tr>
              <td style="width: 33%;">
                <p class="normalAyuda" style="padding-bottom: 15px;font-size: 15px;">Periodos</p>
                <p class="descripcionAyuda" style="padding-bottom: 10px;font-size: 10px;">FileControl4 trabaja a base de periodos. <br>Cada periodo se divide anualmente; esto quiere decir que tendremos un periodo por año. <br>El usuario debe elegir que periodo utilizara para realizar consultas </p>
                <p class="notaAyuda" style="font-size: 10px;">Para Cambiar el periodo, de click en la imagen <img alt="AYUDA" src="images/down-per.png"> que se localiza en la parte superior derecha.</p>
                <br><br><br>
              </td>
              <td style="width: 33%;">
                  <p class="normalAyuda" style="padding-bottom: 15px;padding-left: 20px; font-size: 15px;">Sesi&oacute;n</p>
                  <p class="descripcionAyuda" style="padding-bottom: 10px;padding-left: 20px;font-size: 10px;">Una vez que el usuario logra autenticarse, FileControl4 le asigna una sesion de trabajo con la que podra almacenar y consultar la informaci&oacute;n deseada</p>
                  <p class="notaAyuda" style="font-size: 10px;padding-left: 20px;"></p>
              </td>
              <td style="width: 33%;">
                <p class="normalAyuda" style="padding-bottom: 15px;padding-left: 20px; font-size: 15px;">Seguridad</p>
                <p class="descripcionAyuda" style="padding-bottom: 10px;padding-left: 20px;font-size: 10px;">Para una mayor seguridad en la informaci&oacute;n FileControl4 maneja un tiempo de vida en la sesi&oacute;n</p>
                <p class="notaAyuda" style="font-size: 10px;padding-left: 20px;">Cada sesi&oacute;n expira cada 30 segundos de inactividad. Despues de que la sesion expiro usted debe autenticarse nuevamente</p>
              </td>
          </tr>
        </table>
    </div>
<div id="ImgIni"></div>
<script type="text/javascript">
  $(function(){$("#myform :input").tooltip({position: "center right", offset: [6, 10],effect: "slide",opacity: 0.7});});
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
<%--<script type="text/javascript" src="js/forkit.js" type="text/javascript"></script>--%>
<script>
    <%--document.oncontextmenu=new Function("return false")--%>
</script>
<%--<script type="text/javascript" src="js/login/popup.js" type="text/javascript"></script>--%>
</body>
</html>