package com.example.frasser.banca_movil.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Juan Pablo on 30/01/2018.
 */




public class CuentaDAOSql implements ICuentaDAOSql{
    @Override
    public void eliminarCunta(Context context, String nomCuenta, Integer numeroCuenta) throws Exception {
        conn = new ConexionSQLiteHelper(context,"db_banco",null,1);
        try {
            SQLiteDatabase db = conn.getWritableDatabase();

            String delete = "DELETE FROM  CUENTAS " +
                    " WHERE NOM_CUEN  = '"+nomCuenta.toString()+"' " +
                    " AND NUMERO_CUE = "+numeroCuenta.toString();

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
    public void guardarCuenta(Context context, Integer idCuenta, String nomCuenta, Integer numeroCuenta, Integer entIdFK, Integer FKUsuCU) throws Exception {

        conn = new ConexionSQLiteHelper(context,"db_banco",null,1);

        try {
            SQLiteDatabase db = conn.getWritableDatabase();

            String insert ="INSERT INTO CUENTAS " +

                    " (CUENTA_ID, " +
                    "NOM_CUEN, " +
                    "NUMERO_CUE, " +
                    "ENTI_ID, " +
                    "USUA_ID )" +
                    " VALUES (" +
                    "null, " +
                    "'"+nomCuenta+"', " +
                    ""+numeroCuenta.toString()+"," +
                    ""+entIdFK.toString()+"," +
                    "1) ";
            // ejecuto el query
            db.execSQL(insert);
            db.close();
            //





        }catch (Exception e){

            throw e;
        }finally {

            conn.close();
        }


    }
}
