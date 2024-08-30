package com.example.demo.controller;

public class GetUrl {
    public static String fragSet(String... str) { // これはセッタではなくfragmentをつなげるだけです
        String frag = "";
        for (String s : str) {
            frag = s + "&";
        }
        return frag;
    }
}
