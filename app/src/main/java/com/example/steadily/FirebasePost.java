package com.example.steadily;

public class FirebasePost {
    public String name;
    public int date;

    public FirebasePost(){}

    public FirebasePost(String name){
        this.name = name;
    }
    public FirebasePost(int date){
        this.date = date;
    }

    public String getName(){ return name; }
    public int getDate(){ return date; }

}
