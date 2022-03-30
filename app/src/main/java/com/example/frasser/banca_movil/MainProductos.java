package com.example.frasser.banca_movil;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.adapters.ItemRowAdapter;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.fragments.DetallesFragment;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.TipoProducto;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Pablo on 20/12/2017.
 */

public class MainProductos extends AppCompatActivity{

    List<DataItem> dataItemList;
   // List<DataItem> dataFragments;


    private ImageButton btnpago;
    private ImageButton btntransfer;



    /////////////////////////////
    ArrayList<Producto> tipoList;

    ///// ARRAY CREADO PARA SUPLANTAR EL ID POR EL NOMBRE DEL PRODUCTO///////////////
    ArrayList<TipoProducto> tipotipo;
    ////////////////////////////////////////////////////////////////////////////////
;
    ConexionSQLiteHelper conn;

   public static String nombreItem;
    public static String cod;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.salir, menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.salir){
            Toast.makeText(this,"Sesion Finalizada",Toast.LENGTH_SHORT).show();
            Intent intentSalir = new Intent(this,LoginActivity.class);
            startActivity(intentSalir);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);



        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);



        // dataFragments = new ArrayList<>();










        final ListView listView = findViewById(R.id.listViewCustome);
        /*ItemRowAdapter adapter = new ItemRowAdapter(this,R.layout.adapter_item_row,dataItemList);
        listView.setAdapter(adapter);  */

        ///---- accion para abrir nuevo activity dependiendo del Item seleccionado del listview --------////////////

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long l) {

                //Se busca la referencia del TextView en la vista.
                TextView texto = (TextView) arg1.findViewById(R.id.txt_nombreProducto);

                //Obtiene el texto dentro del TextView.
                nombreItem  = texto.getText().toString();

                TextView tex = (TextView) arg1.findViewById(R.id.txt_numPro);
                cod = tex.getText().toString();




                if (position == 0){
                    //abre un nuevo activity
                    //Intent myIntent = new Intent(view.getContext(),DetallesFragment.class);
                    //startActivity(myIntent);
                    //getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,new DetallesFragment(),"DetallesFragment").commit();


                    Fragment fragment = new DetallesFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).addToBackStack("DetallesFragment").commit();




                }else if (position == 1){


                   // getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,new DetallesFragment(),"DetallesFragment").commit();

                    Fragment fragment = new DetallesFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).addToBackStack("DetallesFragment").commit();



                }else if (position ==2){

                    //Toast.makeText(getApplicationContext(),
                        //    "Position :"+cod , Toast.LENGTH_LONG)
                      //      .show();
                    //getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,new DetallesFragment(),"DetallesFragment").commit();

                    Fragment fragment = new DetallesFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).addToBackStack("DetallesFragment").commit();







                }
            }
        });

        btnpago = findViewById(R.id.btn_pagos);
        btnpago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPago = new Intent(view.getContext(),Pagos.class);
                startActivity(intentPago);
            }
        });

        btntransfer = findViewById(R.id.btn_transferencias);
        btntransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttrans = new Intent(view.getContext(),Transferencias.class);
                startActivity(intenttrans);
            }
        });

        ConsultarProductos();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        //ArrayAdapter<CharSequence> adapterr = new ArrayAdapter(this,android.R.layout.list_content,dataItemList);

        ItemRowAdapter adapter = new ItemRowAdapter(this,R.layout.adapter_item_row,dataItemList);
        listView.setAdapter(adapter);








    }

    private void ConsultarProductos() {

        ////para obtener resultados de una db
        SQLiteDatabase db = conn.getReadableDatabase();
        Producto pro = null;
        TipoProducto tip = null;

        tipoList = new ArrayList<Producto>();
        tipotipo = new ArrayList<TipoProducto>();

        Cursor cursor = db.rawQuery("SELECT *FROM PRODUCTO",null);
        while (cursor.moveToNext()){
            pro = new Producto();
            tip = new TipoProducto();

            pro.setIdProd(cursor.getInt(0));
            pro.setCodProd(cursor.getString(1));
            pro.setVlorProd(cursor.getDouble(2));
            pro.setTipoProdIdFK(cursor.getInt(3));


            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO AEL INT QUE ESTE EN LA VARIABLE PRUEBA ////

            int pruebas = pro.getTipoProdIdFK();
            Cursor cursito = db.rawQuery("SELECT DESCRIPCION_TIPR FROM TIPO_PRODUCTO WHERE TIPR_ID ="+pruebas+"",null);

            while (cursito.moveToNext()){
                tip.setDescriTipoProd(cursito.getString(0));
                tipotipo.add(tip);


            }



            tipoList.add(pro);
        }
        obtener();


    }

    private void obtener() {





        dataItemList = new ArrayList<DataItem>();



        for (int i=0;i<tipoList.size();i++){


            ///// LINEA PARA COLOCAR ICONO DEACUERDO CON ID QUE ENVIE
            int ic = 0;


                if (tipoList.get(i).getTipoProdIdFK() == 1){
                    ic = R.mipmap.ahorro;
                }
                if (tipoList.get(i).getTipoProdIdFK() == 2){
                    ic = R.mipmap.banco;
                } if (tipoList.get(i).getTipoProdIdFK() == 3){
                    ic = R.mipmap.tarjetascredito;
                }if (tipoList.get(i).getTipoProdIdFK() == 4){
                    ic = R.mipmap.debito;
                }



            dataItemList.add(new DataItem(ic,""+tipotipo.get(i).getDescriTipoProd(),""+tipoList.get(i).getCodProd()));

















        }





    }


}
