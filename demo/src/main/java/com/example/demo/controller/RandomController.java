package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RandomController {
    public static JsonNode kaesi(Restaurant res) throws Exception{
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shopsNode =null;
        Random random = new Random();
        try {
            String url = ManyControllers.urlMake(res);  //ただのURL 検索条件がいくつあるかとかによって変わるからね
            // リクエストの作成
	  	    Request request = new Request.Builder().url(url).build();
            // レスポンスの取得
	  	    Response response = client.newCall(request).execute();
            // レスポンスのBody要素を取得
	  	    String responseBody = response.body().string();
            JsonNode rootNode = mapper.readTree(responseBody);
            int i = 0;
            //json長さチェック
            do {
                shopsNode = rootNode.get("results").get("shop").get(i);
                i++;
            } while (shopsNode != null);
            int randomValue = random.nextInt(i-1);
            //乱数で店を指定
            shopsNode = rootNode.get("results").get("shop").get(randomValue);
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return shopsNode;
    }
}
