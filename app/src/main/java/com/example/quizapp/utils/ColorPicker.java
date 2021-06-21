package com.example.quizapp.utils;

import java.util.Random;

public class ColorPicker {

    private  int currentIndex=0;
    String[] arrOfColors = {"#3E89DF",

            "#3685BC",

            "#D36280",

            "#E44F55",

            "#FA8056",

            "#818BCA",

            "#70659F",

            "#51BAB3",

            "#4FB66E",

            "#E3AD17",

            "#627991",

            "#EF8EAD"};



    public String getColor(){
        Random rand = new Random();
        int random_int = rand.nextInt(arrOfColors.length-1);
        currentIndex = random_int;


        return arrOfColors[currentIndex];
    }




}
