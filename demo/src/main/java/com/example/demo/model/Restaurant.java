package com.example.demo.model;

import lombok.Data;

@Data
public class Restaurant {
    private String name; // 店名
    private String adress; // 住所
    private String tel; // 電話番号
    private String photoUrl; // 店舗の大画像URL
    private String shopUrl; // 店舗の詳細URL (ホットペッパー)
    private String openHours; // 営業時間
    private String areaPref; // 都道府県
    private String areaMun; // 市区町村
    private String areaOth; // その他
    private String genre; // ジャンル (ジャンルコード)
    private String budgetCode; // 予算コード
    private String keyword; // フリーワード/地域キーワード
    private String midnight; // 深夜営業フラグ ("1" or null)
    private String nonSmoking; // 禁煙席フラグ ("1" or null)
    private String parking; // 駐車場フラグ ("1" or null)
    private String privateRoom; // 個室ありフラグ ("1" or null)
    private String wifi; // WiFiありフラグ ("1" or null)
    private String child; // お子様連れ歓迎フラグ ("1" or null)
    private String card; // カード利用可フラグ ("1" or null)
    private String lunch; // ランチありフラグ ("1" or null)
    private int burgetMin; // 予算最小値
    private int burgetMax; // 予算最大値
    private boolean[] lestDay = new boolean[7]; // 定休日
}
