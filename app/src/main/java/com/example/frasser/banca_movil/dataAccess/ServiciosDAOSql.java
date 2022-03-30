package com.example.frasser.banca_movil.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Juan Pablo on 30/01/2018.
 */

public class ServiciosDAOSql implements IServiciosDAOSql{
    @Override
    public void eliminarServicio(Context context, String nomServicio, Integer refServicio) throws Exception {
        conn = new ConexionSQLiteHelper(context,"db_banco",null,1);

        try {
            SQLiteDatabase db = conn.getWritableDatabase();

            String delete = "DELETE FROM  SERVICIOS" +
                    " WHERE NOM_SERV  = '"+nomServicio.toString()+"' " +
                    " AND REF_SERV = "+refServicio.toString();


            //ejecuto query
            db.execSQL(delete);
            db.close();


        }catch (Exception e){

            throw e;
        }finally {
            conn.close();
        }

    }

    ConexionSQLiteHelper conn;

    @Override
    public void guardarServicio(Context context, Integer idServicio, String nomServicio, Integer refServicio, Integer FKUsuServ) throws Exception {


        conn = new ConexionSQLiteHelper(context,"db_banco",null,1);

        try {

            SQLiteDatabase db = conn.getWritableDatabase();
            String insert ="INSERT INTO SERVICIOS" +
                    " (SERV_ID, " +
                    "NOM_SERV, " +
                    "REF_SERV, " +
                    "USUA_ID )" +
                    " VALUES (" +
                    "null, " +
                    "'"+nomServicio+"', " +
                    ""+refServicio+", " +
                    "1) ";
            db.execSQL(insert);
            db.close();


        }catch (Exception e){

            throw e;
        }finally {
            conn.close();
        }



    }
}
