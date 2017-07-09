package com.example.wataliba.myapplication.Repository;

import android.content.Context;

import com.example.wataliba.myapplication.DataAccessEvento.DataEventHelper;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryConvidadoScript extends RepositoryConvidado {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS convidado";
    private static final String NOME_BANCO = "prenda_noivos";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EVENTO = "convidado";
    private DataEventHelper dbEvento;

    public static final String SCRIPT_DATABASE_CREATE =
            new StringBuilder().
                    append("create table convidado ( _id text primary key").
                    append(", nome text not null").
                    append(", contato text not null );").toString();

    /*private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
            new StringBuilder().
                    append("create table convidado ( _id text primary key").
                    append(", nome text not null").
                    append(", contato text not null );").toString()};*/

    public RepositoryConvidadoScript(Context ctx) {
        // Criar utilizando um script SQL
        /*dbEvento = new DataEventHelper(ctx, RepositoryConvidadoScript.NOME_BANCO, null, RepositoryConvidadoScript.VERSAO_BANCO,
                RepositoryConvidadoScript.SCRIPT_DATABASE_CREATE, RepositoryConvidadoScript.SCRIPT_DATABASE_DELETE);*/

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
