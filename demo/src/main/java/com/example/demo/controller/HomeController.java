package com.example.demo.controller;

import java.net.URI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
<<<<<<< HEAD
import java.net.URL;

@Controller
public class HomeController {
    public static String KEYPATH = "4dcd88f03d99c82e";
=======
import java.io.*;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class HomeController{
    final static String NULLMESS="必要な情報を入力してください";
>>>>>>> origin/main

    @GetMapping("/RouletteDinner_home") // 一番最初に起動するやつ
    public String start() {
        return "home";
    }

    @GetMapping("/RouletteDinner_form")
    public String form() {
        return "form";
    }

    @PostMapping("/RouletteDinner_res")
<<<<<<< HEAD
    public ModelAndView result(ModelAndView mav, @RequestParam String pref, @RequestParam String area,
            @RequestParam String genre) {

        try {
            String frag = GetUrl.fragSet();

            // URIを使用してURLを構築
            URI uri = new URI("https", "//webservice.recruit.co.jp", "/hotpepper/gourmet/v1",
                    "?key=value&service_area=value&midlle_area=value&keyword=value");

            // URIからURLを取得
            URL url = uri.toURL();

            // URLの各部分の取得
            System.out.println("URL: " + url);
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Fragment: " + url.getRef());

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * APIで何を取得できるかによってここの情報変わります。
         * 地域だけは最低限ないといけないので実装してますが、まあAPI次第。
         */

=======
    public ModelAndView result(ModelAndView mav ,@RequestParam String area) throws Exception{
        if(area.isEmpty()){ //空文字の時の処理
            mav.addObject("nullMes",NULLMESS);  //実際にはnullではなく空文字
            mav.setViewName("form");
            return mav;
        }
        JsonNode shopsNode = RandomController.kaesi(area);
        String name = shopsNode.get("name").asText();
        String address = shopsNode.get("address").asText();
        mav.addObject("name", name);
        mav.addObject("address", address);
>>>>>>> origin/main
        mav.setViewName("result");
        return mav;
    }
}
