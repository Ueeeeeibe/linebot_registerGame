package com.example.linebot;

import com.google.gson.*;

public class SaveData {

    public static void saveToJson(String listName, String gameName, String imageUrl, String userId) {
        // ここに保存処理を書く
        String jsonText = Json.read();

        JsonObject jsonObject = JsonParser.parseString(jsonText).getAsJsonObject();
        // ユーザーが存在するか確認
        System.out.println(jsonObject.getAsJsonArray("userlist"));
        JsonArray okiniiriList;
        JsonArray yattemitaiList;
        for (JsonElement user : jsonObject.getAsJsonArray("userlist")) {
            // ユーザーが存在するか確認
            if (user.getAsJsonObject().get("userId").getAsString().equals(userId)) {
                okiniiriList = user.getAsJsonObject().getAsJsonArray("okiniiriList");
                yattemitaiList = user.getAsJsonObject().getAsJsonArray("yattemitaiList");
                // ユーザーが存在する場合リストに存在するか確認
                if(listName.equals("お気に入りリスト")){
                    for (JsonElement okiniiri : okiniiriList) {
                        if (okiniiri.getAsJsonObject().get("gameName").getAsString().equals(gameName)) {
                            // 既に登録されている場合
                            return;
                        }
                    }
                    // 新規登録
                    JsonObject okiniiriObj = Register.registerGame(gameName, imageUrl);
                    okiniiriList.add(okiniiriObj);
                    Json.write(jsonObject);
                    return;
                }else{
                    for (JsonElement yattematai : yattemitaiList) {
                        if (yattematai.getAsJsonObject().get("gameName").getAsString().equals(gameName)) {
                            // 既に登録されている場合
                            return;
                        }
                    }
                    // 新規登録
                    JsonObject yattemitaiObj = Register.registerGame(gameName, imageUrl);
                    yattemitaiList.add(yattemitaiObj);
                    Json.write(jsonObject);
                    return;
                }
            }
        }
        // 新規登録
        okiniiriList = new JsonArray();
        yattemitaiList = new JsonArray();
        if (listName.equals("お気に入りリスト")) {
            JsonObject okiniiriObj = Register.registerGame(gameName, imageUrl);
            okiniiriList.add(okiniiriObj);
        }
        if (listName.equals("やってみたいリスト")) {
            JsonObject yattemitaiObj = Register.registerGame(gameName, imageUrl);
            yattemitaiList.add(yattemitaiObj);
        }
        jsonObject.getAsJsonArray("userlist").add(new JsonParser().parse("{\"userId\":\"" + userId + "\",\"okiniiriList\":" + okiniiriList + ",\"yattemitaiList\":" + yattemitaiList + "}"));

        Json.write(jsonObject);
    }
}


