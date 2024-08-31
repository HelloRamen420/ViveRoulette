package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/RouletteDinner_home")    //一番最初に起動するやつ
    public String start(){
        return "home";
    }

    @GetMapping("/RouletteDinner_form")
    public String form(){
        return "form";
    }

    @PostMapping("/RouletteDinner_res")
    public ModelAndView result(ModelAndView mav ,@RequestParam String pref) throws Exception{
        if(pref.isEmpty()){ //空文字の時の処理
            mav.addObject("nullMes",NULLMESS);  //実際にはnullではなく空文字
            mav.setViewName("form");
            return mav;
        }
        JsonNode shopsNode = RandomController.kaesi(pref);
        String name = shopsNode.get("name").asText();
        String address = shopsNode.get("address").asText();
        mav.addObject("name", name);
        mav.addObject("address", address);
        mav.addObject("pic",0);
        mav.setViewName("result");
        return mav;
    }
}
