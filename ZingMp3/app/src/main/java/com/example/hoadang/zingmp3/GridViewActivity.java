package com.example.hoadang.zingmp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    private GridView gridView;
    ArrayList<Integer> integers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        for(int i = 0; i < 9; i++){
            integers.add(i);
        }
        gridView = (GridView) findViewById(R.id.girdView);
      //  ArrayAdapter Adapter = new ArrayAdapter(GridViewActivity.this,android.R.layout.simple_list_item_1,integers);
        Adaptergv Adapter = new Adaptergv(GridViewActivity.this);

        gridView.setAdapter(Adapter);
    }
}
