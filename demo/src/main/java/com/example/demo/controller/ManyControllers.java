package com.example.demo.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import com.example.demo.model.Restaurant;
import com.example.demo.model.UrlConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManyControllers {

    /*　~注意~
    * これは検索結果の件数を返すメソッドですが、
    *
    * 取得したファイルに含まれる件数ではなく、
    * 絞り込んだ結果それに当てはまった件数
    *
    * を返しています。
    * 取得したファイルは当てはまった件数全部を表示しているわけではないので。*/

    public static int availableSearch(String url) {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode shopsNode =null;
        try {
            //いつものセット
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            //ここまで
            shopsNode=mapper.readTree(responseBody);
        }catch(IOException e) {
            e.printStackTrace();
        }
        return shopsNode.get("results").get("results_available").asInt();
    }




    /*URLをつくるメソッドです。
    *
    * 何で絞るかUser任意なので、
    * もうRestaurantオブジェクトの中身次第だね〜
    * みたいにするとちゃちゃっとできることに気づきました。
    *
    * そんな複雑にはなってないはずです。
    * フィールドの内容がnullだと何もしない、なんか入ってると対応したクエリをURLに追加するみたいな、
    * あぁ〜シンプル
    *
    * ちなみに最後の処理は、
    * その条件に当てはまる店の件数の中から一つを取得してるだけですね。
    * こうやってしないといけないんです (ﾉД`)ｼｸｼｸ
    * なんでか気になった人は @renkon100000 まで */

    public static String urlMake(Restaurant res) throws IOException {
        Random rm=new Random();
        String rootUrl="https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a5c3c9fb001ca296&format=json";


        //ここからがURLにいろいろつぎ込むとこっすねぇ
        if(Objects.nonNull(res.getAreaPref())){
            rootUrl+="&large_area="+prefecture(res.getAreaPref());
        }

        //ランダムの処理っすねぇ
        String resUrl=rootUrl+"&count=1&start="+(rm.nextInt(availableSearch(rootUrl))+1);

        return resUrl;
    }


    public static String prefecture(String pref) throws IOException {   //完成してます 都道府県のコード取るだけやし
        // リクエストを送るURLを定義する（Json形式に値を修正）
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
