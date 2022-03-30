package com.example.frasser.banca_movil;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class DataItemEliminarCuenta {

    private int idimage;
    private String nombrecuenta;
    private String numcuenta;
    private String nomEnt;




    public DataItemEliminarCuenta(){


    }



    public DataItemEliminarCuenta(int idimage, String nombrecuenta, String numcuenta, String nomEnt) {
        this.idimage = idimage;
        this.nombrecuenta = nombrecuenta;
        this.numcuenta = numcuenta;
        this.nomEnt = nomEnt;

    }

    public int getIdimage() {
        return idimage;
    }

    public void setIdimage(int idimage) {
        this.idimage = idimage;
    }

    public String getNombrecuenta() {
        return nombrecuenta;
    }

    public void setNombrecuenta(String nombrecuenta) {
        this.nombrecuenta = nombrecuenta;
    }

    public String getNumcuenta() {
        return numcuenta;
    }

    public void setNumcuenta(String numcuenta) {
        this.numcuenta = numcuenta;
    }

    public String getNomEnt() {
        return nomEnt;
    }

    public void setNomEnt(String nomEnt) {
        this.nomEnt = nomEnt;
    }


}
