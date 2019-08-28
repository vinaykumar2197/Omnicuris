package com.vinaykumar.omnicuris.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.vinaykumar.omnicuris.MainActivity;
import com.vinaykumar.omnicuris.R;
import com.vinaykumar.omnicuris.adapter.QuestionsAdapter;
import com.vinaykumar.omnicuris.adapter.QuestionsSummaryAdapter;
import com.vinaykumar.omnicuris.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionSummary extends AppCompatActivity {

    private List<Questions> questionsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionsSummaryAdapter mAdapter;
    private HashMap<String,String > userAnswerList;
    String correctAns;
    private TextView tvScore ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_summary);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvScore = (TextView) findViewById(R.id.tv_score);

        questionsList = (ArrayList<Questions>) getIntent().getSerializableExtra("question_list");
        userAnswerList = (HashMap<String,String >) getIntent().getSerializableExtra("answer_list");
        correctAns = getIntent().getStringExtra("answer_count");


        tvScore.setText("Score : "+correctAns+"/"+questionsList.size());



        mAdapter = new QuestionsSummaryAdapter(questionsList,this,userAnswerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
