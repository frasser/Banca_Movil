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

import com.example.frasser.banca_movil.DataItem;
import com.example.frasser.banca_movil.R;

import java.util.List;

/**
 * Created by Juan Pablo on 20/12/2017.
 */

public class ItemRowAdapter extends ArrayAdapter<DataItem> {

    private Context context;
    private int layoutResourceId;
    private List<DataItem> data =null;

    public ItemRowAdapter(@NonNull Context context, int resource, @NonNull List<DataItem> objects) {
        super(context, resource, objects);

        this.layoutResourceId= resource;
        this.context=context;
        this.data=objects;

    }
    static class DataHolder{
        ImageView imageView;
        TextView textView;
        TextView numero;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataHolder holder = null;



        try {
            if (convertView==null){
                LayoutInflater inflater=((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(layoutResourceId,null);

                 holder = new DataHolder();
                holder.imageView = convertView.findViewById(R.id.imaView);
                holder.textView = convertView.findViewById(R.id.txt_nombreProducto);
                holder.numero = convertView.findViewById(R.id.txt_numPro);

                convertView.setTag(holder);

            }else {
                holder = (DataHolder) convertView.getTag();
            }

            DataItem dataItem = data.get(position);
            holder.textView.setText(dataItem.getNombreproducto());
            holder.imageView.setImageResource(dataItem.getId());
            holder.numero.setText(dataItem.getNumeroP());
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;

    }
}
