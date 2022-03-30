package com.example.frasser.banca_movil.modelo;

/**
 * Created by Juan Pablo on 27/01/2018.
 */

public class Usuario {
    private Integer usuId;
    private Integer numident;
    private String nombre;
    private String apellido;
    private String tel;
    private Integer FKTipoIdent;

    private String password;

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public Integer getNumident() {
        return numident;
    }

    public void setNumident(Integer numident) {
        this.numident = numident;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getFKTipoIdent() {
        return FKTipoIdent;
    }

    public void setFKTipoIdent(Integer FKTipoIdent) {
        this.FKTipoIdent = FKTipoIdent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
