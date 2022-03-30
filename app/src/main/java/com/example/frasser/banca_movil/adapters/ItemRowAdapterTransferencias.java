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

import com.example.frasser.banca_movil.DataItemEliminarCuenta;
import com.example.frasser.banca_movil.R;

import java.util.List;

/**
 * Created by Juan Pablo on 1/02/2018.
 */

public class ItemRowAdapterTransferencias extends ArrayAdapter<DataItemEliminarCuenta> {

    private Context contextoC;
    private int layoutResourceCuenta;
    private List<DataItemEliminarCuenta> data = null;

    public ItemRowAdapterTransferencias(@NonNull Context context, int resource, @NonNull List<DataItemEliminarCuenta> objects) {
        super(context, resource, objects);
        this.contextoC = context;
        this.layoutResourceCuenta = resource;
        this.data = objects;
    }

    static class DataHolder {

        ImageView imagenElimicuenta;
        TextView textViewcuenta;
        TextView textNumCuenta;
        TextView textEnti;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataHolder holder = null;

        try {
            if (convertView==null){
                LayoutInflater inflater =  ((Activity)contextoC).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceCuenta,null);

                holder = new DataHolder();
                holder.imagenElimicuenta= convertView.findViewById(R.id.imaViewTrans);
                holder.textViewcuenta = convertView.findViewById(R.id.txt_nombreCueTrans);
                holder.textNumCuenta = convertView.findViewById(R.id.txt_numCue);
                holder.textEnti = convertView.findViewById(R.id.textViewnNomEnt);

                convertView.setTag(holder);
            }else {
                holder = (DataHolder) convertView.getTag();
            }

            DataItemEliminarCuenta dataItem = data.get(position);

            holder.textViewcuenta.setText(dataItem.getNombrecuenta());
            holder.imagenElimicuenta.setImageResource(dataItem.getIdimage());
            holder.textNumCuenta.setText(dataItem.getNumcuenta());
            holder.textEnti.setText(dataItem.getNomEnt());


        }catch (Exception e){
            e.printStackTrace();

        }
        return convertView;




    }
}
