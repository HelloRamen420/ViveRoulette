package com.example.demo.controller;

import com.example.demo.model.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RandomController { //ランダムで取得するとはいえURLの時点でランダムやってます
    public static JsonNode kaesi(Restaurant res) throws Exception{
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shopsNode =null;
        try {
            String url = ManyControllers.urlMake(res);  //ただのURL 検索条件がいくつあるかとかによって変わるからね

	  	    //いつもの
            Request request = new Request.Builder().url(url).build();
	  	    Response response = client.newCall(request).execute();
	  	    String responseBody = response.body().string();
            //ここまで

            shopsNode = mapper.readTree(responseBody);
        }catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        return shopsNode;
    }
}
