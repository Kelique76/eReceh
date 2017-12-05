package com.kelique.chatlatian.uripuruppo.aktifitas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kelique.chatlatian.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class KirimFotoActivity extends AppCompatActivity {

    @BindView(R.id.btnImgKTP)
    Button mBtnImgKTP;
    @BindView(R.id.btnImgDiri)
    Button mBtnImgDiri;
    @BindView(R.id.btnImgToko)
    Button mBtnImgToko;
    @BindView(R.id.buttonKrm)
    Button mButtonKrm;
    @BindView(R.id.textFotoKtp)
    EditText mTextFotoKtp;
    @BindView(R.id.textFotoDiri)
    EditText mTextFotoDiri;
    @BindView(R.id.textFotoToko)
    EditText mTextFotoToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_foto);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnImgKTP, R.id.btnImgDiri, R.id.btnImgToko, R.id.buttonKrm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnImgKTP:

                startActivity(new Intent(KirimFotoActivity.this, UploadKtpActivity.class));
                break;
            case R.id.btnImgDiri:
                startActivity(new Intent(KirimFotoActivity.this, UploadFotoDiriActivity.class));
                break;
            case R.id.btnImgToko:
                startActivity(new Intent(KirimFotoActivity.this, UploadFotoTokoActivity.class));
                break;
            case R.id.buttonKrm:
                startActivity(new Intent(KirimFotoActivity.this, MainActivity.class));
                break;
        }
    }
}
