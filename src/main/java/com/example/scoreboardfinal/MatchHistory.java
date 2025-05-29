package com.example.scoreboardfinal;

import java.util.ArrayList;
import java.util.List;

public class MatchHistory {

    private static final List<String> history = new ArrayList<>();

    public static void addRecord(String record) {
        history.add(record);
    }

    public static List<String> getHistory() {
        return new ArrayList<>(history);
    }

    public static void removeRecord(String record) {
        history.remove(record);
    }
}
