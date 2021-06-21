package com.example.quizapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.google.gson.Gson;

import java.util.Map;
import com.example.quizapp.R;

@RequiresApi(api = Build.VERSION_CODES.R)

public class ResultActivity extends AppCompatActivity {
    Map<String, Question> questions=Map.of();
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView=findViewById(R.id.txtScore);
        setUpViews();
    }

    private void setUpViews() {
        String quizData= getIntent().getStringExtra("QUIZ");
        Gson gson = new Gson();
        Quiz[] quiz=gson.fromJson(quizData,Quiz[].class);
        questions=quiz[0].questions;
        calculateScore();
        setAnwserView();
    }

    private void setAnwserView() {
        StringBuilder stringBuilder = new StringBuilder();
        int index=1;
        TextView textans;
        textans=findViewById(R.id.txtAnswer);
        for(int i=0; i<questions.size(); i++){
            Question question=questions.get("question"+index);
            index++;
            stringBuilder.append("<font color '#18206f'><b>Question<br/>"+question.description+"</b></font><br/><br/>");
            stringBuilder.append("<font color '#18206f'>Answer<br/>"+question.answer+"</font><br/><br/><br/>");
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            textans.setText(Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT));
        }
        else{
            textans.setText(Html.fromHtml(stringBuilder.toString()));
        }

    }

    private void calculateScore() {
        int score=0;
        int index=1;
        for(int i=0; i<questions.size(); i++)
        {
            Question question = questions.get("question"+index);
            index++;
            //Log.i("SAIF", "calculateScore: " + index);
            //Log.i("SAIF", "calculateScore: " + index);
            //Log.i("SAIF", "calculateScore: " + score);
            if(question.answer.equals(question.getUserAnswer() )){
                score+=10;
            }
        }
        textView.setText("Your Score: "+score);
    }
}