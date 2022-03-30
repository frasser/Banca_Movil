package com.example.frasser.banca_movil.fragments;

import android.content.ContentValues;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.frasser.banca_movil.R;
import com.example.frasser.banca_movil.Transferencias;
import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.modelo.Cuentas;
import com.example.frasser.banca_movil.modelo.Producto;
import com.example.frasser.banca_movil.modelo.Servicios;
import com.example.frasser.banca_movil.modelo.TipoProducto;

import java.util.ArrayList;

/**
 * Created by Juan Pablo on 3/02/2018.
 */

public class TransferenciasFragment extends Fragment {

    private TextView cueSel;
    private TextView numSel;
    private TextView txtdesde;
    private TextView ncue;
    private Button btnCancelar;
    private Button btnEfectuar;

    private TextView enti;
    private EditText edtMonto;

    ArrayList<TipoProducto> tipolist;
    ArrayList<Producto> tipolistPro;
    ArrayList<Cuentas> tipolistCue;
    ConexionSQLiteHelper conn;

    String Nu;
    String cod;
    String cue;
    String mosCue;
    String val;
    double p;
    int idSer;
    int valCuenta;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        conn = new ConexionSQLiteHelper(getContext(),"db_banco",null,1);

        View view = inflater.inflate(R.layout.fragment_transferencia,container,false);


        txtdesde = view.findViewById(R.id.textViewCuentaSele);
        ncue = view.findViewById(R.id.textViewNumSelec);
        cueSel = view.findViewById(R.id.textViewPagoA);
        numSel = view.findViewById(R.id.textViewNumeroSeleccTrans);
        enti = view.findViewById(R.id.textViewNombreEntidadSelecTrans);


        txtdesde.setText(Transferencias.seleccionNombreTr);

        cueSel.setText(Transferencias.nomsele);

        numSel.setText(Transferencias.numsele);
        enti.setText(Transferencias.entisele);

        consultarSaldo();


        edtMonto = view.findViewById(R.id.editTextMontoTransfer);

        btnCancelar = view.findViewById(R.id.btn_cancelar_Trans);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtMonto.setText("");


            }
        });

        btnEfectuar = view.findViewById(R.id.btn_efectuar_Trans);
        btnEfectuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                conn = new ConexionSQLiteHelper(getContext(), "db_banco", null, 1);
                String valor = edtMonto.getText().toString();

                if (edtMonto.getText().toString().isEmpty()) {

                    Toast.makeText(view.getContext(),"Ingrese Monto A Transferir !",Toast.LENGTH_SHORT).show();

                }else {

                    double a = Double.parseDouble(edtMonto.getText().toString());
                    double b = Double.parseDouble(val);

                    if (a <= b) {


                        try {
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
                                    "'" + valor + "', " +
                                    "4," +
                                    "1," +
                                    "null," +
                                    "" + valCuenta + "," +
                                    "" + cod + ") ";
                            // ejecuto el query
                            db.execSQL(insert);
                            db.close();

                            reduccion();
                            Toast.makeText(view.getContext(), "Transferencia Realizada Exitosamente", Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {

                            // throw e;
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        } finally {

                            conn.close();
                        }


                        edtMonto.setText("");
                        //Transferencias.comboTipoProdTrans.setSelection(0);

                    }else {
                        Toast.makeText(view.getContext(),"El Monto Ingresado Sobrepasa El Saldo Actual De Tu  "+txtdesde.getText().toString()+"",Toast.LENGTH_SHORT).show();
                    }
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





                            String noser = cueSel.getText().toString();
                            String cueser = numSel.getText().toString();



                    Cuentas cue = null;

                    tipolistCue = new ArrayList<Cuentas>();
                    Cursor cursooo = db.rawQuery("SELECT CUENTA_ID FROM CUENTAS WHERE NOM_CUEN='"+noser+"' AND NUMERO_CUE="+Integer.parseInt(cueser)+" ",null);
                    while (cursooo.moveToNext()) {
                        cue = new Cuentas();
                        cue.setIdCuenta(cursooo.getInt(0));
                        tipolistCue.add(cue);

                        idSer = cursooo.getInt(0);
                        valCuenta = idSer;


                    }


                }

            }
        }



        // SELECT  TP.TIPR_ID FROM TIPO_PRODUCTO TP,PRODUCTO P WHERE TP.TIPR_ID = P.TIPR_ID AND TP.DESCRIPCION_TIPR = C

        //

    }

    private void reduccion() {

        conn = new ConexionSQLiteHelper(getContext(),"db_banco",null,1);

        String valor = edtMonto.getText().toString();
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
