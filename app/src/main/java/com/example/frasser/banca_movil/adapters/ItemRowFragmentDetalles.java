package com.example.frasser.banca_movil.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.frasser.banca_movil.R;
import com.example.frasser.banca_movil.DataItemMovimientos;

import java.util.List;

/**
 * Created by Juan Pablo on 2/02/2018.
 */

public class ItemRowFragmentDetalles extends ArrayAdapter<DataItemMovimientos> {

    private Context context;
    private int layoutResourceId;
    private List<DataItemMovimientos> data =null;

    public ItemRowFragmentDetalles(@NonNull Context context, int resource,  List<DataItemMovimientos> objects) {
        super(context, resource,objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }
    static class DataHolder{

        TextView texTipo;
        TextView texValor;
        TextView texDestino;
        TextView texDEST;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataHolder holder = null;
        try {

            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId, null);

                holder = new DataHolder();
                holder.texTipo = convertView.findViewById(R.id.textViewTipo);
                holder.texValor = convertView.findViewById(R.id.textViewValor);
            //    holder.texDestino = convertView.findViewById(R.id.textViewDestino);
               // holder.texDEST = convertView.findViewById(R.id.textViewDestino);

                convertView.setTag(holder);

            }else {
                holder = (DataHolder) convertView.getTag();

            }

            DataItemMovimientos dataItemMovimientos = data.get(position);
            holder.texTipo.setText(dataItemMovimientos.getTipoMovIdFK());
            holder.texValor.setText(dataItemMovimientos.getVlorMov());
           // holder.texDestino.setText(dataItemMovimientos.getFKServId());
           // holder.texDEST.setText(dataItemMovimientos.getFKServId().toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;


    }

}