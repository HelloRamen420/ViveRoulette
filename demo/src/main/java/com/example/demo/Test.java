package com.example.demo;

import com.example.demo.controller.ManyControllers;
import com.example.demo.model.UrlConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test { //何を取得するんかなとか　ライブラリ揃えるのめんどいんでここでテスト作ってますね
    public static void main(String[] args) throws Exception {
        String url="https://github.com/xxgentaroxx/HP_DB/blob/main/member.csv?plain=1";
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println(responseBody);
    }
}