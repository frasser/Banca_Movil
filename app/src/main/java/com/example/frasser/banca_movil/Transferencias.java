package com.example.frasser.banca_movil;

import android.content.Intent;
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

import com.example.frasser.banca_movil.adapters.ItemRowAdapterTransferencias;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.fragments.TransferenciasFragment;
import com.example.frasser.banca_movil.modelo.Cuentas;
import com.example.frasser.banca_movil.modelo.Entidad;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.TipoProducto;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Pablo on 24/01/2018.
 */


public class Transferencias extends AppCompatActivity {

    static public Spinner comboTipoProdTrans;
    private ImageButton btnagregar;
    private ImageButton btneliminar;

    ArrayList<String> listaTipo;
    ArrayList<Producto> tipoList;

    ///// ARRAY CREADO PARA SUPLANTAR EL ID POR EL NOMBRE DEL PRODUCTO///////////////
    ArrayList<TipoProducto> tipotipo;
    ArrayList<Entidad> tipoEnti;
    ////////////////////////////////////////////////////////////////////////////////

    List<DataItemEliminarCuenta> dataItemListTransfe;
    ArrayList<Cuentas> tipoListTransfer;
    ConexionSQLiteHelper conn;

    public  static String seleccionNombreTr;
    public  static long seleccionTr;

    public static String nomsele;
    public static String numsele;
    public static String entisele;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_transferencias);

       btnagregar = findViewById(R.id.btn_agregar_cuenta);
       btnagregar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intentAgregarCuenta = new Intent(view.getContext(),NewAccount.class);
               startActivity(intentAgregarCuenta);
           }
       });

       btneliminar = findViewById(R.id.btn_eliminar_cuenta);
       btneliminar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intentEliminarCuenta = new Intent(view.getContext(),EliminarCuenta.class);
               startActivity(intentEliminarCuenta);
           }
       });


       comboTipoProdTrans = (Spinner) findViewById(R.id.spn_desde_transferencia);

       conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);

       consultarProductosTransferencia();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listaTipo);
       comboTipoProdTrans.setAdapter(adapter);


        ListView listView = findViewById(R.id.listViewCuentas);
        consultarCuentas();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        ItemRowAdapterTransferencias adapterList = new ItemRowAdapterTransferencias(this,R.layout.adapter_item_row_transferencias,dataItemListTransfe);
        listView.setAdapter(adapterList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int i, long l) {

                int selec =  comboTipoProdTrans.getSelectedItemPosition();
                if (selec != 0 ) {

                    TextView text = (TextView) arg1.findViewById(R.id.txt_nombreCueTrans);
                    nomsele = text.getText().toString();

                    TextView co = (TextView) arg1.findViewById(R.id.txt_numCue);
                    numsele = co.getText().toString();

                    TextView cor = (TextView) arg1.findViewById(R.id.textViewnNomEnt);
                    entisele = cor.getText().toString();


                    seleccionNombreTr = (String) comboTipoProdTrans.getSelectedItem();
                    seleccionTr = comboTipoProdTrans.getSelectedItemId();


                    //getSupportFragmentManager().beginTransaction().add(R.id.container_fragment_transferencias,new TransferenciasFragment(),"TransferenciasFagment").commit();

                    Fragment fragment = new TransferenciasFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container_fragment_transferencias, fragment).addToBackStack("TransferenciasFragment").commit();
                }else{
                    Toast.makeText(adapterView.getContext(),"¡ Debe Seleccionar Una Cuenta De Origen ! ",Toast.LENGTH_SHORT).show();
                }



            }
        });

}

    private void consultarCuentas() {

        ////para obtener resultados de una db

        SQLiteDatabase db = conn.getReadableDatabase();
        Cuentas cuen = null;
        Entidad enti = null;
        tipoListTransfer = new ArrayList<Cuentas>();
        tipoEnti = new ArrayList<Entidad>();

        Cursor cursor = db.rawQuery("SELECT *FROM CUENTAS INNER JOIN ENTIDAD  ON ENTIDAD.ENTI_ID  = CUENTAS.ENTI_ID  ",null);

        while (cursor.moveToNext()){
            cuen = new Cuentas();
            cuen.setIdCuenta(cursor.getInt(0));
            cuen.setNomCuenta(cursor.getString(1));
            cuen.setNumeroCuenta(cursor.getInt(2));
            cuen.setEntIdFK(cursor.getInt(3));

            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO AEL INT QUE ESTE EN LA VARIABLE PRUEBA /////
            int pruebaEnti = cuen.getEntIdFK();
            Cursor cursorcito = db.rawQuery("SELECT *FROM ENTIDAD WHERE ENTI_ID="+pruebaEnti+"",null);
            while (cursorcito.moveToNext()){
                enti = new Entidad();
                enti.setIdEntidad(cursorcito.getInt(0));
                enti.setNomEntidad(cursorcito.getString(1));
                tipoEnti.add(enti);
            }

            tipoListTransfer.add(cuen);
        }

        obtenerListaCuentas();
    }

    private void obtenerListaCuentas() {
        dataItemListTransfe = new ArrayList<DataItemEliminarCuenta>();
        for (int i=0;i<tipoListTransfer.size();i++){
            dataItemListTransfe.add(new DataItemEliminarCuenta(R.mipmap.cuenta,""+tipoListTransfer.get(i).getNomCuenta(),tipoListTransfer.get(i).getNumeroCuenta().toString(),""+tipoEnti.get(i).getNomEntidad()));
        }
    }

    private void consultarProductosTransferencia() {

        ////para obtener resultados de una db en un spinner

        SQLiteDatabase db = conn.getReadableDatabase();
        Producto pro = null;
        TipoProducto tip = null;

        tipoList = new ArrayList<Producto>();
        tipotipo = new ArrayList<TipoProducto>();

        Cursor cursor = db.rawQuery("SELECT  *FROM PRODUCTO  INNER JOIN TIPO_PRODUCTO  ON TIPO_PRODUCTO.TIPR_ID = PRODUCTO.TIPR_ID  ",null);
        while (cursor.moveToNext()){
            pro = new Producto();
            tip = new TipoProducto();

            pro.setIdProd(cursor.getInt(0));
            pro.setCodProd(cursor.getString(1));
            pro.setVlorProd(cursor.getDouble(2));
            pro.setTipoProdIdFK(cursor.getInt(3));

            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO AEL INT QUE ESTE EN LA VARIABLE PRUEBA /////7

            int prueba = pro.getTipoProdIdFK();
            Cursor cursitop = db.rawQuery("SELECT DESCRIPCION_TIPR FROM TIPO_PRODUCTO WHERE TIPR_ID ="+prueba+"",null);

            while (cursitop.moveToNext()){
                tip.setDescriTipoProd(cursitop.getString(0));
                tipotipo.add(tip);
            }
            tipoList.add(pro);


        }

        obtenerLista();

    }

    private void obtenerLista() {

        listaTipo = new ArrayList<String>();
        listaTipo.add("Seleccione");

        for (int i=0;i<tipoList.size();i++){
            listaTipo.add(tipotipo.get(i).getDescriTipoProd());
        }

    }

}