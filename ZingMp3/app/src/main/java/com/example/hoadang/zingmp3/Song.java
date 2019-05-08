package com.example.hoadang.zingmp3;

/**
 * Created by HoaDang on 15/05/2017.
 */

public class Song {

    private String name;
    private String singer;
    private String uriStr;
    private String link;

//    public  Song(String name)
//    {
//        this.name = name;
//    }
    public String getName(){

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSinger(){
        return  singer;
    }
    public void setSinger ( String singer){
        this.singer = singer;
    }
    public  String getUriStr(){
        return uriStr;
    }
    public  void setUriStr(String uriStr){
        this.uriStr = uriStr;
    }
    public String getLink(){
        return link;
    }
    public void setLink(String link){
        this.link = link;
    }

}
