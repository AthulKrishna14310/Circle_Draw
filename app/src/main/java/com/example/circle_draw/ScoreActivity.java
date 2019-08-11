package com.example.circle_draw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity {
    private int temp=0;
    private int total=0;
    private Button scoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_score);

        temp=getIntent().getIntExtra("VALUE",0);
        total=getIntent().getIntExtra("TOTAL",0);
        scoreButton=findViewById(R.id.scoreButton);
        float percent=(temp*100)/total;
        scoreButton.setText(" "+percent+"%");
    }
}
