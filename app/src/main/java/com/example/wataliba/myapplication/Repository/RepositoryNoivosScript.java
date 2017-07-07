package com.example.wataliba.myapplication.Repository;

import android.content.Context;

import com.example.wataliba.myapplication.DataAccessEvento.DataEventHelper;

/**
 * Created by wataliba on 31/03/2017.
 */
public class RepositoryNoivosScript extends RepositoryNoivos {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS noivos";
    private static final String NOME_BANCO = "prenda_noivos";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EVENTO = "noivos";
    private DataEventHelper dbEvento;


    /*private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
            new StringBuilder().
                    append("create table noivos ( _id text primary key").
                    append(", nome text not null").
                    append(", contato text not null );").toString()};*/

    private static final String SCRIPT_DATABASE_CREATE =
            new StringBuilder().
                    append("create table noivos ( _id text primary key").
                    append(", nome text not null").
                    append(", contato text not null );").toString();

    public RepositoryNoivosScript(Context ctx)throws Exception{
        // Criar utilizando um script SQL
        dbEvento = new DataEventHelper(ctx, RepositoryNoivosScript.NOME_BANCO, null, RepositoryNoivosScript.VERSAO_BANCO,
                new String[]{RepositoryNoivosScript.SCRIPT_DATABASE_CREATE, RepositoryEventoScript.SCRIPT_DATABASE_CREATE}
        , RepositoryNoivosScript.SCRIPT_DATABASE_DELETE);

        /*dbEvento = new DataEventHelper(ctx, RepositoryNoivosScript.NOME_BANCO, null
                ,RepositoryNoivosScript.VERSAO_BANCO
                ,RepositoryNoivosScript.SCRIPT_DATABASE_CREATE
                , RepositoryNoivosScript.SCRIPT_DATABASE_DELETE);*/

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

