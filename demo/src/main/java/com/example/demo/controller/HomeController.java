package com.example.demo.controller;

import com.example.demo.model.Restaurant;
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

    @GetMapping("/")
    public String root(){
        return "redirect:/RouletteDinner_home";
    }

    @GetMapping("/RouletteDinner_home")    //一番最初に起動するやつ
    public String start(){
        return "home";
    }

    @GetMapping("/RouletteDinner_form")
    public String form(){
        return "form";
    }

    @PostMapping("/RouletteDinner_res")
    public ModelAndView result(
            ModelAndView mav,
            @RequestParam(required = false, defaultValue = "") String pref,
            @RequestParam(required = false, defaultValue = "") String area,
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false, defaultValue = "") String budget,
            @RequestParam(required = false, defaultValue = "") String midnight,
            @RequestParam(required = false, defaultValue = "") String nonSmoking,
            @RequestParam(required = false, defaultValue = "") String parking,
            @RequestParam(required = false, defaultValue = "") String privateRoom,
            @RequestParam(required = false, defaultValue = "") String wifi,
            @RequestParam(required = false, defaultValue = "") String child,
            @RequestParam(required = false, defaultValue = "") String card,
            @RequestParam(required = false, defaultValue = "") String lunch) throws Exception {

        if(pref.isEmpty()){ // 都道府県は必須
            mav.addObject("nullMes", NULLMESS);
            mav.setViewName("form");
            return mav;
        }

        Restaurant searchCriteria = new Restaurant();
        searchCriteria.setAreaPref(pref);
        searchCriteria.setKeyword(area);
        searchCriteria.setGenre(genre);
        searchCriteria.setBudgetCode(budget);
        searchCriteria.setMidnight(midnight);
        searchCriteria.setNonSmoking(nonSmoking);
        searchCriteria.setParking(parking);
        searchCriteria.setPrivateRoom(privateRoom);
        searchCriteria.setWifi(wifi);
        searchCriteria.setChild(child);
        searchCriteria.setCard(card);
        searchCriteria.setLunch(lunch);

        JsonNode shopsNode = RandomController.kaesi(searchCriteria);

        // 条件に合う店舗がなかった場合
        if (shopsNode == null || 
            !shopsNode.has("results") || 
            !shopsNode.get("results").has("shop") || 
            shopsNode.get("results").get("shop").size() == 0) {
            
            mav.addObject("nullMes", "該当するお店が見つかりませんでした。条件を変更してもう一度お試しください。");
            // フォームに入力値を残すためにパラメータも戻す
            mav.addObject("pref", pref);
            mav.addObject("area", area);
            mav.addObject("genre", genre);
            mav.addObject("budget", budget);
            mav.addObject("midnight", midnight);
            mav.addObject("nonSmoking", nonSmoking);
            mav.addObject("parking", parking);
            mav.addObject("privateRoom", privateRoom);
            mav.addObject("wifi", wifi);
            mav.addObject("child", child);
            mav.addObject("card", card);
            mav.addObject("lunch", lunch);
            mav.setViewName("form");
            return mav;
        }

        // 1件目の店舗ノードを取得
        JsonNode shopNode = shopsNode.get("results").get("shop").get(0);

        Restaurant resultRestaurant = new Restaurant();
        resultRestaurant.setName(shopNode.get("name").asText());
        resultRestaurant.setAdress(shopNode.get("address").asText());
        
        if (shopNode.has("tel")) {
            resultRestaurant.setTel(shopNode.get("tel").asText());
        }
        
        if (shopNode.has("photo") && shopNode.get("photo").has("pc") && shopNode.get("photo").get("pc").has("l")) {
            resultRestaurant.setPhotoUrl(shopNode.get("photo").get("pc").get("l").asText());
        }
        
        if (shopNode.has("urls") && shopNode.get("urls").has("pc")) {
            resultRestaurant.setShopUrl(shopNode.get("urls").get("pc").asText());
        }
        
        if (shopNode.has("open")) {
            resultRestaurant.setOpenHours(shopNode.get("open").asText());
        }

        // ビューにオブジェクトを渡す
        mav.addObject("restaurant", resultRestaurant);
        
        // 検索パラメータをそのまま引き渡す（もう一度ルーレット用）
        mav.addObject("pref", pref);
        mav.addObject("area", area);
        mav.addObject("genre", genre);
        mav.addObject("budget", budget);
        mav.addObject("midnight", midnight);
        mav.addObject("nonSmoking", nonSmoking);
        mav.addObject("parking", parking);
        mav.addObject("privateRoom", privateRoom);
        mav.addObject("wifi", wifi);
        mav.addObject("child", child);
        mav.addObject("card", card);
        mav.addObject("lunch", lunch);
        
        mav.setViewName("result");
        return mav;
    }
}
