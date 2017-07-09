package com.example.wataliba.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by wataliba on 07/07/2017.
 */
public class MenuInicial extends Activity implements View.OnClickListener{

    Button btnCadastrar;
    Button btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_inicial);
        this.btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        this.btnListar =(Button)findViewById(R.id.btnListar);
        this.btnCadastrar.setOnClickListener(this);
        this.btnListar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId())
        {
            case R.id.btnCadastrar:
                i = new Intent(this,CadastroEvento.class);
                break;

            case R.id.btnListar:
                i = new Intent(this,ListaEventosCriados.class);
                break;
        }
        startActivity(i);
    }
}
