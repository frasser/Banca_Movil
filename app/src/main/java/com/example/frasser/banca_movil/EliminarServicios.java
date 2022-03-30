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

import com.example.frasser.banca_movil.adapters.ItemRowAdapterEliminar;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.dataAccess.IServiciosDAOSql;
import com.example.frasser.banca_movil.dataAccess.ServiciosDAOSql;
import com.example.frasser.banca_movil.modelo.Servicios;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class EliminarServicios extends AppCompatActivity {

    List<DataItemEliminar>  dataItemEliminarList;

    ArrayList<Servicios> tipoList;

    ConexionSQLiteHelper conn;

    String nombreItem; //variable local para capturar texto de un listview y mostrarlo en un Dialog
    int codItem; //variable local para capturar texto de un listview y mostrarlo en un Dialog

    private IServiciosDAOSql serviciosDAOSql = new ServiciosDAOSql();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_servicio);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);
/*
        dataItemEliminarList = new ArrayList<>();
        dataItemEliminarList.add(new DataItemEliminar(1,"CLARO No.7272444"));
        dataItemEliminarList.add(new DataItemEliminar(2,"NETFLIX No.544444"));
        dataItemEliminarList.add(new DataItemEliminar(3,"Tarjeta de Credito Visa No.1200"));
*/

            final ListView listViewEli = findViewById(R.id.listViewServiciosElimi);


        consultarCuenta();


        ItemRowAdapterEliminar adapterEli = new ItemRowAdapterEliminar(this,R.layout.adapter_item_row_eliminar,dataItemEliminarList);
        listViewEli.setAdapter(adapterEli);

        listViewEli.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long l) {
                Log.i("Click", "click en el elemento " + position + " de mi ListView");


                //Se busca la referencia del TextView en la vista.
                TextView texto = (TextView) arg1.findViewById(R.id.txt_nombreServicio);

                //Obtiene el texto dentro del TextView.
                nombreItem  = texto.getText().toString();



                //Se busca la referencia del TextView en la vista.
                TextView cod = (TextView) arg1.findViewById(R.id.txt_codigoServicio);

                //Obtiene el texto dentro del TextView.
                 String codi  = cod.getText().toString();
                 codItem = Integer.parseInt(codi);



                /*Toast.makeText(getApplicationContext(),
                        "Position :"+codi , Toast.LENGTH_LONG)
                        .show();
*/




                alert();
            }
        });






    }


    private void consultarCuenta() {
        ////para obtener resultados de una db
        SQLiteDatabase db = conn.getReadableDatabase();
        Servicios serv = null;

        tipoList = new ArrayList<Servicios>();
        Cursor cursor = db.rawQuery("SELECT *FROM SERVICIOS",null);
        while (cursor.moveToNext()){
            serv = new Servicios();
            serv.setIdServicio(cursor.getInt(0));
            serv.setNomServicio(cursor.getString(1));
            serv.setRefServicio(cursor.getInt(2));

            tipoList.add(serv);
        }

        obtenerCuenta();
    }

    private void obtenerCuenta() {

        dataItemEliminarList = new ArrayList<DataItemEliminar>();
        for (int i=0;i<tipoList.size();i++){
            dataItemEliminarList.add(new DataItemEliminar(R.mipmap.reciclaje,""+tipoList.get(i).getNomServicio(),""+tipoList.get(i).getRefServicio()));
        }
    }

    private void alert(){
        //iniciacion de alerta

        AlertDialog alerta;
        alerta = new AlertDialog.Builder(this).create();
        alerta.setTitle("ADVERTENCIA ");
        alerta.setMessage("Confirma Que Desea Eliminar El Servicio: "+nombreItem+" ? ");
        alerta.setCancelable(false);

        //Botones de alert

        alerta.setButton(DialogInterface.BUTTON_POSITIVE, "SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String nom = nombreItem.toString();
                int ref  = codItem;


                try {
                    /// como contexto, utilizo la clase actual, y le coloco .this.   para cuando se trabaje con un Dialog.
                    serviciosDAOSql.eliminarServicio(EliminarServicios.this, nom, ref);
                    MensajeConfirma();

                }catch (Exception e){
                    Toast.makeText(EliminarServicios.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }




            }
        });

        alerta.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();





            }
        });

        /// se muestra la alerta
        alerta.show();



    }

    public void MensajeConfirma(){

        Toast toast = Toast.makeText(this,"Servicio Eliminado ",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();


    }

}
