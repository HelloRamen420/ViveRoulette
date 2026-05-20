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

        // 都道府県
        if(Objects.nonNull(res.getAreaPref()) && !res.getAreaPref().isEmpty()){
            String prefCode = prefecture(res.getAreaPref());
            if (prefCode != null) {
                rootUrl += "&large_area=" + prefCode;
            }
        }

        // フリーワード / 地域キーワード
        if(Objects.nonNull(res.getKeyword()) && !res.getKeyword().isEmpty()){
            rootUrl += "&keyword=" + java.net.URLEncoder.encode(res.getKeyword(), "UTF-8");
        }

        // ジャンル
        if(Objects.nonNull(res.getGenre()) && !res.getGenre().isEmpty()){
            rootUrl += "&genre=" + res.getGenre();
        }

        // 予算
        if(Objects.nonNull(res.getBudgetCode()) && !res.getBudgetCode().isEmpty()){
            rootUrl += "&budget=" + res.getBudgetCode();
        }

        // 深夜営業あり
        if(Objects.nonNull(res.getMidnight()) && "1".equals(res.getMidnight())){
            rootUrl += "&midnight=1";
        }

        // 禁煙席あり
        if(Objects.nonNull(res.getNonSmoking()) && "1".equals(res.getNonSmoking())){
            rootUrl += "&non_smoking=1";
        }

        // 駐車場あり
        if(Objects.nonNull(res.getParking()) && "1".equals(res.getParking())){
            rootUrl += "&parking=1";
        }

        // 個室あり
        if(Objects.nonNull(res.getPrivateRoom()) && "1".equals(res.getPrivateRoom())){
            rootUrl += "&private_room=1";
        }

        // WiFiあり
        if(Objects.nonNull(res.getWifi()) && "1".equals(res.getWifi())){
            rootUrl += "&wifi=1";
        }

        // お子様連れ歓迎
        if(Objects.nonNull(res.getChild()) && "1".equals(res.getChild())){
            rootUrl += "&child=1";
        }

        // カード利用可
        if(Objects.nonNull(res.getCard()) && "1".equals(res.getCard())){
            rootUrl += "&card=1";
        }

        // ランチあり
        if(Objects.nonNull(res.getLunch()) && "1".equals(res.getLunch())){
            rootUrl += "&lunch=1";
        }

        // 全件数を確認
        int available = availableSearch(rootUrl);
        if (available <= 0) {
            return null; // 条件に合うお店がない場合はnullを返す
        }

        // ランダムの処理（全件数の中から1件だけランダムな位置のデータを取得）
        int startPos = rm.nextInt(available) + 1;
        String resUrl = rootUrl + "&count=1&start=" + startPos;

        return resUrl;
    }



    /*めっちゃ完成してます
    * さすがにもう施すとこはないかと(フラグ)
    *
    * 都道府県のcode一覧みたいなとこからfor文いい感じに回してます
    * まあそんなむずいことはしてないかなと
    *
    * ぜひ日本語で読んでみてください
    * jsonの階層構造が把握できればx読めるはずです。*/

    public static String prefecture(String pref) throws IOException {

        String prefCode=null;
        String url = UrlConst.PREF_SEARCH;
        // http通信を行う
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        try {

            //いつもの
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            //ここまで

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prefCode;
    }
}
