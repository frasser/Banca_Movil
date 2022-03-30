package com.example.frasser.banca_movil;

import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.adapters.ItemRowAdapterPagos;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.fragments.PagoFragment;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.Servicios;
import com.example.frasser.banca_movil.modelo.TipoProducto;

import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Pablo on 23/01/2018.
 */

public class Pagos extends AppCompatActivity {

   static public Spinner combotipo;
    ArrayList<String> listaTipo;
    ArrayList<Producto> tipoList;


    ///// ARRAY CREADO PARA SUPLANTAR EL ID POR EL NOMBRE DEL PRODUCTO///////////////
    ArrayList<TipoProducto> tipotipo;
    ////////////////////////////////////////////////////////////////////////////////7

    private ImageButton agregar;
    private ImageButton eliminar;

    ///////////////////////////////

    List<DataItem> dataItemListPagos;
    ArrayList<Servicios> tipoListServi;

    ConexionSQLiteHelper conn;

    public static String nomsel;
    public static String numsel;

    public static long seleccion;
    public static String seleccionNombre;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        agregar = findViewById(R.id.btn_agregar_servicio);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentagregar = new Intent(view.getContext(),NewService.class);
                startActivity(intentagregar);
            }
        });

        eliminar = findViewById(R.id.btn_eliminar_servicio);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inntenteliminar = new Intent(view.getContext(),EliminarServicios.class);
                startActivity(inntenteliminar);
            }
        });

        combotipo = (Spinner) findViewById(R.id.spn_desde);


        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);


        consultarProductos();

        // como ultimo paso , al combo le agrego lo acumulado en el array list

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,listaTipo);
        combotipo.setAdapter(adaptador);




        ListView listView = findViewById(R.id.listViewPagos);

        consultarServicios();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        ItemRowAdapterPagos adapter = new ItemRowAdapterPagos(this,R.layout.adapter_item_row_pagos,dataItemListPagos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long l) {

                int selec = combotipo.getSelectedItemPosition();
                if (selec != 0) {

                    TextView text = (TextView) arg1.findViewById(R.id.txt_nombreSerPago);
                    nomsel = text.getText().toString();

                    TextView co = (TextView) arg1.findViewById(R.id.txt_numSer);
                    numsel = co.getText().toString();

                    //Toast.makeText(getApplicationContext(),
                    //  "Position :"+nomsel+"   numero: "+numsel , Toast.LENGTH_LONG)
                    //  .show();

                    seleccionNombre = (String) combotipo.getSelectedItem();
                    seleccion = combotipo.getSelectedItemId();


                    // Toast.makeText(getApplicationContext(),
                    //       "Position :"+b+"    "+a+"   djid"+c, Toast.LENGTH_LONG)
                    //     .show();


                    Fragment fragment = new PagoFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container_fragment_pagos, fragment).addToBackStack("PagoFragment").commit();



                }else {
                    Toast.makeText(adapterView.getContext(),"¡ Debe Seleccionar Una Cuenta De Origen ! ",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void consultarServicios() {

        ////para obtener resultados de una db
        SQLiteDatabase db = conn.getReadableDatabase();
        Servicios ser = null;

        tipoListServi = new ArrayList<Servicios>();

        Cursor cursor = db.rawQuery("SELECT *FROM SERVICIOS",null);
        while (cursor.moveToNext()){
            ser = new Servicios();
            ser.setIdServicio(cursor.getInt(0));
            ser.setNomServicio(cursor.getString(1));
            ser.setRefServicio(cursor.getInt(2));
            tipoListServi.add(ser);
        }

        obtenerServ();


    }

    private void obtenerServ() {

        dataItemListPagos = new ArrayList<DataItem>();

        for (int i=0;i<tipoListServi.size();i++){
            dataItemListPagos.add(new DataItem(R.mipmap.metodo,""+tipoListServi.get(i).getNomServicio(),""+tipoListServi.get(i).getRefServicio()));
        }


    }

    private void consultarProductos() {

        ////para obtener resultados de una db en un spinner

        SQLiteDatabase db=conn.getReadableDatabase();
        Producto prodid = null;
        TipoProducto tip = null;

        tipoList = new ArrayList<Producto>();
        tipotipo = new ArrayList<TipoProducto>();

        Cursor cursor = db.rawQuery("SELECT  *FROM PRODUCTO  INNER JOIN TIPO_PRODUCTO  ON TIPO_PRODUCTO.TIPR_ID = PRODUCTO.TIPR_ID  ",null);
        while (cursor.moveToNext()){
            prodid = new Producto();
            tip = new TipoProducto();

            prodid.setIdProd(cursor.getInt(0));
            prodid.setCodProd(cursor.getString(1));
            prodid.setVlorProd(cursor.getDouble(2));
            prodid.setTipoProdIdFK(cursor.getInt(3));

            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO AEL INT QUE ESTE EN LA VARIABLE PRUEBA /////7
            int prueba = prodid.getTipoProdIdFK();


            Cursor cursortip = db.rawQuery("SELECT DESCRIPCION_TIPR FROM TIPO_PRODUCTO WHERE TIPR_ID ="+prueba+"",null);

            while (cursortip.moveToNext()){
                tip = new TipoProducto();
                tip.setDescriTipoProd(cursortip.getString(0));

                tipotipo.add(tip);
            }



            tipoList.add(prodid);
        }

        obtenerListaProd();
    }

    private void obtenerListaProd() {

        listaTipo = new ArrayList<String>();
        listaTipo.add("Seleccione");

        for (int i=0;i<tipoList.size();i++){



            listaTipo.add(tipotipo.get(i).getDescriTipoProd());

        }


    }
}
