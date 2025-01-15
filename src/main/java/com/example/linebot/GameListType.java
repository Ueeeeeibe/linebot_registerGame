package com.example.linebot;

public enum GameListType {
    OKINIIRI, YATTEMITAI, NONE;

    public static GameListType fromString(String text) {
        return switch (text) {
            case "お気に入りリスト" -> OKINIIRI;
            case "やってみたいリスト" -> YATTEMITAI;
            default -> NONE;
        };
    }

    public static String toString(GameListType type) {
        return switch (type) {
            case OKINIIRI -> "お気に入りリスト";
            case YATTEMITAI -> "やってみたいリスト";
            default -> "不明";
        };
    }
}
