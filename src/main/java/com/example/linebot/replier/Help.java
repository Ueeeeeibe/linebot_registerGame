package com.example.linebot.replier;

import com.example.linebot.Replier;
import com.linecorp.bot.messaging.model.*;

import java.util.List;

public class Help implements Replier {
    @Override
    public Message reply() {
        String title = "Help";
        String description = """
                
                このBotは、ボードゲーム関連のゲームの検索・登録をすることができます。
                コマンド一覧
                -リストタイプ選択(登録)："登録","ゲーム登録","登録する","ボドゲ登録"などと送信すると、登録するリストタイプを選択するボタンが表示されます。
                -検索：-リストタイプ選択(登録)コマンドを送信したあとに登録するリストを選び、そのあとに入力した名前で画像を検索し、検索結果を5つ表示します。
                -登録：-検索コマンドを送信したあとに表示されたリストの登録ボタンを押すと、そのゲームを登録することができます。
                -リストタイプ選択(表示)："表示","表示する","リスト表示","リストを表示"と送信すると、表示するリストタイプを選択するボタンが表示されます。
                -表示：-リストタイプ選択(表示)コマンドを送信したあとに表示するリストを選び、そのリストに登録されているゲームを表示します。
                -削除：-表示コマンドを送信したあとに表示されたリストの削除ボタンを押すと、そのゲームを削除することができます。
                -ヘルプ："help","Help","HELP","ヘルプ","へるぷ"と送信すると、このヘルプを表示します。
                
                裏技："お気に入りリスト","やってみたいリスト"と送信すると、ボタンを押すことなく、リストタイプを選択した状態でゲームの検索ができます。
                "お気に入りリスト表示","やってみたいリスト表示"と送信すると、ボタンを押すことなく、リストタイプを選択した状態でゲームの表示ができます。
                """;

        FlexText flexTitle = new FlexText.Builder()
                .align(FlexText.Align.CENTER)
                .weight(FlexText.Weight.BOLD)
                .text(title)
                .build();

        FlexText flexDescription = new FlexText.Builder()
                .wrap(true)
                .text(description)
                .build();

        FlexSeparator flexSeparator = new FlexSeparator.Builder()
                .build();

        FlexBox flexBox = new FlexBox.Builder(FlexBox.Layout.VERTICAL, List.of(flexTitle, flexSeparator, flexDescription))
                .build();

        FlexBubble flexBubble = new FlexBubble.Builder()
                .body(flexBox)
                .build();

        return new FlexMessage("Help", flexBubble);
    }
}
