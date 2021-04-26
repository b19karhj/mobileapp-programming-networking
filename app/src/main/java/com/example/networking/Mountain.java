package com.example.networking;

public class Mountain {
    private String name;
    private String location;
    private int height;

    //constructor
    public Mountain(String n,String l,int h)
    {
        name=n;
        location=l;
        height=-1;

    }

    public String info(){
        String tmp=name+" is located in mountain range "+location+" and reaches "+height+"m above sea level."; //Lägger in datan från tidigare funktion till en sträng.
        return tmp;
    }

    @Override
    public String toString(){  //Tar och visar namnet istället för vad som annars står.
        return name;
    }
}
