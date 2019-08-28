package com.vinaykumar.omnicuris.adapter;

/**
 * Created by admin on 27-Aug-19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vinaykumar.omnicuris.MainActivity;
import com.vinaykumar.omnicuris.R;
import com.vinaykumar.omnicuris.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsSummaryAdapter extends RecyclerView.Adapter<QuestionsSummaryAdapter.MyViewHolder> {

    private List<Questions> questionsList;
    private HashMap<String,String > userAnswerList;
    private  Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestionName;
        public TextView tvAnswerUser;
        public TextView tvAnswerJson;
        public ImageView imageViewAnswer;


        public MyViewHolder(View view) {
            super(view);
            tvQuestionName = (TextView) view.findViewById(R.id.tv_question);
            tvAnswerUser = (TextView) view.findViewById(R.id.tv_answer_user);
            tvAnswerJson = (TextView) view.findViewById(R.id.tv_answer_json);
            imageViewAnswer = (ImageView) view.findViewById(R.id.iv_status);


        }
    }


    public QuestionsSummaryAdapter(List<Questions> questionsList, Context context,HashMap<String,String> userAnswerList) {
        this.questionsList = questionsList;
        this.context = context;
        this.userAnswerList = userAnswerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_row_summary, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Questions questions = questionsList.get(position);
        holder.tvQuestionName.setText(questions.getQuestion());


        if(questions.getAnswer().equalsIgnoreCase(userAnswerList.get(String.valueOf(position)))){
            holder.tvAnswerJson.setVisibility(View.GONE);
            holder.imageViewAnswer.setImageResource(R.drawable.ic_correct);
            holder.tvAnswerUser.setText("Answer : "+userAnswerList.get(String.valueOf(position)));
            holder.tvAnswerJson.setText(questions.getAnswer());
        }
        else{
            holder.tvAnswerJson.setVisibility(View.VISIBLE);
            holder.imageViewAnswer.setImageResource(R.drawable.ic_error);
            holder.tvAnswerUser.setText("Your Response : "+userAnswerList.get(String.valueOf(position)));
            holder.tvAnswerJson.setText("Correct Answer : "+questions.getAnswer());
        }


    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}