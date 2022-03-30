package com.example.frasser.banca_movil.modelo;

/**
 * Created by Juan Pablo on 27/01/2018.
 */

public class Servicios {
    private Integer idServicio;
    private String nomServicio;
    private Integer refServicio;
    private Integer FKUsuServ;

    public Integer getFKUsuServ() {
        return FKUsuServ;
    }

    public void setFKUsuServ(Integer FKUsuServ) {
        this.FKUsuServ = FKUsuServ;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNomServicio() {
        return nomServicio;
    }

    public void setNomServicio(String nomServicio) {
        this.nomServicio = nomServicio;
    }

    public Integer getRefServicio() {
        return refServicio;
    }

    public void setRefServicio(Integer refServicio) {
        this.refServicio = refServicio;
    }
}
