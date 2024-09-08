package com.example.demo;

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
            String url = "https://webservice.recruit.co.jp/hotpepper/large_area/v1/?key=a5c3c9fb001ca296&format=json&keyword=山口";
            // リクエストの作成
            Request request = new Request.Builder().url(url).build();
            // レスポンスの取得
            Response response = client.newCall(request).execute();
            // レスポンスのBody要素を取得
            String responseBody = response.body().string();
            shopsNode=mapper.readTree(responseBody);
            System.out.println(shopsNode.get("results").get("large_area").get(0).get("code").asText());
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}