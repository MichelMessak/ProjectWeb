<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <script type='text/javascript' src='http://code.jquery.com/jquery-1.6.js'></script>                        
        <title>Menu FileControl</title>
        <style type="text/css" title="currentStyle">
            @import "css/Help/helpStyle.css";
            @import "css/reset.css";
            @import "css/design.css";
            @import "css/datatable/demo_page.css";
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
        </style>
    </head>
    <body>
        <%@ include file="header.jspf" %>
        <center>
<c:if test="${not empty message}">
                <div id="message">
                    <span>${message}</span>
                </div>
            </c:if>
         <div id="containerMenu">
            <table align="" style="width: 70%;">
                <tr>
                    <c:forEach var="t" items="${sessionScope.UserTasks}" varStatus="status">
                        <c:if test="${t.type == 'MENU'}">
                            <td>
                                <div id="menu" align="center">
                                    <form action="${t.URL}" method="post" name="${t.ID}_basicform" id="${t.ID}_basicform">
                                        <input type="image" src="images/${t.image}" name="${t.ID}_submit" value="${t.name}"/>
                                        <input type="hidden" name="isSubmit" value="true"/>
                                        <div id="menuTitle">${t.name}</div>
                                    </form>
                                </div>
                            </td>
                            <c:set var="contador" value="${contador + 1}"/>
                            <c:if test="${(contador%5) == 0}">
                                </tr><tr>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </tr>
            </table>
        </div>
        </center>
<div style="position: fixed;padding: 10px;color:green; bottom: 10px; right: 10px; z-index: 4;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
    <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Close</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Menu Principal</p></h3><br><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">C'est dans le menu principal que sont répertrié les différentes options disponible pour l'utilisateur.</p>
        <p class="noteHelp">Pour accéder a une option, cliquez sur l'icone correspondante dans le menu principal.</p>
        <br><br><br><center>_________________________________________________________</center><br><br><br><br>
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
    
    <script type="text/javascript" src="js/login/popup.js" ></script>
<script type="text/javascript">
    function openDialog() {
                Avgrund.show( "#popupHelp" );
        }
    function closeDialog() {
                Avgrund.hide();
        }

</script>
<script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
       </body>
</html>