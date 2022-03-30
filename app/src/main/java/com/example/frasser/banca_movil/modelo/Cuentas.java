package com.example.frasser.banca_movil.modelo;

/**
 * Created by Juan Pablo on 27/01/2018.
 */

public class Cuentas {
    private Integer idCuenta;
    private String nomCuenta;
    private Integer numeroCuenta;
    private Integer entIdFK;
    private Integer FKUsuCU;

    public Integer getFKUsuCU() {
        return FKUsuCU;
    }

    public void setFKUsuCU(Integer FKUsuCU) {
        this.FKUsuCU = FKUsuCU;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNomCuenta() {
        return nomCuenta;
    }

    public void setNomCuenta(String nomCuenta) {
        this.nomCuenta = nomCuenta;
    }

    public Integer getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(Integer numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Integer getEntIdFK() {
        return entIdFK;
    }

    public void setEntIdFK(Integer entIdFK) {
        this.entIdFK = entIdFK;
    }
}
