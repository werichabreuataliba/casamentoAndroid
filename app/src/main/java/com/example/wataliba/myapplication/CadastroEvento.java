package com.example.wataliba.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wataliba.myapplication.Entity.EntityConvidado;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 24/04/2017.
 */
public class CadastroEvento extends Activity implements View.OnClickListener {

    List<EntityConvidado> convidados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_evento);
        Button btnConvidados = (Button) findViewById(R.id.btnConvidados);
        Button btnConfirmar = (Button) findViewById(R.id.btconfirma);
        convidados = new ArrayList<EntityConvidado>();
        btnConfirmar.setOnClickListener(this);
        btnConvidados.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            switch (resultCode) {
                case (Activity.RESULT_OK):
                    convidados = (List<EntityConvidado>) data.getSerializableExtra("CONVIDADOS");
                    break;
                case (Activity.RESULT_CANCELED):
                    convidados = new ArrayList<EntityConvidado>();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.btnConvidados):
                Intent i = new Intent(v.getContext(), Listarcontatos.class);
                startActivityForResult(i, 1);
                break;
            default:
                if(!(convidados.size() > 0))
                {
                    Toast.makeText(CadastroEvento.this
                            ,"Você deve selecionar ao menos um convidado para o a confimação do Evento"
                            , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
