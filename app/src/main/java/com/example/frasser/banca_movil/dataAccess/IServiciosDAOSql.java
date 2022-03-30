package com.example.frasser.banca_movil.dataAccess;

import android.content.Context;

/**
 * Created by Juan Pablo on 30/01/2018.
 */

public interface IServiciosDAOSql {
    void guardarServicio (Context context,Integer idServicio,String nomServicio,Integer refServicio,Integer FKUsuServ)throws Exception;

    void eliminarServicio (Context context, String nomServicio, Integer refServicio)throws Exception;

}
