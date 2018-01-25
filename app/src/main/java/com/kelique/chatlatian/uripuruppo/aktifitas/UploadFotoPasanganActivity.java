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

public class UploadFotoPasanganActivity extends AppCompatActivity {
    @BindView(R.id.button1)
    Button mButton1;
    private ImageView mImage;
    private StorageReference mStoreRef;
    private FirebaseStorage mFireStore;
    private static final int GALLERY_INTENT = 2;
    //private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto_diri);
        ButterKnife.bind(this);

        mButton1 = (Button) findViewById(R.id.button1);

        mImage = (ImageView) findViewById(R.id.imageView3);
        mStoreRef = FirebaseStorage.getInstance().getReference();
        mProgDiag = new ProgressDialog(this);
        mButton1.setOnClickListener(new OnClickListener() {
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
                        mProgDiag.setMessage("Sedang Mengunggah Foto...");
                        mProgDiag.show();
                        Uri uri = data.getData();
                        StorageReference tempatfile = mStoreRef.child("FotoPasangan").child(uri.getLastPathSegment());
                        tempatfile.putFile(uri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                            @Override
                            public void onSuccess(TaskSnapshot taskSnapshot) {
//                                @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                                Log.d("DEBUUG", String.valueOf(downloadUri));
//                                Picasso.with(UploadFotoPasanganActivity.this).load(downloadUri).fit().centerCrop().error(R.mipmap.ic_launcher).into(mImage);
                                Toast.makeText(UploadFotoPasanganActivity.this, "Unggahan Foto Berhasil", Toast.LENGTH_SHORT).show();
                                mProgDiag.dismiss();
                                finish();

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

    @OnClick({R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                break;
            case R.id.button2:
                startActivity(new Intent(UploadFotoPasanganActivity.this, KirimFotoActivity.class));
                break;
        }
    }
}
