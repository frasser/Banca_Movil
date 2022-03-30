package com.example.frasser.banca_movil.modelo;

/**
 * Created by Juan Pablo on 3/02/2018.
 */

public class Movimiento {

    private Integer idMov;
    private Double vlorMov;
    private Integer tipoMovIdFK;
    private Integer FKUsuId;
    private Integer FKServId;
    private Integer FKCuenId;
    private Integer FKProdId;

    public Integer getFKServId() {
        return FKServId;
    }

    public void setFKServId(Integer FKServId) {
        this.FKServId = FKServId;
    }

    public Integer getFKCuenId() {
        return FKCuenId;
    }

    public void setFKCuenId(Integer FKCuenId) {
        this.FKCuenId = FKCuenId;
    }

    public Integer getFKProdId() {
        return FKProdId;
    }

    public void setFKProdId(Integer FKProdId) {
        this.FKProdId = FKProdId;
    }

    public Integer getIdMov() {
        return idMov;
    }

    public void setIdMov(Integer idMov) {
        this.idMov = idMov;
    }

    public Double getVlorMov() {
        return vlorMov;
    }

    public void setVlorMov(Double vlorMov) {
        this.vlorMov = vlorMov;
    }

    public Integer getTipoMovIdFK() {
        return tipoMovIdFK;
    }

    public void setTipoMovIdFK(Integer tipoMovIdFK) {
        this.tipoMovIdFK = tipoMovIdFK;
    }

    public Integer getFKUsuId() {
        return FKUsuId;
    }

    public void setFKUsuId(Integer FKUsuId) {
        this.FKUsuId = FKUsuId;
    }
}
