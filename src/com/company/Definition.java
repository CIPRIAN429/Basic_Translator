package com.company;

public class Definition{
    String dict;
    String dictType;
    int year;
    String[] text;

    public Definition(String dict, String dictType, int year, String[] text){
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public int compareTo(Definition definition) {
        int compareyear = (definition).getYear();
        return this.year-compareyear;

    }
}
