package com.example.frasser.banca_movil;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.dataAccess.CuentaDAOSql;
import com.example.frasser.banca_movil.dataAccess.ICuentaDAOSql;
import com.example.frasser.banca_movil.modelo.Entidad;

import java.util.ArrayList;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class NewAccount extends AppCompatActivity{

    private Button btnCancelar;
    private Button btnRegistrar;
    private EditText edtNombre;
    private EditText edtNumero;

    Spinner comboEntidad;
    ArrayList<Entidad> tipoList;
    ArrayList<String> listaTipo;
    ConexionSQLiteHelper conn;


    // para poder utilizar los metodos

    private ICuentaDAOSql cuentaDAOSql = new CuentaDAOSql();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cuenta);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);

        comboEntidad = (Spinner) findViewById(R.id.spn_entidad_combo);
        
        consultarEntidad();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaTipo);
        comboEntidad.setAdapter(adapter);


        edtNombre = findViewById(R.id.edt_nombre_cuenta);
        edtNumero = findViewById(R.id.edt_numero_cuenta);
        btnRegistrar = findViewById(R.id.btn_registrar_cuenta);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selec =  comboEntidad.getSelectedItemPosition();


                if (edtNombre.getText().toString().isEmpty() || edtNumero.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext()," Complete Los Campos Requeridos ",Toast.LENGTH_SHORT).show();
                }else if (selec == 0){
                    Toast.makeText(view.getContext()," Seleccione Una Entidad Finaciera ",Toast.LENGTH_SHORT).show();
                }

                else {


                    String nom = edtNombre.getText().toString();
                    String numero = edtNumero.getText().toString();
                    long eni = comboEntidad.getSelectedItemId();

                    int enti = (int) eni;

                    try {

                        cuentaDAOSql.guardarCuenta(view.getContext(), null, nom, new Integer(numero), enti, null);
                        Toast.makeText(view.getContext(), "Cuenta Guardada Exitosamente", Toast.LENGTH_SHORT).show();
                        limpiar();


                    } catch (Exception e) {

                        Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }


            }

        });



        btnCancelar = findViewById(R.id.btn_cancelar_cuenta);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();



            }
        });


    }

    private void limpiar() {
        edtNombre.setText("");
        edtNumero.setText("");
        comboEntidad.setSelection(0);
    }

    private void consultarEntidad() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Entidad enti = null;
        tipoList = new ArrayList<Entidad>();
        Cursor cursor = db.rawQuery("SELECT *FROM ENTIDAD",null);
        while (cursor.moveToNext()){
            enti = new Entidad();
            enti.setIdEntidad(cursor.getInt(0));
            enti.setNomEntidad(cursor.getString(1));
            tipoList.add(enti);
        }
        obtenerListaEntidad();
    }

    private void obtenerListaEntidad() {
        listaTipo = new ArrayList<String>();
        listaTipo.add("Seleccione");
        for (int i=0;i<tipoList.size();i++){
            listaTipo.add(tipoList.get(i).getIdEntidad()+"    -    "+tipoList.get(i).getNomEntidad());
        }

    }
}
