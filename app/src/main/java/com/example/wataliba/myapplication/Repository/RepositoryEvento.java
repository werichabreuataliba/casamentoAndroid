package com.example.wataliba.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.example.wataliba.myapplication.Entity.EntityEvento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 30/03/2017.
 */
public class RepositoryEvento {
    private static final String CATEGORIA = "Evento";

    // Nome do banco
    private static final String NOME_BANCO = "prenda_noivos";
    // Nome da tabela
    public static final String NOME_TABELA = "evento";

    protected SQLiteDatabase db;

    public RepositoryEvento(Context ctx) {
        // Abre o banco de dados já existente
        db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
    }

    protected RepositoryEvento() {
        // Apenas para criar uma subclasse...
    }

    public long salvar(EntityEvento evento) {
        String id = evento.get_id();

        if (id != "") {
            atualizar(evento);
        } else {
            // Insere novo
            return inserir(evento);
        }
        return  0;
    }

    // Insere um novo carro
    public long inserir(EntityEvento evento) {
        ContentValues values = new ContentValues();
        values.put(EntityEvento.Evento.NOME, evento.get_nome());
        values.put(EntityEvento.Evento.ENDERECO, evento.get_endereco());
       // values.put(EntityEvento.Evento.ID, evento.get_id());
        values.put(EntityEvento.Evento.LATITUDE_LONGITUDE, evento.get_latidude_longitude());
        values.put(EntityEvento.Evento.HORARIO_EVENTO, evento.get_hoaraio_evento());
        values.put(EntityEvento.Evento.DATA_EVENTO, evento.get_data_evento());
        values.put(EntityEvento.Evento.PRENDA_PRECO_MAX, evento.get_prenda_preco_max());
        values.put(EntityEvento.Evento.PRENDA_PRECO_MIN, evento.get_prenda_preco_min());
        values.put(EntityEvento.Evento.ATIVO, evento.is_ativo());
        values.put(EntityEvento.Evento.NOIVOS_ID, evento.get_noivos().get_id());

        long id = inserir(values);
        return id;
    }

    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }

    public int atualizar(EntityEvento evento) {
        ContentValues values = new ContentValues();
        values.put(EntityEvento.Evento.NOME, evento.get_nome());
        values.put(EntityEvento.Evento.ENDERECO, evento.get_endereco());
        // values.put(EntityEvento.Evento.ID, evento.get_id());
        values.put(EntityEvento.Evento.LATITUDE_LONGITUDE, evento.get_latidude_longitude());
        values.put(EntityEvento.Evento.HORARIO_EVENTO, evento.get_hoaraio_evento());
        values.put(EntityEvento.Evento.DATA_EVENTO, evento.get_data_evento());
        values.put(EntityEvento.Evento.PRENDA_PRECO_MAX, evento.get_prenda_preco_max());
        values.put(EntityEvento.Evento.PRENDA_PRECO_MIN, evento.get_prenda_preco_min());
        values.put(EntityEvento.Evento.ATIVO, evento.is_ativo());
        values.put(EntityEvento.Evento.NOIVOS_ID, evento.get_noivos().get_id());

        String _id = evento.get_id();
        String noivos_id = evento.get_noivos().get_id();

        String where = EntityEvento.Evento.ID + "=? and "+ EntityEvento.Evento.NOIVOS_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(_id), noivos_id };

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

    public int deletar(String idEvento, String idNoivos) {
        String where = EntityEvento.Evento.NOIVOS_ID + "=?" +
                " and " + EntityEvento.Evento._ID + "=?";

        String _id = idEvento;
        String _idNoivos = idNoivos;
        String[] whereArgs = new String[] { _id ,_idNoivos };

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
    public EntityEvento buscarEvento(String identificacao) {
        EntityEvento evento = null;

        try {
            // Idem a: SELECT _id,nome,placa,ano from evento where nome = ?
            Cursor c = db.query(NOME_TABELA, EntityEvento.colunas
                    , EntityEvento.Evento.ID + "='" + identificacao + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {
                evento = new EntityEvento(c);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o evento: " + e.toString());
            return null;
        }

        return evento;
    }

    // Retorna um cursor com todos os convidados
    public Cursor getCursor() {
        try {
            // select * from carros
            return db.query(NOME_TABELA, EntityEvento.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os evetnos: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os carros
    public List<EntityEvento> listarEventos() {
        Cursor c = getCursor();

        List<EntityEvento> eventos = new ArrayList<EntityEvento>();

        if (c.moveToFirst()) {
            // Loop até o final
            do {
                eventos.add(new EntityEvento(c));
            } while (c.moveToNext());
        }

        return eventos;
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
