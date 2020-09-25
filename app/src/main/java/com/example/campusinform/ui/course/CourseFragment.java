package com.example.campusinform.ui.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.campusinform.Course;
import com.example.campusinform.R;
import com.example.campusinform.ui.inform.SpiderThread;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CourseFragment extends Fragment {

    private CourseViewModel courseViewModel;
    private int maxCoursesNumber = 0;
    private int currentCoursesNumber = 0;
    private ArrayList<Course> courseList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        courseViewModel =
                ViewModelProviders.of(this).get(CourseViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_course, container, false);

//        Course[] courses = new Course[3];
//        courses[0] = new Course("数学", "刘某", "教学#123", 1, 3, 4);
//        courses[1] = new Course("语文", "张某", "实训#543", 3, 7, 8);
//        courses[2] = new Course("英语", "潘某", "教学#348", 5, 5, 6);


        checkUpdata();

        createHardLeftView(root);
        getDataAndCreate(root);

        return root;
    }

    private void checkUpdata() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Account", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String passwrod = sharedPreferences.getString("password", "");
        if("".equals(username)) {
            Toast.makeText(getActivity(), "请登录！", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SpiderThread spider = new SpiderThread(username, passwrod);
            spider.start();
            spider.join();
            if(spider.result.size() == 0) throw new Exception();
            saveTableData(spider.result);
            Toast.makeText(getActivity(), "信息更新完成！", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
//            System.out.println("网络不佳或账号密码错误！");
//            Toast.makeText(getActivity(), "网络不佳或账号密码错误！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "账号密码错误！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void saveTableData(ArrayList<String> result) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i = 0; i < result.size(); i++) {
            String course = result.get(i);
            editor.putString(Integer.toString(i), course);
            System.out.println(course);
        }
        editor.apply();
    }

    private void getDataAndCreate(View root) {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String courseStr;
        int index = 0;
        String[] patSet = new String[]{"课程名称：(.*?)<br/>上", "星期(.*?) ", "\\[(.*?)-", "\\-(.*)\\]节", "上课地点：(.*)<b"};
        while(true) {
            courseStr = sharedPreferences.getString(Integer.toString(index),"-");
            if("-".equals(courseStr)) break;
            System.out.println(courseStr);
            ArrayList<String> courseSign = new ArrayList<>();
            for(String patStr : patSet) {
                Pattern pat = Pattern.compile(patStr);
                assert courseStr != null;
                Matcher mat = pat.matcher(courseStr + "<br/>");
                if(mat.find()) {
//                    System.out.println(mat.group(1));
                    courseSign.add(mat.group(1));
                }
//                System.out.println("");
            }
            int day = 0;
            switch (courseSign.get(1)) {
                case "一": day = 1; break;
                case "二": day = 2; break;
                case "三": day = 3; break;
                case "四": day = 4; break;
                case "五": day = 5; break;
                case "六": day = 6; break;
                case "日": day = 7; break;
            }
            int start = Integer.parseInt(courseSign.get(2));
            int end = Integer.parseInt(courseSign.get(3));
            createItemCourseView(root, new Course(courseSign.get(0), courseSign.get(4), day, start, end));
//            courseList.add(new Course(courseSign.get(0), courseSign.get(4), day, start, end));
            index += 1;
        }
    }


    private void createLeftView(View root, Course course) {
        int endNumber = course.getEnd();
//        LinearLayout leftViewLayout = (LinearLayout) root.findViewById(R.id.left_view_layout);

        for (int i = 0; i < endNumber - maxCoursesNumber; i++) {
            View view = LayoutInflater.from(root.getContext()).inflate(R.layout.left_view, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 220);
            view.setLayoutParams(params);

            TextView text = view.findViewById(R.id.class_number_text);
            text.setText(String.valueOf(++currentCoursesNumber));

            LinearLayout leftViewLayout = root.findViewById(R.id.left_view_layout);
            leftViewLayout.addView(view);
        }
        maxCoursesNumber = endNumber;
//        return root;
    }

    @SuppressLint("ResourceAsColor")
    private void createHardLeftView(View root) {
        int count = 10;
        for (int i = 0; i < count; i++) {
            View view = LayoutInflater.from(root.getContext()).inflate(R.layout.left_view, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(110, 220);
            view.setLayoutParams(params);

            TextView text = view.findViewById(R.id.class_number_text);
            text.setText(String.valueOf(++currentCoursesNumber));

            LinearLayout leftViewLayout = root.findViewById(R.id.left_view_layout);
            leftViewLayout.addView(view);
        }
    }

    //创建单个课程视图
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void createItemCourseView(View root, final Course course) {
        int getDay = course.getDay();
        int dayId = 0;

        switch (getDay) {
            case 1: dayId = R.id.monday; break;
            case 2: dayId = R.id.tuesday; break;
            case 3: dayId = R.id.wednesday; break;
            case 4: dayId = R.id.thursday; break;
            case 5: dayId = R.id.friday; break;
            case 6: dayId = R.id.saturday; break;
            case 7: dayId = R.id.weekday; break;
        }
        RelativeLayout day = root.findViewById(dayId);

        int height = 220;
        final View v = LayoutInflater.from(root.getContext()).inflate(R.layout.course_card, null); //加载单个课程布局
        v.setY(height * (course.getStart() - 1)); //设置开始高度,即第几节课开始
        int lenght = (course.getEnd() - course.getStart() + 1) * height - 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, lenght); //设置布局高度,即跨多少节课
        v.setLayoutParams(params);


        TextView text = v.findViewById(R.id.text_view);
        text.setText(course.getCourseName() + "\n" + course.getClassRoom()); //显示课程名
//        text.setBackgroundColor(R.color.black);

        day.addView(v);
    }


}


