package com.example.linebot;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Register {
    public static JsonObject registerGame(String gameName, String imageUrl) {
        JsonObject listObject = new JsonObject();
        listObject.add("gameName", new JsonPrimitive(gameName));
        listObject.add("imageUrl", new JsonPrimitive(imageUrl));
        return listObject;
    }
}
