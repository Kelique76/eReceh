package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.kelique.chatlatian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadKtpActivity extends AppCompatActivity {


    @BindView(R.id.buttonPilih)
    Button mButtonPilih;
    @BindView(R.id.buttonKirim)
    Button mButtonKirim;
    @BindView(R.id.imagePoto)
    ImageView mImagePoto;

    private StorageReference mStoreRef;
    private FirebaseStorage mFireStore;
    private static final int GALLERY_INTENT = 2;
    //private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgDiag;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_ktp);
        ButterKnife.bind(this);


        mButtonPilih = (Button) findViewById(R.id.buttonPilih);
        mButtonKirim = (Button) findViewById(R.id.buttonKirim);

        mImagePoto = (ImageView) findViewById(R.id.imagePoto);
        mStoreRef = FirebaseStorage.getInstance().getReference();
        mProgDiag = new ProgressDialog(this);

        mButtonPilih.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case GALLERY_INTENT:
                    if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
//                        double progress = (100.0 * data.getBytesTransferred())/taskSnapshot.getTotalByteCount();
//
//                        //displaying percentage in progress dialog
//                        mProgDiag.setMessage("Menyimpan Data " + ((int) progress) + "%...");
                        mProgDiag.setTitle("Menyimpan Data");
                        mProgDiag.setMessage("Sedang Mengunggah Foto...");
                        mProgDiag.show();
                        Uri uri = data.getData();
                        StorageReference tempatfile = mStoreRef.child("FotoPasangan").child(uri.getLastPathSegment());
                        tempatfile.putFile(uri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                            @Override
                            public void onSuccess(TaskSnapshot taskSnapshot) {
//                                @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                                Log.d("DEBUUG", String.valueOf(downloadUri));
//                                Picasso.with(UploadKtpActivity.this).load(downloadUri).fit().centerCrop().error(R.mipmap.ic_launcher).into(mImagePoto);
//

                                Toast.makeText(UploadKtpActivity.this, "Unggahan Foto Berhasil", Toast.LENGTH_SHORT).show();
                                mProgDiag.dismiss();


                            }
                        }).addOnProgressListener(new OnProgressListener<TaskSnapshot>() {
                            @Override
                            public void onProgress(TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                                //displaying percentage in progress dialog
                                mProgDiag.setMessage("Menyimpan Data " + ((int) progress) + "%...");
                            }
                        });


                    }

                    break;

            }

        } catch (Exception e) {
            Log.i("kalau salah piye", e.getMessage());
        }
    }



    @OnClick({R.id.buttonPilih, R.id.buttonKirim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonPilih:
                break;
            case R.id.buttonKirim:
                startActivity(new Intent(UploadKtpActivity.this, KirimFotoActivity.class));
                break;
        }
    }




}



