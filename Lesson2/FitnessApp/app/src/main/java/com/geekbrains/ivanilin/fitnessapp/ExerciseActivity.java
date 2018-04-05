package com.geekbrains.ivanilin.fitnessapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExerciseActivity extends AppCompatActivity {
    public static final String PREF_NAME = "com.geekbrains.ivanilin.fitnessapp";
    public static final String PREF_RECORD_COUNT = "fitnessapp_recordcount";
    public static final String PREF_RECORD_DATE = "fitnessapp_recorddate";
    SharedPreferences sharedPreferences;

    SeekBar tryCountValue;
    TextView tryCountTextView;
    TextView resultDateValue;
    TextView resultCountValue;
    Button saveResultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        initViews();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_RECORD_COUNT) && sharedPreferences.contains(PREF_RECORD_DATE)){
            resultCountValue.setText(sharedPreferences.getString(PREF_RECORD_COUNT, ""));
            resultDateValue.setText(sharedPreferences.getString(PREF_RECORD_DATE, ""));
        }

        tryCountValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tryCountTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((tryCountValue.getProgress() != 0)
                        && (tryCountValue.getProgress() > Integer.parseInt(resultCountValue.getText().toString()))){
                    resultCountValue.setText(String.valueOf(tryCountValue.getProgress()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    resultDateValue.setText("" + simpleDateFormat.format(new Date()));

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PREF_RECORD_COUNT, resultCountValue.getText().toString());
                    editor.putString(PREF_RECORD_DATE, resultDateValue.getText().toString());
                    editor.apply();
                }
            }
        });
    }

    private void initViews(){
        tryCountValue = (SeekBar)findViewById(R.id.try_count_seekbar);
        resultCountValue = (TextView)findViewById(R.id.result_count_value);
        resultDateValue = (TextView)findViewById(R.id.result_date_value);
        saveResultButton = (Button)findViewById(R.id.save_result);
        tryCountTextView = (TextView)findViewById(R.id.try_count_textview);
    }
}
