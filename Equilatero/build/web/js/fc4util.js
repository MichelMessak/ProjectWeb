/**
 * Funciones javascript para manejo del GUI de FileControl4
 * @author Segundo García Heras
 * @since 1.0.0.0
 */

/**
 * Permite hacer la ejecución de una consulta Ajax al cambiar la selección de un combo
 */

//require("json2.js");

var user_id = null;
var emp_id = null;
var rol_id = null;
var comboUserID = null;
var comboEmpID = null;
var comboRolID = null;

function fc4_listview_select(control)
{
     fc4_listview_deselect_children(control.parentNode);
     control.className = 'fc4_listview_select';
}

function fc4_listview_deselect_children(control)
{
    var i=0;
    var node=null;
    for(i=0;i<control.childNodes.length;i++)
    {
        node=control.childNodes[i];
        if(node.nodeName == "li" || node.nodeName == "LI" && node.className=="fc4_listview_select")
            node.className='fc4_listview_deselect';
    }
}

function sendRequest(URI, URLAsk,usuario,accion)
    {

        var xmlhttp;
        if (window.XMLHttpRequest)
          {// compatibilidad con IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp=new XMLHttpRequest();
          }
        else
          {// compatibilidad con IE6, IE5
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
          }
        xmlhttp.onreadystatechange=function()
          {
          if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
                //alert(xmlhttp.responseText);
                var respuesta = xmlhttp.responseText;
                var respuestaParseada = JSON.parse(respuesta);
                //document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
                //document.getElementById("myDiv").innerHTML=myObject;
                //alert(myObject);
                //var respuesta = jquery.parseJSON(response.responseText);
                //alert(respuesta);
                var error = respuestaParseada.error;
                var correcto = respuestaParseada.correct;
                var usuario = respuestaParseada.user;
                var accion = respuestaParseada.action;
                    if(error != null)
                     {
                        alert(error);
                     }
                 else if(correcto != null)
                     {
                         //alert("antes");alert(usuario);
                         if(accion=="modify")
                         {
                             //alert(accion);
                             document.getElementById(usuario+"Modifica").click();
                         }
                         else if(accion=="delete")
                         {
                             document.getElementById(usuario+"Elimina").click();
                         }
                         else if(accion=="add")
                         {
                             //alert(accion);
                             window.location.href="addUser.form";
                         }
                         //alert("click");
                         
                     }
            }
          }
        var userId = document.getElementById("UserIDJSP").innerHTML;
        var send="AjaxRequestChildTasks=true&sModo=consulta&user_id="+userId+"&URI="+URLAsk+"&usuario="+usuario+"&accion="+accion;
        xmlhttp.open("POST",URI,true);
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        //xmlhttp.send("userID",userId);
        //xmlhttp.send("user_id"+" = "+userId);
        //alert("enviado");
        xmlhttp.send(send);
    }
     
function validateDates()
    {
        var fechaInicio= document.getElementById("calendar2");
        var fechaFinal= document.getElementById("calendar");

        if (fechaInicio.value!==""){
            var anioInicio = parseInt(fechaInicio.value.substring(0,4));
            var mesInicio = fechaInicio.value.substring(5,7);
            var diaInicio = fechaInicio.value.substring(8,10);
            fechaIni = new Date(anioInicio, mesInicio, diaInicio,0,0,0);
        }

        if (fechaFinal.value!==""){
            var anioFin = parseInt(fechaFinal.value.substring(0,4));
            var mesFin = fechaFinal.value.substring(5,7);
            var diaFin = fechaFinal.value.substring(8,10)
            fechaFin = new Date(anioFin, mesFin, diaFin,0,0,0);
        }

        if (typeof(fechaIni)!=='undefined' &&  typeof(fechaFin)!=='undefined')
            if (fechaIni>fechaFin){
            document.getElementById('consult').disabled = true;
            document.getElementById('message').innerHTML = "La Fecha Inicial es superior a la Fecha Final.";
        }
        else {
            document.getElementById('consult').disabled = false;
            document.getElementById('message').innerHTML = "";
        }
    /*
        var fechaInicio= document.getElementById("calendar2");
        var fechaFin= document.getElementById("calendar");
        //alert(fechaInicio);
        //extremos año mes y dia inicial
        var anioInicio = parseInt(fechaInicio.value.substring(0,4));
        var mesInicio = fechaInicio.value.substring(5,7);
        var diaInicio = fechaInicio.value.substring(8,10);
        //extraemos año mes y dia final
        var anioFin = parseInt(fechaFin.value.substring(0,4));
        var mesFin = fechaFin.value.substring(5,7);
        var diaFin = fechaFin.value.substring(8,10);
        var banderadeValidacion = true;

        if(isNaN(anioFin) && isNaN(anioInicio))
            document.getElementById("consult").style.display="";
        if(anioFin < anioInicio)
            {
                alert("No se puede hacer una consulta si la fecha final es menor que la inicial!");
                document.getElementById("consult").style.display="";
                banderadeValidacion = false;
                return(true);
            }
        else
        {
            if (anioFin == anioInicio)
            {
                if(mesFin < mesInicio)
                    {
                        alert("No se puede reslizar consulta si el mes final es menor que el inicial!");
                        document.getElementById("consult").style.display="";
                        alert("Corrija los datos para poder realizar la busqueda");
                        banderadeValidacion =false;
                        return(false);
                    }
                if(mesFin == mesInicio)
                    {
                        if(diaFin >= diaInicio)
                            {
                                document.getElementById("consult").style.display="";
                                banderadeValidacion =true;
                                return(true);
                            }
                        else
                            {
                                alert("El dia final debe ser mayor que el inicial para poder realizar la busqueda!")
                                document.getElementById("consult").style.display="";
                                alert("Corrija los datos para poder realizar la busqueda");
                                banderadeValidacion =false;
                            }
                    }
                else
                    return(false);
            }else
                return(false);
        }
        //alert(banderadeValidacion);
        if(banderadeValidacion ==false)
            document.getElementById('consultar').disabled = true;
            */
    }

function hours() {
var horaInit = document.getElementById("hour_init");
var horaFinal = document.getElementById("hour_end");

    if (horaInit.value !==""){
        var time = horaInit.value.split(":");
        var hours = parseInt(time[0], 10);
        var minutes = parseInt(time[1], 10);
        if (isNaN(minutes) || minutes<0 || minutes>59 || hours <0 || hours >24){
            document.getElementById('consult').disabled = true;
            document.getElementById('message').innerHTML = "Pone un formato de fecha correcto.";

        }
        else {

            horaIni = new Date(0, 0, 0, hours, minutes,0);
            document.getElementById('consult').disabled = false;
            document.getElementById('message').innerHTML = "";
        }
    }

    if (horaFinal.value !==""){
        var time = horaFinal.value.split(":");
        var hours = parseInt(time[0], 10);
        var minutes = parseInt(time[1], 10);
        if (isNaN(minutes) || minutes<0 || minutes>59 || hours <0 || hours >24){
            document.getElementById('consult').disabled = true;
            document.getElementById('message').innerHTML = "Pone un formato de fecha correcto.";

        }
        else {

            horaFin = new Date(0, 0, 0, hours, minutes,0);
            document.getElementById('consult').disabled = false;
            document.getElementById('message').innerHTML = "";
        }
    }

       if (typeof(horaFin)!=='undefined' &&  typeof(horaInit)!=='undefined')
           if (horaFin<=horaIni){
                document.getElementById('consult').disabled = true;
                document.getElementById('message').innerHTML = "La Hora Final es superior a la Hora Initial."; 
            }
            else {
                    document.getElementById('consult').disabled = false;
                    document.getElementById('message').innerHTML = "";          
            }
}
  
function filterDoc(){
var finals = document.getElementById("filterFinal").value;
var init = document.getElementById("filterInit").value;
 var UUID = document.getElementById("UUID").value;

 if ((finals!=="" || init!=="") && (UUID!=="")){
     document.getElementById('consult').disabled = true;
     document.getElementById('message').innerHTML = "Un criterio de busqueda es requerido";
     return ;
}
 sendRequestFilterDoc(init,finals);

}
    
function sendRequestFilterDoc(filterInit, filterFinal){ 

var serieInit="";
var folioInit=0;
var serieFinal="";
var folioFinal=0;

if (filterInit!==("")){
   for (var i =0;i<filterInit.length;i++){

        if (isNaN(filterInit.charAt(i))){
            serieInit+=filterInit.charAt(i);
        }
        else {
            folioInit = folioInit*10 + parseInt(filterInit.charAt(i));
        }
    }
}

if (filterFinal!==("")){
    for (var i =0;i<filterFinal.length;i++){

        if (isNaN(filterFinal.charAt(i))){
            serieFinal+=filterFinal.charAt(i);
        }
        else {
            folioFinal = folioFinal*10 + parseInt(filterFinal.charAt(i));
        }
    }
}

if (serieInit === serieFinal){
    if ((((folioInit>0 && folioFinal===0) || (folioFinal>0 && folioInit===0) || (folioFinal>0 && folioInit>0))) && (folioInit<folioFinal)){
        document.getElementById('message').innerHTML = "";
     document.getElementById('consult').disabled = false;
}

else {
        if ((folioInit===0 && folioFinal===0) || (folioInit>0 && folioFinal===0 && (folioFinal>0 || folioFinal===0 ))){
            document.getElementById('message').innerHTML = "";
            document.getElementById('consult').disabled = false;
        }
        else {
            document.getElementById('message').innerHTML = "Filtro incorecto";
            document.getElementById('consult').disabled = true;
        }
        }
}
else if((serieInit!=="" && serieFinal==="") || (serieInit==="" && serieFinal!=="")){
    document.getElementById('message').innerHTML = "";
    document.getElementById('consult').disabled = false;
}
else {
    document.getElementById('message').innerHTML = "Filtro incorecto";
    document.getElementById('consult').disabled = true;
}
}

function isLogExist(){
   var date= document.getElementById("isLogExist").value;

   if (date==="")
       document.getElementById('consult').disabled = true;

    sendRequestLogExist(date);
}

function sendRequestLogExist(date)
{

    var xmlhttp;
    if (window.XMLHttpRequest)
      {// compatibilidad con IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
      }
    else
      {// compatibilidad con IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
      //alert(xmlhttp.readyState);alert(xmlhttp.status);
    xmlhttp.onreadystatechange=function()
      {
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {

            var request = xmlhttp.responseText;
            var requestParse = JSON.parse(request);

            var error = requestParse.error;
            var correct = requestParse.correct;

             var query;

            if(error === "true")
             {
                query = document.getElementById('message');
                query.innerHTML = "Aucun Log disponible";
                document.getElementById('consult').disabled = true;
             }

             if(correct === "true")
             {
                query = document.getElementById('message');
                query.innerHTML = "";
                document.getElementById('consult').disabled = false;

             }
        }
      }

    var send="AjaxRequestChildTasks=true"+"&date="+date;
    xmlhttp.open("POST","log.task",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(send);
}