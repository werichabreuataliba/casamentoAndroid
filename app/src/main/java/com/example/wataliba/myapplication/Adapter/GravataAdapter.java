package com.example.wataliba.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wataliba.myapplication.Entity.EntityImagemGravata;
import com.example.wataliba.myapplication.Entity.EntityValor;
import com.example.wataliba.myapplication.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by wataliba on 29/03/2017.
 */
public class GravataAdapter extends BaseAdapter {

    Context context;
    List<EntityValor> listaValor;
    List<EntityImagemGravata> listaImagemGravata;

    public GravataAdapter(Context context
            , List<EntityValor> listaValor
            , List<EntityImagemGravata> listaImagemGravata)
    {
        this.context = context;
        this.listaValor = listaValor;
        this.listaImagemGravata = listaImagemGravata;
    }

    @Override
    public int getCount() {
        return listaImagemGravata.size();
    }

    @Override
    public Object getItem(int position) {
        return (EntityValor)  listaValor.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        EntityValor valor = listaValor.get(position);
        EntityImagemGravata entityImagemGravata = listaImagemGravata.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.customlistview,null);
        TextView t =(TextView) view.findViewById(R.id.txtValores);
        //t.setText(String.format("%,d", valor.get_valor()).replace(',', ' '));
        t.setText(new DecimalFormat("R$ ##,##0.00").format( valor.get_valor()));
        ImageView imagem = (ImageView)view.findViewById(R.id.imageView);
        imagem.setImageResource(entityImagemGravata.getIdImagemGravata());

        return view;
    }
}
