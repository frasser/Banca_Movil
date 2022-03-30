package com.example.frasser.banca_movil.dataAccess;

import android.content.Context;

/**
 * Created by Juan Pablo on 30/01/2018.
 */

public interface ICuentaDAOSql {

    void guardarCuenta (Context context,Integer idCuenta, String nomCuenta, Integer numeroCuenta, Integer entIdFK, Integer FKUsuCU) throws Exception;
    void eliminarCunta (Context context,String nomCuenta, Integer numeroCuenta)throws Exception;

}
