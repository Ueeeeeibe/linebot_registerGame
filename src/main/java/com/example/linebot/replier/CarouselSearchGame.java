package com.example.linebot.replier;

import com.example.linebot.GameListType;
import com.example.linebot.Replier;
import com.linecorp.bot.messaging.model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CarouselSearchGame implements Replier {

    private final String inputText;
    private final ArrayList<String> imgUrls;
    private final GameListType gameListType;

    public CarouselSearchGame(String inputText, ArrayList<String> imgUrls, GameListType gameListType) {
        this.inputText = inputText;
        this.imgUrls = imgUrls;
        this.gameListType = gameListType;
    }

    @Override
    public Message reply() {
        // カルーセルメッセージを作る

        ArrayList<FlexBubble> bubbleList = new ArrayList<>();
        //for文でリストに追加
        for (int i = 0; i < imgUrls.size(); i++) {
            var title = new FlexText.Builder()
                    .text(inputText)
                    .align(FlexText.Align.CENTER)
                    .weight(FlexText.Weight.BOLD)
                    .size("18px")
                    .build();

            var header = new FlexBox.Builder(FlexBox.Layout.VERTICAL, List.of(title))
                    .build();

            URI uri;
            try {
                uri = new URI(imgUrls.get(i));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            var image = new FlexImage.Builder(uri)
                    .build();

            var text = new FlexText.Builder()
                    .text(" ")
                    .wrap(true)
                    .build();

            var button = new FlexButton.Builder(new MessageAction("登録", GameListType.toString(gameListType) + "に" + inputText + "を画像" + (i + 1) + "で登録しました"))
                    .style(FlexButton.Style.SECONDARY)
                    .build();

            var body = new FlexBox.Builder(FlexBox.Layout.VERTICAL, List.of(text, button))
                    .build();

            var bubble = new FlexBubble.Builder()
                    .header(header)
                    .hero(image)
                    .body(body)
                    .build();
            bubbleList.add(bubble);
        }

        var carousel = new FlexCarousel.Builder(bubbleList)
                .build();

        return new FlexMessage("カルーセル表示", carousel);
    }
}