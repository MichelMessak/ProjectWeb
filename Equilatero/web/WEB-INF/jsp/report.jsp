<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Report</title>

        <style type="text/css" title="currentStyle">
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
            @import "css/Help/helpStyle.css";
            @import "css/reset.css";
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
                   "bProcessing": true,
                   "bServerSide": true,
                    "BJQueryUI": true,
                    "sAjaxSource": "${data.URL}",
                    "oTableTools": {
                                        "sSwfPath": "js/swf/copy_csv_xls_pdf.swf",
                                        "aButtons": [
                                                    "print","copy",
                                                    {
                                                            "sExtends":    "collection",
                                                            "sButtonText": "Save",
                                                            "aButtons":
                                                                    [ "csv", "xls",
                                                                        {
                                                                            "sExtends": "pdf",
                                                                            "sPdfOrientation": "landscape",
                                                                            "sPdfMessage": ""
                                                                        }
                                                                    ]
                                                    }
                                                     ]
                                    },

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
<body id="dt_example" >
    <%@ include file="header.jspf" %>
<div id="contai">
    <form action="" method="post">
        <input id="previousPage" name="isSubmit" type="submit" value="Retour" title="Retour au filtre."/>
    </form>
  <div id="demo">
    <div id="example_wrapper" class="dataTables_wrapper" role="grid">
     <div class="ColVis TableTools">
        <c:if test="${not empty data}">
            <table class="display" cellspacing="0" cellpadding="0" border="0" id="example" width="100%">
                <thead>
                    <tr>
                        <c:forEach var="columnNames" items="${data.columnNames}">
                            <th>${columnNames}</th>
                        </c:forEach>
                        <c:if test="${not empty data.columnExtras}">
                            <c:forEach var="columnExtras" items="${data.columnExtras}">
                                <th>${columnExtras}</th>
                            </c:forEach>
                        </c:if>
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
     </div>
   </div>
  <br>
    <br>
</div>
    <div style="bottom: 8px;position: inherit;float:left;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <div id="documents" style="margin: 0; padding: 0;position:relative;top:0;right: 0%;">
            <button id="close" onclick="javascript:closeDialog();">Fermer</button>
            <h3 align="center" style="font-size: 15px;"><p class="topHelp">Visualisation de données</p></h3><br>
            <p class="normalHelp" style="font-size: 15px;"></p>
            <p class="descripcionHelp" style="padding-bottom: 10px;">Cette fenêtre sert à visualiser toutes les données .</p>
            <p class="descripcionHelp">Toutes les résultats de la recherches sont détaillés par plusieurs critères.<br></p><br><br>
            <p class="normalHelp">
                <img alt="HELP" src="images/status_E.png">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Indique que le document s'est généré avec des erreurs.<br>
                <img alt="HELP" src="images/status_O.png">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;indique que le document s'est généré correctement.<br>
                <img alt="HELP" src="images/files_open_E.png">
                &nbsp;&nbsp;&nbsp;Cliquer ici nous amène à la fenêtre de visualisation/téléchargement du cocument en question<br> 
                 <img alt="HELP" src="images/PDF.png">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exportation au format PDF.<br>
            <img alt="HELP" src="images/File_XLS_32.png">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exportation au format Excel.<br>
            
            </p>
            <br><br><center>_________________________________________________________</center>
        </div>
        <div id="historyActivity" style="margin: 0; padding: 0;position:relative;top:0;right: 0%;display: none;">
            <button id="close" onclick="javascript:closeDialog();">Fermer</button>
            <h3 align="center" style="font-size: 15px;"><p class="topHelp">Historique des activités</p></h3><br>
            <p class="normalHelp" style="font-size: 15px;"></p>
            <p class="descripcionHelp" style="padding-bottom: 10px;">L'historique des activité permet de cnsulter toutes les actions entreprises par un utilisateur.</p>
            <p class="descripcionHelp"><br></p><br><br>
            <p class="normalHelp"><img alt="HELP" src="images/PDF.png">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exportation au format PDF.<br>
                <img alt="HELP" src="images/File_XLS_32.png">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Exportation au format Excel.<br></p>
            <br><br><center>_________________________________________________________</center>
        </div>
    </div>
<script type="text/javascript" src="js/login/popup.js" ></script><script type="text/javascript"> function openDialog() {Avgrund.show( "#default-popup" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral2.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "80px";document.body.style.opacity = "1";}</script>
<script type="text/javascript" language="javascript" src="js/ExportDocuments/ExportDocuments.js"></script>
<script type="text/javascript">
    window.onload = function ocultaAyudas()
    {
        var taskTitle = document.getElementById("titleTask").innerHTML;
        
        if (taskTitle == "Rapport d'activité")
            {
                document.getElementById("documents").style.display="none";
                document.getElementById("historyActivity").style.display="";
            }


    }
</script>

</body>
</html>