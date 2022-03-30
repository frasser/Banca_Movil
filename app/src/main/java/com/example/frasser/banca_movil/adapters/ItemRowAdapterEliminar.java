package com.example.frasser.banca_movil.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frasser.banca_movil.DataItemEliminar;
import com.example.frasser.banca_movil.R;

import java.util.List;

/**
 * Created by Juan Pablo on 25/01/2018.
 */

public class ItemRowAdapterEliminar extends ArrayAdapter<DataItemEliminar> {

    private Context contexto;
    private int layoutResourceIdEli;
    private List<DataItemEliminar> data =null;


    //Generar constructor

    public ItemRowAdapterEliminar (@NonNull Context context, int resource,@NonNull List<DataItemEliminar> objects) {
        super(context, resource, objects);


        this.layoutResourceIdEli = resource;
        this.contexto = context;
        this.data = objects;
    }

    static class DataHolder{

        ImageView imagenelimi;
        TextView  textViewServ;
        TextView textViewCodServ;

    }
    ///overrrade method getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder = null;

        try {
            if (convertView==null){
                LayoutInflater inflater =  ((Activity)contexto).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceIdEli,null);

                holder = new DataHolder();
                holder.imagenelimi = convertView.findViewById(R.id.imaViewEliminar);
                holder.textViewServ = convertView.findViewById(R.id.txt_nombreServicio);
                holder.textViewCodServ = convertView.findViewById(R.id.txt_codigoServicio);

                convertView.setTag(holder);
            }else {
                holder = (DataHolder) convertView.getTag();
            }

            DataItemEliminar dataItem = data.get(position);
            holder.textViewServ.setText(dataItem.getNombreservicio());
            holder.imagenelimi.setImageResource(dataItem.getIdentificador());
            holder.textViewCodServ.setText(dataItem.getCodigoServicio());

        }catch (Exception e){
            e.printStackTrace();

        }
        return convertView;
    }
}
