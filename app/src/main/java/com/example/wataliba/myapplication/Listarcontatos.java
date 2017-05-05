package com.example.wataliba.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wataliba.myapplication.Entity.EntityConvidado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wataliba on 03/04/2017.
 */
public class Listarcontatos extends Activity implements View.OnClickListener{
    private static final String TAG = Listarcontatos.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;     // contacts unique ID
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private List<EntityConvidado> convidados;
    private List<EntityConvidado> convidadosSelecionados;
    private List<CheckBox> seleoes;

    int permissionCheck = 0;
    /**
     * Called when the activity is first created.
     */
    //  @Override
    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissionToReadUserContacts();
    }*/
    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    // Called when the user is performing an action which requires the app to read the
    // user's contacts
    @TargetApi(Build.VERSION_CODES.M)
    public void getPermissionToReadUserContacts() {
        // 1) Use the support library version ContextCompat.checkSelfPermission(...) to avoid
        // checking the build version since Context.checkSelfPermission(...) is only available
        // in Marshmallow
        // 2) Always check for permission (even if permission has already been granted)
        // since the user can revoke permissions at any time through Settings
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // The permission is NOT already granted.
            // Check if the user has been asked about this permission already and denied
            // it. If so, we want to give more explanation about why the permission is needed.
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                // Show our own UI to explain to the user why we need to read the contacts
                // before actually requesting the permission and showing the default UI
            }

            // Fire off an async request to actually get the permission
            // This will show the standard permission request dialog UI
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSIONS_REQUEST);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // showRationale = false if user clicks Never Ask Again, otherwise true
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);

                if (showRationale) {
                    // do something here to handle degraded mode
                } else {
                    Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onClickSelectContact(View btnSelectContact) {

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK
                , ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }

    ListView lvCallList;
    ProgressDialog pd;
    ArrayList<String> aa = new ArrayList<String>();
    ArrayList<String> num = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissionToReadUserContacts();

        Button desfazer = (Button) findViewById(R.id.button2);
        Button cancelar = (Button) findViewById(R.id.button3);
        Button confirmar = (Button) findViewById(R.id.button);

        convidados = new ArrayList<EntityConvidado>();
        convidadosSelecionados = new ArrayList<EntityConvidado>();
        seleoes = new ArrayList<CheckBox>();

        lvCallList = (ListView) this.findViewById(R.id.list);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                pd = ProgressDialog.show(Listarcontatos.this,
                        "Loading..", "Please Wait", true, false);
            }// End of onPreExecute method

            @Override
            protected Void doInBackground(Void... params) {
                getContacts();

                return null;
            }// End of doInBackground method

            @Override
            protected void onPostExecute(Void result) {
                pd.dismiss();
                CustomAdapter cus = new CustomAdapter(Listarcontatos.this);
                // ArrayAdapter<String>   arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,aa);
                lvCallList.setAdapter(cus);
            }//End of onPostExecute method
        }.execute((Void[]) null);

        desfazer.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        confirmar.setOnClickListener(this);
    }

    private void getContacts() {
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                aa.add(name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    while (pCur.moveToNext()) {
                        String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        num.add(phoneNumber);
                    }
                    pCur.close();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button2:
                for(int i =0; i < lvCallList.getChildCount(); i++) {
                    ViewHolder viewholder = (ViewHolder) lvCallList.getChildAt(i).getTag();
                    viewholder.checkbox.setChecked(false);
                }
                convidadosSelecionados.clear();
                break;
            case R.id.button3:
                Intent i = getIntent();
                setResult(Activity.RESULT_CANCELED, i);
                finish();
                break;
            default:
                for(int j = 0; j < lvCallList.getCount(); j ++) {
                    ViewHolder viewholder = (ViewHolder) lvCallList.getChildAt(j).getTag();
                    if(viewholder.checkbox.isChecked())
                    {
                        convidadosSelecionados.add((EntityConvidado)  lvCallList.getItemAtPosition(j));
                    }
                }

                if(! (convidadosSelecionados.size() > 0) ) {
                    Toast.makeText(Listarcontatos.this, "Você deve selecionar ao menos um convidado para o a confimação do Evento", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent j = getIntent();
                j.putExtra("CONVIDADOS", (Serializable) convidadosSelecionados);
                setResult(Activity.RESULT_OK, j);
                finish();
                break;
        }
    }

    public class CustomAdapter extends BaseAdapter {
        /*
         * Variables Declaration section
         */
        private Context mContext;

        public CustomAdapter(Context context) {
            mContext = context;
        }//End of CustomAdapter constructor

        public int getCount() {
            return aa.size();
        }//End of getCount method

        @Override
        public EntityConvidado getItem(int position) {
            return convidados.get(position);

        }//End of getItem method

        public long getItemId(int position) {
            return position;
        }//End of getItemId method

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final int pos = position;

            if (convertView == null) {
                holder = new ViewHolder();

                convertView = LayoutInflater.from(mContext).inflate(R.layout.display_contact, null);
                holder.tableLayoutView = (TableLayout) convertView.findViewById(R.id.tableLayout1);
                holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.textviewName = (TextView) convertView.findViewById(R.id.textView1);
                holder.textviewNumber = (TextView) convertView.findViewById(R.id.textView2);

                convertView.setTag(holder);
            }//End of if condition
            else {
                holder = (ViewHolder) convertView.getTag();
            }//End of else

            holder.checkbox.setId(position);
            holder.textviewName.setId(position);
            holder.textviewNumber.setId(position);


            EntityConvidado convidado = new EntityConvidado();
            convidado.set_contato(num.get(position));
            convidado.set_nome(aa.get(position));
            convidados.add(convidado);

            holder.textviewName.setText(aa.get(position));
            holder.textviewNumber.setText(" No. " + num.get(position));

            holder.id = position;

            return convertView;
        }//End of getView method
    }//End of CustomAdapter instance inner class

    static class ViewHolder {
        TableLayout tableLayoutView;
        TextView textviewName;
        TextView textviewNumber;
        CheckBox checkbox;
        int id;
    }
}