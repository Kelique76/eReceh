package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.endpoin.JalurNarikAnggota;
import com.kelique.chatlatian.uripuruppo.modeling.Anggota;
import com.kelique.chatlatian.uripuruppo.modeling.Anggota2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.value;


public class TransaksiActivity extends AppCompatActivity {


    DatabaseReference dRef;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ButterKnife.bind(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        dRef = FirebaseDatabase.getInstance().getReference("Peserta Pedagang");

        myRef.setValue("hello There!");

        caraNarikNama();

    }

    private void caraNarikNama() {
        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("proses", "Datanya adalah: " + value);

                ArrayList<Anggota2> data = new ArrayList<Anggota2>();
                for (DataSnapshot mmm : dataSnapshot.getChildren()) {
                    Anggota angg = mmm.getValue(Anggota.class);
                    Anggota2 angg2 = new Anggota2();

                    angg2.setKeyanggota(mmm.getKey());
                    angg2.setNamaanggota(angg.nama_lengkap);
                    angg2.setLamatanggota(angg.alamat);
                    angg2.setKtpanggota(angg.no_ktp);
                    angg2.setHapeanggota(angg.no_hp);
                    angg2.setModalanggota(angg.permodalan);
                    angg2.setHasilanggota(angg.bagi_hasil);

                    data.add(angg2);


                    JalurNarikAnggota adapter = new JalurNarikAnggota(TransaksiActivity.this, data, mRecycler);
                    LinearLayoutManager lin = new LinearLayoutManager(TransaksiActivity.this);
                    mRecycler.setLayoutManager(lin);
                    mRecycler.setAdapter(adapter);

                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("proses", "Gagal membaca data yang diminta.", databaseError.toException());

            }
        });

    }
}
