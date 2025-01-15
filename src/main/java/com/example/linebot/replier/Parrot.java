package com.example.linebot.replier;

import com.example.linebot.Replier;
import com.linecorp.bot.messaging.model.Message;
import com.linecorp.bot.messaging.model.TextMessage;

// おうむ返しをする
public class Parrot implements Replier {

    private String text;

    public Parrot(String text) {
        this.text = text;
    }

    //LineBotの返信メッセージを作る
    @Override
    public Message reply() {
        return new TextMessage(text);
    }
}
