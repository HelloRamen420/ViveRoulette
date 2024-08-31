package com.example.demo.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PrefController {
    public static String prefecture(String pref) throws IOException {
        // リクエストを送るURLを定義する（Json形式に値を修正）
        JsonNode shopsNode =null;
        String url = "https://webservice.recruit.co.jp/hotpepper/large_area/v1/?key=a5c3c9fb001ca296&format=json";
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
        int i = 0;
        do {
            shopsNode = rootNode.get("results").get("large_area").get(i);
            //都道府県比べるとこ
            if(shopsNode.get("name").asText().equals(pref))
                break;
            i++;
        } while (shopsNode != null);
        //都道府県のコードをとる
        String prefe = shopsNode.get("code").asText();
        return prefe;

    }
}
