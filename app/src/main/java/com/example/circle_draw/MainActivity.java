package com.example.circle_draw;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int RADIUS_COORDINATE_X =294 ;
    private static final int RADIUS_COORDINATE_Y =95 ;
    private CircleDrawLayout circleDrawLayout;
    private Button           finishButton;
    private ArrayList<Integer> radiuses=new ArrayList<>();
    private int CENTRE_X_COORDINATE=298;
    private int CENTRE_Y_COORDINATE=310;
    private boolean blackIndex=false;
    private boolean whiteIndex=false;
    private int bullshit=0;
    private int temp=0;
    
    private ArrayList<Integer> XXs=new ArrayList<>();
    private ArrayList<Integer> YYs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        circleDrawLayout=(CircleDrawLayout)findViewById(R.id.circleDraw);
        finishButton=(Button)findViewById(R.id.finishbutton);
        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        circleDrawLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    finishButton.setBackgroundColor(Color.parseColor("#D81B60"));
                    XXs.add(circleDrawLayout.getXX());
                    YYs.add(circleDrawLayout.getYY());

                }
                return false;
            }
        });
        finishButton.setText("DRAW A CIRCLE WITH RED DOT AS CENTRE");

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if (bullshit ==0) {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(MainActivity.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                            .setTitle("Try again")
                            .setIcon(R.drawable.ic_cancel_black_24dp)
                            .setTitle("Please draw slowly with pointed fingers.")
                            .addButton("Redo", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                                    CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            recreate();
                                        }
                                    });

                    builder.show();

                }
                else {
  */                 review();
                   Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
                   intent.putExtra("VALUE",temp);
                   intent.putExtra("TOTAL",radiuses.size());
                   startActivity(intent);
               }

        });


    }

    private void review() {
        temp=0;
        float percent=0;
        for (int i=0;i<XXs.size();i++) {
            radiuses.add((int) Math.sqrt(((XXs.get(i).intValue()
                    - CENTRE_X_COORDINATE) * (XXs.get(i).intValue() - CENTRE_X_COORDINATE)) +
                    ((YYs.get(i).intValue() - CENTRE_Y_COORDINATE)
                            * (YYs.get(i).intValue() - CENTRE_Y_COORDINATE))));
        }
        int fixed=(int) Math.sqrt(((RADIUS_COORDINATE_X
                - CENTRE_X_COORDINATE) * (RADIUS_COORDINATE_X - CENTRE_X_COORDINATE)) +
                (RADIUS_COORDINATE_Y - CENTRE_Y_COORDINATE)
                        * (RADIUS_COORDINATE_Y - CENTRE_Y_COORDINATE));

        for(int i=0;i<radiuses.size();i++){
            if(radiuses.get(i).intValue()<=(fixed+20)&&radiuses.get(i).intValue()>=(fixed-20)
            ){
                temp++;
            }
        }

     }
}
