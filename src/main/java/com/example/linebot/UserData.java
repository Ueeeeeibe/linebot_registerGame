package com.example.linebot;

import java.util.ArrayList;

public class UserData {
    private ArrayList<String> urls;
    private boolean searchingGames = false;
    private GameListType judgeListType = GameListType.NONE;

    public ArrayList<String> getUrls() {
        return urls;
    }
    public boolean getSearchingGames() {
        return searchingGames;
    }
    public GameListType getJudgeListType() {
        return judgeListType;
    }
    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }
    public void setSearchingGames(boolean searchingGames) {
        this.searchingGames = searchingGames;
    }
    public void setJudgeListType(GameListType judgeListType) {
        this.judgeListType = judgeListType;
    }
}
