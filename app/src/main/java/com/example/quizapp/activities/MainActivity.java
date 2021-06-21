package com.example.quizapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.quizapp.R;
import com.example.quizapp.adapters.QuizAdapter;
import com.example.quizapp.models.Quiz;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {


    ActionBarDrawerToggle actionBar;
    NavigationView navigationView;
    private Object DrawerLayout;
    ArrayList<Quiz> quiz = new ArrayList<>();
    QuizAdapter adapter = new QuizAdapter(this, quiz);
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpFireStore();
        setUpviews();
        setUpRecyclerView();
        setUpDatePicker();

    }

    private void setUpDatePicker(){
        FloatingActionButton btn;
        btn = findViewById(R.id.btnDatePicker);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().build();


               datePicker.show(getSupportFragmentManager(), "Date Picker");
               datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                   @Override
                   public void onPositiveButtonClick(Object selection) {


                       Log.d("DatePicker1", datePicker.getHeaderText());

                       String pattern = "dd-MM-yyyy";
                       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                       String date = simpleDateFormat.format(selection);
                       Log.d("DatePicker1", date);

                       Intent myIntent = new Intent(MainActivity.this, QuestionActivity.class);
                       myIntent.putExtra("DATE", date);
                       startActivity(myIntent);

                   }
               }); //
                datePicker.addOnNegativeButtonClickListener(v1 -> {
                    Log.d("DatePicker", datePicker.getHeaderText());

                });

                datePicker.addOnCancelListener(dialog -> {
                    Log.d("DatePicker", "Date Picker was cancelled!");
                });
            }




        }); //onclick
    }
    private void setUpFireStore() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        String TAG = "SAIF";

            firebaseFirestore.collection("quizzes").addSnapshotListener((value, error) -> {
                if(value != null){
                    quiz.clear();
                    quiz.addAll(value.toObjects(Quiz.class));
                    Log.i("QUIZ_DATA", "setUpFireStore: " + quiz);
                    Log.i("MYDATA", value.toObjects(Quiz.class).toString());
                    adapter.notifyDataSetChanged();
                }
                if(value == null || error != null){
                    Toast.makeText(this, "Cannot retrieve Data", Toast.LENGTH_SHORT);
                }
            });





////
//        DocumentReference docRef = firebaseFirestore.collection("quizzes").document("ekcXNvwMb0R3QAO0yjJ0");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d("F2", "DocumentSnapshot data: " + document.getData());
//                        Quiz quiz = document.toObject(Quiz.class);
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//    });

//        DocumentReference doc = firebaseFirestore.collection("cities").document("ekcXNvwMb0R3QAO0yjJ0");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Quiz quiz = documentSnapshot.toObject(Quiz.class);
//            }
//        });



//        final DocumentReference doc = firebaseFirestore.collection("quizzes").document();
//
//        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.i(TAG, "Listen failed.", e);
//                    return;
//                }
//
//                if (snapshot != null && snapshot.exists()) {
//                    Log.i(TAG, "Current data: " + snapshot.getData());
//                } else {
//                    Log.i(TAG, "Current data: null");
//                }
//            }
//        });
    }







    private void setUpRecyclerView() {


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = findViewById(R.id.quizRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

    }

    public void setUpviews(){
        setUpDrawers();
}



public void setUpDrawers(){
    navigationView = findViewById(R.id.navigationView);
    navigationView.setNavigationItemSelectedListener(item -> {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        MainActivity.this.startActivity(intent);
        finish();
        return true;
    });
    Toolbar tb = findViewById(R.id.appBar);
    setSupportActionBar(tb);

    DrawerLayout dr = findViewById(R.id.mainDrawer);
    actionBar = new ActionBarDrawerToggle(this, dr, R.string.app_name, R.string.app_name);
    actionBar.syncState();

}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBar.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}