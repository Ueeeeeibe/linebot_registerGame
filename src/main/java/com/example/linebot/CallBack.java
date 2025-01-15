package com.example.linebot;

import com.example.linebot.replier.*;
import com.linecorp.bot.messaging.model.*;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@LineMessageHandler
public class CallBack {

    private final HashMap<String, UserData> userDataMap = new HashMap<>();

    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public ArrayList<Message> handleMessage(MessageEvent event) throws IOException, URISyntaxException {
        // イベントからユーザIDを取得
        String userId = event.source().userId();
        MessageContent mc = event.message();
        switch (mc) {
            case TextMessageContent tmc:
                // MessageContent が文字列のメッセージ（TextMessageContent)だったとき、tmc = mc にする
                // その上で、送信された文字列を textに入れる
                String text = tmc.text();
                return buildReply(text, userId);
            default:
                // それ以外のメッセージだったとき
                throw new RuntimeException("対応していないメッセージ");
        }
    }

    public TemplateMessage buildConfirm() {
        // 確認メッセージを作る
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new MessageAction("お気に入り","お気に入りリスト"));
        actions.add(new MessageAction("やってみたい","やってみたいリスト"));
        return new TemplateMessage("登録するゲームを入力してください", new ConfirmTemplate("登録するリストを選んでください", actions));
    }

    public TemplateMessage buildConfirmShow() {
        // 確認メッセージを作る
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new MessageAction("お気に入り","お気に入りリスト表示"));
        actions.add(new MessageAction("やってみたい","やってみたいリスト表示"));
        return new TemplateMessage("リストを表示", new ConfirmTemplate("表示するリストを選んでください", actions));
    }

    public ArrayList<Message> buildReply(String text, String userId) throws IOException, URISyntaxException {
        UserData userData = userDataMap.get(userId);
        if (userData == null) {
            userData = new UserData();
            userDataMap.put(userId, userData);
        }
        if(userData.getSearchingGames()){
            userData.setSearchingGames(false);
            ArrayList<String> urls = WebScraping.getImageUrls(text);
            urls.add("https://thumb.ac-illust.com/18/185fe60d543230116f69f3b41238a90d_t.jpeg");
            userData.setUrls(urls);
            return new ArrayList<>(List.of(new CarouselSearchGame(text, urls, userData.getJudgeListType()).reply()));
        }

        switch (text) {
            case "お気に入りリスト表示":
            case "やってみたいリスト表示":
                return buildReplyMessageList(text, userId);
            default:
                Message message = buildReplyMessage(text, userId, userData);
                if (message == null) return new ArrayList<>();
                return new ArrayList<>(List.of(message));
        }
    }

    public ArrayList<Message> buildReplyMessageList(String text, String userId) throws URISyntaxException {
        GameListType judgeListType = GameListType.fromString(text.substring(0, text.length() - 2));
        ArrayList<String> imgUrls = Json.getImgUrls(judgeListType, userId);
        ArrayList<String> gameNames = Json.getGameNames(judgeListType, userId);
        boolean countToEleven = gameNames.size() >= 11;
        if (gameNames.isEmpty()) {
            return new ArrayList<>(List.of(new TextMessage("リストに登録されているゲームはありません")));
        }
        if (gameNames.size() > 50) {
            return new ArrayList<>(List.of(new TextMessage("50個以上ゲームを表示することはできません")));
        }
        return CreateCarouselList.createCarouselList(gameNames, imgUrls, judgeListType, countToEleven);
    }

    public Message buildReplyMessage(String text, String userId, UserData userData) {
        switch (text) {
            // 送信された文字列の内容で、返答するメッセージのクラスを選んでインスタンス化し、返答メッセージを作る
            // 初期設定では、どんな文字を送ってもおうむ返しをする
            case "おみくじ":
                Omikuji omikuji = new Omikuji();
                return omikuji.reply();
            case "こんにちは":
                Greet greet = new Greet();
                return greet.reply();
            case "登録","ゲーム登録","登録する","ゲーム登録する","ボードゲーム登録","ボドゲ登録","TRPG登録","マーダーミステリー登録","マダミス登録","ボドゲ登録する","TRPG登録する","マダミス登録する":
                return buildConfirm();
            case "お気に入りリスト":
                userData.setSearchingGames(true);
                userData.setJudgeListType(GameListType.OKINIIRI);
                return new TextMessage("検索するゲーム名を入力してください");
            case "やってみたいリスト":
                userData.setSearchingGames(true);
                userData.setJudgeListType(GameListType.YATTEMITAI);
                return new TextMessage("検索するゲーム名を入力してください");
            case "表示","表示する","リスト表示","リストを表示":
                return buildConfirmShow();
            case "help","Help","HELP","ヘルプ","へるぷ":
                return new Help().reply();
            default:
                String registerRegex = "(お気に入りリスト|やってみたいリスト)に(.+)を画像([1-5])で登録しました";
                String deleteRegex = "(お気に入りリスト|やってみたいリスト)の(.+)を削除しました";
                if(text.matches(registerRegex)){
                    String listName = text.replaceFirst(registerRegex, "$1");
                    String gameName = text.replaceFirst(registerRegex, "$2");
                    int imgUrlIndex = Integer.parseInt(text.replaceFirst(registerRegex, "$3")) - 1;
                    // 保存処理
                    SaveData.saveToJson(listName, gameName, userData.getUrls().get(imgUrlIndex), userId);
                    return null;
                }else if (text.matches(deleteRegex)){
                    String listName = text.replaceFirst(deleteRegex, "$1");
                    String gameName = text.replaceFirst(deleteRegex, "$2");
                    // 削除処理
                    Json.deleteGame(GameListType.fromString(listName), userId, gameName);
                    return null;
                }
                Parrot parrot = new Parrot(text);
                return parrot.reply();
        }
    }
}