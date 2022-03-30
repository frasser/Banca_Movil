package com.example.frasser.banca_movil.modelo;

/**
 * Created by Juan Pablo on 27/01/2018.
 */

public class Producto {
    private Integer idProd;
    private String codProd;
    private Double vlorProd;
    private Integer tipoProdIdFK;
    private Integer FKProUsu;

    public Integer getFKProUsu() {
        return FKProUsu;
    }

    public void setFKProUsu(Integer FKProUsu) {
        this.FKProUsu = FKProUsu;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public Double getVlorProd() {
        return vlorProd;
    }

    public void setVlorProd(Double vlorProd) {
        this.vlorProd = vlorProd;
    }

    public Integer getTipoProdIdFK() {
        return tipoProdIdFK;
    }

    public void setTipoProdIdFK(Integer tipoProdIdFK) {
        this.tipoProdIdFK = tipoProdIdFK;
    }
}
