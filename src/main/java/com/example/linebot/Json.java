package com.example.linebot;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Json {

    private static final Path jsonPath = Path.of("./src/main/java/com/example/linebot/user_data.json");

    static {
        // ファイルが存在しない場合は作成
        try {

            Files.createFile(jsonPath);
            // ファイルが存在しない場合は{"userlist":[]}を書き込む
            try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(jsonPath))) {
                writer.beginObject();
                writer.name("userlist").beginArray().endArray();
                writer.endObject();
            }
        } catch (IOException e) {
            // ファイルが存在する場合は何もしない
        }
    }

    public static ArrayList<String> getImgUrls(GameListType gameListType, String userId) {
        JsonObject jsonObject = JsonParser.parseString(read()).getAsJsonObject();
        for (JsonElement user : jsonObject.getAsJsonArray("userlist")) {
            if(user.getAsJsonObject().get("userId").getAsString().equals(userId)){
                ArrayList<String> imgUrls = new ArrayList<>();
                if(gameListType == GameListType.OKINIIRI){
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("okiniiriList")) {
                        imgUrls.add(game.getAsJsonObject().get("imageUrl").getAsString());
                    }
                }else{
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("yattemitaiList")) {
                        imgUrls.add(game.getAsJsonObject().get("imageUrl").getAsString());
                    }
                }
                return imgUrls;
            }
        }
        return new ArrayList<>();
    }

    public static ArrayList<String> getGameNames(GameListType gameListType, String userId) {

        JsonObject jsonObject = JsonParser.parseString(read()).getAsJsonObject();
        for (JsonElement user : jsonObject.getAsJsonArray("userlist")) {
            if(user.getAsJsonObject().get("userId").getAsString().equals(userId)){
                ArrayList<String> gameNames = new ArrayList<>();
                if(gameListType == GameListType.OKINIIRI){
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("okiniiriList")){
                        gameNames.add(game.getAsJsonObject().get("gameName").getAsString());
                    }
                }else{
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("yattemitaiList")){
                        gameNames.add(game.getAsJsonObject().get("gameName").getAsString());
                    }
                }
                return gameNames;
            }
        }
        return new ArrayList<>();
    }

    public static void deleteGame(GameListType gameListType, String userId, String gameName) {
        JsonObject jsonObject = JsonParser.parseString(read()).getAsJsonObject();
        for (JsonElement user : jsonObject.getAsJsonArray("userlist")) {
            if(user.getAsJsonObject().get("userId").getAsString().equals(userId)) {
                if(gameListType == GameListType.OKINIIRI){
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("okiniiriList")){
                        if(game.getAsJsonObject().get("gameName").getAsString().equals(gameName)){
                            user.getAsJsonObject().getAsJsonArray("okiniiriList").remove(game);
                            write(jsonObject);
                            return;
                        }
                    }
                }else{
                    for(JsonElement game : user.getAsJsonObject().getAsJsonArray("yattemitaiList")){
                        if(game.getAsJsonObject().get("gameName").getAsString().equals(gameName)){
                            user.getAsJsonObject().getAsJsonArray("yattemitaiList").remove(game);
                            write(jsonObject);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void write(JsonObject jsonObject) {
        try (JsonWriter writer = new JsonWriter(Files.newBufferedWriter(jsonPath))) {
            new Gson().toJson(jsonObject, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read() {
        try {
            return Files.readString(jsonPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
