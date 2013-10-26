<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <%@include file="htmlhead.jspf" %>

        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Reporte Archivos</title>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/Ayudas/estiloAyuda.css";
            @import "css/login/popup.css";
            @import "css/login/estilopopup.css";
            @import "css/design.css";
            @import "css/datatable/demo_page.css";
            @import "css/datatable/demo_table.css";
	</style>
        <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8">
      var asInitVals = new Array();

$(document).ready(function() {
	var oTable = $('#example').dataTable( {
                     "sPaginationType": "full_numbers",
                     "aoColumns": [
                                                    { "sWidth": "15%" ,"sClass":"center"},
                                                    { "sWidth": "5%" },
                                                    { "sWidth": "5%" },
                                                    { "sWidth": "20%" ,"sClass":"center"},
                                                    { "sWidth": "20%", "sClass": "center"},
                                                    { "sWidth": "5%" , "bSortable": false},
                                                    { "sWidth": "5%" , "bSortable": false},
                                            ]
                    } );
} );
        </script>
        <script type="text/javascript" charset="utf-8">
        var oTable;
        var giRedraw = false;

$(document).ready(function() {
	/* Add a click handler to the rows - this could be used as a callback */
	$("#example tbody").click(function(event) {
		$(oTable.fnSettings().aoData).each(function (){
			$(this.nTr).removeClass('row_selected');
		});
		$(event.target.parentNode).addClass('row_selected');
	});
	/* Add a click handler for the delete row */
	$('#delete').click( function() {
		var anSelected = fnGetSelected( oTable );
		oTable.fnDeleteRow( anSelected[0] );
	} );
	/* Init the table */
	oTable = $('#example').dataTable( );
} );
/* Get the rows which are currently selected */
function fnGetSelected( oTableLocal )
{
	var aReturn = new Array();
	var aTrs = oTableLocal.fnGetNodes();

	for ( var i=0 ; i<aTrs.length ; i++ )
	{
		if ( $(aTrs[i]).hasClass('row_selected') )
		{
			aReturn.push( aTrs[i] );
		}
	}
	return aReturn;
}
</script>
</head>
<body id="dt_example">
<%@ include file="header.jspf" %>
    <form id="Previous" action="" method="post">
        <input id="regresarPagina" name="isSubmitPreviousReport" type="submit" value="Previous" title="Previous" >

    </form>
  <div id="container">
      
    <c:if test="${not empty docs}">
      <table class="display" cellspacing="0" cellpadding="0" border="0" id="example" width="100%">
        <thead>
            <tr>
                <th>ID Empresa</th>
                <th>Tipo</th>
                <th>SubTipo</th>
                <th>ID Doc</th>
                <th>Archivo</th>
                <th>Ver</th>
                <th>Descargar</th>
            </tr>
        </thead>
     <c:forEach var="o" items="${docs}">
            <tr>
                <td>${o.empId}</td>
                <td>${o.docTypeId}</td>
                <td>${o.docSubtypeId}</td>
                <td>${o.docId}</td>
                <td>${o.filename}</td>
                <td><a target="blank_" href="view.do?archive=${o.filenameURLEncoded}&repository=${o.pathURLEncoded}"><img border="0" src="images/files_view.png"/></a></td>
                <td><a href="view.do?archive=${o.filenameURLEncoded}&repository=${o.pathURLEncoded}&download=true"><img border="0" src="images/files_download.png"/></a></td>
            </tr>
       </c:forEach>
     </table>
    </c:if>
    <c:if test="${empty docs}">
        <span class="empty">
            No hay archivos existentes que mostrar
        </span>
    </c:if>
   </div>
    <div style="bottom: 8px;position: absolute;right: 1%;">
        <div id="buttonHelp">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
        </div>
    </div>
<div id="popupHelp" class="avgrund-popup">
    <br><br>
        <button id="close" onclick="javascript:closeDialog();">Cerrar</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Reporte de Archivos Genericos</p></h3><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Este es un reporte de archivos guardados en el sistema, este reporte nos permite descargar o ver el archivo.</p>
        <br><br>
        <p class="normalHelp"><img src="images/files_view.png" alt="HELP">&nbsp;&nbsp;&nbsp;&nbsp;Para visualizar el documento de click en este icono.<br><img alt="HELP" src="images/files_download.png">&nbsp;&nbsp;&nbsp;&nbsp;Dando click en este icono podemos descargar el archivo.</p>
        <br><br><center>_________________________________________________________</center><br><br>
</div>
<script type="text/javascript" src="js/login/popup.js" type="text/javascript"></script><script> <%--//activa popup para las ayudas--%>function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
 <script type="text/javascript" language="javascript" src="js/ExportDocuments/ExportDocuments.js"></script>
</body>
</html>