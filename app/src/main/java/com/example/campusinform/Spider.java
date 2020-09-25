package com.example.campusinform;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

class Globel {
    static String loginRUL = "http://jiaowu.jvtc.jx.cn/jsxsd/xk/LoginToXk";
    static String lessonRUL = "http://jiaowu.jvtc.jx.cn/jsxsd/framework/main_index_loadkb.jsp";
    static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
//    Location: http://jiaowu.jvtc.jx.cn/jsxsd/framework/xsMain.jsp
}

public class Spider {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
//        String[] inform = getFormData();
//        try {
//            String cookie = loginSiteSendData(inform);
//            System.out.println("Hello Debug!");
//            String HTML = getLessonByCookie(cookie);
//            ArrayList<String> lesson = getDataByHTML(HTML);
//            for(String s : lesson) {
//                System.out.println(s);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//<p title = '课程学分：4<br/>课程属性：必修<br/>课程名称：人工智能AI<br/>上课时间：第2周 星期二 [01-02]节<br/>上课地点：教-221(濂溪)' style='text-align: left;font-size: 12px;font-weight: bold;'  >人工智能AI..</p>

    public static ArrayList<String> getDataByHTML(String html) {
//        System.out.println(html);
        ArrayList<String> lesson = new ArrayList<>();
        String pattern = "<p title = '(.*)' style";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(html);
//        String[] patSet = new String[]{"课程名称：(.*?)<br/>上", "星期(.*?) ", "\\[(.*?)-", "\\-(.*)\\]节", "上课地点：(.*)<b"};
        while(m.find()) {
//            ArrayList<String> result = new ArrayList<>();
            String goal = m.group(1);

//            System.out.println(goal);
//            for(String patStr : patSet) {
//                Pattern pat = Pattern.compile(patStr);
//                assert goal != null;
//                Matcher mat = pat.matcher(goal + "<br/>");
//                if(mat.find()) {
////                    System.out.println(mat.group(1));
//                    result.add(mat.group(1));
//                }
////                System.out.println("");
//            }
            lesson.add(goal);

        }
//        for(String s : lesson) {
//            System.out.println(s);
//        }
        return lesson;
    }

    public static String getLessonByCookie(String cookie) throws IOException {
//        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        URL url = new URL(Globel.lessonRUL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.setRequestProperty("User-Agent", Globel.userAgent);
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        System.out.println(cookie);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
//        connection.connect();

//        Date date = new Date();
//        String sjmsValue = "956C06E11ABA1DDFE0530100007FC305";
//        String rq = String.format("%s-%s-%s", 1900 + date.getYear(), date.getMonth() + 1, date.getDate());
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String rq = df.format(t);
        String sjmsValue = "956C06E11ABA1DDFE0530100007FC305";
        String data = String.format("rq=%s&sjmsValue=%s", rq, sjmsValue);

        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(data);
        out.flush();
        out.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder html = new StringBuilder(new String(""));
        String line;
        while ((line = br.readLine()) != null) {
            html.append(line).append("\n");
        }
//        System.out.println(html);
        return html.toString();
    }

    public static String getFormData(String username, String password) {
//        Scanner reader = new Scanner(System.in);
//        System.out.println("请输入密码");
//        String username = "", password = new String("");
//        password = reader.nextLine();
//        System.out.println(password);
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeUsername = encoder.encodeToString(username.getBytes());
        String encodePassword = encoder.encodeToString(password.getBytes());
//        String content = username + "%%%" + password;
        String encoded = encodeUsername + "%%%" + encodePassword;
//        String encoded = encoder.encodeToString(content.getBytes());
//        System.out.println(encoded);
        return encoded;
    }

    public static String loginSiteSendData(String[] inform) throws IOException {
//        Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        URL url = new URL(Globel.loginRUL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", Globel.userAgent);
//        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        connection.connect();

        String username = "userAccount=" + URLEncoder.encode(inform[0], "UTF-8");
        String passwrod = "userPassword=" + URLEncoder.encode(inform[1], "UTF-8");
        String encoded = "encoded=" + URLEncoder.encode(inform[2], "UTF-8");
        String data = username + "&" + passwrod + "&" + encoded;
//        System.out.println(data);
//        System.out.println(Arrays.toString(data.getBytes()));
//        connection.getOutputStream().write(data.getBytes());
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(data);
        out.flush();
        out.close();


        String cookie = connection.getHeaderField("set-cookie");
        String pattern = "(\\S+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(cookie);
        if(m.find()) {
            cookie = m.group(0);
            connection.disconnect();
            return cookie;
//            System.out.println(m.group(0));
        } else {
            System.out.println("ERROR!");
        }
//        System.out.println(cookie);


        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK){
            throw new IOException();
        }

        return "";
    }

}
