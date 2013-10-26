package com.itc.fc4.importa;

import java.util.Vector;

/**
 *
 * @author JBM
 */

public class Datos
{
    private String id = "";
    private String empresa="";
    private String tipo="";
    private String subTipo="";
    private String folio="";
    private String serie="";
    private String fechaGen="";
    private String horaGen="";
    private String fechaMod="";
    private String horaMod="";
    private String moneda="";
    private String total="";
    private String id2="";



    private String nombreReceptora="";
    private String empresaReceptora="";
    
    private String archivoEntrada ="";
    private Vector listaArchivosEntrada = null;
    private String rutaArchivoEntrada = "";
    private String status="";

    public Datos()
    {
    }

        public String getId() {
        return id;
    }



    public String getId2() {
        return id2;
    }


    public String getEmpresa() {
        return empresa;
    }

    public String getEmpresaReceptora() {
        return empresaReceptora;
    }

    public String getFechaGen() {
        return fechaGen;
    }

    public String getFechaMod() {
        return fechaMod;
    }

    public String getFolio() {
        return folio;
    }

    public String getHoraGen() {
        return horaGen;
    }

    public String getHoraMod() {
        return horaMod;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getNombreReceptora() {
        return nombreReceptora;
    }

    public String getSubTipo() {
        return subTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTotal() {
        return total;
    }

        public void setId(String id) {
        this.id = id;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setEmpresaReceptora(String empresaReceptora) {
        this.empresaReceptora = empresaReceptora;
    }

    public void setFechaGen(String fechaGen) {
        this.fechaGen = fechaGen;
    }

    public void setFechaMod(String fechaMod) {
        this.fechaMod = fechaMod;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setHoraGen(String horaGen) {
        this.horaGen = horaGen;
    }

    public void setHoraMod(String horaMod) {
        this.horaMod = horaMod;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setNombreReceptora(String nombreReceptora) {
        this.nombreReceptora = nombreReceptora;
    }

    public void setSubTipo(String subTipo) {
        this.subTipo = subTipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setArchivoEntrada(String archivoEntrada) {
        this.archivoEntrada = archivoEntrada;
    }

    public void setListaArchivosEntrada(Vector listaArchivosEntrada) {
        this.listaArchivosEntrada = listaArchivosEntrada;
    }

    public String getArchivoEntrada() {
        return archivoEntrada;
    }

    public Vector getListaArchivosEntrada() {
        return listaArchivosEntrada;
    }

    public String getRutaArchivoEntrada() {
        return rutaArchivoEntrada;
    }

    public void setRutaArchivoEntrada(String rutaArchivoEntrada) {
        this.rutaArchivoEntrada = rutaArchivoEntrada;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }
    public String getAnio()
    {
        if(this.fechaGen != null)
            return this.fechaGen.substring(0,4);
        else
            return null;
    }
}
