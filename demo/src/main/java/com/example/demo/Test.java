package com.example.demo;

import com.example.demo.controller.PrefController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test { //何を取得するんかなとか　ライブラリ揃えるのめんどいんでここでテスト作ってますね
    public static void main(String[] args) throws Exception{
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shopsNode =null;
        Random random = new Random();
        try {
            String url = "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a5c3c9fb001ca296&large_area=Z014&budget+codeB008&results_available&format=json";
            // リクエストの作成
            Request request = new Request.Builder().url(url).build();
            // レスポンスの取得
            Response response = client.newCall(request).execute();
            // レスポンスのBody要素を取得
            String responseBody = response.body().string();
            System.out.println(responseBody);
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}