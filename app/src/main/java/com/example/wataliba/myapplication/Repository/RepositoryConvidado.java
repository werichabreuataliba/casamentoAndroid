package com.example.wataliba.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.wataliba.myapplication.Entity.EntityConvidado;
import com.example.wataliba.myapplication.Entity.EntityNoivos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryConvidado {

    private static final String CATEGORIA = "Evento_Convidado";

    // Nome do banco
    private static final String NOME_BANCO = "prenda_noivos";
    // Nome da tabela
    public static final String NOME_TABELA = "convidado";

    protected SQLiteDatabase db;

    public RepositoryConvidado(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    protected RepositoryConvidado() {
        // Apenas para criar uma subclasse...
    }

    public String salvar(EntityConvidado convidado) {
        String id = convidado.get_id();

        if (id != "") {
            atualizar(convidado);
        } else {
            // Insere novo
            id = String.valueOf((inserir(convidado)));
        }

        return id;
    }

    // Insere um novo carro
    public long inserir(EntityConvidado convidado) {
        ContentValues values = new ContentValues();
        values.put(EntityConvidado.Convidado.NOME, convidado.get_nome());
        values.put(EntityConvidado.Convidado.CONTATO, convidado.get_contato());
        values.put(EntityConvidado.Convidado.ID, convidado.get_id());

        long id = inserir(values);
        return id;
    }

    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }

    public int atualizar(EntityConvidado convidado) {
        ContentValues values = new ContentValues();
        values.put(EntityConvidado.Convidado.NOME, convidado.get_nome());
        values.put(EntityConvidado.Convidado.CONTATO, convidado.get_contato());
        values.put(EntityConvidado.Convidado.ID, convidado.get_id());

        String _id = convidado.get_id();

        String where = EntityConvidado.Convidado.ID + "=?";
        String[] whereArgs = new String[] { _id };

        int count = atualizar(values, where, whereArgs);

        return count;
    }

    // Atualiza o convidado com os valores abaixo
    // A cláusula where é utilizada para identificar o convidado a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }

    public int deletar(String id) {
        String where = EntityNoivos.Noivos.ID + "=?";

        String _id = id;
        String[] whereArgs = new String[] { _id };

        int count = deletar(where, whereArgs);

        return count;
    }

    // Deleta o convidado com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    // Busca o carro pelo nome "select * from convidado where nome=?"
    public EntityConvidado buscarconvidado(String identificacao) {
        EntityConvidado convidado = null;

        try {
            // Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
            Cursor c = db.query(NOME_TABELA, EntityConvidado.colunas
                    , EntityConvidado.Convidado.ID + "='" + identificacao + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {
                convidado = new EntityConvidado(c);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o convidado: " + e.toString());
            return null;
        }

        return convidado;
    }

    // Retorna um cursor com todos os convidados
    public Cursor getCursor() {
        try {
            // select * from carros
            return db.query(NOME_TABELA, EntityConvidado.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os convidados: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os carros
    public List<EntityConvidado> listarConvidados() {
        Cursor c = getCursor();

        List<EntityConvidado> convidados = new ArrayList<EntityConvidado>();

        if (c.moveToFirst()) {

            // Loop até o final
            do {
                convidados.add(new EntityConvidado(c));
            } while (c.moveToNext());
        }

        return convidados;
    }

    // Busca um carro utilizando as configurações definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de carro
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }
}
