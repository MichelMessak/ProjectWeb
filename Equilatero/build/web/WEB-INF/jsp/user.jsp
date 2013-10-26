<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.jspf" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Utilisateurs</title>
        <style type="text/css" title="currentStyle">
            @import "css/design.css";
            @import "css/Help/helpStyle.css";
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
            @import "css/datatable/demo_page.css";
            @import "css/datatable/demo_table.css";
            @import "css/datatable/TableTools.css";
        </style>
        <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript">

function demo1() {

     var elements = document.getElementsByTagName('input');
     var elementos = document.getElementsByTagName("th");
     var texto = document.getElementById("texto").value;
     alert(document.getElementById("th").innerHTML);
      var text = "";
     var fila = 0;
     parseInt(fila);
     var doc = new jsPDF();
          for(var i=0; i<elementos.length; i++) {
            var input = elements[i];
            var th = elementos[i];
             fila = fila+10;
             var text = elementos[i].toString();
             alert(text);
            //text = th.innerHTML;
            //alert(th.innerHTML);
            doc.text(20, fila, text);
          }
          doc.setFontSize(16);
                doc.text(20, 20, 'gogg');
                doc.setFontSize(10);

                doc.addPage();
                doc.text(20, 20, 'test');
	// Output as Data URI
	doc.output('datauri');
}

</script>
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
                        "sDom": 'T<"clear">lfrtip',
                    "oTableTools": {

                            "sSwfPath": "js/swf/copy_csv_xls_pdf.swf",
                            "sRowSelect": "multi",
                            "aButtons": [
                               
                            ]
                        },
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
        <%@include file="header.jspf"%>
        <div id="contai">
<c:if test="${not empty error}">
    <div id="error">
        <span>${error}</span>
    </div>
</c:if>
            <div id="demo">
                <a id="addUser" href="#" onclick="sendRequest('reportUser.task', 'addUser.form' ,'addUser','add')">
                    <img alt="Ajouter un Utilisateur" title="Ajouter un utilisateur"src="images/button_add_user.png" style="border: 0;"/>
                </a>
            </div>
            <c:if test="${not empty data}">
                <div id="test">
                <table class="display" cellspacing="0" cellpadding="0" border="0" id="example" width="100%">
                    <thead>
                        <tr>
                            <c:forEach var="columnNames" items="${data.columnNames}">
                                <th id="th">${columnNames}</th>
                            </c:forEach>
                            <c:forEach var="columnExtras" items="${data.columnExtras}">
                                <th>${columnExtras} </th>
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
                </div>
            </c:if>
        </div>
        <div style="bottom: 80%;position: absolute;right: 1%;">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
            <h3 align="center" style="font-size: 15px;"><p class="topHelp">Utilisateurs</p></h3><br>
            <p class="normalHelp" style="font-size: 15px;"></p>
            <p class="descripcionHelp" style="padding-bottom: 10px;">Cette fenêtre permet de visualiser tous les utilisateurs du logiciel.</p>
            <p class="descripcionHelp">Pour réaliser des recherches spécifiques, utiliser les champs prévus à cet effet.<br></p><br><br>
            <p class="normalHelp"><img src="images/button_add_user.png" style="height: 25px;width: 25px;" alt="HELP">
                &nbsp;&nbsp;&nbsp;&nbsp;Pour ajouter un nouvel utilisateur appuyer sur le bouton.<br>
                <img alt="HELP" src="images/deleteIcon.png">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pour supprimer un utilisateur appuyer sur le bouton<br>
                <img alt="HELP" src="images/editIcon.png">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pour modifier les données d'un utilisateur appuyer sur le bouton.<br></p>
            <br><br><center>_________________________________________________________</center><br><br>
        </div>

<script type="text/javascript" src="js/login/popup.js" ></script>
<script type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>

        <script type="text/javascript" language="javascript" src="js/ExportDocuments/ExportDocuments.js"></script>
<script type="text/javascript">

</script>
</body>
</html>