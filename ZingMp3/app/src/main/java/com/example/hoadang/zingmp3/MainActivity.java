package com.example.hoadang.zingmp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ImageView imgPlay;
    private boolean checkbtnPlay = true;
    private ImageView imgRepeat;
    private ImageView imgShuffle;
    private boolean checkbtnShuffle = true;
    private int demRepeat;
    private ImageView imgNext;
    private int dem;
    private boolean isVailable;
    private ImageView imgPrev;
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor descriptor;
    private ArrayList<Song> listsong = new ArrayList<>();
    private SeekBar seekbar;
    private TextView tvmax, song, author;
    private TextView tvcurrent;
    private int max;
    private CircleImageView anh;

    private String readText() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.zingmp3);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
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
                listsong.add(song1);

                //set data above this line
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //sharedPreferrences
    void text() {
        song.setText(listsong.get(dem).getName());
        author.setText(listsong.get(dem).getSinger());
        String url = listsong.get(dem).getUriStr();
        Glide.with(this)
                .load(url)
                .into(anh);

    }

    void play() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            mediaPlayer.reset();
             descriptor = getAssets().openFd(dem+".mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            if (!checkbtnPlay)
                mediaPlayer.start();
            editor.putInt("dem", dem);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("zingmp3", Context.MODE_PRIVATE);
        demRepeat = sharedPreferences.getInt("dem_repeat", 0);
        mediaPlayer = new MediaPlayer();
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgShuffle = (ImageView) findViewById(R.id.imgShuffle);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        imgPrev = (ImageView) findViewById(R.id.imgPrev);
        tvmax = (TextView) findViewById(R.id.tvmax);
        tvcurrent = (TextView) findViewById(R.id.tvcurrent);
        song = (TextView) findViewById(R.id.song);
        author = (TextView) findViewById(R.id.author);
        anh = (CircleImageView) findViewById(R.id.anh);
        dem = getIntent().getExtras().getInt("dem");
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        isVailable = false;
        readJSonFile();
        text();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }
        });
        seekBar();

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbtnPlay) {
                    imgPlay.setImageResource(R.drawable.ic_pause_dark);
                    checkbtnPlay = false;


                    //mediaplayer là cái để phát nhạc hoặc video
                    //mediaplayer là cái để phát nhạc hoặc video
                    try {
                        if (!isVailable) {
                            mediaPlayer.reset(); //reset lại
                            descriptor = getAssets().openFd(dem+".mp3"); //mở file
                            mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
                                    descriptor.getStartOffset(), descriptor.getLength()); //set nguồn dữ liệu cho mediaplayer
                            descriptor.close(); //mở file thì phải có đóng file
                            mediaPlayer.prepare(); //chuẩn bị dữ liệu để phát
                            isVailable = true; //bà code sai logic ở đây này
                            seekBar();
                            mediaPlayer.start();


                        } else {

                            mediaPlayer.start(); //bắt đầu phát
                            //ngoài ra có vài cái hay ho nữa
                        }
//                        seekbar.setMax(mediaPlayer.getDuration());
//                        max = mediaPlayer.getDuration() / 1000;
//                        tvmax.setText(max / 60 + ":" + max % 60);
//                        mediaPlayer.getDuration(); //trả về độ dài audio hoặc video tính theo miliseconds. Muốn đối sang giờ phút
//                                                    //giây thì viết 1 hàm convert ra là xong
//                        mediaPlayer.getCurrentPosition(); //trả về thời điểm hiện tại tính theo miliseconds. Muốn chuyển thì viết hàm
//                                                            //như trên
//                        mediaPlayer.seekTo(1000); //chuyển đến thời điểm 1000ms. Cái này để set sự kiện cho thanh seekbar khi kéo nó
//                        mediaPlayer.setVolume(1f, 1f); //cài đặt âm lượng, tham số vào là 2 số kiểu float tương ứng với bên trái/phải.
//                                                    //bằng 0 sẽ không có tiếng, bằng 1 sẽ ra tiếng to nhất. Sẽ cần khi muốn tạo âm thanh
                                                    //effect sống ảo gì đấy
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    imgPlay.setImageResource(R.drawable.ic_play_dark);
                    checkbtnPlay = true;
                    mediaPlayer.pause(); //tạm dừng luồng phát, khi start sẽ tiếp tục từ thời điểm tạm dừng
                    //nếu dùng mediaPlayer.stop() thì luồng sẽ dừng hẳn luôn. start sé phát lại từ đầu
                }
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(checkbtnShuffle) {
                    if (dem == 19 && demRepeat == 1) dem = 0;
                    if (dem != 19 && demRepeat == 1) dem++;
                    if (dem != 19 && demRepeat == 0) dem++;
                    if (dem == 19 && demRepeat == 0) {
                        dem = 0;
                        checkbtnPlay = true;
                        imgPlay.setImageResource(R.drawable.ic_play_dark);
                    }
                }
                else {
                    Random rd=new Random();
                    dem=rd.nextInt((19-0+1)+0);
                }
                play();
                seekBar();
            }
        });

        imgPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (checkbtnPlay) {
                    imgPlay.setImageResource(R.drawable.ic_fast_play_video_big);
                } else
                    imgPlay.setImageResource(R.drawable.ic_pause_light);
                return false;
            }
        });


        //bị sao
        imgRepeat = (ImageView) findViewById(R.id.imgRepeat);
        if (demRepeat == 0) imgRepeat.setImageResource(R.drawable.ic_player_repeat);
        if (demRepeat == 1) imgRepeat.setImageResource(R.drawable.ic_player_repeat_all);
        if (demRepeat == 2) imgRepeat.setImageResource(R.drawable.ic_player_repeat_1);
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (demRepeat) {
                    case 0:
                        imgRepeat.setImageResource(R.drawable.ic_player_repeat_all);
                        editor.putInt("dem_repeat", 1);
                        editor.commit();
                        demRepeat = 1;
                        break;
                    case 1:
                        imgRepeat.setImageResource(R.drawable.ic_player_repeat_1);
                        editor.putInt("dem_repeat", 2);
                        editor.commit();
                        demRepeat = 2;
                        break;
                    case 2:
                        imgRepeat.setImageResource(R.drawable.ic_player_repeat);
                        editor.putInt("dem_repeat", 0);
                        editor.commit();
                        demRepeat = 0;
                        break;
                }

            }
        });

        checkbtnShuffle = sharedPreferences.getBoolean("checkShuffle", true);
        if (checkbtnShuffle) imgShuffle.setImageResource(R.drawable.ic_player_shuffle);
        else imgShuffle.setImageResource(R.drawable.ic_player_shuffle_selected);
        imgShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (checkbtnShuffle) {
                    imgShuffle.setImageResource(R.drawable.ic_player_shuffle_selected);
                    editor.putBoolean("checkShuffle", false);
                    editor.commit();
                    checkbtnShuffle = false;

                } else {
                    imgShuffle.setImageResource(R.drawable.ic_player_shuffle);
                    editor.putBoolean("checkShuffle", true);
                    editor.commit();
                    checkbtnShuffle = true;
                }
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (dem == listsong.size()) dem = 0;
                else dem++;
                text();
                try {
                    mediaPlayer.reset();
                    descriptor = getAssets().openFd(dem+".mp3");
                    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                    descriptor.close();
                    mediaPlayer.prepare();
                    seekBar();
                    if (!checkbtnPlay)
                        mediaPlayer.start();
                    editor.putInt("dem", dem);
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (dem == 0) dem = listsong.size();
                else dem--;
                text();
                try {
                    mediaPlayer.reset();
                    descriptor = getAssets().openFd(dem+".mp3");
                    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                    descriptor.close();
                    mediaPlayer.prepare();
                    seekBar();
                    if (!checkbtnPlay)
                        mediaPlayer.start();
                    editor.putInt("dem", dem);
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }
    void seekBar() {
        max = mediaPlayer.getDuration();
        tvmax.setText(max / 60000 + ":" + (max / 1000) % 60);
        seekbar.setMax(max);
        final Handler mHandler = new Handler();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int time = mediaPlayer.getCurrentPosition();
                    seekbar.setProgress(time);
                    tvcurrent.setText(time/60000+":"+(time/1000)%60);
                    mHandler.postDelayed(this, 1000);
                }
            }
        });

  }

}