package com.example.campusinform.ui.inform;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.campusinform.Spider;

import java.util.ArrayList;

class Global {
    static ArrayList<String> lessonList;
}

public class SpiderThread extends Thread {
    private String username, password;
    public ArrayList<String> result;

    SpiderThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        String encoded = Spider.getFormData(username, password);
        String[] inform = new String[]{username, password, encoded};
        try {
            String cookie = Spider.loginSiteSendData(inform);
            String HTML = Spider.getLessonByCookie(cookie);
            result = Spider.getDataByHTML(HTML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
