package com.example.circle_draw;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int RADIUS_COORDINATE_X =455 ;
    private static int RADIUS_COORDINATE_Y =110 ;
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
    private TextView resultText;
    private ArrayList<Integer> XXs=new ArrayList<>();
    private ArrayList<Integer> YYs=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        resultText=findViewById(R.id.resultText);
        finishButton=findViewById(R.id.submitButton);
        circleDrawLayout=findViewById(R.id.circleDraw);
        circleDrawLayout2=findViewById(R.id.circleDraw1);
        uid=getIntent().getStringExtra("UID");

    }

    @Override
    protected void onStart() {
        super.onStart();
        ErrorMediaPlayer = MediaPlayer.create(this, R.raw.error);
        if(ErrorMediaPlayer !=null)
        {
            ErrorMediaPlayer.setLooping(true);
        }
        findViewById(R.id.refreshButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circleDrawLayout.lastr==0&& circleDrawLayout.lastg==255&& circleDrawLayout.lastb==0) {
                    displaySecondCircle();
                }else{
                    showfinalDialogue(false);

                }
            }

        });

        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });
        findViewById(R.id.skipButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip();
            }
        });
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        displayFirstCircle();
    }

    private void home() {
        finishAffinity();
    }

    private void refresh() {
        if (circleDrawLayout.getVisibility() == View.VISIBLE) {
            circleDrawLayout.clear();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            Bitmap mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.draw_bg, options);
            Paint b = circleDrawLayout.getBrush();
            Path p = circleDrawLayout.getPath();
            Canvas canvas = new Canvas(mBackground);
            b.setColor(Color.WHITE);
            for (int i = 0; i < circleDrawLayout.totalPixelX.size(); i++) {
                p.moveTo(circleDrawLayout.totalPixelX.get(i), circleDrawLayout.totalPixelY.get(i));
                canvas.drawPath(p, b);
            }
            circleDrawLayout.setImageBitmap(mBackground);
            circleDrawLayout.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.draw_bg));
            circleDrawLayout.clear();
        }else if(circleDrawLayout2.getVisibility()==View.VISIBLE){
            circleDrawLayout2.clear();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            Bitmap mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.draw_bg, options);
            Paint b = circleDrawLayout2.getBrush();
            Path p = circleDrawLayout2.getPath();
            Canvas canvas = new Canvas(mBackground);
            b.setColor(Color.WHITE);
            for (int i = 0; i < circleDrawLayout2.totalPixelX.size(); i++) {
                p.moveTo(circleDrawLayout2.totalPixelX.get(i), circleDrawLayout2.totalPixelY.get(i));
                canvas.drawPath(p, b);
            }
            circleDrawLayout2.setImageBitmap(mBackground);
            circleDrawLayout2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.self));
            circleDrawLayout2.clear();

        }
    }

    private void back() {
        if(circleDrawLayout.getVisibility()== View.VISIBLE){
            Toast.makeText(getApplicationContext(),"You are at beginning",Toast.LENGTH_SHORT).show();
        }else if(circleDrawLayout2.getVisibility()==View.VISIBLE){
            displayFirstCircle();
        }
        refresh();

    }

    private void skip() {
        if(circleDrawLayout.getVisibility()== View.VISIBLE){
            displaySecondCircle();
        }else if(circleDrawLayout2.getVisibility()==View.VISIBLE){
            Toast.makeText(getApplicationContext(),"You are at end",Toast.LENGTH_SHORT).show();
        }
        refresh();

    }

    private void displayFirstCircle(){
        circleDrawLayout2.setVisibility(View.INVISIBLE);
        circleDrawLayout.setVisibility(View.VISIBLE);
        circleDrawLayout.getBrush().setColor(Color.GREEN);
        circleDrawLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){

                }

                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    circleDrawLayout.setBackground(getDrawable(R.drawable.draw_bg_1));
                    if(circleDrawLayout.isOutIndex()){
                        resultText.setTextColor(Color.RED);
                        resultText.setText("TRACED OUT ");
                        if(ErrorMediaPlayer != null)
                        {
                            ErrorMediaPlayer.start();
                        }

                    }else{
                        resultText.setTextColor(Color.BLACK);
                        resultText.setText("Going good.. Continue drawing circle..");
                        if(ErrorMediaPlayer != null && ErrorMediaPlayer.isPlaying())
                        {
                            ErrorMediaPlayer.pause();
                        }
                    }

                }
                if(event.getAction()==MotionEvent.ACTION_UP){

                    ErrorMediaPlayer.pause();
                    resultText.setTextColor(Color.BLACK);
                    resultText.setText("CLICK SUBMIT IF FINISHED ");
                    circleDrawLayout.setBackground(getDrawable(R.drawable.draw_bg));


                }

                return false;
            }
        });

    }


    private void displaySecondCircle() {
        circleDrawLayout.setVisibility(View.INVISIBLE);
        circleDrawLayout2.setVisibility(View.VISIBLE);
        circleDrawLayout2.setBackground(getDrawable(R.drawable.self));
        resultText.setTextColor(Color.BLACK);
        resultText.setText("Now draw circle yourself, with given radius.");
        circleDrawLayout2.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if(event.getAction()==MotionEvent.ACTION_DOWN){

              }

              if(event.getAction()==MotionEvent.ACTION_MOVE){
                  resultText.setTextColor(Color.BLACK);
                  resultText.setText("Continue drawing the circle...");
                  XXs.add(circleDrawLayout2.getXX());
                  YYs.add(circleDrawLayout2.getYY());

              }
              if(event.getAction()==MotionEvent.ACTION_UP){
                  resultText.setTextColor(Color.BLACK);
                  resultText.setText("Click submit if finished...");
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

              }else { showfinalDialogue(false);

              }
              }
      });
    }

    private void review() {
        temp=0;
        float percent=0;
        for (int i=0;i<XXs.size();i++) {
            radiuses.add(
                    (int) Math.sqrt(((XXs.get(i).intValue()
                    - CENTRE_X_COORDINATE) * (XXs.get(i).intValue() - CENTRE_X_COORDINATE)) +
                    ((YYs.get(i).intValue() - CENTRE_Y_COORDINATE)
                            * (YYs.get(i).intValue() - CENTRE_Y_COORDINATE))));
        }
        int fixed=(int) Math.sqrt(((RADIUS_COORDINATE_X
                - CENTRE_X_COORDINATE) * (RADIUS_COORDINATE_X - CENTRE_X_COORDINATE)) +
                (RADIUS_COORDINATE_Y - CENTRE_Y_COORDINATE)
                        * (RADIUS_COORDINATE_Y - CENTRE_Y_COORDINATE));

        for(int i=0;i<radiuses.size();i++){
            if(radiuses.get(i).intValue()<=(fixed+30)&&radiuses.get(i).intValue()>=(fixed-30)
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
