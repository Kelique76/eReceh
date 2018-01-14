package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.Manifest;
import android.Manifest.permission;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.jangkar.MyFunction;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TambahAktifity extends MyFunction implements OnClickListener {


    DatabaseReference dRef;
    String id;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mIdPhotosStorageReference;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgDiag;


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
    @BindView(R.id.imageView2)
    ImageView mImageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aktifitas);
        ButterKnife.bind(this);




        dRef = FirebaseDatabase.getInstance().getReference("daftar_pedagang");
        mFirebaseStorage = FirebaseStorage.getInstance();
        //mIdPhotosStorageReference = mFirebaseStorage.getReference().child("foto_diri/"+ imagePath);
        mIdPhotosStorageReference = mFirebaseStorage.getReferenceFromUrl("gs://chat-app-64353.appspot.com");
        id = dRef.push().getKey();

        mTextModal = (EditText) findViewById(R.id.textModal);
        mTextBagiHsl = (EditText) findViewById(R.id.textBagiHsl);
        mBtnTotal = (Button) findViewById(R.id.btnTotal);
        mBtnProid = (Button) findViewById(R.id.btnProid);
        mTextCap = (TextView) findViewById(R.id.textCap);
        mTextPriod = (TextView) findViewById(R.id.textPriod);
        mImageView1 = (ImageView) findViewById(R.id.imageView2);


        mBtnTotal.setOnClickListener(this);
        mBtnProid.setOnClickListener(this);

        //TODO: #1. Dibagian ini bikin crash nggak ngerti kenapa?
        //TODO: #2 Setelah berhasil filenya nyangkut di TambahAktivity agar di tarik ke Firebase bersamaan dengan datanya

        mImageView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cekPermisi();
            }
        });


    }

    int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=123;
    private void cekPermisi() {
        if(VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
        }
        ambilGambar();

    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode ==MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ambilGambar();
            } else {
                Toast.makeText(TambahAktifity.this,"Cannot access your images",Toast.LENGTH_LONG).show();
            }
        }
    }
    int PICK_IMAGE_CODE=123;
    private void ambilGambar() {
        Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_CODE && data != null && resultCode == RESULT_OK) {
            Uri fotoPilihan = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(fotoPilihan,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            bitmap = BitmapFactory.decodeFile(picturePath);
            mImageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));

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


//        String imageRef = mIdPhotosStorageReference.child("foto_diri/" + imagePath);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail().toString();
        DateFormat detfor = new SimpleDateFormat("ddMMyyHHmmss");
        Date dataObj = new Date();
        String imagePath = SplitString(email) + "," + detfor.format(dataObj) + ".jpg";
        StorageReference imageRef = mIdPhotosStorageReference.child("foto_diri/" + imagePath);
        mImageView1.setDrawingCacheEnabled(true);
        mImageView1.buildDrawingCache();
        Bitmap bitmap = mImageView1.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] fotoDiri = baos.toByteArray();

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

                    final Anggota anggota = new Anggota(nama, alamat, namaibu, pasangan, ktp, hp, hppasangan, permodalan, bagihasil, periode);
                    UploadTask uploadTask = imageRef.putBytes(fotoDiri);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(TambahAktifity.this,"Gagal upload",Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                            dRef.child(id).setValue(anggota);
                            dRef.child(id).child("alamatUrl").setValue(downloadUrl);
                            Toast.makeText(getApplicationContext(), "Penambahan Data Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahAktifity.this, KirimFotoActivity.class));

                        }
                    }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
                        @Override
                        public void onProgress(TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//                            displaying percentage in progress dialog
                            mProgDiag.setMessage("Menyimpan Data " + ((int) progress) + "%...");
                        }
                    });



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
//    @OnClick(R.id.btnFotoDiri)
//    public void onViewClicked() {
//        myIntent(PilihActivity.class);
//    }
}
