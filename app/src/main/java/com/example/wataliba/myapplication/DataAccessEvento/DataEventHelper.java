package com.example.wataliba.myapplication.DataAccessEvento;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataEventHelper extends SQLiteOpenHelper {

    private static final String CATEGORIA = "evetnos";
    private String[] scriptSQLCreate;
    private String scriptDelete;

    public DataEventHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    public DataEventHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                           String[] scriptSQLCreate, String scriptDelete) {
        super(context, name, null, version);

        this.scriptSQLCreate = scriptSQLCreate;
        this.scriptDelete = scriptDelete;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(CATEGORIA, "Criando banco com SQL");
        int qtdeScripts = scriptSQLCreate.length;
        for (int i = 0 ; i < qtdeScripts; i ++){
            String sql = scriptSQLCreate[i];
            Log.i(CATEGORIA, sql);
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(CATEGORIA, "Atualizando versão "+ oldVersion + " para " + newVersion +
        ". todos os registrod foram deletados");
        Log.i(CATEGORIA, scriptDelete);
        db.execSQL(scriptDelete);
        onCreate(db);
    }
}
