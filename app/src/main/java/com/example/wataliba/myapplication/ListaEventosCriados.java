package com.example.wataliba.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.wataliba.myapplication.Adapter.ListaEventosAdapter;
import com.example.wataliba.myapplication.Entity.EntityEvento;
import com.example.wataliba.myapplication.Repository.RepositoryEvento;

import java.util.List;

/**
 * Created by wataliba on 21/06/2017.
 */
public class ListaEventosCriados extends Activity{

    List<EntityEvento> listEvento;
    ListaEventosAdapter listaEventosAdapter;
    RepositoryEvento repositoryEvento;
    ListView listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_eventos);

        repositoryEvento = new RepositoryEvento(this);
        this.listEvento = repositoryEvento.listarEventos();
        this.listaEventos = (ListView) findViewById(R.id.lstEventos);
        this.listaEventosAdapter = new ListaEventosAdapter(this,this.listEvento);
        this.listaEventos.setAdapter(this.listaEventosAdapter);
    }
}
