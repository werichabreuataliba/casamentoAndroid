package com.example.wataliba.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wataliba.myapplication.Adapter.GravataAdapter;
import com.example.wataliba.myapplication.Entity.EntityImagemGravata;
import com.example.wataliba.myapplication.Entity.EntityValor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 28/03/2017.
 */
public class TesteGravat extends Activity{

    int[] ids = {R.drawable.garavata
            , R.drawable.parte_2
            , R.drawable.parte_1};
    ProgressDialog p;

    String[] valores = {"R$ 50,00", "R$ 30,00", "R$ 20,00"};
    String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testegravata);
        ListView lista = (ListView) this.findViewById(R.id.lstIamges);
        //lista.setAdapter(new ImageAdapter());

        List<EntityImagemGravata> gravatas = new ArrayList<EntityImagemGravata>();
        gravatas.add(new EntityImagemGravata(R.drawable.garavata));
        gravatas.add(new EntityImagemGravata(R.drawable.parte_2));
        gravatas.add(new EntityImagemGravata(R.drawable.parte_1));

        List<EntityValor> valores = new ArrayList<EntityValor>();
        valores.add(new EntityValor(50.99));
        valores.add(new EntityValor(30.99));
        valores.add(new EntityValor(20.99));

        lista.setAdapter(new GravataAdapter(this, valores, gravatas));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t = (TextView) view.findViewById(R.id.txtValores);
                valor = t.getText().toString();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(TesteGravat.this);
                alertDialog.setCancelable(true);
                alertDialog.setMessage("Deseja doar "+ valor +" ao noivo ?");
                alertDialog.setTitle("Gravata do noivo");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        p = new ProgressDialog(TesteGravat.this);
                        p.setMessage("Enviando " + valor + " a carteira do noivo aguarde...");
                        //p.show();

                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected void onPreExecute() {
                                p.show();
                            }

                            @Override
                            protected Void doInBackground(Void... params) {
                                try {
                                    Looper.prepare();
                                    Toast.makeText(TesteGravat.this, "doInBackground", Toast.LENGTH_SHORT).show();
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                p.dismiss();
                                Toast.makeText(TesteGravat.this, "Valor de " + valor + " enviado a carteira do noivo!", Toast.LENGTH_SHORT).show();
                            }
                        }.execute();
                    }

                });
                alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TesteGravat.this, "Valor não enviado!", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });
    }

    class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object getItem(int position) {
            return (TextView)  findViewById(R.id.txtValores);
        }

        @Override
        public long getItemId(int position) {
             return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.customlistview, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
            TextView texto = (TextView) convertView.findViewById(R.id.txtValores);
            texto.setText(valores[position]);
            image.setImageResource(ids[position]);
            return convertView;
        }
    }
}
