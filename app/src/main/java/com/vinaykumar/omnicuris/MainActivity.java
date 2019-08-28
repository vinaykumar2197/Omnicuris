package com.vinaykumar.omnicuris;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vinaykumar.omnicuris.activity.QuestionSummary;
import com.vinaykumar.omnicuris.adapter.QuestionsAdapter;
import com.vinaykumar.omnicuris.model.Questions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Questions> questionsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private QuestionsAdapter mAdapter;
    private HashMap<String,String > userAnswerList;
    private TextView btSubmit;
    private int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btSubmit = (TextView) findViewById(R.id.bt_submit);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userAnswerList.size()==questionsList.size()) {
                    for (int i = 0; i < questionsList.size(); i++) {
                        if (questionsList.get(i).getAnswer().equalsIgnoreCase(userAnswerList.get(String.valueOf(i)))) {
                            count++;
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), QuestionSummary.class);
                    intent.putExtra("question_list", (Serializable) questionsList);
                    intent.putExtra("answer_list", (Serializable) userAnswerList);
                    intent.putExtra("answer_count", String.valueOf(count));
                    startActivity(intent);
                    finish();
//                    btSubmit.setText(String.valueOf(count));
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Kindly attempt all questions", Snackbar.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(),"Kindly attempt all questions",Toast.LENGTH_LONG).show();
                }
            }
        });

        mAdapter = new QuestionsAdapter(questionsList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        userAnswerList = new HashMap<>();

        String jsonData = loadsonFromRaw();

        prepareQuestionData(jsonData);
    }

    private void prepareQuestionData(String jsonData) {

        try {
            JSONObject jsonObject  = new JSONObject(jsonData);
            JSONArray jsonQuestionsArray = jsonObject.getJSONArray("data");
            for(int i = 0 ; i<jsonQuestionsArray.length();i++){
                JSONObject questionObject = jsonQuestionsArray.getJSONObject(i);

                String id = questionObject.getString("id");
                String question = questionObject.getString("question");
                String option_1 = questionObject.getString("option_1");
                String option_2 = questionObject.getString("option_2");
                String option_3 = questionObject.getString("option_3");
                String option_4 = questionObject.getString("option_4");
                String answer = questionObject.getString("answer");

                Questions questions = new Questions(id,question, option_1, option_2,option_3,option_4,answer);
                questionsList.add(questions);
            }
//            String  uniName = uniObject.getJsonString("name");
//            String uniURL = uniObject.getJsonString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }



//        Questions questions = new Questions("1","Where is Dublin?", "Ireland", "Canada","USA","CHINA","Ireland");
//        questionsList.add(questions);
//
//        questions = new Questions("2","Who is vice president of india?", "Manmohan Singh", "Hamid Ansari","Ramnath Kovind","Venkaiah Naidu","Venkaiah Naidu");
//        questionsList.add(questions);


        mAdapter.notifyDataSetChanged();
    }
//     private void prepareQuestionData() {
//                Questions questions = new Questions("1","Where is Dublin?", "Ireland", "Canada","USA","CHINA","Ireland");
//            questionsList.add(questions);
//
//            questions = new Questions("2","Who is vice president of india?", "Manmohan Singh", "Hamid Ansari","Ramnath Kovind","Venkaiah Naidu","Venkaiah Naidu");
//            questionsList.add(questions);
//
//
//            mAdapter.notifyDataSetChanged();
//        }

    public void noteAnswer(int index , String answer){
        userAnswerList.put(String.valueOf(index),answer);
    }

    public String loadsonFromRaw() {
        String json = null;
        try {

            InputStream is =  getApplicationContext().getResources().openRawResource(R.raw.questions);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}