package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kelique.chatlatian.R;

public class PilihActivity extends AppCompatActivity {

    Button btn_choose_from_gallery, btn_go_to_camera;

    int REQUEST_GALLERY = 1;
    int REQUEST_CAMERA = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih);

        btn_choose_from_gallery = (Button) findViewById(R.id.fromGalerry);
        btn_go_to_camera = (Button) findViewById(R.id.fromCamera);

        btn_choose_from_gallery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_GALLERY);
            }
        });

        btn_go_to_camera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent_camera = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_camera, REQUEST_CAMERA);
            }
        });
    }

    /* Choose Image from Gallery & Camera onActivityResult */
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {

        super.onActivityResult(reqCode, resCode, data);

        if (resCode == RESULT_OK) {
            if (reqCode == REQUEST_CAMERA) {
                if (data != null) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                     /* Passing BITMAP to the Second Activity */
                    Intent IntentCamera = new Intent(this, TambahAktifity.class);
                    IntentCamera.putExtra("BitmapImage", photo);
                    startActivity(IntentCamera);
//                    Uri CapturedImgUri = data.getData();
//                    /* Passing ImageURI to the Second Activity */
//                    Intent IntentCamera = new Intent(this, PenerimaActivity.class);
//                    IntentCamera.putExtra("CapturedImgUri", CapturedImgUri);
//                    startActivity(IntentCamera);
                }
            } else if (reqCode == REQUEST_GALLERY) {
                if (data != null) {
                    Uri selectedImgUri = data.getData();
                    /* Passing ImageURI to the Second Activity */
                    Intent IntentGallery = new Intent(this, TambahAktifity.class);
                    IntentGallery.setData(selectedImgUri);
                    startActivity(IntentGallery);

                }
            }
        }
    }
}
