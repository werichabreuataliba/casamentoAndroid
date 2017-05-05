package com.example.wataliba.myapplication.Entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by wataliba on 30/03/2017.
 */
public class EntityEvento {

    private String _id;
    private String        _nome;
    private EntityNoivos _noivos;
    private String  _convite;
    private String  _latidude_longitude;
    private boolean _ativo;
    private String  _hoaraio_evento;
    private String  _data_evento;
    private double  _prenda_preco_min;
    private double  _prenda_preco_max;
    private String _endereco;
    private List<EntityConvidado> convidados;

    public static String[] colunas = new String[] { Evento._ID, Evento.NOME
            , Evento.ATIVO ,Evento.CONVITE, Evento.ENDERECO, Evento.HORARIO_EVENTO
            , Evento.LATITUDE_LONGITUDE, Evento.NOIVOS_ID, Evento.NOME
            , Evento.DATA_EVENTO, Evento.PRENDA_PRECO_MAX, Evento.PRENDA_PRECO_MIN };
    public static final String AUTHORITY = "br.livro.android.provider.evento";

    public EntityEvento(String _id, String _nome, EntityNoivos _noivos
            , String _convite, String _latidude_longitude, boolean _ativo
            , String _hoaraio_evento, String _data_evento, double _prenda_preco_min
            , double _prenda_preco_max, String _endereco, List<EntityConvidado> convidados) {
        this._id = _id;
        this._nome = _nome;
        this._noivos = _noivos;
        this._convite = _convite;
        this._latidude_longitude = _latidude_longitude;
        this._ativo = _ativo;
        this._hoaraio_evento = _hoaraio_evento;
        this._data_evento = _data_evento;
        this._prenda_preco_min = _prenda_preco_min;
        this._prenda_preco_max = _prenda_preco_max;
        this._endereco = _endereco;
        this.convidados = convidados;
    }

    public EntityEvento(Cursor c)
    {
        this._id = c.getString(c.getColumnIndex("_id"));
        this._nome = c.getString(c.getColumnIndex("nome"));

        this._noivos = new EntityNoivos();
        _noivos.set_id(c.getString(c.getColumnIndex("noivos_id")));

        this._convite = c.getString(c.getColumnIndex("convite"));
        this._latidude_longitude = c.getString(c.getColumnIndex("latidude_longitude"));
        this._ativo = c.getInt(c.getColumnIndex("ativo")) == 1 ? true : false ;
        this._hoaraio_evento = c.getString(c.getColumnIndex("hoarario_evento"));
        this._data_evento = c.getString(c.getColumnIndex("data_evento"));
        this._prenda_preco_min= c.getDouble(c.getColumnIndex("prenda_preco_min"));
        this._prenda_preco_max = c.getDouble(c.getColumnIndex("prenda_preco_max"));
        this._endereco = c.getString(c.getColumnIndex("endereco"));
    }

    public EntityNoivos get_noivos() {
        return _noivos;
    }

    public void set_noivos(EntityNoivos _noivos) {
        this._noivos = _noivos;
    }

    public String get_convite() {
        return _convite;
    }

    public void set_convite(String _convite) {
        this._convite = _convite;
    }

    public String get_latidude_longitude() {
        return _latidude_longitude;
    }

    public void set_latidude_longitude(String _latidude_longitude) {
        this._latidude_longitude = _latidude_longitude;
    }

    public boolean is_ativo() {
        return _ativo;
    }

    public void set_ativo(boolean _ativo) {
        this._ativo = _ativo;
    }

    public String get_hoaraio_evento() {
        return _hoaraio_evento;
    }

    public void set_hoaraio_evento(String _hoaraio_evento) {
        this._hoaraio_evento = _hoaraio_evento;
    }

    public String get_data_evento() {
        return _data_evento;
    }

    public void set_data_evento(String _data_evento) {
        this._data_evento = _data_evento;
    }

    public double get_prenda_preco_min() {
        return _prenda_preco_min;
    }

    public void set_prenda_preco_min(double _prenda_preco_min) {
        this._prenda_preco_min = _prenda_preco_min;
    }

    public double get_prenda_preco_max() {
        return _prenda_preco_max;
    }

    public void set_prenda_preco_max(double _prenda_preco_max) {
        this._prenda_preco_max = _prenda_preco_max;
    }

    public String get_endereco() {
        return _endereco;
    }

    public void set_endereco(String _endereco) {
        this._endereco = _endereco;
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

    public EntityEvento(){}

    public static final class Evento implements BaseColumns {

        // Não pode instanciar esta Classe
        private Evento() {
        }

        // content://br.livro.android.provider.noivo/noivos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/eventos");

        // Mime Type para todos os noivos
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.eventos";

        // Mime Type para um único casal
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.evento";

        // Ordenação default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String ID = "_id";
        public static final String NOIVOS_ID = "noivos_id";

        public static final String CONVITE = "convite";
        public static final String LATITUDE_LONGITUDE = "latidude_longitude";
        public static final String ATIVO = "ativo";
        public static final String HORARIO_EVENTO = "hoarario_evento";
        public static final String DATA_EVENTO = "data_evento";
        public static final String PRENDA_PRECO_MIN = "prenda_preco_min";
        public static final String PRENDA_PRECO_MAX = "prenda_preco_max";
        public static final String ENDERECO = "endereco";

        // Método que constrói uma Uri para um Carro específico, com o seu id
        // A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /carros
            Uri uriEvento = ContentUris.withAppendedId(Evento.CONTENT_URI, id);
            return uriEvento;
        }
    }
}
