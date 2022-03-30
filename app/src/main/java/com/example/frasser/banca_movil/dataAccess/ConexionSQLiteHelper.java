package com.example.frasser.banca_movil.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan Pablo on 26/01/2018.
 */

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String tablaTipoIdentificacion = "CREATE TABLE TIPO_IDENTIFICACION\n" +
                "(\n" +
                "  TIID_ID NUMERIC NOT NULL,\n" +
                "  CODIGO TEXT NOT NULL,\n" +
                "  DESCRIPCION TEXT NOT NULL,\n" +
                "  CONSTRAINT TIID_PK PRIMARY KEY (TIID_ID)\n" +
                ")";

        String tablaTipoMovimiento = "CREATE TABLE TIPO_MOVIMIENTO\n" +
                "(\n" +
                "  TIMO_ID NUMERIC NOT NULL,\n" +
                "  DESCRIPCION_TIMO TEXT NOT NULL,\n" +
                "  CONSTRAINT TIMO_PK PRIMARY KEY (TIMO_ID)\n" +
                ")";

        String tablaMovimiento = "CREATE TABLE MOVIMIENTO\n" +
                "(\n" +
                "  MOV_ID INTEGER,\n" +
                "  VALOR_MOV NUMERIC NOT NULL,\n" +
                "  TIMO_ID NUMERIC NOT NULL,\n" +
                "  USUA_ID NUMERIC NOT NULL,\n" +
                "  SERV_ID INTEGER NULL,\n" +
                "  CUENTA_ID INTEGER NULL,\n" +
                "  PROD_ID NUMERIC NOT NULL,\n" +
                "  CONSTRAINT MOV_PK PRIMARY KEY (MOV_ID)\n" +
                "  CONSTRAINT FK_TIMO_MOVIMIENTO FOREIGN KEY (TIMO_ID) REFERENCES TIPO_MOVIMIENTO (TIMO_ID)\n" +
                "  CONSTRAINT FK_USUA_MOVIMIENTO FOREIGN KEY (USUA_ID) REFERENCES USUARIO (USUA_ID)\n" +

                "  CONSTRAINT FK_SERV_MOVIMIENTO FOREIGN KEY (SERV_ID) REFERENCES SERVICIOS (SERV_ID)\n" +
                "  CONSTRAINT FK_CUENTA_MOVIMIENTO FOREIGN KEY (CUENTA_ID) REFERENCES CUENTAS (CUENTA_ID)\n" +
                "  CONSTRAINT FK_PROD_MOVIMIENTO FOREIGN KEY (PROD_ID) REFERENCES PRODUCTO (PROD_ID)\n" +
                ")";


        String tablaServicios= "CREATE TABLE SERVICIOS\n" +
                "(\n" +
                "  SERV_ID INTEGER,\n" +
                "  NOM_SERV TEXT NOT NULL,\n" +
                "  REF_SERV NUMERIC NOT NULL,\n" +
                "  USUA_ID NUMERIC NULL,\n" +
                "  CONSTRAINT SERV_PK PRIMARY KEY (SERV_ID)\n" +
                "  CONSTRAINT FK_USUA_SERVICIOS FOREIGN KEY (USUA_ID) REFERENCES USUARIO (USUA_ID)\n" +
                ")";


        String tablaEntidad = "CREATE TABLE ENTIDAD\n" +
                "(\n" +
                "  ENTI_ID NUMERIC NOT NULL,\n" +
                "  NOM_ENTI TEXT NOT NULL,\n" +
                "  CONSTRAINT ENTI_PK PRIMARY KEY (ENTI_ID)\n" +
                ")";


        String tablaCuentas= "CREATE TABLE CUENTAS\n" +
                "(\n" +
                "  CUENTA_ID INTEGER,\n" +
                "  NOM_CUEN TEXT NOT NULL,\n" +
                "  NUMERO_CUE NUMERIC NOT NULL,\n" +
                "  ENTI_ID NUMERIC NOT NULL,\n" +
                "  USUA_ID NUMERIC NOT NULL,\n" +
                "  CONSTRAINT CUENTA_PK PRIMARY KEY (CUENTA_ID)\n" +
                "  CONSTRAINT FK_ENTI_CUENTAS FOREIGN KEY (ENTI_ID) REFERENCES ENTIDAD (ENTI_ID)\n" +
                "  CONSTRAINT FK_USUA_CUENTAS FOREIGN KEY (USUA_ID) REFERENCES USUARIO (USUA_ID)\n" +
                ")";



        String tablaTipoProducto = "CREATE TABLE TIPO_PRODUCTO\n" +
                "(\n" +
                "  TIPR_ID NUMERIC NOT NULL,\n" +
                "  DESCRIPCION_TIPR TEXT NOT NULL,\n" +
                "  CONSTRAINT TIPR_PK PRIMARY KEY (TIPR_ID)\n" +
                ")";


        String tablaProducto = "CREATE TABLE PRODUCTO\n" +
                "(\n" +
                "  PROD_ID NUMERIC NOT NULL,\n" +
                "  CODIGO TEXT NOT NULL,\n" +
                "  VALOR NUMERIC NOT NULL,\n" +
                "  TIPR_ID NUMERIC,\n" +
                "  USUA_ID NUMERIC,\n" +
                "  CONSTRAINT PROD_PK PRIMARY KEY (PROD_ID)\n" +
                "  CONSTRAINT FK_TIPR_PROD FOREIGN KEY (TIPR_ID) REFERENCES TIPO_PRODUCTO (TIPR_ID)\n" +
                "  CONSTRAINT FK_USUA_PRODUCTO FOREIGN KEY (USUA_ID) REFERENCES USUARIO (USUA_ID)\n" +
                ")";



        String tablaUsuario = "CREATE TABLE USUARIO\n" +
                "(\n" +
                "  USUA_ID NUMERIC NOT NULL,\n" +
                "  IDENTIFICACION NUMERIC NOT NULL,\n" +
                "  NOMBRES TEXT,\n" +
                "  APELLIDOS TEXT,\n" +
                "  TELEFONO TEXT NOT NULL,\n" +
                "  TIID_ID NUMERIC,\n" +
                "  CONTRASENA TEXT NOT NULL,\n" +
                "  CONSTRAINT USUA_PK PRIMARY KEY (USUA_ID),\n" +
                "  CONSTRAINT FK_TIID_USUA FOREIGN KEY (TIID_ID) REFERENCES TIPO_IDENTIFICACION (TIID_ID)\n" +

                ")";


        String index = "CREATE INDEX IX_TIID_USUA ON USUARIO (TIID_ID)";
        String index2 = "CREATE INDEX IX_USUA_PRODUCTO ON PRODUCTO (USUA_ID)";
        String index3 = "CREATE INDEX IX_USUA_SERVICIOS ON SERVICIOS (USUA_ID)";
        String index4 = "CREATE INDEX IX_USUA_CUENTAS ON CUENTAS (USUA_ID)";
        String index5 = "CREATE INDEX IX_TIPR_PROD ON PRODUCTO (TIPR_ID)";
        String index6 = "CREATE INDEX IX_ENTI_CUENTAS ON CUENTAS (ENTI_ID)";
        String index7 = "CREATE INDEX IX_TIMO_MOVIMIENTO ON MOVIMIENTO (TIMO_ID)";
        String index8 = "CREATE INDEX IX_USUA_MOVIMIENTO ON MOVIMIENTO (USUA_ID)";

        String index9 = "CREATE INDEX IX_SERV_MOVIMIENTO ON MOVIMIENTO (SERV_ID)";
        String index10 = "CREATE INDEX IX_CUENTA_MOVIMIENTO ON MOVIMIENTO (CUENTA_ID)";
        String index11 = "CREATE INDEX IX_PROD_MOVIMIENTO ON MOVIMIENTO (PROD_ID)";




        String insertRE ="INSERT INTO TIPO_MOVIMIENTO" +
                " (TIMO_ID, " +
                "DESCRIPCION_TIMO)" +
                " VALUES (" +
                "1, " +
                "'RETIRO')";

        String insertAB ="INSERT INTO TIPO_MOVIMIENTO" +
                " (TIMO_ID, " +
                "DESCRIPCION_TIMO)" +
                " VALUES (" +
                "2, " +
                "'ABONO')";

        String insertCON ="INSERT INTO TIPO_MOVIMIENTO" +
                " (TIMO_ID, " +
                "DESCRIPCION_TIMO)" +
                " VALUES (" +
                "3, " +
                "'CONSIGNACION')";

        String insertAVA ="INSERT INTO TIPO_MOVIMIENTO" +
                " (TIMO_ID, " +
                "DESCRIPCION_TIMO)" +
                " VALUES (" +
                "4, " +
                "'TRANSFERENCIA')";







        /////////////////////// INSERTS ///////////////////////////////////////////////////////////////

        String insertCC ="INSERT INTO TIPO_IDENTIFICACION" +
                " (TIID_ID, " +
                "CODIGO, " +
                "DESCRIPCION)" +
                " VALUES (" +
                "1, " +
                "'CC', " +
                "'CEDULA CIUDADANIA')";

        String insertCE ="INSERT INTO TIPO_IDENTIFICACION" +
                " (TIID_ID, " +
                "CODIGO, " +
                "DESCRIPCION)" +
                " VALUES (" +
                "2, " +
                "'CE', " +
                "'CEDULA EXTRANJERIA')";
        //////////////////////////////////////////////////


        String insertCAH ="INSERT INTO TIPO_PRODUCTO" +
                " (TIPR_ID, " +
                "DESCRIPCION_TIPR)" +
                " VALUES (" +
                "1, " +
                "'CUENTA AHORROS')";


        String insertCCOR ="INSERT INTO TIPO_PRODUCTO" +
                " (TIPR_ID, " +
                "DESCRIPCION_TIPR)" +
                " VALUES (" +
                "2, " +
                "'CUENTA CORRIENTE')";

        String insertTC ="INSERT INTO TIPO_PRODUCTO" +
                " (TIPR_ID, " +
                "DESCRIPCION_TIPR)" +
                " VALUES (" +
                "3, " +
                "'TARJETA CREDITO VISA')";

        String insertTD ="INSERT INTO TIPO_PRODUCTO" +
                " (TIPR_ID, " +
                "DESCRIPCION_TIPR)" +
                " VALUES (" +
                "4, " +
                "'TARJETA DEBITO')";





        //////////////////////////////////////////


        String insertUsu = "INSERT INTO USUARIO" +
                " (USUA_ID, " +
                "IDENTIFICACION, " +
                "NOMBRES, " +
                "APELLIDOS, " +
                "TELEFONO, " +
                "TIID_ID, " +
                "CONTRASENA) " +
                " VALUES (" +
                "1, " +
                "1111111111, " +
                "'Jose Jose', " +
                "'SANCHEZ ', " +
                "'1111111111', " +
                "1, " +
                "'1234') ";


        ///////////////////////////////////////////


        String insertP01 ="INSERT INTO PRODUCTO" +
                " (PROD_ID, " +
                "CODIGO, " +
                "VALOR, " +
                "TIPR_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "1, " +
                "'1200000', " +
                "3000000," +
                "1, " +
                "1) ";

        String insertP02 ="INSERT INTO PRODUCTO" +
                " (PROD_ID, " +
                "CODIGO, " +
                "VALOR, " +
                "TIPR_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "2, " +
                "'1234567', " +
                "1000000," +
                "2, " +
                "1) ";

        String insertP03 ="INSERT INTO PRODUCTO" +
                " (PROD_ID, " +
                "CODIGO, " +
                "VALOR, " +
                "TIPR_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "3, " +
                "'63637730', " +
                "4000000," +
                "3, " +
                "1) ";



        //////////////////////////////////////////////////////////


        String insertS01 ="INSERT INTO SERVICIOS" +
                " (SERV_ID, " +
                "NOM_SERV, " +
                "REF_SERV, " +
                "USUA_ID )" +
                " VALUES (" +
                "null, " +
                "'NETFLIX', " +
                "166666, " +
                "1) ";

        String insertS02 ="INSERT INTO SERVICIOS" +
                " (SERV_ID, " +
                "NOM_SERV, " +
                "REF_SERV, " +
                "USUA_ID )" +
                " VALUES (" +
                "null, " +
                "'UNE TELECOMUNICACIONES', " +
                "5555556, " +
                "1) ";

        ////////////////////////////////////////////////////////////


        String insertENT01 ="INSERT INTO ENTIDAD" +
                " (ENTI_ID, " +
                "NOM_ENTI)" +
                " VALUES (" +
                "1, " +
                "'BANCOLOMBIA')";

        String insertENT02 ="INSERT INTO ENTIDAD" +
                " (ENTI_ID, " +
                "NOM_ENTI)" +
                " VALUES (" +
                "2, " +
                "'BBVA')";

        String insertENT03 ="INSERT INTO ENTIDAD" +
                " (ENTI_ID, " +
                "NOM_ENTI)" +
                " VALUES (" +
                "3, " +
                "'AV VILLAS')";

        String insertENT04 ="INSERT INTO ENTIDAD" +
                " (ENTI_ID, " +
                "NOM_ENTI)" +
                " VALUES (" +
                "4, " +
                "'BANCO DE BOGOTA')";

        String insertENT05 ="INSERT INTO ENTIDAD" +
                " (ENTI_ID, " +
                "NOM_ENTI)" +
                " VALUES (" +
                "5, " +
                "'DAVIVIENDA')";

        ////////////////////////////////////////////////

        String insertCU01 ="INSERT INTO CUENTAS" +
                " (CUENTA_ID, " +
                "NOM_CUEN, " +
                "NUMERO_CUE, " +
                "ENTI_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "null, " +
                "'CARLOS FERNANDEZ', " +
                "727282," +
                "2," +
                "1) ";

        String insertCU02 ="INSERT INTO CUENTAS" +
               " (CUENTA_ID, " +
                "NOM_CUEN, " +
                "NUMERO_CUE, " +
                "ENTI_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "null, " +
                "'LINA CARDONA', " +
                "9033," +
                "4," +
                "1) ";



        String insertCU03 ="INSERT INTO CUENTAS" +
                " (CUENTA_ID, " +
                "NOM_CUEN, " +
                "NUMERO_CUE, " +
                "ENTI_ID, " +
                "USUA_ID )" +
                " VALUES (" +
                "null, " +
                "'MOTOPARTES SAS', " +
                "2200022," +
                "3," +
                "1) ";


        ////////////////////////////////////////

        db.execSQL(tablaTipoIdentificacion);
        db.execSQL(tablaServicios);
        db.execSQL(tablaEntidad);
        db.execSQL(tablaCuentas);
        db.execSQL(tablaTipoProducto);
        db.execSQL(tablaProducto);
        db.execSQL(tablaUsuario);
        db.execSQL(tablaTipoMovimiento);
        db.execSQL(tablaMovimiento);

       /////////////////////////////////////////////

        db.execSQL(index);
        db.execSQL(index2);
        db.execSQL(index3);
        db.execSQL(index4);
        db.execSQL(index5);
        db.execSQL(index6);
        db.execSQL(index7);
        db.execSQL(index8);
        db.execSQL(index9);
        db.execSQL(index10);
        db.execSQL(index11);
///////////////////////////////////////////////////////

        db.execSQL(insertRE);
        db.execSQL(insertAB);

        db.execSQL(insertCON);

        db.execSQL(insertAVA);



        db.execSQL(insertCC);
        db.execSQL(insertCE);

        db.execSQL(insertCAH);
        db.execSQL(insertCCOR);
        db.execSQL(insertTC);
        db.execSQL(insertTD);

        db.execSQL(insertUsu);


        db.execSQL(insertP01);
        db.execSQL(insertP02);
        db.execSQL(insertP03);


        db.execSQL(insertS01);
        db.execSQL(insertS02);

        db.execSQL(insertENT01);
        db.execSQL(insertENT02);
        db.execSQL(insertENT03);
        db.execSQL(insertENT04);
        db.execSQL(insertENT05);

        db.execSQL(insertCU01);
        db.execSQL(insertCU02);
        db.execSQL(insertCU03);




        String x = "";




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS TIPO_IDENTIFICACION");
        db.execSQL("DROP TABLE IF EXISTS SERVICIOS");
        db.execSQL("DROP TABLE IF EXISTS ENTIDAD");
        db.execSQL("DROP TABLE IF EXISTS CUENTAS");
        db.execSQL("DROP TABLE IF EXISTS TIPO_PRODUCTO");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTO");
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS TIPO_MOVIMIENTO");
        db.execSQL("DROP TABLE IF EXISTS MOVIMIENTO");
        onCreate(db);


    }
}
