package com.example.quizapp.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.activities.LoginActivity;
import com.example.quizapp.activities.SignupActivity;
import com.example.quizapp.models.Question;

import java.util.ArrayList;
import java.util.List;

public class option_adapter extends RecyclerView.Adapter<option_adapter.MyviewHolder> {
    public ArrayList<String> options = new ArrayList<>();
    Question question;



    public option_adapter(Question que) {
        this.options.add(que.option1);
        this.options.add(que.option2);
        this.options.add(que.option3);
        this.options.add(que.option4);
        this.question = que;
    }


    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.option_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        String optionee = options.get(position);
        holder.optionView.setText(optionee+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(optionee);
                notifyDataSetChanged();
            }
        }); //onclick

            if(question.getUserAnswer() == optionee){
                holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg);
            }
            else{
                holder.itemView.setBackgroundResource(R.drawable.option_item_bg);
            }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public  static  class MyviewHolder extends  RecyclerView.ViewHolder{

        TextView optionView;

        public MyviewHolder(View itemView) {
            super(itemView);
            optionView = itemView.findViewById(R.id.quiz_option);

        }
    }


}
