package com.example.hoadang.zingmp3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by HoaDang on 11/05/2017.
 */

public class ADapter extends BaseAdapter {
    private Context context;
    private ArrayList<Song> song;
    private int i = 0;

    public ADapter(Context context, ArrayList<Song> song) {
        this.context = context;
        this.song = song;
    }

    @Override
    public int getCount() { //trả về số hàng
        return song.size();
    }

    @Override
    public Object getItem(int position) { //trả về dữ liệu của một hàng
        return null;
    }

    @Override
    public long getItemId(int position) { // trả về id của một view
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) { //position la chi so cua hang

        final Holder holder;
        i = position;
        View v = convertView;
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            holder = new Holder();
            holder.tv1 = (TextView) v.findViewById(R.id.tv1);
            holder.tv2 = (TextView) v.findViewById(R.id.tv2);
            holder.stt = (TextView) v.findViewById(R.id.stt);
            holder.img = (ImageView) v.findViewById(R.id.img);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainActivity.class);
//                intent.putExtra("dem",position);
//                context.startActivity(intent);
//            }
//        });
        holder.tv1.setText(song.get(position).getName());
        holder.tv2.setText(song.get(position).getSinger());
        int count = position + 1;
        if (position < 9)
            holder.stt.setText("0" + count);
        else holder.stt.setText(count + "");
        String url = song.get(position).getUriStr();
        Glide.with(context)
                .load(url)
                .into(holder.img);

        return v;
    }

    class Holder {
        TextView stt;
        TextView tv1;
        TextView tv2;
        ImageView img;
    }
}