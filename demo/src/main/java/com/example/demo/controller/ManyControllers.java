package com.example.demo.controller;

import java.io.IOException;

import com.example.demo.model.Restaurant;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManyControllers {
    public static String urlMake(Restaurant res){
        return "";
    }


    public static String prefecture(String pref) throws IOException {   //完成してます 都道府県のコード取るだけやし
        // リクエストを送るURLを定義する（Json形式に値を修正）
        JsonNode shopsNode =null;
        String url = "https://webservice.recruit.co.jp/hotpepper/large_area/v1/?key=a5c3c9fb001ca296&keyword="+pref+"&format=json"; //URLで指定できるのでします
        // http通信を行う
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        // リクエストの作成
        Request request = new Request.Builder().url(url).build();
        // レスポンスの取得
        Response response = client.newCall(request).execute();
        // レスポンスのBody要素を取得
        String responseBody = response.body().string();
        JsonNode rootNode = mapper.readTree(responseBody);
        return rootNode.get("results").get("large_area").get(0).get("code").asText();   //所得したコードを裸で返す　ダメ人間、Java

    }
}
