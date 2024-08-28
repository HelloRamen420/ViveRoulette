package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController{

    @GetMapping("/RouletteDinner_home")    //一番最初に起動するやつ
    public String start(){
        return "home";
    }

    @GetMapping("/RouletteDinner_form")
    public String form(){
        return "form";
    }

    @PostMapping("/RouletteDinner_res")
    public ModelAndView result(ModelAndView mav ,@RequestParam String area){
        /*APIで何を取得できるかによってここの情報変わります。
         * 地域だけは最低限ないといけないので実装してますが、まあAPI次第。*/
        mav.setViewName("result");
        return mav;
    }
}