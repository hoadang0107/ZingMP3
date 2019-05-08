package com.example.hoadang.zingmp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Song> song =new ArrayList<>();
   // private ArrayList<Integer> integers = new ArrayList<>();

    private String readText() throws IOException{
        InputStream is = getResources().openRawResource(R.raw.zingmp3);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void readJSonFile() {
        try {
            String jsonText = readText();
            JSONObject jsonRoot = new JSONObject(jsonText);
            JSONObject jsonData = jsonRoot.getJSONObject("data");
            JSONArray jsonSong = jsonData.getJSONArray("songs");

            for (int i = 0; i < jsonSong.length(); i++) {
                JSONObject jsonObject = jsonSong.getJSONObject(i);
                String name = jsonObject.getString("name");
                String singer = jsonObject.getString("artist");
                String uriStr = jsonObject.getString("thumbnail");
                String link = jsonObject.getString("link");

//                String albumLink = jsonObject1.getString("link");


                //set data below this line
                Song song1 = new Song();
                song1.setName(name);
                song1.setSinger(singer);
                song1.setUriStr(uriStr);
                song1.setLink(link);
                song.add(song1);

                //set data above this line
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listView);

        Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
        intent.putExtra("song",song);

       // ArrayAdapter Adapter = new ArrayAdapter(ListViewActivity.this,android.R.layout.simple_list_item_1,integers);
        readJSonFile();
        ADapter aDapter = new ADapter(ListViewActivity.this, song);

        listView.setAdapter(aDapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
                intent.putExtra("dem",position);
                startActivity(intent);
            }
        });

    }
}
