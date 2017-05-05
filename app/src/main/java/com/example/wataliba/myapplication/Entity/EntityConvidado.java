package com.example.wataliba.myapplication.Entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by wataliba on 30/03/2017.
 */

public class EntityConvidado implements Serializable{

    public static final String AUTHORITY = "br.livro.android.provider.convidado";
    private String _id;
    private String _nome;
    private String _contato;
    public static String[] colunas = new String[] { Convidado._ID, Convidado.NOME, Convidado.CONTATO  };

    public String get_contato() {
        return _contato;
    }

    public void set_contato(String _contato) {
        this._contato = _contato;
    }

    public EntityConvidado() {
    }

    public EntityConvidado(Cursor c)
    {
        this._id = c.getString(c.getColumnIndex("_id"));
        this._nome = c.getString(c.getColumnIndex("nome"));
        this._contato = c.getString(c.getColumnIndex("contato"));
    }
    public EntityConvidado(String _id, String _nome, String contato) {

        this._id = _id;
        this._nome = _nome;
        this._contato = contato;
    }

    public String get_id() {

        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_nome() {
        return _nome;
    }

    public void set_nome(String _nome) {
        this._nome = _nome;
    }

    public static final class Convidado implements BaseColumns {

        // Não pode instanciar esta Classe
        private Convidado() {
        }

        // content://br.livro.android.provider.noivo/noivos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/convidados");

        // Mime Type para todos os noivos
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.convidados";

        // Mime Type para um único casal
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.voivo";

        // Ordenação default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String ID = "_id";
        public static final String CONTATO = "contato";

        // Método que constrói uma Uri para um Carro específico, com o seu id
        // A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /carros
            Uri uriConvidado = ContentUris.withAppendedId(Convidado.CONTENT_URI, id);
            return uriConvidado;
        }
    }
}
