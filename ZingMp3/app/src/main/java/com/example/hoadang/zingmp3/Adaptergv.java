package com.example.hoadang.zingmp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by HoaDang on 19/05/2017.
 */

public class Adaptergv extends BaseAdapter {

    ImageView imageView;
    Context context;
    public Adaptergv(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        return view;
    }
}
