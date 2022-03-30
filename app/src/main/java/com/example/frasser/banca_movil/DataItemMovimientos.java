package com.example.frasser.banca_movil;

/**
 * Created by Juan Pablo on 5/02/2018.
 */

public class DataItemMovimientos {
    private String vlorMov;
    private String tipoMovIdFK;
  //  private String FKServId;
   // private String FKCuenId;
    //private Integer FKProdId;

    public String getVlorMov() {
        return vlorMov;
    }

    public void setVlorMov(String vlorMov) {
        this.vlorMov = vlorMov;
    }

    public String getTipoMovIdFK() {
        return tipoMovIdFK;
    }

    public void setTipoMovIdFK(String tipoMovIdFK) {
        this.tipoMovIdFK = tipoMovIdFK;
    }

   // public String getFKServId() {
     //   return FKServId;
    //}

    //public void setFKServId(String FKServId) {
      //  this.FKServId = FKServId;
    //}

    //public String getFKCuenId() {
      //  return FKCuenId;
    //}

    //public void setFKCuenId(String FKCuenId) {
      //  this.FKCuenId = FKCuenId;
    //}

   // public Integer getFKProdId() {
    //    return FKProdId;
    //}

    //public void setFKProdId(Integer FKProdId) {
      //  this.FKProdId = FKProdId;
    //}

    public DataItemMovimientos(String vlorMov, String tipoMovIdFK) {
        this.vlorMov = vlorMov;
        this.tipoMovIdFK = tipoMovIdFK;
       // this.FKServId = FKServId;
      // this.FKCuenId = FKCuenId;
        //this.FKProdId = FKProdId;

    }
}
