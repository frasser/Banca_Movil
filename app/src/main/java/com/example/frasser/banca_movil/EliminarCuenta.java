package com.example.frasser.banca_movil;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.adapters.ItemRowAdapterEliminarCuenta;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.dataAccess.CuentaDAOSql;
import com.example.frasser.banca_movil.dataAccess.ICuentaDAOSql;
import com.example.frasser.banca_movil.modelo.Cuentas;
import com.example.frasser.banca_movil.modelo.Entidad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class EliminarCuenta extends AppCompatActivity {

    List<DataItemEliminarCuenta>  dataItemEliminarCuentaList;

    ArrayList<Cuentas> tipoList;
    ArrayList<Entidad> tipoEnti;

    ConexionSQLiteHelper conn;

    String nomCu;
    int numCu;

    private ICuentaDAOSql cuentaDAOSql = new CuentaDAOSql();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cuenta);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);


        ListView listViewEliCuenta = findViewById(R.id.listViewCuentasElimi);


        consultarCuentasEli();

        ItemRowAdapterEliminarCuenta adapterEliCuenta = new ItemRowAdapterEliminarCuenta(this,R.layout.adapter_item_row_eliminar,dataItemEliminarCuentaList);
        listViewEliCuenta.setAdapter(adapterEliCuenta);

        listViewEliCuenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long l) {
                Log.i("CLICK","Click en el elemento"+position+"de mi ListView");

                //Se busca la referencia del TextView en la vista.
                TextView tex = (TextView) arg1.findViewById(R.id.txt_nombreServicio);

                //Obtiene el texto dentro del TextView.
                nomCu = tex.getText().toString();

                TextView cod = (TextView) arg1.findViewById(R.id.txt_codigoServicio);
                String co = cod.getText().toString();
                numCu = Integer.parseInt(co);


                alert();

            }
        });


    }

    private void consultarCuentasEli() {

        ////para obtener resultados de una db

        SQLiteDatabase db = conn.getReadableDatabase();
        Cuentas cuen = null;
        Entidad enti = null;
        tipoList = new ArrayList<Cuentas>();
        tipoEnti = new ArrayList<Entidad>();

        Cursor cursor = db.rawQuery("SELECT *FROM CUENTAS INNER JOIN ENTIDAD  ON ENTIDAD.ENTI_ID  = CUENTAS.ENTI_ID  ",null);

        while (cursor.moveToNext()){
            cuen = new Cuentas();
            cuen.setIdCuenta(cursor.getInt(0));
            cuen.setNomCuenta(cursor.getString(1));
            cuen.setNumeroCuenta(cursor.getInt(2));
            cuen.setEntIdFK(cursor.getInt(3));

            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO A EL INT QUE ESTE EN LA VARIABLE PRUEBA /////
            int pruebaEnti = cuen.getEntIdFK();
            Cursor cursorcito = db.rawQuery("SELECT *FROM ENTIDAD WHERE ENTI_ID="+pruebaEnti+"",null);
            while (cursorcito.moveToNext()){
                enti = new Entidad();
                enti.setIdEntidad(cursorcito.getInt(0));
                enti.setNomEntidad(cursorcito.getString(1));
                tipoEnti.add(enti);
            }

            tipoList.add(cuen);
        }

        obtenerCueta();

    }

    private void obtenerCueta() {
        dataItemEliminarCuentaList = new ArrayList<DataItemEliminarCuenta>();
        for (int i=0;i<tipoList.size();i++){
            dataItemEliminarCuentaList.add(new DataItemEliminarCuenta(R.mipmap.reciclaje,""+tipoList.get(i).getNomCuenta(),""+tipoList.get(i).getNumeroCuenta(),"Entidad :   "+tipoEnti.get(i).getNomEntidad()));
        }
    }

    private void alert(){

        //iniciacion de alerta

        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("ADVERTENCIA ");
        alertDialog.setMessage("Confirma Que Desea Eliminar La Cuenta:  "+nomCu+" ? ");
        alertDialog.setCancelable(false);


        //Botones de alert

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String no = nomCu;
                int nu = numCu;
                try {
                    /// como contexto, utilizo la clase actual, y le coloco .this.   para cuando se trabaje con un Dialog.
                    cuentaDAOSql.eliminarCunta(EliminarCuenta.this,no,nu);
                    mensajeConfir();

                }catch (Exception e){
                    Toast.makeText(EliminarCuenta.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        /// se muestra la alerta
        alertDialog.show();

    }

    public void mensajeConfir(){
        Toast toast = Toast.makeText(this,"Cuenta Eliminada",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();

    }

}
