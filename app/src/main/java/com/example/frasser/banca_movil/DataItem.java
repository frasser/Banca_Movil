package com.example.frasser.banca_movil;

/**
 * Created by Juan Pablo on 20/12/2017.
 */

public class DataItem {
    private  int id;
    private String nombreproducto;
    private String numeroP;



    public  DataItem(){
    }

    public DataItem(int id, String nombreproducto, String numeroP) {
        this.id = id;
        this.nombreproducto = nombreproducto;
        this.numeroP = numeroP;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getNumeroP() {
        return numeroP;
    }

    public void setNumeroP(String numeroP) {
        this.numeroP = numeroP;
    }
}

