package com.example.frasser.banca_movil.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.DataItemMovimientos;
import com.example.frasser.banca_movil.MainProductos;
import com.example.frasser.banca_movil.R;
import com.example.frasser.banca_movil.adapters.ItemRowFragmentDetalles;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.modelo.Cuentas;
import com.example.frasser.banca_movil.modelo.Movimiento;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.Servicios;
import com.example.frasser.banca_movil.modelo.TipoMovimiento;
import com.example.frasser.banca_movil.modelo.TipoProducto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juan Pablo on 18/01/2018.
 */

public class DetallesFragment extends android.support.v4.app.Fragment {


    List<DataItemMovimientos> dataItemMovimientosList;
    ArrayList<Movimiento> tipoListM;
    ArrayList<TipoMovimiento> tipoMov;
    ArrayList<Servicios> tipoSer;
    ArrayList<Cuentas> tipoCue;
    ArrayList<TipoProducto> tipoPro;

    private TextView txtsaldo;
    private TextView txtNombre;
    private TextView txtCodigo;
    private ImageView imaIco;

    ArrayList<Producto> tipolist;

    String sal;
    double p;
    String Nu;
    String cod;

    ConexionSQLiteHelper conn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable  Bundle savedInstanceState) {

        conn = new ConexionSQLiteHelper(getContext(),"db_banco",null,1);


        View view=inflater.inflate(R.layout.fragment_detalles,container,false);

        txtsaldo = view.findViewById(R.id.textViewSaldo);
        txtNombre = view.findViewById(R.id.textViewNombreCuenta);
        txtCodigo = view.findViewById(R.id.textViewCuenta);

        txtNombre.setText(MainProductos.nombreItem);
        txtCodigo.setText(MainProductos.cod);

        imaIco = (ImageView)view.findViewById(R.id.imaViewFragment);




        consultarSaldo();
        if (cod.equals("1")){
            imaIco.setImageResource(R.mipmap.ahorro);
        }if (cod.equals("2")){
            imaIco.setImageResource(R.mipmap.banco);
        }if (cod.equals("3")){
            imaIco.setImageResource(R.mipmap.tarjetascredito);
        }if (cod.equals("4")){
            imaIco.setImageResource(R.mipmap.debito);
        }

        consultarCuentasEli();
        ListView listView = view.findViewById(R.id.listViewCustomeDetalles);

        ItemRowFragmentDetalles adapter = new ItemRowFragmentDetalles(view.getContext(),R.layout.adapter_item_row_detalles,dataItemMovimientosList);
        listView.setAdapter(adapter);





        return view;




    }

    private void consultarSaldo() {

        String no= txtNombre.getText().toString();
        int c = Integer.parseInt(txtCodigo.getText().toString());

        SQLiteDatabase db = conn.getReadableDatabase();
        Producto pro = null;
        tipolist = new ArrayList<Producto>();
        Cursor cursor = db.rawQuery("SELECT VALOR FROM PRODUCTO WHERE CODIGO="+c+"",null);
        while (cursor.moveToNext()){
            pro = new Producto();
            pro.setVlorProd(cursor.getDouble(0));
            tipolist.add(pro);
             p = cursor.getDouble(0);
             sal = String.valueOf(p) ;

            txtsaldo.setText(sal.toString());


            String nomb= txtNombre.getText().toString();
            //int c = Integer.parseInt(txtCodigo.getText().toString());


            TipoProducto tip = null;
            tipoPro = new ArrayList<TipoProducto>();
            Cursor cursore = db.rawQuery("SELECT TIPR_ID FROM TIPO_PRODUCTO WHERE DESCRIPCION_TIPR='"+nomb+"' ",null);
            while (cursore.moveToNext()) {
                tip = new TipoProducto();
                tip.setTipoProdId(cursore.getInt(0));
                tipoPro.add(tip);

                Nu = cursore.getString(0);
                cod = String.valueOf(Nu);

            }



        }

                // SELECT  TP.TIPR_ID FROM TIPO_PRODUCTO TP,PRODUCTO P WHERE TP.TIPR_ID = P.TIPR_ID AND TP.DESCRIPCION_TIPR = C

        //

    }
    private void consultarCuentasEli() {

        ////para obtener resultados de una db

        SQLiteDatabase db = conn.getReadableDatabase();

            Movimiento mov = null;
            TipoMovimiento tipMo = null;
            Servicios ser = null;
            Cuentas cue = null;

        tipoListM = new ArrayList<Movimiento>();
        tipoMov = new ArrayList<TipoMovimiento>();
        tipoCue = new ArrayList<Cuentas>();
        tipoSer = new ArrayList<Servicios>();

        Cursor cursor = db.rawQuery("SELECT *FROM MOVIMIENTO WHERE PROD_ID ="+cod+"",null);

        while (cursor.moveToNext()){
            mov = new Movimiento();
            mov.setIdMov(cursor.getInt(0));
            mov.setVlorMov(cursor.getDouble(1));
            mov.setTipoMovIdFK(cursor.getInt(2));
            mov.setFKUsuId(cursor.getInt(3));
            mov.setFKServId(cursor.getInt(4));
            mov.setFKCuenId(cursor.getInt(5));


            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO A EL INT QUE ESTE EN LA VARIABLE PRUEBA /////
            int pruebaCue = mov.getFKCuenId();
            Cursor cursorcitob = db.rawQuery("SELECT NOM_CUEN FROM CUENTAS WHERE CUENTA_ID="+pruebaCue+"",null);
            while (cursorcitob.moveToNext()){
                cue = new Cuentas();
                cue.setNomCuenta(cursorcitob.getString(0));

                tipoCue.add(cue);
            }
            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO A EL INT QUE ESTE EN LA VARIABLE PRUEBA /////
            int pruebaSer = mov.getFKServId();
            Cursor cursorcitoa = db.rawQuery("SELECT NOM_SERV FROM SERVICIOS WHERE SERV_ID="+pruebaSer+"",null);
            while (cursorcitoa.moveToNext()){
                ser = new Servicios();
                ser.setNomServicio(cursorcitoa.getString(0));


                tipoSer.add(ser);




            }
            ////// SENTENCIA PARA SELECCIONAR LA DESCRIPCION, DE ACUERDO A EL INT QUE ESTE EN LA VARIABLE PRUEBA /////
            int pruebaMov = mov.getTipoMovIdFK();
            Cursor cursorcito = db.rawQuery("SELECT DESCRIPCION_TIMO FROM TIPO_MOVIMIENTO WHERE TIMO_ID="+pruebaMov+"",null);
            while (cursorcito.moveToNext()){
                tipMo = new TipoMovimiento();
                tipMo.setNomTipoMov(cursorcito.getString(0));

                tipoMov.add(tipMo);
            }

            tipoListM.add(mov);
        }

       obtenerCueta();

    }

    private void obtenerCueta() {

        dataItemMovimientosList = new ArrayList<DataItemMovimientos>();
        for (int i=0;i<tipoListM.size();i++) {
            tipoListM.removeAll(Collections.singleton(null));






                dataItemMovimientosList.add(new DataItemMovimientos("" + tipoListM.get(i).getVlorMov(), "" + tipoMov.get(i).getNomTipoMov()));
                // dataItemMovimientosList.add(new DataItemMovimientos(tipoListM.get(i).getVlorMov(),tipoListM.get(i).getTipoMovIdFK()+Integer.parseInt(tipoMov.get(i).getNomTipoMov()),tipoListM.get(i).getFKServId()+""+tipoSer.get(i).getNomServicio()+""+tipoCue.get(i).getNomCuenta(),null,null));


        }

    }
}
