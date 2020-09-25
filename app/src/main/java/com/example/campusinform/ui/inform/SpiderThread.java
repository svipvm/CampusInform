package com.example.campusinform.ui.inform;


import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.campusinform.MainActivity;
import com.example.campusinform.Spider;

import java.io.IOException;
import java.util.ArrayList;

class Global {
    static ArrayList<String> lessonList;
}

public class SpiderThread extends Thread {
    private String username, password;
    public ArrayList<String> result;

    public SpiderThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        String encoded = Spider.getFormData(username, password);
        String[] inform = new String[]{username, password, encoded};
        String HTML = null;
        try {
            String cookie = Spider.loginSiteSendData(inform);
            HTML = Spider.getLessonByCookie(cookie);
            result = Spider.getDataByHTML(HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
