package com.kelique.chatlatian.uripuruppo.endpoin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelique.chatlatian.R;

/**
 * Created by kelique on 10/26/2017.
 */

public class MyHolder {
    public TextView nametext, alamatxtFull, permodalanTxt, bagihasilText, periodeTxt;
    public ImageView img;
    public MyHolder(View view){
        nametext = (TextView)view.findViewById(R.id.nameTxt);
        alamatxtFull = (TextView)view.findViewById(R.id.txtAlamatFull);
        alamatxtFull = (TextView)view.findViewById(R.id.txtAlamatFull);
        permodalanTxt = (TextView)view.findViewById(R.id.txtModalAngka);
        bagihasilText = (TextView)view.findViewById(R.id.txtBgHslAngka);
        periodeTxt = (TextView)view.findViewById(R.id.txtPeriode);
        img = (ImageView)view.findViewById(R.id.beachimage);

    }
}
