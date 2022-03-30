package com.example.frasser.banca_movil;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.modelo.TipoIdentidad;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    Spinner combotipo;
    ArrayList<String> listaTipo;
    ArrayList<TipoIdentidad> tipoList;

    private Button btnIr;
    private EditText num;
    private EditText pass;
    String numeroDoc;
    String contraseña;
    int com ;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);

        setContentView(R.layout.activity_login);


        num = findViewById(R.id.edt_numero_doc);
        pass = findViewById(R.id.edt_password);

        btnIr = findViewById(R.id.btn_entrar);

        btnIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               // Intent intentprod = new Intent(view.getContext(),MainProductos.class);

                String numeroDoc = num.getText().toString();
                String contraseña = pass.getText().toString();
                int tipo = com;

                if (combotipo.getSelectedItemPosition() ==1){
                    com = 1;
                }if (combotipo.getSelectedItemPosition() ==2){
                    com = 2;
                }


                int sele = combotipo.getSelectedItemPosition();
                if (sele != 0) {



                            try {




                                    Cursor cursor = consultarUsuPass(com,numeroDoc, contraseña);
                                     if (cursor.getCount() > 0) {
                                      Intent i = new Intent(getApplicationContext(), MainProductos.class);
                                      startActivity(i);
                                      //finish();

                                         Toast.makeText(getApplicationContext(), "BIENVENIDO A TU BANCA MOVIL !", Toast.LENGTH_LONG).show();

                                      combotipo.setSelection(0);

                                     } else {
                                      Toast.makeText(getApplicationContext(), "No Se Encuentran Registros De El Usuario Ingresado !", Toast.LENGTH_SHORT).show();

                                      }
                                      num.setText("");
                                      pass.setText("");
                                       num.findFocus();


                            } catch (Exception e) {
                                      throw e;
                                     }
                             }else {
                    Toast.makeText(getApplicationContext(), "Seleccione Un Tipo De Documento !", Toast.LENGTH_SHORT).show();

                }







            }
        });


        combotipo = (Spinner)  findViewById(R.id.spn_documento);



        cosultarTipoDocu();

        // como ultimo paso , al combo le agrego lo acumulado en el array list
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listaTipo);
        combotipo.setAdapter(adaptador);




    }

    private void cosultarTipoDocu() {
        ////para obtener resultados de una db en un spinner

        SQLiteDatabase db=conn.getReadableDatabase();
        TipoIdentidad tipoid = null;
        tipoList = new ArrayList<TipoIdentidad>();

        Cursor cursor = db.rawQuery("SELECT *FROM TIPO_IDENTIFICACION",null);
        while (cursor.moveToNext()){
            tipoid = new TipoIdentidad();
            tipoid.setIdTipo(cursor.getInt(0));
            tipoid.setCodTipo(cursor.getString(1));
            tipoid.setDescripTipo(cursor.getString(2));

            tipoList.add(tipoid);
        }

        obtenerLista();

    }

    private void obtenerLista() {

        listaTipo = new ArrayList<String>();
        listaTipo.add("Seleccione");

        for (int i=0;i<tipoList.size();i++){
            listaTipo.add(tipoList.get(i).getCodTipo()+" - "+tipoList.get(i).getDescripTipo());
        }


    }
    public Cursor consultarUsuPass(Integer tip,String usu, String pass) throws SQLException{

        Cursor mcursor = null;



        conn = new ConexionSQLiteHelper(getApplicationContext(),"db_banco",null,1);
        mcursor = conn.getReadableDatabase().query("USUARIO",new String[]{"USUA_ID","IDENTIFICACION","NOMBRES","APELLIDOS","TELEFONO","TIID_ID ","CONTRASENA "}, "TIID_ID ="+tip+" AND IDENTIFICACION LIKE '"+usu+"' AND CONTRASENA LIKE '"+pass+"' ",null, null,null,null);


        return mcursor;
    }
}

