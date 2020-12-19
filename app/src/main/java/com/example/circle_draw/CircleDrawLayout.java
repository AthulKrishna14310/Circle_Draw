package com.example.circle_draw;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class CircleDrawLayout extends androidx.appcompat.widget.AppCompatImageView {

    public  ViewGroup.LayoutParams params;
    private Path path=new Path();
    private Paint brush=new Paint();
    private Bitmap bitmap;

    public Path getPath() {
        return path;
    }

    public Paint getBrush() {
        return brush;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private int touched=0;
    public int total=0;
    private boolean outIndex=false;
    private ArrayList<Integer> pixelX=new ArrayList<>();
    private ArrayList<Integer> pixelY=new ArrayList<>();

    public ArrayList<Float> totalPixelX=new ArrayList<>();
    public ArrayList<Float> totalPixelY=new ArrayList<>();
    private int X,Y;
    private ArrayList<Integer> taps=new ArrayList<>();
    public ArrayList<Integer> getPixelX() {
        return pixelX;
    }

    public int lastr,lastg,lastb;
    private Context context;

    public void setPixelX(ArrayList<Integer> pixelX) {
        this.pixelX = pixelX;
    }

    public ArrayList<Integer> getPixelY() {
        return pixelY;
    }

    public void setPixelY(ArrayList<Integer> pixelY) {
        this.pixelY = pixelY;
    }

    private int totalCoordinates=0;




    public CircleDrawLayout(Context context) {
        super(context);

        brush.setAntiAlias(true);
        brush.setColor(Color.GREEN);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(15f);

        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache(true);
        params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        this.context=context;
    }

    public CircleDrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        brush.setAntiAlias(true);
        brush.setColor(Color.GREEN);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(15f);

        this.setBackground(context.getDrawable(R.drawable.draw_bg));
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache(true);
        this.context=context;
    }

    public CircleDrawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        brush.setAntiAlias(true);
        brush.setColor(Color.GREEN);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(15f);

        this.setBackground(context.getDrawable(R.drawable.draw_bg));
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache(true);
        this.context=context;
    }

    public ArrayList<Integer> getTaps() {
        return taps;
    }

    public void setTaps(ArrayList<Integer> taps) {
        this.taps = taps;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX=event.getX();
        float pointY=event.getY();
        totalPixelX.add(pointX);
        totalPixelY.add(pointY);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX,pointY);

                return true;

            case MotionEvent.ACTION_MOVE:
                bitmap=this.getDrawingCache();

                if((int)event.getX()>0&&(int)event.getY()>0&&(int)event.getY()<bitmap.getHeight()
                &&(int)event.getX()<bitmap.getWidth()) {

                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    lastr=r;
                    lastg=g;
                    lastb=b;

                    total++;
                    X=(int) event.getX();
                    Y=(int) event.getY();

                    if(r==255&&g==0&&b==255){
                        outIndex=false;
                        touched++;

                        pixelX.add((int) event.getX());
                        pixelY.add( (int) event.getY());
                        taps.add(0);
                    }

                    else if(r==255&&g==255&&b==255){
                        outIndex=true;
                        taps.add(1);

                    }
                    else
                        {
                        }
                    path.lineTo(pointX, pointY);
                }
                break;


            default:
                return false;

        }
        postInvalidate();
        return false;
    }

    public boolean isOutIndex() {
        return outIndex;
    }

    public void setOutIndex(boolean outIndex) {
        this.outIndex = outIndex;
    }

    public void clear(){
        path=new Path();
        brush=new Paint();
        brush.setAntiAlias(true);
        brush.setColor(Color.GREEN);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(15f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
          canvas.drawPath(path,brush);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);

    }

    public int getTouched() {
        return touched;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setTouched(int touched) {
        this.touched = touched;
    }


    public int getXX() {
        return X;
    }


    public int getYY() {
        return Y;
    }

}
