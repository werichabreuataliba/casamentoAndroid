package com.example.wataliba.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.wataliba.myapplication.Entity.EntityConvidado;
import com.example.wataliba.myapplication.Entity.EntityEventoConvidado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryEventoConvidado{
    private static final String CATEGORIA = "Evento_Convidado";

    // Nome do banco
    private static final String NOME_BANCO = "prenda_noivos";
    // Nome da tabela
    public static final String NOME_TABELA = "evento_convidado";

    protected SQLiteDatabase db;

    public RepositoryEventoConvidado(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    protected RepositoryEventoConvidado() {
        // Apenas para criar uma subclasse...
    }

    /********************************************************************************/

    public long salvar(EntityEventoConvidado eventoConvidado) {
        long idEventoConvidado = eventoConvidado.get_id();

        if (idEventoConvidado != 0) {
            atualizar(eventoConvidado);
        } else {
            // Insere novo
            idEventoConvidado = inserir(eventoConvidado);
        }

        return idEventoConvidado;
    }

    // Insere um novo carro
    public long inserir(EntityEventoConvidado eventoConvidado) {
        ContentValues values = new ContentValues();
        values.put(EntityEventoConvidado.EventoConvidado.NOME, eventoConvidado.get_nome());
        values.put(EntityEventoConvidado.EventoConvidado._ID, eventoConvidado.get_id());
        values.put(EntityEventoConvidado.EventoConvidado.EVENTO_ID, eventoConvidado.get_evento().get_id());
        values.put(EntityEventoConvidado.EventoConvidado.PRENDA_PAGA, eventoConvidado.get_prenda_paga());
        values.put(EntityEventoConvidado.EventoConvidado.PRESENCA, eventoConvidado.get_presenca());
        values.put(EntityEventoConvidado.EventoConvidado.VALOR_PAGO, eventoConvidado.get_valor_pago());

        long id = inserir(values);
        return id;
    }

    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }

    public int atualizar(EntityEventoConvidado eventoConvidado) {
        ContentValues values = new ContentValues();
        values.put(EntityEventoConvidado.EventoConvidado.NOME, eventoConvidado.get_nome());
        values.put(EntityEventoConvidado.EventoConvidado._ID, eventoConvidado.get_id());
        values.put(EntityEventoConvidado.EventoConvidado.EVENTO_ID, eventoConvidado.get_evento().get_id());
        values.put(EntityEventoConvidado.EventoConvidado.PRENDA_PAGA, eventoConvidado.get_prenda_paga());
        values.put(EntityEventoConvidado.EventoConvidado.PRESENCA, eventoConvidado.get_presenca());
        values.put(EntityEventoConvidado.EventoConvidado.VALOR_PAGO, eventoConvidado.get_valor_pago());

        String _id = String.valueOf(eventoConvidado.get_id());

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

    public int deletar(int id) {
        String where = EntityEventoConvidado.EventoConvidado._ID + "=?";

        String _id = String.valueOf(id);
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
    public EntityEventoConvidado buscarEventoConvidados(int id) {
        EntityEventoConvidado entityEventoConvidado = null;

        try {
            // Idem a: SELECT _id,nome,placa,ano from CARRO where nome = ?
            Cursor c = db.query(NOME_TABELA, EntityConvidado.colunas
                    , EntityConvidado.Convidado.ID + "='" + id + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {
                entityEventoConvidado = new EntityEventoConvidado(c);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o convidado: " + e.toString());
            return null;
        }

        return entityEventoConvidado;
    }

    // Retorna um cursor com todos os convidados
    public Cursor getCursor() {
        try {
            // select * from carros
            return db.query(NOME_TABELA, EntityEventoConvidado.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os convidados: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os carros
    public List<EntityEventoConvidado> listarConvidados() {
        Cursor c = getCursor();

        List<EntityEventoConvidado> entityEventoConvidados = new ArrayList<EntityEventoConvidado>();

        if (c.moveToFirst()) {

            // Loop até o final
            do {
                entityEventoConvidados.add(new EntityEventoConvidado(c));
            } while (c.moveToNext());
        }

        return entityEventoConvidados;
    }

    // Busca um carro utilizando as configurações definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de carro
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

    /********************************************************************************/

    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }
}
