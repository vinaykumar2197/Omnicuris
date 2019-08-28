package com.vinaykumar.omnicuris.adapter;

/**
 * Created by admin on 27-Aug-19.
 */

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaykumar.omnicuris.MainActivity;
import com.vinaykumar.omnicuris.R;
import com.vinaykumar.omnicuris.model.Questions;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> {

    private List<Questions> questionsList;
    private  Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestionName;
        public RadioGroup questionRadioGroup;
        public RadioButton radioOption1;
        public RadioButton radioOption2;
        public RadioButton radioOption3;
        public RadioButton radioOption4;

        public MyViewHolder(View view) {
            super(view);
            tvQuestionName = (TextView) view.findViewById(R.id.tv_question);
            questionRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
            radioOption1 = (RadioButton) view.findViewById(R.id.radio_option_1);
            radioOption2 = (RadioButton) view.findViewById(R.id.radio_option_2);
            radioOption3 = (RadioButton) view.findViewById(R.id.radio_option_3);
            radioOption4 = (RadioButton) view.findViewById(R.id.radio_option_4);

        }
    }


    public QuestionsAdapter(List<Questions> questionsList, Context context) {
        this.questionsList = questionsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Questions questions = questionsList.get(position);
        holder.tvQuestionName.setText(questions.getQuestion());
        holder.radioOption1.setText(questions.getOption_1());
        holder.radioOption2.setText(questions.getOption_2());
        holder.radioOption3.setText(questions.getOption_3());
        holder.radioOption4.setText(questions.getOption_4());


        holder.questionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                 //   Toast.makeText(context, rb.getText(), Toast.LENGTH_SHORT).show();
                    if (context instanceof MainActivity) {
                        ((MainActivity)context).noteAnswer(position, String.valueOf(rb.getText()));
                    }
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}