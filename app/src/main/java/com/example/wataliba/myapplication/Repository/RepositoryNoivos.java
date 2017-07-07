package com.example.wataliba.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.wataliba.myapplication.Entity.EntityNoivos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryNoivos {

    private static final String CATEGORIA = "Evento_Convidado";

    // Nome do banco
    private static final String NOME_BANCO = "prenda_noivos";
    // Nome da tabela
    public static final String NOME_TABELA = "noivos";

    protected SQLiteDatabase db;

    // Insere um novo carro
    public long inserir(EntityNoivos noivos) {
        ContentValues values = new ContentValues();
        values.put(EntityNoivos.Noivos.NOME, noivos.get_nome());
        values.put(EntityNoivos.Noivos.CONTATO, noivos.get_contato());
        values.put(EntityNoivos.Noivos.ID, noivos.get_id());

        long id = inserir(values);
        return id;
    }

    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }

    public int atualizar(EntityNoivos noivos) {
        ContentValues values = new ContentValues();
        values.put(EntityNoivos.Noivos.NOME, noivos.get_nome());
        values.put(EntityNoivos.Noivos.CONTATO, noivos.get_contato());
        values.put(EntityNoivos.Noivos.ID, noivos.get_id());

        String _id = noivos.get_id();

        String where = EntityNoivos.Noivos.ID + "=?";
        String[] whereArgs = new String[] { _id };

        int count = atualizar(values, where, whereArgs);

        return count;
    }

    // Atualiza o carro com os valores abaixo
    // A cláusula where é utilizada para identificar o carro a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }

    public int deletar(long id) {
        String where = EntityNoivos.Noivos.ID + "=?";

        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };

        int count = deletar(where, whereArgs);

        return count;
    }

    // Deleta o carro com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    public RepositoryNoivos(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    protected RepositoryNoivos() {
        // Apenas para criar uma subclasse...
    }

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }


    public EntityNoivos buscarNoivo(String identificacao) {
        EntityNoivos noivos = null;

        try {
            // Idem a: SELECT _id,nome,placa,ano from evento where nome = ?
            Cursor c = db.query(NOME_TABELA, EntityNoivos.colunas
                    , EntityNoivos.Noivos.ID + "='" + identificacao + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {
                noivos = new EntityNoivos(c);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o noivo: " + e.toString());
            return null;
        }

        return noivos;
    }

    public Cursor getCursor() {
        try {
            // select * from carros
            return db.query(NOME_TABELA, EntityNoivos.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os evetnos: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os carros
    public List<EntityNoivos> listarNoivos() {
        Cursor c = getCursor();

        List<EntityNoivos> noivos = new ArrayList<EntityNoivos>();

        if (c.moveToFirst()) {
            // Loop até o final
            do {
                noivos.add(new EntityNoivos(c));
            } while (c.moveToNext());
        }

        return noivos;
    }

    // Busca um carro utilizando as configurações definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de carro
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }
}
