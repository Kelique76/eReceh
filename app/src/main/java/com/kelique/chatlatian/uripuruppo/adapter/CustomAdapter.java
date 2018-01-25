package com.kelique.chatlatian.uripuruppo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kelique.chatlatian.R;
import com.kelique.chatlatian.uripuruppo.endpoin.MyHolder;
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
    ImageView imgView;
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
        if(mInflater==null) {
            mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(view==null) {
            view = mInflater.inflate(R.layout.tpa_listview_pedagang_layout, viewGroup, false);
        }


        imgView = view.findViewById(R.id.beachimage);
        MyHolder holder = new MyHolder(view);


        holder.nametext.setText(mBakulArrayList.get(i).getnama());
        holder.alamatxtFull.setText(mBakulArrayList.get(i).getAlamat());
        holder.permodalanTxt.setText(mBakulArrayList.get(i).getpermodalan());
        holder.bagihasilText.setText(mBakulArrayList.get(i).getbagi_hasil());
        holder.periodeTxt.setText(mBakulArrayList.get(i).getperiode());
        //Glide.with(c).load(mBakulArrayList.get(i).getPhotoUrl()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgView);
        Picasso.with(c).load(mBakulArrayList.get(i).getalamatUrl()).into(imgView);
        holder.periodeTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
