package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.adapters.option_adapter;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.DoubleStream;


@RequiresApi(api = Build.VERSION_CODES.R)
public class QuestionActivity extends AppCompatActivity {

    public ArrayList<Quiz> quizzes = new ArrayList<>();
    public Map<String, Question> questions= Map.of();
    FirebaseFirestore db;
    int index = 1;

    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setUpFireStore1();
        setUpEventListener();
        //ConnectToFireStore();

    }


    public void setUpEventListener(){

        Button btnNext;
        Button btnPrev;
        Button btnFinish;

        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnFinish = findViewById(R.id.btnSubmit);

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index -1 ;
                bindViews();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("QUESTIONS", "onClick: " + questions);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json=gson.toJson(quizzes);

                Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
                intent.putExtra("QUIZ", json);
                QuestionActivity.this.startActivity(intent);
                finish();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = index + 1 ;
                bindViews();
            }
        });




    }


    private void bindViews() {

        Button btnNext;
        Button btnPrev;
        Button btnFinish;

        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnFinish = findViewById(R.id.btnSubmit);

        btnNext.setVisibility(View.GONE);
        btnPrev.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);

        if (index == 1){
            btnNext.setVisibility(View.VISIBLE);
        }else if (index == questions.size() || questions.size() == 1){
            btnFinish.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }else{
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);

        }

        Question question = questions.get("question"+index);
        Log.i("DDDD1", "bindViews: " + questions);
        if (question != null){
            TextView description = findViewById(R.id.description);
            Log.i("DDDD", "bindViews: " + question);
            description.setText(question.description);
            option_adapter optionAdapter = new option_adapter(question);
            RecyclerView optionList = findViewById(R.id.optionList);
            optionList.setLayoutManager(new LinearLayoutManager(this));
            optionList.setAdapter(optionAdapter);
        }

    }


    private void setUpFireStore1(){

        db= FirebaseFirestore.getInstance();
        String date= getIntent().getStringExtra("DATE");
        if(date!=null){
            db.collection("quizzes")
                    .whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(queryDocumentSnapshots!=null && !queryDocumentSnapshots.isEmpty()){
                                quizzes.addAll(queryDocumentSnapshots.toObjects(Quiz.class));
                                Log.i("ZIA_KHAN", "onSuccess: "  + quizzes);
                                questions = quizzes.get(0).questions;
                                bindViews();
                            }
                            else{
                                Toast.makeText(QuestionActivity.this,"No Quiz for Given Date",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                                QuestionActivity.this.startActivity(intent);
                                finish();
                            }
                        }
                    });
        }

    }



    public void setUpFireStore() {


    firebaseFirestore = FirebaseFirestore.getInstance();
    String TAG = "DATAQUERY";
    String date = getIntent().getStringExtra("DATE");
      //  Log.i("DATEEE", "setUpFireStore: " + date);
    if (date != null) {
        firebaseFirestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot != null && !snapshot.isEmpty()) {
                        List<DocumentSnapshot> mylist =  snapshot.getDocuments();




                        Log.i("IAMSAIF", "setUpFireStore: " + mylist.get(0).getData());
                        quizzes.addAll(snapshot.toObjects(Quiz.class));
                        questions = quizzes.get(0).questions;
                        //Log.i("DATEEE", "setUpFireStore: " + snapshot.getDocuments());
                        bindViews();
                    }
                });

    }//if null
}//function end

    public void ConnectToFireStore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("quizzes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Set<String> keys=document.getData().keySet();
                                Log.i("MAIN_DOC_KEYS", "onComplete: "+ keys);


                                for (String key: keys) {
                                    Quiz quiz = new Quiz();
                                    if(key.equalsIgnoreCase("title")){
                                        quiz.setTitle((String) document.getData().get(key));
                                        Log.i("TITLE_KEY", "onComplete: " + quiz.getTitle());

                                    }


                                    if(key.equalsIgnoreCase("questions")){

                                        HashMap<String,Object> questions=(HashMap<String, Object>) document.getData().get(key);
                                        Log.i("QUESTIONS_LIST", "onComplete: " + questions);

                                        Set<String> questionsKeys=questions.keySet();
                                        Log.i("QUESTION_KEYS", "onComplete: " + questionsKeys);

                                        //Iterate all questions

                                        for (String qkey:questionsKeys) {
                                            Question ques = new Question();
                                            Log.i("QUES_KEY", " key: "+qkey);
                                            Log.i("QUES_KEY_DATE", " data:"+questions.get(qkey));

                                            //iterate each question so that we can get all parts of queston like description and options
                                            HashMap<String,String> question= (HashMap<String, String>) questions.get(qkey);
                                            Log.i("UN", "onComplete: " + question);
                                            Set<String> questonparts=question.keySet();


                                            Log.i("SAIF321", "onComplete: " + questonparts);

                                            Log.i("ATOM_BOMB", "\nQuestionENd\n ");
                                            for (String part:questonparts){
                                                Log.i("QUES_PARTS", "Key: "+part+" value: "+question.get(part));
                                                Log.i("SAIF1234", "onComplete: " + part);


                                                if (part.equalsIgnoreCase("description")){
                                                    ques.setDescription(question.get(part));
                                                    Log.i("ATOM_BOMB", "DES " + ques.getDescription());
                                                }
                                                else if (part.equalsIgnoreCase("answer")){
                                                    ques.setAnswer(question.get(part));
                                                    Log.i("ATOM_BOMB", "ANS: " + ques.getAnswer());
                                                }
                                                else if (part.equalsIgnoreCase("option1")){
                                                    ques.setOption1(question.get(part));
                                                    Log.i("ATOM_BOMB", "1: " + ques.getOption1());
                                                }
                                                else if (part.equalsIgnoreCase("option2")){
                                                    ques.setOption2(question.get(part));
                                                    Log.i("ATOM_BOMB", "2 " + ques.getOption2());
                                                }
                                                else if (part.equalsIgnoreCase("option3")){
                                                    ques.setOption3(question.get(part));
                                                    Log.i("ATOM_BOMB", "3 " + ques.getOption3());
                                                }
                                                else if (part.equalsIgnoreCase("option4")){
                                                    ques.setOption4(question.get(part));
                                                    Log.i("ATOM_BOMB", "4" + ques.getOption4());
                                                }
                                                Log.i("OBJECT_SAIF", "onComplete: " + ques);





                                            }
                                            Log.i("OBJECT_SAIF", "onComplete: " + ques);

                                            //quiz.setQuestions((Map<String, Quiz>) ques);
                                            Log.i("QUIZ_STR", "onComplete: " + ques);
                                        }
                                    }



                                }



                            }
                        } else {
                            Log.w("JALEEL", "Error getting documents.", task.getException());
                        }
                    }
                });

    }





}



