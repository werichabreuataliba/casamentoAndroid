package com.example.wataliba.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    int[] ids = {R.drawable.garavata
            , R.drawable.parte_2
            , R.drawable.parte_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ImageView[] gravata = new ImageView[3];

        int[] ids = new int[3];
        ids[0] = R.drawable.garavata;
        ids[1] = R.drawable.parte_2;
        ids[2] = R.drawable.parte_1;

        final String[] valores = new String[3];
        valores[0] = "R$ 50,00";
        valores[1] = "R$ 30,00";
        valores[2] = "R$ 20,00";*/
/*
        ListView lista = (ListView) this.findViewById(R.id.lstImagens);
        lista.setAdapter(new ImageAdapter());*/

        /*
         gravata[0] = (ImageView) this.findViewById(R.id.imgVieGravata);
         gravata[1] = (ImageView) this.findViewById(R.id.imgParte_2);
         gravata[2] = (ImageView) this.findViewById(R.id.imgParte_1);

        if (gravata != null) {

            for (int i = 0; i < 3; i++) {
                gravata[i].setImageResource(ids[i]);
                final String valor = valores[i];
                gravata[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                        alertDialog.setCancelable(true);
                        alertDialog.setMessage("Deseja doar "+ valor +" ao noivo ?");
                        alertDialog.setTitle("Gravata do noivo");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Valor de "+ valor +" enviado ao baú do noivo", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Valor não enviado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.create();
                        alertDialog.show();
                    }
                });

            }
        }
        }*/
    }
/*
    class ImageAdapter extends BaseAdapter {
        final String[] valores = {"R$ 50,00", "R$ 30,00", "R$ 20,00"};

        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlistview, null);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
            image.setImageResource(ids[position]);
            return null;
        }
    }*/


}