package com.example.circle_draw;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

    private static int RADIUS_COORDINATE_X =294 ;
    private static int RADIUS_COORDINATE_Y =74 ;
    private CircleDrawLayout circleDrawLayout,circleDrawLayout2;
    private Button           finishButton;
    private ArrayList<Integer> radiuses=new ArrayList<>();
    private int CENTRE_X_COORDINATE=303;
    private int CENTRE_Y_COORDINATE=296;
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
        finishButton.setText("DRAW THE SAME CIRCLE ON THE RIGHT WITH SAME RADIUS");

        circleDrawLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finishButton.setText("CLICK HERE IF FINISHED");
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                 circleDrawLayout.setBackground(getDrawable(R.drawable.draw_bg_1));
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                  circleDrawLayout.setBackground(getDrawable(R.drawable.draw_bg));
                }

                return false;
            }
        });

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
  */                  displaySecondCircle();
            }

        });


    }

    private void displaySecondCircle() {
        circleDrawLayout2=findViewById(R.id.circleDraw1);
        circleDrawLayout2.setVisibility(View.VISIBLE);

        circleDrawLayout2.setBackground(getDrawable(R.drawable.self));
        finishButton.setText("NOW DRAW THE CIRCLE BY YOURSELF, WITH GIVEN RADIUS");

      circleDrawLayout2.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {


              if(event.getAction()==MotionEvent.ACTION_DOWN){
                  RADIUS_COORDINATE_X=circleDrawLayout.getXX();
                  RADIUS_COORDINATE_Y=circleDrawLayout.getYY();
              }

              if(event.getAction()==MotionEvent.ACTION_MOVE){
                  finishButton.setBackgroundColor(Color.parseColor("#00574B"));
                  finishButton.setText("CONTINUE DRAWING THE CIRCLE");
                  XXs.add(circleDrawLayout2.getXX());
                  YYs.add(circleDrawLayout2.getYY());

              }
              if(event.getAction()==MotionEvent.ACTION_UP){
                  finishButton.setBackgroundColor(Color.parseColor("#D81B60"));
                  finishButton.setText("CLICK HERE IF FINISHED");


              }

              return false;
          }
      });

      finishButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(circleDrawLayout2.lastr==255&&
                      circleDrawLayout2.lastg==0&&
                      circleDrawLayout2.lastb==255){
                  review();

              }else {
                     showfinalDialogue(false);
                    }
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
        Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
        intent.putExtra("VALUE",temp);
        intent.putExtra("TOTAL",radiuses.size());
        startActivity(intent);


     }

    private void showfinalDialogue(boolean success) {
        // Create Alert using Builder
        if (success) {
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Success. Congrats you hit the score.")
                    .setIcon(R.drawable.ic_check_circle_black_24dp)
                    .addButton("Try your self", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE,
                            CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });

            builder.show();
        } else {
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setTitle("Please complete the circle and don't produce it outside its " +
                            "curve.")
                    .setIcon(R.drawable.ic_cancel_black_24dp)

                    .addButton("Redo", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                            CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                      dialog.dismiss();
                                      }
                            });

            builder.show();


        }
    }
}
