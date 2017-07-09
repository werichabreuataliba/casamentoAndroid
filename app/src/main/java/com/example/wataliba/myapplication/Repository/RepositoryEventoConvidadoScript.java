package com.example.wataliba.myapplication.Repository;

import android.content.Context;

import com.example.wataliba.myapplication.DataAccessEvento.DataEventHelper;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryEventoConvidadoScript extends RepositoryEventoConvidado {

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS evento_convidado";
    private static final String NOME_BANCO = "prenda_noivos";
    private static final int VERSAO_BANCO = 1;
    private static final String TABELA_EVENTO = "evento_convidado";
    private DataEventHelper dbEvento;

    public static final String SCRIPT_DATABASE_CREATE =
            new StringBuilder().
                    append("create table evento_convidado ( _id text primary key").
                    append(", nome text not null").
                    append(", presenca BOOLEAN").
                    append(", prenda_paga BOOLEAN").
                    append(", valor_pago real null ").
                    append(", evento_id integer not null").
                    append(", convidado_id text not null").
                    append(", FOREIGN KEY(evento_id) REFERENCES evento(_id)").
                    append(", FOREIGN KEY(convidado_id) REFERENCES convidado(_id));").toString();

    /*private static final String[] SCRIPT_DATABASE_CREATE = new String[]{
            new StringBuilder().
                    append("create table evento_convidado ( _id text primary key").
                    append(", nome text not null").
                    append(", FOREIGN KEY(evento_id) REFERENCES evento(_id)").
                    append(", FOREIGN KEY(convidado_id) REFERENCES convidado(_id)").
                    append(", presenca BOOLEAN default 0").
                    append(", prenda_paga BOOLEAN default 0").
                    append(", valor_pago real null );")
                    .toString()};*/

    public RepositoryEventoConvidadoScript(Context ctx) {
        // Criar utilizando um script SQL
        /*dbEvento = new DataEventHelper(ctx, RepositoryEventoConvidadoScript.NOME_BANCO, null, RepositoryEventoConvidadoScript.VERSAO_BANCO,
                RepositoryEventoConvidadoScript.SCRIPT_DATABASE_CREATE, RepositoryEventoConvidadoScript.SCRIPT_DATABASE_DELETE);*/

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
