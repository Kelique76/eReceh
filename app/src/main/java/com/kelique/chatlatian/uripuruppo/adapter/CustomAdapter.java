package com.kelique.chatlatian.uripuruppo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.modeling.Pedagang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



/**
 * Created by kelique on 11/16/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Pedagang> mBakulArrayList;
    LayoutInflater mInflater;
    public CustomAdapter(Context c, ArrayList<Pedagang> mBakulArrayList) {
        this.c = c;
        this.mBakulArrayList = mBakulArrayList;


    }

    @Override
    public int getCount() {
        return mBakulArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mBakulArrayList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.tpa_listview_pedagang_layout, null);
        TextView nametext, alamatxtFull, permodalanTxt, bagihasilText, periodeTxt;
        ImageView imgView;

        nametext = view.findViewById(R.id.nameTxt);
        alamatxtFull = view.findViewById(R.id.txtAlamatFull);
        permodalanTxt = view.findViewById(R.id.txtModalAngka);
        bagihasilText = view.findViewById(R.id.txtBgHslAngka);
        periodeTxt = view.findViewById(R.id.txtPeriode);
        imgView = view.findViewById(R.id.beachimage);

        nametext.setText(mBakulArrayList.get(i).getName());
        alamatxtFull.setText(mBakulArrayList.get(i).getAlamat());
        permodalanTxt.setText(mBakulArrayList.get(i).getModal());
        bagihasilText.setText(mBakulArrayList.get(i).getBghasil());
        periodeTxt.setText(mBakulArrayList.get(i).getPeriode());
        //Glide.with(c).load(mBakulArrayList.get(i).getPhotoUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgView);
        Picasso.with(c).load(mBakulArrayList.get(i).getPhotoUrl()).into(imgView);
        return view;
    }
}
