package com.example.demo.controller;

public class GetUrl {
    public static String fragSet(String... str) { // これはセッタではなくfragmentをつなげるだけです
        String frag = "";
        for (String s : str) {
            frag = s + "&";
        }
        return frag;
    }

    public static String emptyCheck(String... str) {
        if (str.isEmpty())
            return "";
        return str + "&";
    }
}
