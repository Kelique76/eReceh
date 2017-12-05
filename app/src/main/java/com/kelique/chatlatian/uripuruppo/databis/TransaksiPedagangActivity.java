package com.kelique.chatlatian.uripuruppo.databis;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.aktifitas.TambahAktifity;
import com.kelique.chatlatian.uripuruppo.endpoin.FirebaseClient;
import com.kelique.chatlatian.uripuruppo.jangkar.MyFunction;
import com.kelique.chatlatian.uripuruppo.modeling.Pedagang;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransaksiPedagangActivity extends MyFunction {


    EditText nameEditext, urlEditext, infoEditext;
    Button btnsave, btncancel;

    @BindView(R.id.listview)
    ListView mListview;
    ArrayList<Pedagang> bakulArrayList = new ArrayList<>();
    FirebaseClient firebaseClient;
    private String DB_URL = "https://chat-app-64353.firebaseio.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpa_databis_pedagang);
        ButterKnife.bind(this);
//          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                myIntent(TambahAktifity.class);
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseClient = new FirebaseClient(this, DB_URL, mListview);
        firebaseClient.refreshdata();
        bakulArrayList = firebaseClient.dataArrayList();

        //TODO: # 9 Listview dan data tidak mau muncul, bantu cari salahnya.

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final Dialog updateDialog = new Dialog(TransaksiPedagangActivity.this, R.style.AppTheme_NoActionBar);
//                updateDialog.setContentView(R.layout.updatehewan);
//                updateDialog.setTitle("Data Hewan");
//                updateDialog.show();
//
//                final EditText edtID = (EditText) updateDialog.findViewById(R.id.edtidhewan);
//                final EditText edtNama = (EditText) updateDialog.findViewById(R.id.edtnamahewan);
//                final EditText edtGambar = (EditText) updateDialog.findViewById(R.id.edtgambar);
//                final EditText edtKetrangan = (EditText) updateDialog.findViewById(R.id.edtketerangan);
//                Button btnUpdate = (Button) updateDialog.findViewById(R.id.btnupdate);
//                Button btnDelete = (Button) updateDialog.findViewById(R.id.btndelete);
//
//                //ArrayList<Hewan> hewanArrayList = new ArrayList<>();
//
//                key = hewanArrayList.get(i).getKey();
//                info = hewanArrayList.get(i).getInfo();
//                name = hewanArrayList.get(i).getName();
//                url = hewanArrayList.get(i).getUrl();
//
//                edtGambar.setText(url);
//                edtID.setText(key);
//                edtKetrangan.setText(info);
//                edtNama.setText(name);
//
//                edtID.setEnabled(false);
//
//                //button
//                btnDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        firebaseClient.delete(key);
//                        firebaseClient.refreshdata();
//                        updateDialog.dismiss();
//                    }
//                });
//                btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //     Toast.makeText(FirebaseDatabaseActivity.this, key, Toast.LENGTH_SHORT).show();
//                        key = edtID.getText().toString();
//                        name = edtNama.getText().toString();
//                        info = edtKetrangan.getText().toString();
//                        url = edtGambar.getText().toString();
//                        firebaseClient.update(key, name,info, url);
//                        firebaseClient.refreshdata();
//                        updateDialog.dismiss();
//                    }
//                });

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseClient = new FirebaseClient(this, DB_URL, mListview);
        firebaseClient.refreshdata();
    }


}
