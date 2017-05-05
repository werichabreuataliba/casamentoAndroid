package com.example.wataliba.myapplication.Entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wataliba on 30/03/2017.
 */
public class EntityNoivos {

    public static final String AUTHORITY = "br.livro.android.provider.noivo";
    private String _id;
    private String _nome;
    private String _contato;

    public String get_contato() {
        return _contato;
    }

    public void set_contato(String _contato) {
        this._contato = _contato;
    }

    public EntityNoivos(Cursor c)
    {
        this._id = c.getString(c.getColumnIndex("_id"));
        this._nome = c.getString(c.getColumnIndex("nome"));
        this._contato = c.getString(c.getColumnIndex("contato"));
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

    public EntityNoivos(String id, String nome, String contato){
        this._id = id;
        this._nome = nome;
        this._contato = contato;
    }
    public EntityNoivos(){}

    public static final class Noivos implements BaseColumns {

        // Não pode instanciar esta Classe
        private Noivos() {
        }

        // content://br.livro.android.provider.noivo/noivos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/noivos");

        // Mime Type para todos os noivos
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.noivos";

        // Mime Type para um único casal
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.noivos";

        // Ordenação default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String ID = "_id";
        public static final String CONTATO = "contato";

        // Método que constrói uma Uri para um Carro específico, com o seu id
        // A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /carros
            Uri uriNoivos = ContentUris.withAppendedId(Noivos.CONTENT_URI, id);
            return uriNoivos;
        }
    }

}
