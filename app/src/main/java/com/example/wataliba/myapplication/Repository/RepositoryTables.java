package com.example.wataliba.myapplication.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.wataliba.myapplication.DataAccessEvento.DataEventHelper;

/**
 * Created by wataliba on 07/07/2017.
 */
public class RepositoryTables {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS noivos";
    private static final String NOME_BANCO = "prenda_noivos";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EVENTO = "noivos";
    private DataEventHelper dbEvento;
    SQLiteDatabase db;

    public RepositoryTables(Context ctx)throws Exception{
        // Criar utilizando um script SQL
        dbEvento = new DataEventHelper(ctx
                , RepositoryTables.NOME_BANCO
                , null, RepositoryTables.VERSAO_BANCO
                , new String[]
                {
                        RepositoryNoivosScript.SCRIPT_DATABASE_CREATE
                        , RepositoryEventoScript.SCRIPT_DATABASE_CREATE
                        , RepositoryConvidadoScript.SCRIPT_DATABASE_CREATE
                        , RepositoryEventoConvidadoScript.SCRIPT_DATABASE_CREATE
                }
                , RepositoryTables.SCRIPT_DATABASE_DELETE);

        // abre o banco no modo escrita para poder alterar também
        db = dbEvento.getWritableDatabase();
    }

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }
}
