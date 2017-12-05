package com.kelique.chatlatian.uripuruppo.endpoin;

import android.content.Context;
import android.widget.ImageView;

import com.kelique.chatlatian.R;
import com.squareup.picasso.Picasso;

/**
 * Created by kelique on 10/26/2017.
 */

public class PicassoClient {
    public static  void  downloading(Context c, String url, ImageView img){
        if (url!=null&&url.length()>0){
            Picasso.with(c).load(url).placeholder(R.drawable.people3).into(img);
        }else{
            Picasso.with(c).load(R.drawable.people3).into(img);
        }
    }
}
