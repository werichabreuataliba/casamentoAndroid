package com.example.wataliba.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wataliba.myapplication.Entity.EntityNoivos;

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
}
