package com.example.circle_draw;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int RADIUS_COORDINATE_X =455 ;
    private static int RADIUS_COORDINATE_Y =125 ;
    private CircleDrawLayout circleDrawLayout,circleDrawLayout2;
    private Button           finishButton;
    private ArrayList<Integer> radiuses=new ArrayList<>();
    private int CENTRE_X_COORDINATE=455;
    private int CENTRE_Y_COORDINATE=455;
    private boolean blackIndex=false;
    private boolean whiteIndex=false;
    private int bullshit=0;
    private int temp=0;
    private MediaPlayer ErrorMediaPlayer;
    private String uid;

    private ArrayList<Integer> XXs=new ArrayList<>();
    private ArrayList<Integer> YYs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        ErrorMediaPlayer = MediaPlayer.create(this, R.raw.error);
        if(ErrorMediaPlayer !=null)
        {
            ErrorMediaPlayer.setLooping(true);
        }



        circleDrawLayout=(CircleDrawLayout)findViewById(R.id.circleDraw);
        finishButton=(Button)findViewById(R.id.finishbutton);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setTitle("Instructions")
                .setMessage("1.Draw the circle given quadrants.\n" +
                        "2.Click above button\n" +
                        "3.Draw circle yourself with given radius of left circle\n" +
                        "4.Click above button to get score.")
                .setIcon(R.drawable.ic_info_black_24dp)
                .addButton("Ok, I understand", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE,
                        CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        builder.show();

        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        finishButton.setBackgroundColor(Color.parseColor("#00574B"));

        finishButton.setText("DRAW THE SAME CIRCLE ON THE RIGHT WITH SAME RADIUS");

        circleDrawLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if(event.getAction()==MotionEvent.ACTION_DOWN){
                }

                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    circleDrawLayout.setBackground(getDrawable(R.drawable.demo));
                    if(circleDrawLayout.isOutIndex()){
                        finishButton.setBackgroundColor(Color.parseColor("#FF0000"));
                        finishButton.setText("TRACED OUT ");

                        if(ErrorMediaPlayer != null)
                        {
                            ErrorMediaPlayer.start();
                        }

                    }else{
                        finishButton.setBackgroundColor(Color.parseColor("#00574B"));
                        finishButton.setText("CONTINUE DRAWING THE CIRCLE "+""+circleDrawLayout.getXX()+","+circleDrawLayout.getYY());

                        if(ErrorMediaPlayer != null && ErrorMediaPlayer.isPlaying())
                        {
                            ErrorMediaPlayer.pause();
                        }
                    }

                }
                if(event.getAction()==MotionEvent.ACTION_UP){

                    ErrorMediaPlayer.pause();
                    finishButton.setBackgroundColor(Color.parseColor("#D81B60"));
                    finishButton.setText("CLICK HERE IF FINISHED");
                    circleDrawLayout.setBackground(getDrawable(R.drawable.draw_bg));


                }

                return false;
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circleDrawLayout.lastr==0&&
                        circleDrawLayout.lastg==255&&
                        circleDrawLayout.lastb==0) {
                    displaySecondCircle();
                }else{
                    showfinalDialogue(false);

                }
            }

        });

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                System.exit(0);
            }
        });
        uid=getIntent().getStringExtra("UID");
    }

    private void displaySecondCircle() {

        circleDrawLayout2=findViewById(R.id.circleDraw1);
        circleDrawLayout2.setVisibility(View.VISIBLE);
        circleDrawLayout2.setBackground(getDrawable(R.drawable.self));

        finishButton.setBackgroundColor(Color.parseColor("#00574B"));

        finishButton.setText("NOW DRAW THE CIRCLE BY YOURSELF, WITH GIVEN RADIUS");

      circleDrawLayout2.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {


              if(event.getAction()==MotionEvent.ACTION_DOWN){
               }

              if(event.getAction()==MotionEvent.ACTION_MOVE){
                  finishButton.setBackgroundColor(Color.parseColor("#00574B"));
                  finishButton.setText("CONTINUE DRAWING THE CIRCLE "+""+circleDrawLayout2.getXX()+","+circleDrawLayout2.getYY());
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
              if(circleDrawLayout2.lastr==0&&
                      circleDrawLayout2.lastg==255&&
                      circleDrawLayout2.lastb==0){
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
            if(radiuses.get(i).intValue()<=(fixed+25)&&radiuses.get(i).intValue()>=(fixed-25)
            ){
                temp++;
            }
        }
        Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
        intent.putExtra("VALUE",temp);
        intent.putExtra("TOTAL",radiuses.size());
        intent.putExtra("UID",uid);
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
