package com.kelique.chatlatian.uripuruppo.databis;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.endpoin.FirebaseClient;
import com.kelique.chatlatian.uripuruppo.helper.MyFunction;
import com.kelique.chatlatian.uripuruppo.modeling.Pedagang;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransaksiPedagangActivity extends MyFunction {


    //TODO: masuk ke Firebase project ChatApp dengan USER: rrluck2017@gmail.com password: gadisa08


    @BindView(R.id.listview)
    ListView mListview;
    ArrayList<Pedagang> bakulArrayList = new ArrayList<>();
    FirebaseClient firebaseClient;
    @BindView(R.id.progressBar2)
    ProgressBar mProgressBar2;
    @BindView(R.id.buttonTambah)
    FloatingActionButton mButtonTambah;
    @BindView(R.id.buttonUrut)
    FloatingActionButton mButtonUrut;
    @BindView(R.id.buttonCari)
    FloatingActionButton mButtonCari;
    @BindView(R.id.multiple_actions_down)
    FloatingActionsMenu mMultipleActionsDown;


    private String DB_URL = "https://chat-app-64353.firebaseio.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpa_databis_pedagang);
        ButterKnife.bind(this);


        setProgressBarIndeterminate(true);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListview = (ListView) findViewById(R.id.listview);

        firebaseClient = new FirebaseClient(this, DB_URL, mListview);
        firebaseClient.refreshdata();

        setProgressBarIndeterminate(false);


        //bakulArrayList = firebaseClient.dataArrayList();

        //TODO: # 9 Listview dan data tidak mau muncul, bantu cari salahnya.
        //TODO: pada setOnClickListener dibawah ini sebaiknya mengunakan Custome Alert saja untuk meyakinkan user dalam
        //melakukan transaksi dan hanya berfikus pada pengurangan periode pembayaran saja lalu
        // dilempar ke aktivity webView atao ActionView

        mListview.setOnItemClickListener(new OnItemClickListener() {
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

    @OnClick({R.id.buttonTambah, R.id.buttonUrut, R.id.buttonCari})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonTambah:
                break;
            case R.id.buttonUrut:
                break;
            case R.id.buttonCari:
                break;
        }
    }


//    private void showFABMenu(){
//        isFABOpen=true;
//        fabLayout2.setVisibility(View.VISIBLE);
//        fabLayout3.setVisibility(View.VISIBLE);
//        fabBGLayout.setVisibility(View.VISIBLE);
//
//        fab.animate().rotationBy(180);
//
//        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
//        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
//    }
//
//
//    private void closeFABMenu(){
//        isFABOpen=false;
//        fabBGLayout.setVisibility(View.GONE);
//        fab.animate().rotationBy(-180);
//        fabLayout2.animate().translationY(0);
//        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                if(!isFABOpen){
//                    fabLayout2.setVisibility(View.GONE);
//                    fabLayout3.setVisibility(View.GONE);
//                }
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (isFABOpen) {
//            closeFABMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }


}
