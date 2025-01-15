package com.example.linebot.replier;

import com.example.linebot.GameListType;
import com.linecorp.bot.messaging.model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CreateCarouselList {
    public static ArrayList<Message> createCarouselList(ArrayList<String> registerGameName, ArrayList<String> imgUrls, GameListType gameListType, boolean countToEleven) throws URISyntaxException {

        ArrayList<FlexBubble> bubbleList;
        ArrayList<Message> carouselMessageList = new ArrayList<>();
        int limitedShowNumber = 6;
        int limitedCarouselNumber = 10;
        if (countToEleven){
            //リストの数が11個以上の場合
            for (int i = 0; i < registerGameName.size() / limitedCarouselNumber; i++) {
                if(i >= limitedShowNumber){
                    continue;
                }
                ArrayList<String> subRegisterGameName = new ArrayList<>();
                ArrayList<String> subImgUrls = new ArrayList<>();
                for (int j = 0; j < limitedCarouselNumber; j++) {
                    subRegisterGameName.add(registerGameName.get(i * limitedCarouselNumber + j));
                    subImgUrls.add(imgUrls.get(i * limitedCarouselNumber + j));
                }
                bubbleList = createSubBubbleList(subRegisterGameName, subImgUrls, gameListType);
                var carousel = new FlexCarousel.Builder(bubbleList)
                        .build();
                carouselMessageList.add(new FlexMessage("カルーセル", carousel));
            }
            //リストの数が10で割り切れない場合
            System.out.println("size = " + registerGameName.size());
            ArrayList<String> subRegisterGameName = new ArrayList<>();
            ArrayList<String> subImgUrls = new ArrayList<>();
            for(int i = 0; i< registerGameName.size() % limitedCarouselNumber; i++){
                if(i >= limitedShowNumber){
                    continue;
                }
                System.out.println("index = " + (registerGameName.size() - registerGameName.size() % limitedCarouselNumber + i));
                subRegisterGameName.add(registerGameName.get(registerGameName.size() - registerGameName.size() % limitedCarouselNumber + i));
                subImgUrls.add(imgUrls.get(imgUrls.size() - imgUrls.size() % limitedCarouselNumber + i));
            }
            bubbleList = createSubBubbleList(subRegisterGameName, subImgUrls, gameListType);
            var carousel = new FlexCarousel.Builder(bubbleList)
                    .build();
            carouselMessageList.add(new FlexMessage("カルーセル", carousel));

            return carouselMessageList;
        }else{
        //リストの数が10個以下の場合
        bubbleList = createSubBubbleList(registerGameName, imgUrls, gameListType);
        var carousel = new FlexCarousel.Builder(bubbleList)
                .build();
        carouselMessageList.add(new FlexMessage("カルーセル", carousel));
        return carouselMessageList;
        }
    }

    private static ArrayList<FlexBubble> createSubBubbleList(ArrayList<String> registerGameName, ArrayList<String> imgUrls, GameListType gameListType) throws URISyntaxException {
        ArrayList<FlexBubble> bubbleList = new ArrayList<>();
        for (int i = 0; i < registerGameName.size(); i++) {
            var title = new FlexText.Builder()
                    .text(registerGameName.get(i))
                    .align(FlexText.Align.CENTER)
                    .weight(FlexText.Weight.BOLD)
                    .size("18px")
                    .build();

            var header = new FlexBox.Builder(FlexBox.Layout.VERTICAL, List.of(title))
                    .build();

            var image = new FlexImage.Builder(new URI(imgUrls.get(i)))
                    .build();

            var text = new FlexText.Builder()
                    .text(" ")
                    .wrap(true)
                    .build();

            var button = new FlexButton.Builder(new MessageAction("削除", GameListType.toString(gameListType) + "の" +registerGameName.get(i) + "を削除しました"))
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
        return bubbleList;
    }
}
