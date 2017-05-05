package com.example.wataliba.myapplication.Repository;

import android.content.Context;

import com.example.wataliba.myapplication.DataAccessEvento.DataEventHelper;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryEventoScript extends RepositoryEvento{

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS evento";
    private static final String NOME_BANCO = "prenda_noivos";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EVENTO = "evento";
    private DataEventHelper dbEvento;


    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            new StringBuilder().
                    append("create table evento ( _id text primary key").
                    append(", nome_evento text not null").
                    append(", convite_evento text null").
                    append(", latitude_longitude_evento text null").
                    append(", endereco_evento text not null").
                    append(", ativo boolean 1").
                    append(", horario_evento text not null").
                    append(", data_evento text not null").
                    append(", FOREIGN KEY(noivos_id) REFERENCES customers(_id)").
                    append(", prenda_preco_min real not null").
                    append(", prenda_preco_max real not null );").toString()
                    };

    public RepositoryEventoScript(Context ctx) {
        // Criar utilizando um script SQL
        dbEvento = new DataEventHelper(ctx, RepositoryEventoScript.NOME_BANCO, null,RepositoryEventoScript.VERSAO_BANCO,
                RepositoryEventoScript.SCRIPT_DATABASE_CREATE, RepositoryEventoScript.SCRIPT_DATABASE_DELETE);

        // abre o banco no modo escrita para poder alterar também
        db = dbEvento.getWritableDatabase();
    }

    @Override
    public void fechar() {
        super.fechar();
        if (dbEvento != null) {
            dbEvento.close();
        }
    }
}
