package com.muhsener98.exercises.exercise3;

public class Post {

    private int id;
    private String text;



    public Post(int id)  {
        this.id = id;
    }

    public Post(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
