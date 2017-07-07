package com.example.wataliba.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.wataliba.myapplication.Entity.EntityEvento;
import com.example.wataliba.myapplication.R;

import java.util.List;

/**
 * Created by wataliba on 21/06/2017.
 */
public class ListaEventosAdapter extends BaseAdapter {

    Context context;
    List<EntityEvento> listeventos;

    public ListaEventosAdapter(Context context, List<EntityEvento> listeventos)
    {
        this.context = context;
        this.listeventos = listeventos;
    }

    @Override
    public int getCount() {
        return this.listeventos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listeventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        EntityEvento evento = (EntityEvento) this.listeventos.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_lista_evento, null);

        TextView textEvento = (TextView) view.findViewById(R.id.txtEventoNome);
        Button botaoVisualizar = (Button) view.findViewById(R.id.btnVisualizar);

        textEvento.setText(evento.get_nome());

        return view;
    }
}
