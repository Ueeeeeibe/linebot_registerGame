package com.example.linebot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebScraping {

    public static ArrayList<String> getImageUrls(String searchText) throws IOException {
        Document document = Jsoup.connect("https://www.google.com/search?q=" + searchText + "&udm=2").get();
        String documentText = document.toString();

        // 検索する文字列
        Pattern pattern = Pattern.compile("https://encrypted-tbn0\\.gstatic\\.com/images\\?q\\\\u003dtbn:.+?\\\\u0026s");
        // 検索対象の文字列
        Matcher matcher = pattern.matcher(documentText);
        ArrayList<String> imgUrls = new ArrayList<>();

        // 検索結果を一つずつ探してimgUrlsに追加
        while (matcher.find()) {
            String url = matcher.group();
            url = url.replace("\\u003d", "=").replace("\\u0026", "&");
            imgUrls.add(url);
        }

        HashSet<String> imgUrlSet = new HashSet<>(imgUrls);
        ArrayList<String> imgUrlSetNumber = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            imgUrlSetNumber.add(imgUrlSet.toArray()[i].toString());
        }
        return imgUrlSetNumber;
    }
}
