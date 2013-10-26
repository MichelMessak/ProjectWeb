/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



var slide = jQuery('<div id="ExportDocuments"><ul><li><a href="reportPDF.form" target="_blank"><div id="PDF"></div></a></li><li><a href="reportExcel.form" target="_blank"><div id="Excel"></div></a></li></ul></div>');
$('#container').before($(slide));