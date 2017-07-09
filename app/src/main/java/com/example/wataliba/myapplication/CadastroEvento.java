package com.example.wataliba.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wataliba.myapplication.Entity.EntityConvidado;
import com.example.wataliba.myapplication.Entity.EntityEvento;
import com.example.wataliba.myapplication.Entity.EntityNoivos;
import com.example.wataliba.myapplication.Repository.RepositoryEvento;
import com.example.wataliba.myapplication.Repository.RepositoryNoivos;
import com.example.wataliba.myapplication.Repository.RepositoryTables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by wataliba on 24/04/2017.
 */
public class CadastroEvento extends Activity implements View.OnClickListener {

    List<EntityConvidado> convidados;
    EntityEvento evento;
    RepositoryEvento repositoryevento;
    final String message = "O campo %s deve ser informado";
    final String messageDataHorario = "O campo %s esta incorreto!";
    TextView txtNomeEvento;
    EditText txtData;
    EditText txtHora;
    TextView txtEndereco;
    TextView txtNoivos;
    EditText txtPrecoMax;
    EditText txtPrecoMin;
    Button btnConvidados;
    Button btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_evento);

        try {

            RepositoryTables tables = new RepositoryTables(this);
            tables.fechar();

            RepositoryNoivos n1 = new RepositoryNoivos(this);
            EntityNoivos n = n1.buscarNoivo("1");
            if((n == null)) {
                n1.inserir(new EntityNoivos("1", "dono deste smart Fone", "5555-5555"));
            }
        }
        catch (Exception exc){
            Toast.makeText(CadastroEvento.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

        convidados      = new ArrayList<EntityConvidado>();
        txtData         = (EditText) findViewById(R.id.txtData);
        txtNomeEvento   = (TextView) findViewById(R.id.txtNomeEvento);
        txtData         = (EditText) findViewById(R.id.txtData);
        txtHora         = (EditText) findViewById(R.id.txtHora);
        txtEndereco     = (TextView) findViewById(R.id.txtEndereco);
        txtNoivos       = (TextView) findViewById(R.id.txtNoivos);
        txtPrecoMax     = (EditText) findViewById(R.id.txtPrecoMax);
        txtPrecoMin     = (EditText) findViewById(R.id.txtPrecoMin);
        btnConvidados   = (Button)   findViewById(R.id.btnConvidados);
        btnConfirmar    = (Button)   findViewById(R.id.btconfirma);
        btnConfirmar.setOnClickListener(this);
        btnConvidados.setOnClickListener(this);
        txtData.addTextChangedListener(Mask.insert("##/##/####", txtData));
        txtHora.addTextChangedListener(Mask.insert("##:##", txtHora));
        txtPrecoMin.addTextChangedListener(Mask.insert("##.##", txtPrecoMin));
        txtPrecoMax.addTextChangedListener(Mask.insert("##.##", txtPrecoMax));
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
                try {

                    this.validaCampos();
                    this.salvarEvento();
                    Intent j = new Intent(v.getContext(), ListaEventosCriados.class);
                    startActivity(j);

                } catch (Exception e) {
                    Toast.makeText(CadastroEvento.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void salvarEvento() throws Exception
    {
        this.evento = new EntityEvento(""
                , this.txtNomeEvento.getText().toString()
                ,new EntityNoivos("1",this.txtNoivos.getText().toString(),"12345")
                ,""
                ,""
                ,true
                ,this.txtHora.getText().toString()
                ,this.txtData.getText().toString()
                ,Double.parseDouble(this.txtPrecoMin.getText().toString())
                ,Double.parseDouble(this.txtPrecoMax.getText().toString())
                ,this.txtEndereco.getText().toString()
                ,this.convidados);

        repositoryevento = new RepositoryEvento(this);
        if(0 == repositoryevento.salvar(this.evento)){ throw new Exception("Erro ao salavar o evento"); }

//        if(repositoryevento.listarEventos().size() > 0 )
            Toast.makeText(CadastroEvento.this, "Evento criado com sucesso, felicidades aos dois!", Toast.LENGTH_SHORT).show();
    }

    private void validaCampos() throws Exception {
        if(TextUtils.isEmpty(txtNomeEvento.getText())){ throw new Exception(String.format(message,"Nome do Evento")); }
        if(TextUtils.isEmpty(txtNoivos.getText()))    { throw new Exception(String.format(message,"Nome dos Noivos")); }
        if(TextUtils.isEmpty(txtEndereco.getText()))  { throw new Exception(String.format(message,"Endereço")); }
        if(TextUtils.isEmpty(txtPrecoMax.getText()))  { throw new Exception(String.format(message,"Preço Máximo")); }
        if(TextUtils.isEmpty(txtPrecoMin.getText()))  { throw new Exception(String.format(message,"Preço Mínimo")); }
        if(TextUtils.isEmpty(txtData.getText()))      { throw new Exception(String.format(message,"Data")); }
        if(TextUtils.isEmpty(txtHora.getText()))      { throw new Exception(String.format(message,"Hora")); }
        if(!(convidados.size() > 0))                  { throw new Exception("Você deve selecionar ao menos um convidado para a confimação do Evento"); }

        StringBuilder helper = new StringBuilder();
        if(!TextUtils.isEmpty(txtHora.getText()))
        {
            helper.append(txtHora.getText().subSequence(0,2));
            if(new Integer(helper.toString()) > 23 ){ throw new Exception(String.format(messageDataHorario,"Hora")); }

            helper = new StringBuilder();

            helper.append(txtHora.getText().subSequence(3,5));
            if(new Integer(helper.toString()) > 59){ throw new Exception(String.format(messageDataHorario,"Minutos")); }
        }

        if(!TextUtils.isEmpty(txtData.getText()))
        {
            int year = Integer.parseInt(txtData.getText().subSequence(6,10).toString());
            int month = Integer.parseInt(txtData.getText().subSequence(3,5).toString());
            int day = Integer.parseInt(txtData.getText().subSequence(0,2).toString());

            Calendar calendar = new GregorianCalendar();
            try {
                calendar.setLenient(false);
                calendar.set(year, month, day);
            }
            catch(Exception exc)
            {
                throw exc;
            }
        }
    }



}
