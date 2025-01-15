package com.example.linebot.replier;

import com.example.linebot.Replier;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

import java.time.LocalTime;

public class Greet implements Replier {

    @Override
    public Message reply() {
        //現在時刻を取得する
        LocalTime lt = LocalTime.now();
        //現在時刻（時）を調べる
        int hour = lt.getHour();
        if (hour >= 17) {
            return new TextMessage("こんばんは！");
        }
        if (hour >= 11) {
            return new TextMessage("こんにちは！");
        }
        return new TextMessage("おはよう！");
    }

}