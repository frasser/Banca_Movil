package com.example.frasser.banca_movil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frasser.banca_movil.dataAccess.ConexionSQLiteHelper;
import com.example.frasser.banca_movil.dataAccess.IServiciosDAOSql;
import com.example.frasser.banca_movil.dataAccess.ServiciosDAOSql;

/**
 * Created by Juan Pablo on 24/01/2018.
 */

public class NewService extends AppCompatActivity {

    private Button cancelar;
    private Button registrar;
    private EditText nombre;
    private EditText numero;

    ConexionSQLiteHelper conn;

    // para poder utilizar los metodos
    private IServiciosDAOSql  serviciosDAOSql = new ServiciosDAOSql();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_servicio);

        conn = new ConexionSQLiteHelper(getApplication(),"db_banco",null,1);

        nombre = findViewById(R.id.edt_nombre_servicio);
        numero = findViewById(R.id.edt_numero_ref);
        registrar = findViewById(R.id.btn_registrar_servicio);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



             if (nombre.getText().toString().isEmpty() || numero.getText().toString().isEmpty()){
                 Toast.makeText(view.getContext()," Complete Los Campos Requeridos ",Toast.LENGTH_SHORT).show();
             }

                 else {

                 String nom = nombre.getText().toString();
                 String num = numero.getText().toString();
                 int n = (Integer.parseInt(num));

                 try {
                     serviciosDAOSql.guardarServicio(view.getContext(), null, nom, n, null);
                     Toast.makeText(view.getContext(), "Servicio Guardado Exitosamente", Toast.LENGTH_SHORT).show();
                     limpiar();

                 } catch (Exception e) {
                     Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
            }

        });


       cancelar = findViewById(R.id.btn_cancelar_servicio);
       cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               limpiar();
           }
       });
    }

    private void limpiar() {

        nombre.setText("");
        numero.setText("");
    }
}
