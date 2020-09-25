package com.example.campusinform.ui.course;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.campusinform.Course;
import com.example.campusinform.ui.inform.SpiderThread;

import java.util.ArrayList;



public class CourseViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<Course>> courseInform;
    static ArrayList<ArrayList<String>> lessonList;


    public CourseViewModel() throws InterruptedException {
        courseInform = new MutableLiveData<ArrayList<Course>>();

        ArrayList<Course> courses = new ArrayList<>();
//
//        for(int i = 0; i < lessonList.size(); i++) {
//            ArrayList<String> course = lessonList.get(i);
//            int day = 0;
//            switch (course.get(1)) {
//                case "一": day = 1; break;
//                case "二": day = 2; break;
//                case "三": day = 3; break;
//                case "四": day = 4; break;
//                case "五": day = 5; break;
//                case "六": day = 6; break;
//                case "日": day = 7; break;
//            }
//            int start = Integer.parseInt(course.get(2));
//            int end = Integer.parseInt(course.get(3));
//
//            courses.add(new Course(course.get(0), course.get(4), day, start, end));
//        }
        courses.add(new Course("数学", "教学#123", 1, 3, 4));
        courses.add(new Course("语文",  "实训#543", 3, 7, 8));
        courses.add(new Course("英语",  "教学#348", 5, 5, 6));

        courseInform.setValue(courses);
    }



//    public LiveData<String> getText() {
//        return mText;
//    }
    public MutableLiveData<ArrayList<Course>> getCourses() {
        return courseInform;
    }
//<p title = '课程学分：4<br/>课程属性：必修<br/>课程名称：人工智能AI<br/>上课时间：第2周 星期二 [01-02]节<br/>上课地点：教-221(濂溪)' style='text-align: left;font-size: 12px;font-weight: bold;'  >人工智能AI..</p>

//    private ArrayList<Course> getCourseList() {
//        if(Global.lessonList.size() == 0) return null;
//        ArrayList<Course> courses = new ArrayList<>();
//        int a = 0, b = 0, c = 0;
//        for(int i = 0; i < Global.lessonList.size(); i++) {
//            String course = Global.lessonList.get(i);
//            a = (a + 1) % 7 + 1;
//            b = (b + 1) % 12 + 1;
//            c = (c + 1) % 12 + 1;
//            if(c < b) {
//                int t = a; a = b; b = t;
//            }
//            courses.add(new Course(course, "", "", a, b, c));
//        }
//        return courses;
//    }
}

