<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entreprise</title>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/Help/helpStyle.css";
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
            @import "css/design.css";
            @import "css/datatable/demo_page.css";
            @import "css/datatable/demo_table.css";
            @import "css/datatable/TableTools.css";
	</style>
        <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>

        <script type="text/javascript" charset="utf-8">
var asInitVals = new Array();
        $(document).ready(function()
        {
            $('#example').dataTable({
            "sPaginationType": "full_numbers",
            "bServerSide": true,
            "sAjaxSource": "${data.URL}",
            "BJQueryUI": true,
             "aoColumns": [
                            ${data.javascriptColumnDefinition}
                            ],
            "oLanguage": {
            "sSearch": "Rechercher dans toutes les colonnes:"
        }
} );
	$("tfoot input").keyup( function () {
		/* Filter on the column (the index) of this element */
		oTable.fnFilter( this.value, $("tfoot input").index(this) );
	} );
	/*
	 * Support functions to provide a little bit of 'user friendlyness' to the textboxes in
	 * the footer
	 */
	$("tfoot input").each( function (i) {
		asInitVals[i] = this.value;
	} );

	$("tfoot input").focus( function () {
		if ( this.className == "search_init" )
		{
			this.className = "";
			this.value = "";
		}
	} );

	$("tfoot input").blur( function (i) {
		if ( this.value == "" )
		{
			this.className = "search_init";
			this.value = asInitVals[$("tfoot input").index(this)];
		}
            });
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
        <div id="contai">
        <div id="demo">
            <a href="addEnterprise.form"><img alt="Ajouter une entreprise" title="Ajouter une entreprise"src="images/button_add_enterprise.png"style="border: 0;"/></a></div>
        <c:if test="${not empty data}">
            <table class="display" cellspacing="0" cellpadding="0" border="0" class="display" id="example" width="100%">
             <thead>
                <tr>
                    <c:forEach var="columnNames" items="${data.columnNames}">
                        <th>${columnNames}</th>
                    </c:forEach>
                    <c:forEach var="columnExtras" items="${data.columnExtras}">
                        <th>${columnExtras}</th>
                    </c:forEach>
                </tr>
             </thead>
            <tbody>
                <c:forEach var="rows" items="${data.data}">
                <tr>
                    <c:forEach var="cols" items="${rows}">
                        <td>${cols}</td>
                    </c:forEach>
                </tr>
                </c:forEach>
            </tbody>
             
             <tfoot>
                <tr>
                    <c:forEach var="search" items="${data.javascriptSearchDefinition}">
                        <th>${search}</th>
                    </c:forEach>
                </tr>
            </tfoot>
            </table>
        </c:if>
        </div>
       <div style="bottom: 8px;position: inherit;float:left;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Entreprise</p></h3><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-bottom: 10px;">Le tableau nous montre une liste des entreprises associéees à l'utilisateur.</p>
        <p class="descripcionHelp">
            Sur les champs de texte vous pouvez réaliser une recherche par colonne
            <br>
        </p>
        <br><br>
        <p class="normalHelp">
            <img style="height: 25px;width: 25px;"alt="HELP" src="images/button_add_enterprise.png">
            &nbsp;&nbsp;&nbsp;&nbsp;Pour ajouter une entreprise appuyer sur le boutton ???.
            <br>
            <img alt="HELP" src="images/deleteIcon.png">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;En appuyant sur cette icone vous pouvez supprimer l'entreprise selectioner.
            <br>
            <img alt="HELP" src="images/editIcon.png">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pour modifier les données de l'entreprises, appyuer sur le boutton.
            <br>
        </p>
        <br><br><center>_________________________________________________________</center><br><br>
     </div>
<script type="text/javascript" src="js/login/popup.js" ></script><script type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral2.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
<script type="text/javascript" language="javascript" src="js/ExportDocuments/ExportDocuments.js"></script>
    </body>
</html>
