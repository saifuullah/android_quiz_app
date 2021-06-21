package com.example.quizapp.utils;

import com.example.quizapp.R;

import java.util.Random;

public class IconPicker {


        private  int currentIndex=0;
        int[] arrOfIcons = {
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
                R.drawable.ic_quiz_2,
                R.drawable.ic_quiz_3,
        };



        public int getIcon(){
            Random rand = new Random();
            int random_int = rand.nextInt(arrOfIcons.length-1);
            currentIndex = random_int;


            return arrOfIcons[currentIndex];
        }


    }
