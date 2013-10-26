<%@include file="include.jspf" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <style type="text/css" title="currentStyle">
        @import "css/datatable/demo_page.css";
        @import "css/reset.css";
        @import "css/design.css";
    @import "css/AltaUsuariosForm/bootstrap.css";
    @import "css/AltaUsuariosForm/form.css";
	</style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modification d'un utilisateur</title>
    </head>
    <body>
         <div id="header"></div>
         <div id="Logo"></div>
         <div id="user">
             ${sessionScope.user_name}
         </div>
         <a href="home.do">
             <div id="Home"></div>
         </a>
         <a href="settings.do">
             <div id="Engrane"></div>
         </a>
         <a href="logout.do" accesskey="l">
             <div id="LogOut"></div>
         </a>
         <div class="taskTitle">Modification d'un utilisateur</div><br>
         <meta HTTP-EQUIV="REFRESH" content=".5; url=reporteGenericoUsuarios.task?isSubmit=true">
<link rel="stylesheet" href="css/AltaUsuariosForm/jpreloader.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="js/AltaUsuariosForm/jpreLoader.js"></script>

<script type="text/javascript">
<!-- jPreLoader script -->
$(document).ready(function() {
	var timer;

	//calling jPreLoader function with properties
	$('body').jpreLoader({
		splashID: "#jSplash",
		splashFunction: function() {  //passing Splash Screen script to jPreLoader
			$('#jSplash').children('section').not('.selected').hide();
			$('#jSplash').hide().fadeIn(6000);

			timer = setInterval(function() {
				splashRotator();
			}, 6000);
		}
	});
});
</script>

    </body>
</html>

