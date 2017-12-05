package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.jangkar.MyFunction;
import com.kelique.chatlatian.uripuruppo.modeling.Anggota;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TambahAktifity extends MyFunction implements OnClickListener {


    DatabaseReference dRef;
    String id;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mIdPhotosStorageReference;


    @BindView(R.id.textName)
    EditText mTextName;
    @BindView(R.id.textAlamat)
    EditText mTextAlamat;
    @BindView(R.id.textIbuKdg)
    EditText mTextIbuKdg;
    @BindView(R.id.textPasangan)
    EditText mTextPasangan;
    @BindView(R.id.textNoKtp)
    EditText mTextNoKtp;
    @BindView(R.id.textNoHp)
    EditText mTextNoHp;
    @BindView(R.id.textNoHpPsgn)
    EditText mTextNoHpPsgn;
    @BindView(R.id.textModal)
    EditText mTextModal;
    @BindView(R.id.textBagiHsl)
    EditText mTextBagiHsl;
    @BindView(R.id.textCap)
    TextView mTextCap;
    @BindView(R.id.textPriod)
    TextView mTextPriod;
    @BindView(R.id.buttonKrm)
    Button mButtonKrm;
    @BindView(R.id.btnProid)
    Button mBtnProid;
    @BindView(R.id.btnTotal)
    Button mBtnTotal;
    @BindView(R.id.btnFotoDiri)
    Button mBtnFotoDiri;
    @BindView(R.id.imageView2)
    ImageView mImageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aktifitas);
        ButterKnife.bind(this);


        dRef = FirebaseDatabase.getInstance().getReference("daftar_pedagang");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mIdPhotosStorageReference = mFirebaseStorage.getReference().child("foto_diri");
        id = dRef.push().getKey();

        mTextModal = (EditText) findViewById(R.id.textModal);
        mTextBagiHsl = (EditText) findViewById(R.id.textBagiHsl);
        mBtnTotal = (Button) findViewById(R.id.btnTotal);
        mBtnProid = (Button) findViewById(R.id.btnProid);
        mTextCap = (TextView) findViewById(R.id.textCap);
        mTextPriod = (TextView) findViewById(R.id.textPriod);
        mImageView1 = (ImageView) findViewById(R.id.imagePoto);


        mBtnTotal.setOnClickListener(this);
        mBtnProid.setOnClickListener(this);


        /* ambil hasil dari hasil pilihan galery di PilihActivity */
        Uri selectedImgUri = getIntent().getData();
        if (selectedImgUri != null) {
            Log.e("Gallery ImageURI", "" + selectedImgUri);
            String[] selectedImgPath = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImgUri,
                    selectedImgPath, null, null, null);
            cursor.moveToFirst();

            int indexCol = cursor.getColumnIndex(selectedImgPath[0]);
            String imgPath = cursor.getString(indexCol);
            cursor.close();
            //TODO: #1. Dibagian ini bikin crash nggak ngerti kenapa?
            //TODO: #2 Setelah berhasil filenya nyangkut di TambahAktivity agar di tarik ke Firebase bersamaan dengan datanya
            mImageView1.setImageBitmap(BitmapFactory.decodeFile(imgPath));
        }

    /* ambil hasil dari hasil jepretan kamera di PilihActivity */
        Intent intent_camera = getIntent();
        Bitmap camera_img_bitmap = (Bitmap) intent_camera
                .getParcelableExtra("BitmapImage");
        if (camera_img_bitmap != null) {
            mImageView1.setImageBitmap(camera_img_bitmap);
        }


    }

    @OnClick({R.id.buttonKrm})
    public void onViewClicked(View view) {


        String nama = mTextName.getText().toString();
        String alamat = mTextAlamat.getText().toString();
        String namaibu = mTextIbuKdg.getText().toString();
        String pasangan = mTextPasangan.getText().toString();
        String ktp = mTextNoKtp.getText().toString();
        String hp = mTextNoHp.getText().toString();
        String hppasangan = mTextNoHpPsgn.getText().toString();
        String permodalan = mTextModal.getText().toString();
        String bagihasil = mTextBagiHsl.getText().toString();
        String periode = mTextPriod.getText().toString();
        //TODO: #3 Tarik data gambar ke Firebase dan masukkan ke Folder "foto_diri", StorageReference sudah dipersiapkan di line 85

        switch (view.getId()) {
            case R.id.buttonKrm:
                if (TextUtils.isEmpty(nama)) {
                    mTextName.setError("Nama Harap Diisi");
                } else if (TextUtils.isEmpty(alamat)) {
                    mTextAlamat.setError("Alamat Harap Diisi");
                } else if (TextUtils.isEmpty(namaibu)) {
                    mTextIbuKdg.setError("Nama Ibu Kandung Harap Diisi");
                } else if (TextUtils.isEmpty(pasangan)) {
                    mTextPasangan.setError("Nama Harap Diisi");
                } else if (TextUtils.isEmpty(ktp)) {
                    mTextNoKtp.setError("KTP Harap Diisi");
                } else if (TextUtils.isEmpty(hp)) {
                    mTextNoHp.setError("No. HP Harap Diisi");
                } else if (TextUtils.isEmpty(hppasangan)) {
                    mTextNoHpPsgn.setError("No Hp Harap Diisi");
                } else if (TextUtils.isEmpty(permodalan)) {
                    mTextModal.setError("Jumlah modal Harap Diisi");
                } else if (TextUtils.isEmpty(bagihasil)) {
                    mTextBagiHsl.setError("Jumlah Bagi hasil Harap Diisi");
                } else if (TextUtils.isEmpty(permodalan)) {
                    mTextCap.setError("Jangan Lupa Tekan Tombol Hitung Total");
                } else if (TextUtils.isEmpty(periode)) {
                    mTextCap.setError("Jangan Lupa Tekan Tombol Hitung Periode");
                } else {

                    Anggota anggota = new Anggota(nama, alamat, namaibu, pasangan, ktp, hp, hppasangan, permodalan, bagihasil, periode);
                    dRef.child(id).setValue(anggota);
                    startActivity(new Intent(TambahAktifity.this, KirimFotoActivity.class));
                    Toast.makeText(getApplicationContext(), "Penambahan Data Berhasil", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @OnClick({R.id.btnProid, R.id.btnTotal})
    public void onClick(View view) {
        String sumber = mTextModal.getText().toString();
        String tambah = mTextBagiHsl.getText().toString();
        String total = mTextCap.getText().toString();

        switch (view.getId()) {
            case R.id.btnProid:
                try {
                    int dibagi = Integer.parseInt(total) / Integer.parseInt("20000");
                    mTextPriod.setText(String.valueOf(dibagi));
                } catch (Exception e) {
                    //Handle the Null Pointer Exception here.
                    Log.e("Lupa mengisi", "Error occurred!, Error = " + e.toString());
                }
                break;
            case R.id.btnTotal:
                try {
                    int ditambah = Integer.parseInt(sumber) + Integer.parseInt(tambah);
                    mTextCap.setText(String.valueOf(ditambah));
                } catch (Exception e) {
                    //Handle the Null Pointer Exception here.
                    Log.e("Lupa mengisi", "Error occurred!, Error = " + e.toString());
                }
                break;

        }
    }
//TODO: Lebih baik dibuatkan Custome Alert yang terdiri dua pilihan (mau ambil file pakai Galerry atau Camera)
    @OnClick(R.id.btnFotoDiri)
    public void onViewClicked() {
        myIntent(PilihActivity.class);
    }
}
