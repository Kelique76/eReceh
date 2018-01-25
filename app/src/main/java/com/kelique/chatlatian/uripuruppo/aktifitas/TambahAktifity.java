package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
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
import com.kelique.chatlatian.uripuruppo.helper.GalleryUtil;
import com.kelique.chatlatian.uripuruppo.helper.MyFunction;
import com.kelique.chatlatian.uripuruppo.helper.SessionManager;
import com.kelique.chatlatian.uripuruppo.modeling.Anggota;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


//    public ProgressDialog mProgDiag;


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
    String picturePath;
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;
    private int PICK_IMAGE_CODE = 123;
    ProgressBar pb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aktifitas);
        ButterKnife.bind(this);

        pb  = (ProgressBar) findViewById(R.id.progressBar1);
        dRef = FirebaseDatabase.getInstance().getReference("daftar_pedagang");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //currentUser = mAuth.getCurrentUser();

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

        mImageView1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //cekPermisi();
                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }

        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                // Set The Bitmap Data To ImageView
                mImageView1.setImageBitmap(selectedBitmap);
                mImageView1.setScaleType(ScaleType.FIT_XY);
            }
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
        String bagi_hasil = mTextBagiHsl.getText().toString();
        String periode = mTextPriod.getText().toString();

        SessionManager sesi = new SessionManager(c);

        DateFormat detfor = new SimpleDateFormat("ddMMyyHHmmss");
        Date dataObj = new Date();
        String imagePath = SplitString(sesi.getAlamat()) + "," + detfor.format(dataObj) + ".jpg";
        StorageReference imageRef = mIdPhotosStorageReference.child("foto_diri/" + imagePath);
        mImageView1.setDrawingCacheEnabled(true);
        mImageView1.buildDrawingCache();
        Bitmap bitmap = mImageView1.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);
        byte[] fotoDiri = baos.toByteArray();


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
                } else if (TextUtils.isEmpty(bagi_hasil)) {
                    mTextBagiHsl.setError("Jumlah Bagi hasil Harap Diisi");
                } else if (TextUtils.isEmpty(permodalan)) {
                    mTextCap.setError("Jangan Lupa Tekan Tombol Hitung Total");
                } else if (TextUtils.isEmpty(periode)) {
                    mTextCap.setError("Jangan Lupa Tekan Tombol Hitung Periode");
                } else {

                    final Anggota anggota = new Anggota(nama, alamat, namaibu, pasangan, ktp, hp, hppasangan, permodalan, bagi_hasil, periode);
                    UploadTask uploadTask = imageRef.putBytes(fotoDiri);

                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(TambahAktifity.this, "Gagal upload", Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                        @Override
                        public void onSuccess(TaskSnapshot taskSnapshot) {

                            String downloadUrl = taskSnapshot.getDownloadUrl().toString();

                            dRef.child(id).setValue(anggota);
                            dRef.child(id).child("alamatUrl").setValue(downloadUrl);

                            customeToast();
                            startActivity(new Intent(TambahAktifity.this, KirimFotoActivity.class));
                            finish();


                        }
                    }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
                        @Override
                        public void onProgress(TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            pb.setVisibility(ProgressBar.VISIBLE);
                            pb.setBackgroundColor(0001);
                            pb.setHovered(true);
                            pb.setMax(100);
                            pb.setProgress((int) progress);

                        }
                    });


                }
                break;

        }
    }

    private void customeToast() {
        LayoutInflater inflater = getLayoutInflater();
        View viewcustomtoast = inflater.inflate(R.layout.toast_custome,null);
        ImageView imgcustom =(ImageView) viewcustomtoast.findViewById(R.id.imgcustomtoast);
        TextView textcustom = (TextView) viewcustomtoast.findViewById((R.id.txtcustomtoast));

        textcustom.setText(getString(R.string.pesan));
        imgcustom.setImageResource(R.drawable.ceq);

        Toast toastcust = new Toast(TambahAktifity.this);
        toastcust.setView(viewcustomtoast);
        toastcust.setGravity(Gravity.BOTTOM|Gravity.CENTER_VERTICAL,0,0);
        toastcust.setDuration(Toast.LENGTH_LONG);
        toastcust.show();
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

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "HP anda tidak mendukung pemotongan foto";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
