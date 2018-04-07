package com.geekbrains.ivanilin.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button exercise1Button;
    Button exercise2Button;
    Button exercise3Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        exercise1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_exercise);
                startActivity(new Intent(MainActivity.this, ExerciseActivity.class));
            }
        });
    }

    private void initView(){
        exercise1Button = (Button)findViewById(R.id.exercise_1_button);
        exercise2Button = (Button)findViewById(R.id.exercise_2_button);
        exercise3Button = (Button)findViewById(R.id.exercise_3_button);
    }
}
