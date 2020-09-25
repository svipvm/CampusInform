package com.example.campusinform;


import java.io.Serializable;

public class Course implements Serializable {

    private String courseName;
    private String classRoom;
    private int day;
    private int classStart;
    private int classEnd;

    public Course(String courseName, String classRoom, int day, int classStart, int classEnd) {
        this.courseName = courseName;
        this.classRoom = classRoom;
        this.day = day;
        this.classStart = classStart;
        this.classEnd = classEnd;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStart() {
        return classStart;
    }

    public void setStart(int classStart) {
        this.classEnd = classStart;
    }

    public int getEnd() {
        return classEnd;
    }

    public void setEnd(int classEnd) {
        this.classEnd = classEnd;
    }
}
