package com.example.demo.model;

import lombok.Data;

@Data
public class Restaurant{
    private String areaPref;    //都道府県
    private String areaMun;     //市区町村
    private String areaOth;     //その他
    private String genre;       //ジャンル
    private int burgetMin;      //予算最小値
    private int burgetMax;      //予算最大値
}