package com.example.quizapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.LoginActivity;
import com.example.quizapp.activities.QuestionActivity;
import com.example.quizapp.activities.SignupActivity;
import com.example.quizapp.models.Quiz;
import com.example.quizapp.utils.ColorPicker;
import com.example.quizapp.utils.IconPicker;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyviewHolder>{
     ArrayList<Quiz> quizzes;
     Context context;

    public QuizAdapter(Context context, ArrayList<Quiz> quizzes) {

        this.quizzes = quizzes;
        this.context = context;
    }

    public QuizAdapter() {

    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new MyviewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.title.setText(quizzes.get(position).title);
        ColorPicker colorPicker = new ColorPicker();
        IconPicker iconPicker = new IconPicker();
        holder.card.setCardBackgroundColor(Color.parseColor(colorPicker.getColor()));
        holder.icon.setImageResource(iconPicker.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, QuestionActivity.class);
                myIntent.putExtra("DATE", quizzes.get(position).title);
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public  static  class MyviewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        ImageView icon;
        CardView card;
        public MyviewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quizTitle);
            icon = itemView.findViewById(R.id.quizIcon);
            card = itemView.findViewById(R.id.cardContainer);

        }
    }






}
