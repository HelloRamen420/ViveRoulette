package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


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
    public ModelAndView result(ModelAndView mav ,@RequestParam String area,@RequestParam String pref){

        if(area.isEmpty()){ //空文字の時の処理
            mav.addObject("nullMes",NULLMESS);  //実際にはnullではなく空文字
            mav.setViewName("form");
            return mav;
        }

        mav.addObject("pref",pref);
        mav.addObject("area",area);
        mav.setViewName("result");
        /* APIで何を取得できるかによってここの情報変わります。
         * 地域だけは最低限ないといけないので実装してますが、まあAPI次第。*/
        return mav;
    }
}