package com.example.wataliba.myapplication.Entity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wataliba on 30/03/2017.
 */
public class EntityEventoConvidado {

    private EntityConvidado _convidado;
    private Boolean _presenca;
    private EntityEvento _evento;
    private Boolean _prenda_paga;
    private double _valor_pago;
    private int _id;
    private String _nome;

    public String get_nome() {
        return _nome;
    }

    public void set_nome(String _nome) {
        this._nome = _nome;
    }

    public static String[] colunas = new String[] { EventoConvidado.NOME
            ,EventoConvidado.ID
            ,EventoConvidado.EVENTO_ID
            ,EventoConvidado.PRESENCA
            ,EventoConvidado.PRENDA_PAGA
            ,EventoConvidado.VALOR_PAGO  };

            public static final String AUTHORITY = "br.livro.android.provider.eventoConvidado";

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public EntityConvidado get_convidado() {
        return _convidado;
    }

    public void set_convidado(EntityConvidado _convidado) {
        this._convidado = _convidado;
    }

    public Boolean get_presenca() {
        return _presenca;
    }

    public void set_presenca(Boolean _presenca) {
        this._presenca = _presenca;
    }

    public EntityEvento get_evento() {
        return _evento;
    }

    public void set_evento(EntityEvento _evento) {
        this._evento = _evento;
    }

    public Boolean get_prenda_paga() {
        return _prenda_paga;
    }

    public void set_prenda_paga(Boolean _prenda_paga) {
        this._prenda_paga = _prenda_paga;
    }

    public double get_valor_pago() {
        return _valor_pago;
    }

    public void set_valor_pago(double _valor_pago) {
        this._valor_pago = _valor_pago;
    }

    public EntityEventoConvidado() {

    }

    public EntityEventoConvidado(String _nome,int _id,double _valor_pago, Boolean _prenda_paga, EntityEvento _evento, Boolean _presenca, EntityConvidado _convidado) {

        this._valor_pago = _valor_pago;
        this._prenda_paga = _prenda_paga;
        this._evento = _evento;
        this._presenca = _presenca;
        this._convidado = _convidado;
        this._id = _id;
        this._nome = _nome;
    }

    public EntityEventoConvidado(Cursor c) {

        this._id = c.getInt(c.getColumnIndex("_id"));
        this._valor_pago = c.getDouble(c.getColumnIndex("valor_pago"));
        this._prenda_paga = c.getInt(c.getColumnIndex("prenda_paga")) == 1 ? true : false;

        this._evento = new EntityEvento();
        this._evento.set_id(c.getString(c.getColumnIndex("evento_id")));

        this._presenca = c.getInt(c.getColumnIndex("presenca")) == 1 ? true : false;

        this._convidado = new EntityConvidado();
        this._convidado.set_id(c.getString(c.getColumnIndex("convidado_id")));

        this._nome = c.getString(c.getColumnIndex("nome"));
    }

    public static final class EventoConvidado implements BaseColumns {

        // Não pode instanciar esta Classe
        private EventoConvidado() {
        }

        // content://br.livro.android.provider.noivo/noivos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/eventoConvidados");

        // Mime Type para todos os noivos
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.eventoConvidados";

        // Mime Type para um único casal
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.eventoConvidado";

        // Ordenação default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String ID = "_id";
        public static final String EVENTO_ID = "evento_id";
        public static final String PRESENCA = "presenca";
        public static final String PRENDA_PAGA = "prenda_paga";
        public static final String VALOR_PAGO = "valor_´pago";


        // Método que constrói uma Uri para um Carro específico, com o seu id
        // A Uri é no formato "content://br.livro.android.provider.carro/carros/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /carros
            Uri uriEventoConvidado = ContentUris.withAppendedId(EventoConvidado.CONTENT_URI, id);
            return uriEventoConvidado;
        }
    }

}
