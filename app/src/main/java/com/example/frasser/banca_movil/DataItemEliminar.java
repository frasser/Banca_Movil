package com.example.frasser.banca_movil;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class DataItemEliminar {

    private int identificador;
    private String nombreservicio;
    private String codigoServicio;



    public  DataItemEliminar(){

    }
    public DataItemEliminar(int identificador, String nombreservicio, String codigoServicio) {
        this.identificador = identificador;
        this.nombreservicio = nombreservicio;
        this.codigoServicio = codigoServicio;
    }


    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombreservicio() {
        return nombreservicio;
    }

    public void setNombreservicio(String nombreservicio) {
        this.nombreservicio = nombreservicio;
    }

    public String getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }



}
