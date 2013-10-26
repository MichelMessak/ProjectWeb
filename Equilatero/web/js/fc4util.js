
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

function sendRequest(URI, URLAsk,user,action)
    {

        var xmlhttp;
        if (window.XMLHttpRequest)
          {
            xmlhttp=new XMLHttpRequest();
          }
        else
          {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
          }
        xmlhttp.onreadystatechange=function()
          {
          if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                var request = xmlhttp.responseText;
                var parseRequest = JSON.parse(request);
                var error = parseRequest.error;
                var correct = parseRequest.correct;
                var user = parseRequest.user;
                var action = parseRequest.action;



                    if(error != null)
                     {
                        alert(error);
                     }
                 else if(correct != null)
                     {
                         if(action=="modify")
                         {
                             document.getElementById(user+"Modify").click();
                         }
                         else if(action=="delete")
                         {
                             document.getElementById(user+"Delete").click();
                         }
                         else if(action=="add")
                         {
                             window.location.href="addUser.form";
                         }
                         
                     }
            }
          }
        var userId = document.getElementById("UserIDJSP").innerHTML;
        var send="AjaxRequestChildTasks=true&sModo=consulta&user_id="+userId+"&URI="+URLAsk+"&user="+user+"&action="+action;
        xmlhttp.open("POST",URI,true);
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.send(send);
    }
     
function validateDates()
    {
        var dateInit= document.getElementById("calendar2");
        var dateFinal= document.getElementById("calendar");
        var initialDate, finalDate;

        if (dateInit.value!==""){
            var yearInit = parseInt(dateInit.value.substring(0,4));
            var monthInit = dateInit.value.substring(5,7);
            var dayInit = dateInit.value.substring(8,10);
            initialDate = new Date(yearInit, monthInit, dayInit,0,0,0);
        }

        if (dateFinal.value!==""){
            var yearFinal = parseInt(dateFinal.value.substring(0,4));
            var monthFinal = dateFinal.value.substring(5,7);
            var dayFinal = dateFinal.value.substring(8,10)
            finalDate = new Date(yearFinal, monthFinal, dayFinal,0,0,0);
        }

        if (typeof(initialDate)!=='undefined' &&  typeof(finalDate)!=='undefined')
            if (initialDate>finalDate){
            document.getElementById('consult').disabled = true;
            document.getElementById('message').innerHTML = "La date de début est supérieur à la date final.";
        }
        else {
            document.getElementById('consult').disabled = false;
            document.getElementById('message').innerHTML = "";
        }
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
            document.getElementById('message').innerHTML = "Format incorrect.";

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
            document.getElementById('message').innerHTML = "Format incorrect.";

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
                document.getElementById('message').innerHTML = "L'heure initial est supérieur à l'heure final.";
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
     document.getElementById('message').innerHTML = "Un critère de recherches est obligatoire";
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
            document.getElementById('message').innerHTML = "Filtre incorect";
            document.getElementById('consult').disabled = true;
        }
        }
}
else if((serieInit!=="" && serieFinal==="") || (serieInit==="" && serieFinal!=="")){
    document.getElementById('message').innerHTML = "";
    document.getElementById('consult').disabled = false;
}
else {
    document.getElementById('message').innerHTML = "Filtre incorect";
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
      {
        xmlhttp=new XMLHttpRequest();
      }
    else
      {
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
      
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