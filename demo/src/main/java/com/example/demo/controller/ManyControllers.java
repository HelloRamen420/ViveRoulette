package com.example.demo.controller;

import java.io.IOException;
import java.util.Objects;

import com.example.demo.model.Restaurant;
import com.example.demo.model.UrlConst;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManyControllers {
    public static String urlMake(Restaurant res) throws IOException {
        String rootUrl="https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a5c3c9fb001ca296&format=json";
        if(Objects.nonNull(res.getAreaPref())){
            rootUrl+="&large_area="+prefecture(res.getAreaPref());
        }
        return rootUrl;
    }


    public static String prefecture(String pref) throws IOException {   //完成してます 都道府県のコード取るだけやし
        // リクエストを送るURLを定義する（Json形式に値を修正）
        JsonNode shopsNode =null;
        String prefCode=null;
        String url = UrlConst.PREF_SEARCH;
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

        //都道府県の数(厳密にはjsonファイルに含まれる検索結果の数)が取得できるのでそれで回そうというやつ
        for(int i=0;i<rootNode.get("results").get("results_returned").asInt();i++){
            //ややこしく見えるけどやってることは、「引数の都道府県」と「検索結果を上からダァーっとひとつづつ取得」が同じですか？ってやつ
            if(pref.equals(rootNode.get("results").get("large_area").get(i).get("name").asText())){
                //シンプルに引数の都道府県と対応したcodeをin
                prefCode=rootNode.get("results").get("large_area").get(i).get("code").asText();
                break;
            }
        }
        return prefCode;
    }
}
