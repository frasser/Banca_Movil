package com.example.frasser.banca_movil.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.Pagos;
import com.example.frasser.banca_movil.R;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.Servicios;
import com.example.frasser.banca_movil.modelo.TipoIdentidad;
import com.example.frasser.banca_movil.modelo.TipoProducto;

import java.util.ArrayList;

/**
 * Created by Juan Pablo on 2/02/2018.
 *
 *
 *
 */

public class PagoFragment extends Fragment {

    private TextView cueSel;
    private TextView numSel;
    private TextView txtdesde;
    private TextView ncue;
    private RadioButton minimo;
    private RadioButton maximo;
    private RadioButton otro;
    private EditText monto;
    private Button btnAceptar;
    private Button btnCancelar;
    private RadioGroup grupoRadio;


    ArrayList<TipoProducto> tipolist;
    ArrayList<Producto> tipolistPro;

    ConexionSQLiteHelper conn;

    String Nu;
    String cod;
    String cue;
    String mosCue;
    String val;
    double p;
    int idSer;
    int idServicio;

    String valorDesdeRadio;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        conn = new ConexionSQLiteHelper(getContext(),"db_banco",null,1);

        View view =inflater.inflate(R.layout.fragment_pagos,container,false);

        cueSel = view.findViewById(R.id.textViewPagoA);
        numSel = view.findViewById(R.id.textViewNumeroRefSelecc);
        txtdesde = view.findViewById(R.id.textViewCuentaSele);
        ncue = view.findViewById(R.id.textViewNumSelec);

        minimo = view.findViewById(R.id.radioButtonMini);
        maximo = view.findViewById(R.id.radioButtonMax);
        otro = view.findViewById(R.id.radioButtonOtro);
        monto = view.findViewById(R.id.editTextValor);
        grupoRadio = view.findViewById(R.id.radioGroupValor);
        monto.setEnabled(false);

        cueSel.setText(Pagos.nomsel);
        numSel.setText(Pagos.numsel);
        txtdesde.setText(Pagos.seleccionNombre);

        btnCancelar = view.findViewById(R.id.btn_cancelar_pago);
        btnAceptar = view.findViewById(R.id.btn_efectuar_pago);


        consultarSaldo();



        otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otro.isChecked()==true){

                    monto.setEnabled(true);
                }
                valorDesdeRadio = "";



            }
        });
        minimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otro.isChecked()==false){
                    monto.setEnabled(false);
                    monto.setText("");
                }
                valorDesdeRadio = String.valueOf(50000);
            }
        });
        maximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otro.isChecked()==false){
                    monto.setEnabled(false);
                    monto.setText("");
                }
                valorDesdeRadio = String.valueOf(100000);
            }
        });



        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monto.setText("");
                grupoRadio.clearCheck();
                monto.setEnabled(false);
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {




                if (grupoRadio.getCheckedRadioButtonId() != -1) {


                    if (otro.isChecked() == true && monto.getText().toString().isEmpty()) {


                        Toast.makeText(view.getContext(), "Ingrese Monto A Pagar !", Toast.LENGTH_SHORT).show();

                        // valorDesdeRadio = monto.getText().toString();


                    } else {

                        //Toast.makeText(view.getContext(), "sigamos!", Toast.LENGTH_SHORT).show();

                        if (otro.isChecked() == true)
                            valorDesdeRadio = monto.getText().toString();
                            double b = Double.parseDouble(val);
                            double a = Double.parseDouble(valorDesdeRadio);
                            if (a <= b){
                                conn = new ConexionSQLiteHelper(view.getContext(), "db_banco", null, 1);
                              try{

                                   SQLiteDatabase db = conn.getWritableDatabase();
                                  String insert = "INSERT INTO MOVIMIENTO " +

                                    " (MOV_ID, " +
                                    "VALOR_MOV, " +
                                    "TIMO_ID, " +
                                    "USUA_ID, " +
                                    "SERV_ID, " +
                                    "CUENTA_ID, " +
                                    "PROD_ID )" +
                                    " VALUES (" +
                                    "null, " +
                                    "'" + valorDesdeRadio + "', " +
                                    "3," +
                                    "1," +
                                    "" + idSer + "," +
                                    "null," +
                                    "" + cod + ") ";
                                    // ejecuto el query
                                db.execSQL(insert);
                                db.close();
                                  restarValor();
                                  Toast.makeText(view.getContext(), "Transferencia Realizada Exitosamente", Toast.LENGTH_SHORT).show();


                              } catch (Exception e){
                                 Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                              }finally {
                                conn.close();
                              }

                                monto.setText("");
                                grupoRadio.clearCheck();
                                monto.setEnabled(false);

                                //SINTAXIS UTILIZADA PARA CERRAR EL FRAGMENT ACTUAL
                              // getFragmentManager().beginTransaction().remove(PagoFragment.this).commit();
                                //Pagos.combotipo.setSelection(0);


                            }else {
                                Toast.makeText(view.getContext(),"El Monto Ingresado Sobrepasa El Saldo Actual De Tu  "+txtdesde.getText().toString()+"",Toast.LENGTH_SHORT).show();
                            }
                    }
                } else {
                    Toast.makeText(view.getContext(), "Seleccione o Ingrese Monto  Pagar !", Toast.LENGTH_SHORT).show();

                }


            }
        });

        return view;
    }
    private void consultarSaldo() {

        String nomb= txtdesde.getText().toString();
        //int c = Integer.parseInt(txtCodigo.getText().toString());

        SQLiteDatabase db = conn.getReadableDatabase();
        TipoProducto tip = null;
        tipolist = new ArrayList<TipoProducto>();
        Cursor cursor = db.rawQuery("SELECT TIPR_ID FROM TIPO_PRODUCTO WHERE DESCRIPCION_TIPR='"+nomb+"' ",null);
        while (cursor.moveToNext()){
            tip = new TipoProducto();
            tip.setTipoProdId(cursor.getInt(0));
            tipolist.add(tip);

            Nu = cursor.getString(0);
           cod = String.valueOf(Nu) ;


            Producto pro = null;
            Producto pr = null;
            tipolistPro = new ArrayList<Producto>();
            Cursor cursorcito = db.rawQuery("SELECT CODIGO FROM PRODUCTO WHERE TIPR_ID='"+cod+"' ",null);
            while (cursorcito.moveToNext()) {
                pro = new Producto();
                pro.setCodProd(cursorcito.getString(0));
                tipolistPro.add(pro);

                cue = cursorcito.getString(0);
                mosCue = cue;
                ncue.setText(mosCue.toString());

                Cursor cursorcitos = db.rawQuery("SELECT VALOR FROM PRODUCTO WHERE TIPR_ID='"+cod+"' AND CODIGO='"+mosCue+"' ",null);
                while (cursorcitos.moveToNext()) {
                    pr = new Producto();
                    pr.setVlorProd(cursorcitos.getDouble(0));
                    tipolistPro.add(pr);

                    p = cursorcitos.getDouble(0);
                    val = String.valueOf(p);


                    Servicios ser = null;

                    String nomSer = cueSel.getText().toString();
                    String numSelt = numSel.getText().toString();

                    //tipolistCue = new ArrayList<Cuentas>();
                    Cursor cursooo = db.rawQuery("SELECT SERV_ID FROM SERVICIOS WHERE NOM_SERV='"+nomSer+"' AND REF_SERV="+Integer.parseInt(numSelt)+" ",null);
                    while (cursooo.moveToNext()) {
                        ser = new Servicios();
                        ser.setIdServicio(cursooo.getInt(0));
                        //tipolistCue.add(cue);

                        idSer = cursooo.getInt(0);
                        idServicio = idSer;


                    }

                }

            }
        }

        // SELECT  TP.TIPR_ID FROM TIPO_PRODUCTO TP,PRODUCTO P WHERE TP.TIPR_ID = P.TIPR_ID AND TP.DESCRIPCION_TIPR = C

        //

    }
   private void restarValor(){

       conn = new ConexionSQLiteHelper(getContext(),"db_banco",null,1);

       String valor = valorDesdeRadio;
       double v = Double.parseDouble(valor);


       try {
           SQLiteDatabase db = conn.getWritableDatabase();

           ContentValues cv = new ContentValues();



           String update = "UPDATE PRODUCTO SET VALOR = VALOR - "+valor+ " WHERE PROD_ID ="+Integer.parseInt(cod);

           // ejecuto el query
           db.execSQL(update);
           db.close();
           Toast.makeText((Context)getContext(),"Saldo de Cuenta Actualizado",Toast.LENGTH_SHORT).show();



       }catch (Exception e){

           // throw e;
           Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
       }finally {

           conn.close();
       }


   }
}
